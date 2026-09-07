package com.platform.mesh.bpm.biz.soa.process.pass;


import com.platform.mesh.core.enums.bpm.ProcessPassEnum;

/**
 * @description 流程通过工厂
 * @author 蝉鸣
 */
public interface ProcessPassService<T> {

    /**
     * 功能描述:
     * 〈过程通过类型〉
     * @return 正常返回:{@link ProcessPassEnum}
     * @author 蝉鸣
     */
    ProcessPassEnum processPass();

    /**
     * 功能描述:
     * 〈流程处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);
}
