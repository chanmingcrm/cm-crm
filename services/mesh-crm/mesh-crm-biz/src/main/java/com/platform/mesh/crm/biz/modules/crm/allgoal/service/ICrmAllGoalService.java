package com.platform.mesh.crm.biz.modules.crm.allgoal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.dto.CrmAllGoalDTO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.vo.CrmAllGoalVO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.service.manual.CrmAllGoalServiceManual;

import java.math.BigDecimal;
import java.time.Month;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系目标信息
 * @author 蝉鸣
 */
public interface ICrmAllGoalService extends IService<CrmAllGoal> {


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link CrmAllGoalServiceManual}
     * @author 蝉鸣
     */
    CrmAllGoalServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈新增客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link CrmAllGoalVO}
     * @author 蝉鸣
     */
    CrmAllGoalVO addGoal(CrmAllGoalDTO allGoalDTO);

    /**
     * 功能描述:
     * 〈修改客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link CrmAllGoalVO}
     * @author 蝉鸣
     */
    CrmAllGoalVO editGoal(CrmAllGoalDTO allGoalDTO);

    /**
     * 功能描述:
     * 〈删除客户关系目标〉
     * @param allGoalId allGoalId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteGoal(Long allGoalId);

    /**
     * 功能描述:
     * 〈获取天目标〉
     * @param crmAllGoal crmAllGoal
     * @param month month
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    BigDecimal getDayGoal(CrmAllGoal crmAllGoal, Month month);
}