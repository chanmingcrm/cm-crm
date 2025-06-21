package com.platform.mesh.bpm.biz.logic.ref;


import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;

import java.util.List;

/**
 * @description 逻辑关系工厂
 * @author 蝉鸣
 */
public interface LogicRefService {

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    LogicRefEnum refType();

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param paramOne paramOne
     * @param paramTwo paramTwo
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean compare(List<String> paramOne,List<String> paramTwo);
}
