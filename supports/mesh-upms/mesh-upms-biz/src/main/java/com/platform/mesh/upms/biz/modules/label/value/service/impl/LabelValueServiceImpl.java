package com.platform.mesh.upms.biz.modules.label.value.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.label.value.domain.dto.LabelValueDTO;
import com.platform.mesh.upms.biz.modules.label.value.domain.vo.LabelValueVO;
import com.platform.mesh.upms.biz.modules.label.value.exception.LabelValueExceptionEnum;
import com.platform.mesh.upms.biz.modules.label.value.mapper.LabelValueMapper;
import com.platform.mesh.upms.biz.modules.label.value.service.ILabelValueService;
import com.platform.mesh.upms.biz.modules.label.value.service.manual.LabelValueServiceManual;
import com.platform.mesh.upms.biz.modules.label.value.domain.po.LabelValue;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 标签值
 * @author 蝉鸣
 */
@Service
public class LabelValueServiceImpl extends ServiceImpl<LabelValueMapper, LabelValue> implements ILabelValueService  {

    @Autowired
    private LabelValueServiceManual labelValueServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前标签值信息〉
     * @param valueId valueId  
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    @Override
    public LabelValueVO getValueInfoById(Long valueId) {
        LabelValue labelValue = this.getById(valueId);
        return labelValueServiceManual.getValueInfoById(labelValue);
    }

    /**
     * 功能描述:
     * 〈新增标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    @Override
    public LabelValueVO addValue(LabelValueDTO valueDTO) {
        LabelValue labelValue = BeanUtil.copyProperties(valueDTO, LabelValue.class);
        this.save(labelValue);
        return BeanUtil.copyProperties(labelValue, LabelValueVO.class);
    }

    /**
     * 功能描述:
     * 〈修改标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    @Override
    public LabelValueVO editValue(LabelValueDTO valueDTO) {
        if(ObjectUtil.isEmpty(valueDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(LabelValueDTO::getId);
            throw LabelValueExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        LabelValue labelValue = BeanUtil.copyProperties(valueDTO, LabelValue.class);
        this.updateById(labelValue);
        return BeanUtil.copyProperties(labelValue, LabelValueVO.class);
    }

    /**
     * 功能描述:
     * 〈删除标签值〉
     * @param valueId valueId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteValue(Long valueId) {
        
        return this.removeById(valueId);
    }
}