package com.platform.mesh.upms.biz.modules.label.base.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.label.base.domain.vo.LabelBaseVO;
import com.platform.mesh.upms.biz.modules.label.base.domain.po.LabelBase;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 标签基础
 * @author 蝉鸣
 */
@Service
public class LabelBaseServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前${moduelDesc}信息〉
     * @param labelBase labelBase 
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    public LabelBaseVO getBaseInfoById(LabelBase labelBase) {
        LabelBaseVO labelBaseVO = new LabelBaseVO();
        if(ObjectUtil.isEmpty(labelBaseVO)){
            return labelBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(labelBase, labelBaseVO);
        return labelBaseVO;
    }

}