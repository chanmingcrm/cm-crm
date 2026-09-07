package com.platform.mesh.ai.biz.modules.ai.prompttemp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.dto.AiPromptTempDTO;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.po.AiPromptTemp;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.vo.AiPromptTempVO;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.exception.AiPromptTempExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.mapper.AiPromptTempMapper;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.service.IAiPromptTempService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI提示词模板
 * @author 蝉鸣
 */
@Service
public class AiPromptTempServiceImpl extends ServiceImpl<AiPromptTempMapper, AiPromptTemp> implements IAiPromptTempService {

    
    /**
     * 功能描述: 
     * 〈获取当前AI提示词模板信息〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    @Override
    public AiPromptTempVO getAiPromptTempById(Long promptTempId) {
        AiPromptTemp aiPromptTemp = this.getById(promptTempId);
        return BeanUtil.copyProperties(aiPromptTemp, AiPromptTempVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AI提示词模板〉
     * @param promptTempDTO promptTempDTO
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    @Override
    public AiPromptTempVO addAiPromptTemp(AiPromptTempDTO promptTempDTO) {
        AiPromptTemp aiPromptTemp = BeanUtil.copyProperties(promptTempDTO, AiPromptTemp.class);
        this.save(aiPromptTemp);
        return BeanUtil.copyProperties(aiPromptTemp, AiPromptTempVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AI提示词模板〉
     * @param promptTempDTO promptTempDTO
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    @Override
    public AiPromptTempVO editAiPromptTemp(AiPromptTempDTO promptTempDTO) {
        if(ObjectUtil.isEmpty(promptTempDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiPromptTempDTO::getId);
            throw AiPromptTempExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiPromptTemp aiPromptTemp = BeanUtil.copyProperties(promptTempDTO, AiPromptTemp.class);
        this.updateById(aiPromptTemp);
        return BeanUtil.copyProperties(aiPromptTemp, AiPromptTempVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AI提示词模板〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiPromptTemp(Long promptTempId) {
        return this.removeById(promptTempId);
    }
}
