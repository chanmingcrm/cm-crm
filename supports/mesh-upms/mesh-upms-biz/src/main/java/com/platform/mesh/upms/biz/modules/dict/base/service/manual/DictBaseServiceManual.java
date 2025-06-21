package com.platform.mesh.upms.biz.modules.dict.base.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.dict.base.domain.vo.DictBaseVO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 字典基础
 * @author 蝉鸣
 */
@Service
public class DictBaseServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前${moduelDesc}信息〉
     * @param dictBase dictBase 
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    public DictBaseVO getBaseInfoById(DictBase dictBase) {
        DictBaseVO dictBaseVO = new DictBaseVO();
        if(ObjectUtil.isEmpty(dictBaseVO)){
            return dictBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(dictBase, dictBaseVO);
        return dictBaseVO;
    }

    /**
     * 功能描述:
     * 〈获取当前子项Ids信息〉
     * @param childDict childDict
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    public List<Long> getChildIds(List<DictBase> childDict) {
        if(ObjectUtil.isEmpty(childDict)){
            return CollUtil.newArrayList();
        }
        return childDict.stream().map(DictBase::getId).toList();
    }
}