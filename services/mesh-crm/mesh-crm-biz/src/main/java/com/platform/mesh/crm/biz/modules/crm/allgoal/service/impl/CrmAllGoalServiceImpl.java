package com.platform.mesh.crm.biz.modules.crm.allgoal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.dto.CrmAllGoalDTO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.vo.CrmAllGoalVO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.exception.CrmAllGoalExceptionEnum;
import com.platform.mesh.crm.biz.modules.crm.allgoal.mapper.CrmAllGoalMapper;
import com.platform.mesh.crm.biz.modules.crm.allgoal.service.ICrmAllGoalService;
import com.platform.mesh.crm.biz.modules.crm.allgoal.service.manual.CrmAllGoalServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系目标
 * @author 蝉鸣
 */
@Service
public class CrmAllGoalServiceImpl extends ServiceImpl<CrmAllGoalMapper, CrmAllGoal> implements ICrmAllGoalService {

    @Autowired
    private CrmAllGoalServiceManual crmAllGoalServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link CrmAllGoalServiceManual}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGoalServiceManual getServiceManual() {
        return this.crmAllGoalServiceManual;
    }

    /**
     * 功能描述:
     * 〈新增客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link CrmAllGoalVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGoalVO addGoal(CrmAllGoalDTO allGoalDTO) {
        CrmAllGoal crmAllGoal = BeanUtil.copyProperties(allGoalDTO, CrmAllGoal.class);
        BigDecimal dayGoal = crmAllGoalServiceManual.getAddDayGoal(crmAllGoal);
        crmAllGoal.setDayGoal(dayGoal);
        this.save(crmAllGoal);
        return BeanUtil.copyProperties(crmAllGoal, CrmAllGoalVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客户关系目标〉
     * @param allGoalDTO allGoalDTO
     * @return 正常返回:{@link CrmAllGoalVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGoalVO editGoal(CrmAllGoalDTO allGoalDTO) {
        if(ObjectUtil.isEmpty(allGoalDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CrmAllGoalDTO::getId);
            throw CrmAllGoalExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CrmAllGoal crmAllGoal = BeanUtil.copyProperties(allGoalDTO, CrmAllGoal.class);
        this.updateById(crmAllGoal);
        return BeanUtil.copyProperties(crmAllGoal, CrmAllGoalVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客户关系目标〉
     * @param allGoalId allGoalId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteGoal(Long allGoalId) {
        return this.removeById(allGoalId);
    }

    /**
     * 功能描述:
     * 〈获取天目标〉
     * @param crmAllGoal crmAllGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    @Override
    public BigDecimal getDayGoal(CrmAllGoal crmAllGoal, Month month) {
        return crmAllGoalServiceManual.getAvgDayGoal(crmAllGoal,month);
    }
}