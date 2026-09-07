package com.platform.mesh.crm.biz.modules.crm.precontacts.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precontacts.mapper.CrmPreContactsMapper;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.ICrmPreContactsService;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.manual.CrmPreContactsServiceManual;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系联系人
 * @author 蝉鸣
 */
@Service
public class CrmPreContactsServiceImpl extends AppServiceAbstract<CrmPreContactsMapper, CrmPreContacts> implements ICrmPreContactsService  {

    @Autowired
    private CrmPreContactsServiceManual crmPreContactsServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系联系人〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreContactsData> crmPreContactsDataList = BeanUtil.copyToList(dataList, CrmPreContactsData.class);
        //批量保存data表数据
        crmPreContactsServiceManual.addDbDataBatch(crmPreContactsDataList);
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
                .set(CrmPreContacts::getScopeUserId,scopeUserId)
                .set(CrmPreContacts::getScopeOrgId,scopeOrgId)
                .in(CrmPreContacts::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈查重客户关系联系人〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    @Override
    public List<CheckVO> checkPreContacts(CheckDTO checkDTO) {
        //查询信息,限制10条
        List<CheckVO> voList = this.getBaseMapper().checkPreContacts(checkDTO);
        //查询模块信息
        List<Long> moduleIds = voList.stream().map(CheckVO::getModuleId).toList();
        List<AppModuleBaseBO> moduleBases = getAppServiceManual().getModuleInfo(moduleIds);
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
}