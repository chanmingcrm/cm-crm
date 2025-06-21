package com.platform.mesh.bpm.biz.logic.type;


import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;

/**
 * @description 逻辑类型工厂
 * @author 蝉鸣
 */
public interface LogicTypeService {

    /**
     * 功能描述:
     * 〈逻辑运算类型〉
     * @return 正常返回:{@link LogicTypeEnum}
     * @author 蝉鸣
     */
    LogicTypeEnum logicType();

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param oneParam oneParam
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean compare(Boolean ...oneParam);
}
