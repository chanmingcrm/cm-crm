package com.platform.mesh.upms.biz.modules.label.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.label.base.domain.dto.LabelBaseDTO;
import com.platform.mesh.upms.biz.modules.label.base.domain.vo.LabelBaseVO;
import com.platform.mesh.upms.biz.modules.label.base.exception.LabelBaseExceptionEnum;
import com.platform.mesh.upms.biz.modules.label.base.mapper.LabelBaseMapper;
import com.platform.mesh.upms.biz.modules.label.base.service.ILabelBaseService;
import com.platform.mesh.upms.biz.modules.label.base.service.manual.LabelBaseServiceManual;
import com.platform.mesh.upms.biz.modules.label.base.domain.po.LabelBase;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 标签基础
 * @author 蝉鸣
 */
@Service
public class LabelBaseServiceImpl extends ServiceImpl<LabelBaseMapper, LabelBase> implements ILabelBaseService  {

    @Autowired
    private LabelBaseServiceManual labelBaseServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前标签基础信息〉
     * @param baseId baseId  
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    @Override
    public LabelBaseVO getBaseInfoById(Long baseId) {
        LabelBase labelBase = this.getById(baseId);
        return labelBaseServiceManual.getBaseInfoById(labelBase);
    }

    /**
     * 功能描述:
     * 〈新增标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    @Override
    public LabelBaseVO addBase(LabelBaseDTO baseDTO) {
        LabelBase labelBase = BeanUtil.copyProperties(baseDTO, LabelBase.class);
        this.save(labelBase);
        return BeanUtil.copyProperties(labelBase, LabelBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈修改标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    @Override
    public LabelBaseVO editBase(LabelBaseDTO baseDTO) {
        if(ObjectUtil.isEmpty(baseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(LabelBaseDTO::getId);
            throw LabelBaseExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        LabelBase labelBase = BeanUtil.copyProperties(baseDTO, LabelBase.class);
        this.updateById(labelBase);
        return BeanUtil.copyProperties(labelBase, LabelBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈删除标签基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteBase(Long baseId) {
        
        return this.removeById(baseId);
    }
}
