package com.platform.mesh.ai.biz.modules.ai.model.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.model.domain.dto.AiModelDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.domain.vo.AiModelVO;
import com.platform.mesh.ai.biz.modules.ai.model.exception.AiModelExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.model.mapper.AiModelMapper;
import com.platform.mesh.ai.biz.modules.ai.model.service.IAiModelService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI模型
 * @author 蝉鸣
 */
@Service
public class AiModelServiceImpl extends ServiceImpl<AiModelMapper, AiModel> implements IAiModelService {

    
    /**
     * 功能描述: 
     * 〈获取当前AI模型信息〉
     * @param modelId modelId
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    @Override
    public AiModelVO getAiModelById(Long modelId) {
        AiModel aiModel = this.getById(modelId);
        return BeanUtil.copyProperties(aiModel, AiModelVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AI模型〉
     * @param modelDTO modelDTO
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    @Override
    public AiModelVO addAiModel(AiModelDTO modelDTO) {
        AiModel aiModel = BeanUtil.copyProperties(modelDTO, AiModel.class);
        this.save(aiModel);
        return BeanUtil.copyProperties(aiModel, AiModelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AI模型〉
     * @param modelDTO modelDTO
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    @Override
    public AiModelVO editAiModel(AiModelDTO modelDTO) {
        if(ObjectUtil.isEmpty(modelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiModelDTO::getId);
            throw AiModelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiModel aiModel = BeanUtil.copyProperties(modelDTO, AiModel.class);
        this.updateById(aiModel);
        return BeanUtil.copyProperties(aiModel, AiModelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AI模型〉
     * @param modelId modelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiModel(Long modelId) {
        return this.removeById(modelId);
    }
}
