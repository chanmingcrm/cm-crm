package com.platform.mesh.bpm.biz.soa.node.run;


import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;

/**
 * @description 节点运行工厂
 * @author 蝉鸣
 */
public interface NodeRunService<T> {

    /**
     * 功能描述:
     * 〈节点运行类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    NodeRunEnum nodeRun();

    /**
     * 功能描述:
     * 〈节点处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);
}
