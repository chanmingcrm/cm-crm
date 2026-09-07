package com.platform.mesh.ai.biz.modules.cc.webset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.group.mapper.CcGroupMapper;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationGuideDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationLeadDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcWebSetDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.po.CcWebSet;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.vo.CcWebSetVO;
import com.platform.mesh.ai.biz.modules.cc.webset.exception.CcWebSetExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.webset.mapper.CcWebSetMapper;
import com.platform.mesh.ai.biz.modules.cc.webset.service.ICcWebSetService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服人员
 * @author 蝉鸣
 */
@Service
public class CcWebSetServiceImpl extends ServiceImpl<CcWebSetMapper, CcWebSet> implements ICcWebSetService {

    @Resource
    private CcGroupMapper ccGroupMapper;

    
    /**
     * 功能描述: 
     * 〈获取当前客服页面配置〉
     * @param webSetId webSetId
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    @Override
    public CcWebSetVO getCcWebSetById(Long webSetId) {
        CcWebSet ccSession = this.getById(webSetId);
        return BeanUtil.copyProperties(ccSession, CcWebSetVO.class);
    }

    /**
     * 功能描述:
     * 〈获取访客端可公开读取的咨询引导配置〉
     * @param webSetId webSetId
     * @return 正常返回:{@link CcConsultationGuideDTO}
     * @author Codex
     */
    @Override
    public CcConsultationGuideDTO getConsultationGuideById(Long webSetId) {
        CcWebSet ccWebSet = baseMapper.selectPublicById(webSetId);
        if (ObjectUtil.isEmpty(ccWebSet)) {
            throw CcWebSetExceptionEnum.CONSULTATION_CONFIG_NOT_FOUND.getBaseException();
        }
        return CcConsultationGuideDTO.fromJson(ccWebSet.getConsultationGuide());
    }

    /**
     * 功能描述:
     * 〈保存官网咨询手机号到对应租户的客服会话〉
     * @param leadDTO leadDTO
     * @author Codex
     */
    @Override
    public void saveConsultationLead(CcConsultationLeadDTO leadDTO) {
        CcWebSet ccWebSet = baseMapper.selectPublicById(leadDTO.getWebSetId());
        if (ObjectUtil.isEmpty(ccWebSet)) {
            throw CcWebSetExceptionEnum.CONSULTATION_CONFIG_NOT_FOUND.getBaseException();
        }
        int updated = ccGroupMapper.saveConsultationLead(
                leadDTO.getGroupHash(),
                leadDTO.getPhone(),
                leadDTO.getIntent(),
                leadDTO.getSourcePage());
        if (updated == 0) {
            throw CcWebSetExceptionEnum.CONSULTATION_GROUP_NOT_FOUND.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈新增客服人员〉
     * @param webSetDTO webSetDTO
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    @Override
    public CcWebSetVO addCcWebSet(CcWebSetDTO webSetDTO) {
        CcWebSet ccSession = BeanUtil.copyProperties(webSetDTO, CcWebSet.class);
        this.save(ccSession);
        return BeanUtil.copyProperties(ccSession, CcWebSetVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客服人员〉
     * @param webSetDTO webSetDTO
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    @Override
    public CcWebSetVO editCcWebSet(CcWebSetDTO webSetDTO) {
        if(ObjectUtil.isEmpty(webSetDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcWebSetDTO::getId);
            throw CcWebSetExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CcWebSet ccSession = BeanUtil.copyProperties(webSetDTO, CcWebSet.class);
        this.updateById(ccSession);
        return BeanUtil.copyProperties(ccSession, CcWebSetVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客服人员〉
     * @param webSetId webSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcWebSet(Long webSetId) {
        return this.removeById(webSetId);
    }
}
