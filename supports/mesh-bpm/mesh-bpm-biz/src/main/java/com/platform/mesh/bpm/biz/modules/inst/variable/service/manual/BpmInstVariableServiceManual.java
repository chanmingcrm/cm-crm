package com.platform.mesh.bpm.biz.modules.inst.variable.service.manual;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.logic.ref.LogicRefService;
import com.platform.mesh.bpm.biz.logic.ref.factory.LogicRefFactory;
import com.platform.mesh.bpm.biz.logic.type.LogicTypeService;
import com.platform.mesh.bpm.biz.logic.type.factory.LogicTypeFactory;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.enums.InstLinePassEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.process.service.manual.BpmInstProcessServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.po.BpmInstVarRefer;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.service.IBpmInstVarReferService;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.domain.po.BpmInstVarValue;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.IBpmInstVarValueService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstVariableServiceManual {

    private final static Logger log = LoggerFactory.getLogger(BpmInstProcessServiceManual.class);

    @Autowired
    LogicRefFactory logicRefFactory;

    @Autowired
    LogicTypeFactory logicTypeFactory;

    @Autowired
    IBpmInstVarReferService bpmInstVarReferService;

    @Autowired
    IBpmInstVarValueService bpmInstVarValueService;

    /**
     * 功能描述:
     * 〈通过变量校验当前线是否可以通过〉
     * @param instLine instLine
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean checkVariableByLine(BpmInstLine instLine) {
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        BpmInstNode bpmInstNode = instNodeService.getById(instLine.getInstInNodeId());
        //过滤不需要校验的节点
        if(checkByNodeType(bpmInstNode)){
            return Boolean.TRUE;
        }
        //过滤不需要校验的线
        if(checkByLineInPass(instLine)){
            return Boolean.TRUE;
        }
        //执行需要校验的
        //获取变量参照值
        List<BpmInstVarRefer> instVarRefers = bpmInstVarReferService.selectVarReferByInstLineId(instLine.getId());
        Map<Long, List<BpmInstVarRefer>> referMap = getReferMap(instVarRefers);
        List<Tree<Long>> referTree = buildTree(instVarRefers);
        //获取变量实际值
        List<BpmInstVarValue> instVarValues = bpmInstVarValueService.selectVarValueByInstNodeId(instLine.getInstInNodeId());
        Map<String, BpmInstVarValue> valueMap = getValueMap(instVarValues);
        //构建对比表达式
        return buildExpression(referTree,referMap,valueMap);
    }

    /**
     * 功能描述:
     * 〈通过节点类型是否可以通过〉
     * @param bpmInstNode flowInstNode
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean checkByNodeType(BpmInstNode bpmInstNode) {
        NodeTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeTypeEnum.class, bpmInstNode.getNodeFlag());
        return switch (enumByValue) {
            case GATEWAY_NODE,START_NODE, BASE_NODE, END_NODE -> Boolean.TRUE;
            case AUDIT_NODE, TIME_NODE -> Boolean.FALSE;
        };
    }

    /**
     * 功能描述:
     * 〈通过节点类型是否可以通过〉
     * @param instLine instLine
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean checkByLineInPass(BpmInstLine instLine) {
        //如果IN_PASS为TRUE 则默认可以直接通过改线
        //如果IN_PASS为FALSE 则需要变量参数校验
        //如果OUT_PASS为TRUE 则示为直接或者间接通过了改线
        //如果OUT_PASS为FALSE 则示为没有通过该线进入下一节点
        InstLinePassEnum enumByValue = BaseEnum.getEnumByValue(InstLinePassEnum.class, instLine.getInstInLinePass());
        if(InstLinePassEnum.PASS.equals(enumByValue)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    /**
     * 功能描述:
     * 〈获取线变量参照〉
     * @param instVarRefers instVarRefers
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<Long,List<BpmInstVarRefer>> getReferMap(List<BpmInstVarRefer> instVarRefers){
        return instVarRefers.stream().collect(Collectors.groupingBy(BpmInstVarRefer::getId));
    }

    /**
     * 功能描述:
     * 〈获取节点下变量值〉
     * @param instVarValues instVarValues
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, BpmInstVarValue> getValueMap(List<BpmInstVarValue> instVarValues){
        return instVarValues.stream().collect(Collectors.toMap(BpmInstVarValue::getVariableName, Function.identity(),(v1, v2)->v2));

    }

    /**
     * 功能描述:
     * 〈构建参照树形结构〉
     * @param varRefer varRefer
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public List<Tree<Long>> buildTree(List<BpmInstVarRefer> varRefer){
        //构建树形结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey(ObjFieldUtil.getFieldName(BpmInstVarRefer::getId));
        treeNodeConfig.setParentIdKey(ObjFieldUtil.getFieldName(BpmInstVarRefer::getParentId));
        treeNodeConfig.setChildrenKey("children");
        treeNodeConfig.setNameKey(ObjFieldUtil.getFieldName(BpmInstVarRefer::getVariableName));
        return TreeUtil.build(varRefer, NumberConst.NUM_0.longValue(), treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setName(treeNode.getVariableName());
            tree.setParentId(treeNode.getParentId());
        });

    }

    /**
     * 功能描述:
     * 〈构建表达式〉
     * @param varList varList
     * @param refMap refMap
     * @param valueMap valueMap
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean buildExpression(List<Tree<Long>> varList, Map<Long,List<BpmInstVarRefer>> refMap, Map<String, BpmInstVarValue> valueMap){
        Boolean result = null;
        if(CollUtil.isEmpty(varList) || CollUtil.isEmpty(refMap) || CollUtil.isEmpty(valueMap)){
            return Boolean.FALSE;
        }
        //同层级的条件构造
        for (Tree<Long> longTree : varList) {
            if(CollUtil.isNotEmpty(longTree.getChildren())){
                //子级条件构造
                result = buildExpression(longTree.getChildren(),refMap,valueMap);
            }else{
                //获取对照值
                List<BpmInstVarRefer> instVarRefers = refMap.get(longTree.getId());
                List<String> refers = instVarRefers.stream().map(BpmInstVarRefer::getVariableRefer).toList();
                //获取实际值
                String variableName = longTree.getName().toString();

                BpmInstVarValue instVarValue = valueMap.get(variableName);
                List<String> values = CollUtil.newArrayList();
                if(ObjectUtil.isNotEmpty(instVarValue)){
                    values = CollUtil.newArrayList(instVarValue.getVariableValue());
                }
                //获取参数逻辑关系
                BpmInstVarRefer varRefer = CollUtil.getFirst(instVarRefers);
                Integer ref = varRefer.getVariableRef();
                Integer type = varRefer.getVariableType();
                Boolean parseType = this.compareRef(ref, refers, values);
                //获取参数逻辑类型
                if(ObjectUtil.isEmpty(result)){
                    result = this.compareType(type,parseType);
                }else{
                    result = this.compareType(type,result,parseType);
                }
            }
        }
        return result;

    }

    /**
     * 功能描述:
     * 〈根据逻辑类型对比参数〉
     * @param type type
     * @param params params
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean compareType(Integer type ,Boolean ...params){
        LogicTypeEnum enumByValue = BaseEnum.getEnumByValue(LogicTypeEnum.class, type);
        LogicTypeService logicTypeService = logicTypeFactory.getLogicTypeService(enumByValue);
        return logicTypeService.compare(params);
    }

    /**
     * 功能描述:
     * 〈根据逻辑关系对比参数〉
     * @param type type
     * @param sourceData sourceData
     * @param targetData targetData
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean compareRef(Integer type , List<String> sourceData, List<String> targetData){
        LogicRefEnum enumByValue = BaseEnum.getEnumByValue(LogicRefEnum.class, type);
        LogicRefService logicRefService = logicRefFactory.getLogicRefService(enumByValue);
        return logicRefService.compare(sourceData, targetData);
    }
}

