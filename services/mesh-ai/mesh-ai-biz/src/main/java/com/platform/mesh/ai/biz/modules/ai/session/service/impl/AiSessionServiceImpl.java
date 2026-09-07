package com.platform.mesh.ai.biz.modules.ai.session.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionPageDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.modules.ai.session.domain.vo.AiSessionVO;
import com.platform.mesh.ai.biz.modules.ai.session.exception.AiSessionExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.session.mapper.AiSessionMapper;
import com.platform.mesh.ai.biz.modules.ai.session.service.IAiSessionService;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI会话
 * @author 蝉鸣
 */
@Service
public class AiSessionServiceImpl extends ServiceImpl<AiSessionMapper, AiSession> implements IAiSessionService {

    /**
     * 功能描述:
     * 〈获取当前AI会话分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiSessionVO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AiSessionVO> selectPage(AiSessionPageDTO pageDTO) {
        MPage<AiSession> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiSession.class);
        return this.getBaseMapper().selectMPage(mPage, pageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前AI会话信息〉
     * @param sessionId sessionId
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    @Override
    public AiSessionVO getAiSessionById(Long sessionId) {
        AiSession aiSession = this.getById(sessionId);
        return BeanUtil.copyProperties(aiSession, AiSessionVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AI会话〉
     * @param sessionDTO sessionDTO
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    @Override
    public AiSessionVO addAiSession(AiSessionDTO sessionDTO) {
        AiSession aiSession = BeanUtil.copyProperties(sessionDTO, AiSession.class);
        aiSession.setAgentId(NumberConst.NUM_0.longValue());
        this.save(aiSession);
        return BeanUtil.copyProperties(aiSession, AiSessionVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AI会话〉
     * @param sessionDTO sessionDTO
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    @Override
    public AiSessionVO editAiSession(AiSessionDTO sessionDTO) {
        if(ObjectUtil.isEmpty(sessionDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiSessionDTO::getId);
            throw AiSessionExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiSession aiSession = BeanUtil.copyProperties(sessionDTO, AiSession.class);
        this.updateById(aiSession);
        return BeanUtil.copyProperties(aiSession, AiSessionVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AI会话〉
     * @param sessionId sessionId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiSession(Long sessionId) {
        return this.removeById(sessionId);
    }

}
