package com.platform.mesh.bpm.biz.modules.temp.process.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.po.BpmAllGroup;
import com.platform.mesh.bpm.biz.modules.group.allgroup.service.IBpmAllGroupService;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.po.BpmAllGroupRel;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.IBpmAllGroupRelService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.factory.ProcessTypeFactory;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmTempProcessServiceManual {

    @Autowired
    private IBpmAllGroupService bpmAllGroupService;

    @Autowired
    private IBpmAllGroupRelService bpmAllGroupRelService;

    @Autowired
    private ProcessTypeFactory processTypeFactory;

    /**
     * 功能描述:
     * 〈添加流程模板信息〉
     * @param bpmTempProcessDesignDTO bpmTempProcessAddDTO
     * @return 正常返回:{@link BpmTempProcess}
     */
    public BpmTempProcess designProcessTemp(BpmTempProcessDesignDTO bpmTempProcessDesignDTO) {
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //初始化实例节点
            processTypeService.addTemp(bpmTempProcessDesignDTO);
        }
        //返回流程实例
        return BeanUtil.copyProperties(bpmTempProcessDesignDTO.getProcessDTO(), BpmTempProcess.class);
    }

    /**
     * 功能描述:
     * 〈添加流程模板信息〉
     * @param bpmTempProcessDesignVO bpmTempProcessDesignVO
     * @return 正常返回:{@link BpmTempProcess}
     */
    public BpmTempProcessDesignVO getProcessTemp(BpmTempProcessDesignVO bpmTempProcessDesignVO) {
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //初始化实例节点
            processTypeService.getTemp(bpmTempProcessDesignVO);
        }
        //返回流程实例VO
        return bpmTempProcessDesignVO;
    }

    /**
     * 功能描述:
     * 〈删除流程模板信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link BpmTempProcess}
     */
    public Boolean delProcessTemp(Long tempProcessId) {
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //删除流程模板
            processTypeService.delTemp(tempProcessId);
        }
        //结果
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈校验流程模板是否有运行中的实例〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link Boolean}
     */
    public Boolean checkRunProcessTemp(Long tempProcessId) {
        IBpmInstProcessService bpmInstProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        return bpmInstProcessService.lambdaQuery()
                .eq(BpmInstProcess::getTempProcessId, tempProcessId)
                .ne(BpmInstProcess::getRunFlag, ProcessRunEnum.END)
                .exists();
    }

    /**
     * 功能描述:
     * 〈校验流程有效性〉
     * @param tempProcess tempProcess
     * @return 正常返回:{@link Boolean}
     */
    public Boolean checkTempProcess(BpmTempProcess tempProcess) {
        //校验节点回路是否有死循环

        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈校验节点回路是否有死循环〉
     * @param tempProcess tempProcess
     * @return 正常返回:{@link Boolean}
     */
    public Boolean checkTempProcessLoop(BpmTempProcess tempProcess) {
        //获取所有超过1条出路的节点

        //判断节点线出路与节点是否构成回路

        //如果节点上级节点存在非直接执行节点则有效

        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈添加流程分组关系〉
     * @param groupId groupId
     * @param tempProcessId tempProcessId
     */
    public void addGroupRel(Long groupId, Long tempProcessId) {
        BpmAllGroup group = bpmAllGroupService.getById(groupId);
        if(ObjectUtil.isEmpty(group)) {
            return;
        }
        //保存新数据
        BpmAllGroupRel groupRel = new BpmAllGroupRel();
        groupRel.setGroupId(groupId);
        groupRel.setGroupType(group.getGroupType());
        groupRel.setDataId(tempProcessId);
        bpmAllGroupRelService.save(groupRel);
    }

    /**
     * 功能描述:
     * 〈添加流程分组关系〉
     * @param originGroupId originGroupId
     * @param targetGroupId targetGroupId
     * @param tempProcessId tempProcessId
     */
    public void editGroupRel(Long originGroupId, Long targetGroupId, Long tempProcessId) {
        BpmAllGroup group = bpmAllGroupService.getById(targetGroupId);
        if(ObjectUtil.isEmpty(group)) {
            return;
        }
        bpmAllGroupRelService.lambdaUpdate()
                .eq(BpmAllGroupRel::getGroupId, originGroupId)
                .eq(BpmAllGroupRel::getDataId, tempProcessId)
                .remove();
        //保存新数据
        BpmAllGroupRel groupRel = new BpmAllGroupRel();
        groupRel.setGroupId(targetGroupId);
        groupRel.setGroupType(group.getGroupType());
        groupRel.setDataId(tempProcessId);
        bpmAllGroupRelService.save(groupRel);
    }

    /**
     * 功能描述:
     * 〈获取子分组ID〉
     * @author 蝉鸣
     */
    public List<Long> getChildGroupIds(Long groupId) {
        List<Long> ids = CollUtil.newArrayList();
        if(ObjectUtil.isEmpty(groupId)){
            return ids;
        }
        ids.add(groupId);
        //查询所有下级
        String childrenSql = SqlUtil.getCommonChildrenSql(BpmAllGroup.class, groupId);
        //查询子项
        List<BpmAllGroup> childGroups = bpmAllGroupService.lambdaQuery().apply(childrenSql).list();
        if(CollUtil.isEmpty(childGroups)){
            return ids;
        }
        List<Long> childIds = childGroups.stream().map(BpmAllGroup::getId).toList();
        ids.addAll(childIds);
        return ids;
    }
}

