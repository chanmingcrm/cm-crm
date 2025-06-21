package com.platform.mesh.upms.biz.modules.msg.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBaseDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBasePageDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.base.exception.MsgBaseExceptionEnum;
import com.platform.mesh.upms.biz.modules.msg.base.mapper.MsgBaseMapper;
import com.platform.mesh.upms.biz.modules.msg.base.service.IMsgBaseService;
import com.platform.mesh.upms.biz.modules.msg.base.service.manual.MsgBaseServiceManual;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 消息
 * @author 蝉鸣
 */
@Service
public class MsgBaseServiceImpl extends ServiceImpl<MsgBaseMapper, MsgBase> implements IMsgBaseService  {

    @Autowired
    private MsgBaseServiceManual msgBaseServiceManual;


    /**
     * 功能描述:
     * 〈获取分页消息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<MsgBaseVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<MsgBaseVO> selectPage(MsgBasePageDTO pageDTO) {
        MPage<MsgBase> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, MsgBase.class);
        MPage<MsgBase> page = this.lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(pageDTO.getModuleId()), MsgBase::getModuleId, pageDTO.getModuleId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getDataId()), MsgBase::getDataId, pageDTO.getDataId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getMsgFlag()), MsgBase::getMsgFlag, pageDTO.getMsgFlag())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getMsgType()), MsgBase::getMsgType, pageDTO.getMsgType())
                .page(baseMPage);
        return MPageUtil.convertToVO(page, MsgBaseVO.class);
    }

    /**
     * 功能描述: 
     * 〈获取当前消息信息〉
     * @param baseId baseId  
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    @Override
    public MsgBaseVO getBaseInfoById(Long baseId) {
        MsgBase msgBase = this.getById(baseId);
        return msgBaseServiceManual.getBaseInfoById(msgBase);
    }

    /**
     * 功能描述:
     * 〈新增消息〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBase(MsgBaseDTO baseDTO) {
        MsgBase msgBase = BeanUtil.copyProperties(baseDTO, MsgBase.class);
        //保存消息信息
        this.save(msgBase);
        //保存消息接收人信息
        msgBaseServiceManual.saveMsgUser(msgBase.getId(),baseDTO.getMsgUserIds());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除消息〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteBase(Long baseId) {
        
        return this.removeById(baseId);
    }

}