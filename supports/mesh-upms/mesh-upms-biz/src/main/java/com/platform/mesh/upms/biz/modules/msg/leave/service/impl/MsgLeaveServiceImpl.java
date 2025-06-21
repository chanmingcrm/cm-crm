package com.platform.mesh.upms.biz.modules.msg.leave.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.dto.MsgLeaveDTO;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.dto.MsgLeavePageDTO;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.po.MsgLeave;
import com.platform.mesh.upms.biz.modules.msg.leave.domain.vo.MsgLeaveVO;
import com.platform.mesh.upms.biz.modules.msg.leave.exception.MsgLeaveExceptionEnum;
import com.platform.mesh.upms.biz.modules.msg.leave.mapper.MsgLeaveMapper;
import com.platform.mesh.upms.biz.modules.msg.leave.service.IMsgLeaveService;
import com.platform.mesh.upms.biz.modules.msg.leave.service.manual.MsgLeaveServiceManual;
import com.platform.mesh.utils.http.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 留言消息
 * @author 蝉鸣
 */
@Service
public class MsgLeaveServiceImpl extends ServiceImpl<MsgLeaveMapper, MsgLeave> implements IMsgLeaveService {

    @Autowired
    private MsgLeaveServiceManual msgBaseServiceManual;


    /**
     * 功能描述:
     * 〈获取分页留言消息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgLeaveVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<MsgLeaveVO> selectPage(MsgLeavePageDTO pageDTO) {
        MPage<MsgLeave> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, MsgLeave.class);
        MPage<MsgLeave> page = this.lambdaQuery().orderByDesc(MsgLeave::getUpdateTime).page(baseMPage);
        return MPageUtil.convertToVO(page, MsgLeaveVO.class);
    }

    /**
     * 功能描述:
     * 〈新增留言消息〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link MsgLeaveVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBase(MsgLeaveDTO baseDTO) {
        //校验防刷
        String hostIp = IpUtil.getHostIp();
        //查看redis中缓存是否存在
        Boolean phoneKey = msgBaseServiceManual.getRedisKey(baseDTO.getPhone());
        Boolean ipKey = msgBaseServiceManual.getRedisKey(hostIp);
        //如果存在则提示请勿重复操作
        if(!(phoneKey && ipKey)){
            throw MsgLeaveExceptionEnum.ADD_ADD_LIMIT.getBaseException();
        }
        //如果redis中缓存不存在,查询数据库是否存在，如果存在则留言次数+1
        MsgLeave one = this.lambdaQuery().eq(MsgLeave::getPhone, baseDTO.getPhone()).one();
        if(ObjectUtil.isNotEmpty(one)){
            one.setLeaveNum(one.getLeaveNum() + NumberConst.NUM_1);
            this.updateById(one);
            return Boolean.TRUE;
        }
        //如果不存在则新增
        MsgLeave msgBase = BeanUtil.copyProperties(baseDTO, MsgLeave.class);
        //获取IP信息
        msgBase.setIpAgent(IpUtil.getIpAgent());
        msgBase.setIpAddr(hostIp);
        msgBase.setDivideFlag(YesOrNoEnum.INIT.getValue());
        msgBase.setLeaveNum(NumberConst.NUM_1);
        //保存留言消息信息
        this.save(msgBase);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除留言消息〉
     * @param leaveId leaveId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteBase(Long leaveId) {
        return this.removeById(leaveId);
    }

}