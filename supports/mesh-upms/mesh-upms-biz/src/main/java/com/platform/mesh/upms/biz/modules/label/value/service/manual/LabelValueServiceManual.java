package com.platform.mesh.upms.biz.modules.label.value.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.label.value.domain.vo.LabelValueVO;
import com.platform.mesh.upms.biz.modules.label.value.domain.po.LabelValue;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 标签值
 * @author 蝉鸣
 */
@Service
public class LabelValueServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前${moduelDesc}信息〉
     * @param labelValue labelValue 
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    public LabelValueVO getValueInfoById(LabelValue labelValue) {
        LabelValueVO labelValueVO = new LabelValueVO();
        if(ObjectUtil.isEmpty(labelValueVO)){
            return labelValueVO;
        }
        //转换VO
        BeanUtil.copyProperties(labelValue, labelValueVO);
        return labelValueVO;
    }

}