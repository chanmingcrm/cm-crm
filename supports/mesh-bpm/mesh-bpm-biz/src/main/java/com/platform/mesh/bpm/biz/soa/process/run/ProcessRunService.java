package com.platform.mesh.bpm.biz.soa.process.run;


import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;

/**
 * @description 节点工厂
 * @author 蝉鸣
 */
public interface ProcessRunService<T> {

    /**
     * 功能描述:
     * 〈过程运行类型〉
     * @return 正常返回:{@link ProcessRunEnum}
     * @author 蝉鸣
     */
    ProcessRunEnum processRun();

    /**
     * 功能描述:
     * 〈流程处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);
}
