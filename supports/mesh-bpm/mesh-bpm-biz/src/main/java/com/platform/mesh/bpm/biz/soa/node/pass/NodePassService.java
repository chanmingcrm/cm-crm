package com.platform.mesh.bpm.biz.soa.node.pass;


import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;

/**
 * @description 节点通过工厂
 * @author 蝉鸣
 */
public interface NodePassService<T> {

    /**
     * 功能描述:
     * 〈节点通过类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    NodePassEnum nodePass();

    /**
     * 功能描述:
     * 〈节点处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);
}
