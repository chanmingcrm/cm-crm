package com.platform.mesh.crm.biz.bi.crm.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.api.modules.inst.feign.RemoteBpmService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;
import com.platform.mesh.crm.biz.bi.crm.enums.TodoTypeEnum;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系统计
 * @author 蝉鸣
 */
@Service
public class CrmTodoServiceManual {

    @Autowired
    private RemoteBpmService remoteBpmService;

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;

    @Autowired
    private AppServiceManual appServiceManual;


    /**
     * 功能描述:
     * 〈重置参数〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<T>}
     * @author 蝉鸣
     */
    public <T extends AppPO> MPage<T> getPageDTO(TodoPDTO pageDTO, Class<T> clazz) {
        //设置请求参数
        setTodoDTO(pageDTO);
        //转换分页类型
        return MPageUtil.pageEntityToMPage(pageDTO, clazz);
    }

    /**
     * 功能描述:
     * 〈重置参数〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    public void setTodoDTO(TodoPDTO pageDTO) {
        //设置时间
        pageDTO.setStartTime(DateTimeUtil.getDayStartDateTime(LocalDate.now()));
        pageDTO.setEndTime(DateTimeUtil.getDayEndDateTime(LocalDate.now()));
        //设置过滤数据权限
        ScopeBO scopeBO = DataScopeUtil.parseBiDTO(DataScopeEnum.SELF.getValue(), DataFlagEnum.USER.getValue(), pageDTO.getDataIds());
        pageDTO.setDataIds(scopeBO.getDataIds());
    }

    /**
     * 功能描述:
     * 〈封装返回结果〉
     * @param poPage poPage
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    public <T extends AppPO> PageVO<Object> packRelVO(MPage<?> poPage, List<T> poList) {
        if(CollUtil.isEmpty(poList)){
            return new PageVO<>();
        }
        Long moduleId = CollUtil.getFirst(poList).getModuleId();
        List<Long> ids = poList.stream().map(T::getId).toList();
        List<Object> list = this.getEsDataByIds(moduleId,ids);
        PageVO<Object> pageVO = MPageUtil.convertToVO(poPage,Object.class);
        pageVO.setRecords(list);
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈封装返回结果〉
     * @param poPage poPage
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    public <T extends AppPO> PageVO<Object> packRelVO(PageVO<?> poPage, List<T> poList) {
        if(CollUtil.isEmpty(poList)){
            return new PageVO<>();
        }
        Long moduleId = CollUtil.getFirst(poList).getModuleId();
        List<Long> ids = poList.stream().map(T::getId).toList();
        List<Object> list = this.getEsDataByIds(moduleId,ids);
        PageVO<Object> pageVO = MPageUtil.convertToVO(poPage,Object.class);
        pageVO.setRecords(list);
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈获取审批待办数量总计〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Long> getBpmTodoNum(TodoPDTO pageDTO) {
        BpmPDTO bpmPDTO = BeanUtil.copyProperties(pageDTO, BpmPDTO.class);
        bpmPDTO.setProcessRunFlag(ProcessRunEnum.RUNNING.getValue());
        List<OrgMemberBO> memberBOS = remoteOrgMemberService.getOrgMemberByUserIds(pageDTO.getDataIds()).getData();
        if(CollUtil.isEmpty(memberBOS)){
            return new HashMap<>();
        }
        List<Long> memberIds = memberBOS.stream().map(OrgMemberBO::getId).toList();
        bpmPDTO.setAuditDataIds(memberIds);
        return remoteBpmService.getRunDataNumByModuleSchema(bpmPDTO).getData();
    }

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @param moduleSchema moduleSchema
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public PageVO<Long> getBpmDataIds(TodoPDTO pageDTO,String moduleSchema,List<Long> userIds) {
        BpmPDTO bpmPDTO = BeanUtil.copyProperties(pageDTO, BpmPDTO.class);
        //解析审批参数
        int processRunFlag;
        TodoTypeEnum todoTypeEnum = BaseEnum.getEnumByValue(TodoTypeEnum.class, pageDTO.getTodoType(), TodoTypeEnum.TODO);
        if (todoTypeEnum == TodoTypeEnum.DONE) {
            processRunFlag = ProcessPassEnum.PASS.getValue();
        } else {
            processRunFlag = ProcessRunEnum.RUNNING.getValue();
        }
        bpmPDTO.setProcessRunFlag(processRunFlag);
        bpmPDTO.setModuleSchema(moduleSchema);
        List<OrgMemberBO> memberBOS = remoteOrgMemberService.getOrgMemberByUserIds(userIds).getData();
        if(CollUtil.isEmpty(memberBOS)){
            return new PageVO<>();
        }
        List<Long> memberIds = memberBOS.stream().map(OrgMemberBO::getId).toList();
        bpmPDTO.setAuditDataIds(memberIds);
        return remoteBpmService.getRunDataIdsByModuleSchema(bpmPDTO).getData();
    }

    /**
     * 功能描述:
     * 〈根据Ids获取Es数据〉
     * @param moduleId moduleId
     * @param ids ids
     * @return 正常返回:{@link List<Object>}
     * @author 蝉鸣
     */
    public List<Object> getEsDataByIds(Long moduleId,List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return CollUtil.newArrayList();
        }
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(moduleId);
        if(ObjectUtil.isEmpty(moduleInfo)){
            return CollUtil.newArrayList();
        }
       return appServiceManual.getEsByIds(moduleInfo.getModuleIndex(),ids);
    }

}