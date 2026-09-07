package com.platform.mesh.crm.biz.modules.crm.precustomer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.AbatractVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.mapper.CrmPreCustomerMapper;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.manual.CrmPreCustomerServiceManual;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
@Service
public class CrmPreCustomerServiceImpl extends AppServiceAbstract<CrmPreCustomerMapper, CrmPreCustomer> implements ICrmPreCustomerService  {

    @Autowired
    private CrmPreCustomerServiceManual crmPreCustomerServiceManual;

    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO <Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        
        //添加分析字段
        List<String> aggregations = CollUtil.newArrayList();
        aggregations.add(ObjFieldUtil.getColumnName(CrmPreCustomer::getTotalMoney));
        aggregations.add(ObjFieldUtil.getColumnName(CrmPreCustomer::getReceivedMoney));
        pageDTO.setAggregations(aggregations);
        PageVO<Object> pageVO = this.getAppServiceManual().selectEsPage(pageDTO);
        //解析列表
        return crmPreCustomerServiceManual.parseVO(pageVO);

    }

    /**
     * 功能描述:
     * 〈新增客户关系客户对象〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreCustomerData> crmPreCustomerDataList = BeanUtil.copyToList(dataList, CrmPreCustomerData.class);
        //批量保存data表数据
        crmPreCustomerServiceManual.addDbDataBatch(crmPreCustomerDataList);
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param crmPreCustomer crmPreCustomer
     * @param dataAddSimpDTO dataAddSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(CrmPreCustomer crmPreCustomer, DataAddSimpDTO dataAddSimpDTO) {
        crmPreCustomer.setUnreceivedMoney(crmPreCustomer.getTotalMoney().subtract(crmPreCustomer.getReceivedMoney()));
        this.updateById(crmPreCustomer);
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param crmPreCustomer crmPreCustomer
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editOtherAction(CrmPreCustomer crmPreCustomer, DataEditSimpDTO dataEditSimpDTO) {
        //获取客户旧数据
        CrmPreCustomer oldData = getById(crmPreCustomer.getId());
        if(ObjectUtil.isEmpty(oldData)){
            return;
        }
        if(crmPreCustomer.getDataName().equals(oldData.getDataName())){
            return;
        }
        //发送订阅,修改关联数据名称信息,异步执行无需等待结果
        FutureHandleUtil.runNoResult(crmPreCustomer,this.getAppServiceManual()::pubSyncName);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmPreCustomer::getScopeUserId,scopeUserId)
                .set(CrmPreCustomer::getScopeOrgId,scopeOrgId)
                .in(CrmPreCustomer::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈查重客户关系客户对象〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    @Override
    public List<CheckVO> checkPreCustomer(CheckDTO checkDTO) {
        //查询信息,限制10条
        List<CheckVO> voList = this.getBaseMapper().checkPreCustomer(checkDTO);
        //查询模块信息
        List<Long> moduleIds = voList.stream().map(CheckVO::getModuleId).toList();
        List<AppModuleBaseBO> moduleBases = this.getAppServiceManual().getModuleInfo(moduleIds);;
        if(CollUtil.isEmpty(moduleBases)){
            return voList;
        }
        //设置模块名称
        Map<Long, String> moduleMap = moduleBases.stream().collect(Collectors.toMap(AppModuleBaseBO::getId, AppModuleBaseBO::getModuleName));
        for (CheckVO record : voList) {
            if(moduleMap.containsKey(record.getModuleId())){
                String moduleName = moduleMap.get(record.getModuleId());
                record.setModuleName(moduleName);
            }
            SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(record.getScopeUserId());
            if(ObjectUtil.isNotEmpty(sysUserBO)){
                record.setScopeUserName(sysUserBO.getNickName());
            }
        }
        return voList;
    }

    /**
     * 功能描述:
     * 〈摘要〉
     * @param customerId customerId
     * @return 正常返回:{@link AbatractVO}
     * @author 蝉鸣
     */
    @Override
    public AbatractVO abstractPreCustomer(Long customerId) {
        return this.getBaseMapper().abstractPreCustomer(customerId);
    }

    /**
     * 功能描述:
     * 〈修改客户成交状态〉
     * @param customerId customerId
     * @author 蝉鸣
     */
    @Override
    public void updateConfirmFlag(Long customerId, ConfirmFlagEnum confirmFlag, BigDecimal totalMoney) {
        CrmPreCustomer preCustomer = getById(customerId);
        if(ObjectUtil.isEmpty(preCustomer)){
            return;
        }
        preCustomer.setTotalMoney(totalMoney);
        preCustomer.setUnreceivedMoney(preCustomer.getTotalMoney().subtract(preCustomer.getReceivedMoney()));
        //修改DB数据
        this.updateById(preCustomer);
        //成交状态
        JSONArray array = JSONUtil.createArray();
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(StrConst.ID, confirmFlag.getValue());
        jsonObject.set(StrConst.NAME, confirmFlag.getDesc());
        array.add(jsonObject);
        //修改DB_DATA数据
        crmPreCustomerServiceManual.updateData(preCustomer.getId(),EsConst.CRM_CONFIRM_FLAG_JSON,array);
        //修改ES数据
        AppModuleBaseBO moduleInfo = this.getAppServiceManual().getModuleInfo(preCustomer.getModuleId());
        Map<String, Object> docMap = AppUtil.beanToMap(preCustomer);
        docMap.put(EsConst.CRM_CONFIRM_FLAG_JSON,array);
        docMap.put(ObjFieldUtil.getColumnName(CrmPreCustomer::getTotalMoney), preCustomer.getTotalMoney());
        docMap.put(ObjFieldUtil.getColumnName(CrmPreCustomer::getReceivedMoney), preCustomer.getReceivedMoney());
        docMap.put(ObjFieldUtil.getColumnName(CrmPreCustomer::getUnreceivedMoney), preCustomer.getUnreceivedMoney());
        this.getAppServiceManual().editEsData(moduleInfo.getModuleIndex(),preCustomer,docMap);
    }

    /**
     * 功能描述:
     * 〈更新客户收款金额〉
     * @param customerId customerId
     * @param customerMoney customerMoney
     * @author 蝉鸣
     */
    @Override
    public void updateReceivedMoney(Long customerId, BigDecimal customerMoney) {
        CrmPreCustomer crmPreCustomer = getById(customerId);
        if(ObjectUtil.isEmpty(crmPreCustomer)){
            return;
        }
        crmPreCustomer.setReceivedMoney(customerMoney);
        crmPreCustomer.setUnreceivedMoney(crmPreCustomer.getTotalMoney().subtract(crmPreCustomer.getReceivedMoney()));
        this.updateById(crmPreCustomer);
        //更新ES
        AppModuleBaseBO moduleBaseBO = getAppServiceManual().getModuleInfo(crmPreCustomer.getModuleId());
        HashMap<String, Object> map = new HashMap<>();
        map.put(ObjFieldUtil.getColumnName(CrmPreCustomer::getReceivedMoney), crmPreCustomer.getReceivedMoney());
        map.put(ObjFieldUtil.getColumnName(CrmPreCustomer::getUnreceivedMoney), crmPreCustomer.getUnreceivedMoney());
        getAppServiceManual().editEsData(moduleBaseBO.getModuleIndex(),crmPreCustomer,map);
    }

}