package com.platform.mesh.ai.biz.modules.cc.msg.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgPageDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.vo.CcSessionMsgVO;
import com.platform.mesh.ai.biz.modules.cc.msg.exception.CcSessionMsgExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.msg.mapper.CcSessionMsgMapper;
import com.platform.mesh.ai.biz.modules.cc.msg.service.ICcSessionMsgService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服会话
 * @author 蝉鸣
 */
@Service
public class CcSessionMsgServiceImpl extends ServiceImpl<CcSessionMsgMapper, CcSessionMsg> implements ICcSessionMsgService {


    /**
     * 功能描述:
     * 〈获取分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    @Override
    public PageVO<CcSessionMsgVO> selectPage(CcSessionMsgPageDTO pageDTO) {
        MPage<CcSessionMsg> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcSessionMsg.class);
        
        MPage<CcSessionMsg> msgMPage = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(pageDTO.getGroupHash()), CcSessionMsg::getGroupHash, pageDTO.getGroupHash())
                .orderByDesc(CcSessionMsg::getCreateTime)
                .page(mPage);
        
        return MPageUtil.convertToVO(msgMPage, CcSessionMsgVO.class);
    }

    /**
     * 功能描述: 
     * 〈获取当前客服会话信息〉
     * @param sessionId sessionId
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    @Override
    public CcSessionMsgVO getCcSessionMsgById(Long sessionId) {
        CcSessionMsg ccSessionMsg = this.getById(sessionId);
        return BeanUtil.copyProperties(ccSessionMsg, CcSessionMsgVO.class);
    }

    /**
     * 功能描述:
     * 〈新增客服会话〉
     * @param sessionMsgDTO sessionDTO
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    @Override
    public CcSessionMsgVO addCcSessionMsg(CcSessionMsgDTO sessionMsgDTO) {
        CcSessionMsg ccSessionMsg = BeanUtil.copyProperties(sessionMsgDTO, CcSessionMsg.class);
        this.save(ccSessionMsg);
        return BeanUtil.copyProperties(ccSessionMsg, CcSessionMsgVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客服会话〉
     * @param sessionMsgDTO sessionDTO
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    @Override
    public CcSessionMsgVO editCcSessionMsg(CcSessionMsgDTO sessionMsgDTO) {
        if(ObjectUtil.isEmpty(sessionMsgDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcSessionMsgDTO::getId);
            throw CcSessionMsgExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CcSessionMsg ccSessionMsg = BeanUtil.copyProperties(sessionMsgDTO, CcSessionMsg.class);
        this.updateById(ccSessionMsg);
        return BeanUtil.copyProperties(ccSessionMsg, CcSessionMsgVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客服会话〉
     * @param sessionMsgId sessionMsgId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcSessionMsg(Long sessionMsgId) {
        return this.removeById(sessionMsgId);
    }
}
