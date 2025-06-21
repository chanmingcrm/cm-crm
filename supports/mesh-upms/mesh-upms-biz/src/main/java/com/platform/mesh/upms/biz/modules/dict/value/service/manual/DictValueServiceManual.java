package com.platform.mesh.upms.biz.modules.dict.value.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import com.platform.mesh.upms.biz.modules.dict.base.service.IDictBaseService;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValuePageDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.po.DictValue;
import com.platform.mesh.upms.biz.modules.dict.value.domain.vo.DictValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 字典值
 * @author 蝉鸣
 */
@Service
public class DictValueServiceManual{

    @Autowired
    private IDictBaseService dictBaseService;


    /**
     * 功能描述:
     * 〈获取需要查询的字典〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    public List<Long> getDictIds(DictValuePageDTO pageDTO) {
        List<Long> dictIds = CollUtil.newArrayList(pageDTO.getDictId());
        if(YesOrNoEnum.YES.getValue().equals(pageDTO.getNeedChild())){
            List<DictBase> childDict = dictBaseService.getChildDict(pageDTO.getDictId());
            if (CollUtil.isNotEmpty(childDict)) {
                dictIds.addAll(childDict.stream().map(DictBase::getId).toList());
            }
        }
        return dictIds;
    }
    
    /**
     * 功能描述: 
     * 〈获取当前字典值信息〉
     * @param dictValue dictValue 
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    public DictValueVO getValueInfoById(DictValue dictValue) {
        DictValueVO dictValueVO = new DictValueVO();
        if(ObjectUtil.isEmpty(dictValueVO)){
            return dictValueVO;
        }
        //转换VO
        BeanUtil.copyProperties(dictValue, dictValueVO);
        return dictValueVO;
    }

}