package com.platform.mesh.crm.biz.modules.crm.predrainage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.ThirdFormColumnMappingBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.init.db.exception.DbExceptionEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.dto.CrmPreDrainageGetDTO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.crm.biz.modules.crm.predrainage.exception.CrmPreDrainageExceptionEnum;
import com.platform.mesh.crm.biz.modules.crm.predrainage.mapper.CrmPreDrainageMapper;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.ICrmPreDrainageService;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.manual.CrmPreDrainageServiceManual;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po.CrmPreDrainageThird;
import com.platform.mesh.douyin.constants.DouYinConst;
import com.platform.mesh.douyin.domain.feiyu.dto.DouYinPageDTO;
import com.platform.mesh.douyin.domain.feiyu.dto.DouyinAppDTO;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.wxwork.app.domain.ContactUserBO;
import com.platform.mesh.wxwork.app.domain.ExternalContactBO;
import com.platform.mesh.wxwork.app.domain.FollowUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动引流
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageServiceImpl extends AppServiceAbstract<CrmPreDrainageMapper, CrmPreDrainage> implements ICrmPreDrainageService  {

    @Autowired
    private CrmPreDrainageServiceManual crmPreDrainageServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系活动引流〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreDrainageData> crmPreDrainageDataList = BeanUtil.copyToList(dataList, CrmPreDrainageData.class);
        //批量保存data表数据
        crmPreDrainageServiceManual.addDbDataBatch(crmPreDrainageDataList);
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
                .set(CrmPreDrainage::getScopeUserId,scopeUserId)
                .set(CrmPreDrainage::getScopeOrgId,scopeOrgId)
                .in(CrmPreDrainage::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈查重客户关系活动引流〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    @Override
    public List<CheckVO> checkPreDrainage(CheckDTO checkDTO) {
        //查询信息,限制10条
        List<CheckVO> voList = this.getBaseMapper().checkPreDrainage(checkDTO);
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

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void syncDouYinClue(ConfSysSetBO sysSetBO) {
        //获取当前配置
        DouyinAppDTO douyinAppDTO = crmPreDrainageServiceManual.getDouyinAppDTO(sysSetBO);
        //获取请求token
        String clientToken = crmPreDrainageServiceManual.getClientToken(douyinAppDTO);
        if(ObjectUtil.isEmpty(clientToken)){
            throw CrmPreDrainageExceptionEnum.ADD_MODULE_SYNC_DATA_NO_TOKEN.getBaseException();
        }
        //获取对应字段
        List<ThirdFormColumnMappingBO> thirdFieldList = crmPreDrainageServiceManual.getThirdFieldMapping(ConfSourceEnum.DOU_YIN.getValue());
        if(CollUtil.isEmpty(thirdFieldList)){
            throw CrmPreDrainageExceptionEnum.ADD_MODULE_SYNC_DATA_NO_FIELD.getBaseException();
        }
        //获取字段映射对象
        ThirdFormColumnMappingBO mappingBO = CollUtil.getFirst(thirdFieldList);
        //获取模块对象
        AppModuleBaseBO moduleInfo = this.getAppServiceManual().getModuleInfo(mappingBO.getModuleId());
        //获取模块表单字段
        List<AppFormColumnBO> formColumnInfo = this.getAppServiceManual().getFormColumnInfo(mappingBO.getModuleId(), mappingBO.getFormId());
        //获取字段映射关联
        Map<String, String> mapping = thirdFieldList.stream()
                .collect(Collectors.toMap(ThirdFormColumnMappingBO::getRelColumnMac, ThirdFormColumnMappingBO::getColumnMac));
        //组装查询信息
        Integer pageNum = NumberConst.NUM_1;
        DouYinPageDTO pageDTO = new DouYinPageDTO();
        pageDTO.setPageSize(NumberConst.NUM_10);
        pageDTO.setAccessToken(clientToken);
        pageDTO.setStartTime(LocalDateTime.now().minusDays(7));
        pageDTO.setEndTime(LocalDateTime.now());
        try{
            while(true){
                pageDTO.setPageNum(pageNum);
                List<Map<String, Object>> mapList = crmPreDrainageServiceManual.syncDouYinClue(douyinAppDTO,pageDTO);
                if(CollUtil.isEmpty(mapList)){
                    return;
                }
                //查询已经存在的第三方数据
                List<String> thirdIds = mapList.stream().map(map -> map.get(DouYinConst.CLUE_ID).toString()).distinct().toList();
                List<CrmPreDrainageThird> existList = crmPreDrainageServiceManual.getThirdFieldData(thirdIds);
                Map<String, CrmPreDrainageThird> existMap = existList.stream().collect(Collectors.toMap(CrmPreDrainageThird::getThirdId, Function.identity(),(v1,v2)->v2));
                //保存Db数据
                List<CrmPreDrainage> drainages = CollUtil.newArrayList();
                List<CrmPreDrainageData> dataList = CollUtil.newArrayList();
                List<CrmPreDrainageThird> thirdList = CollUtil.newArrayList();
                List<String> phones = CollUtil.newArrayList();
                for (Map<String, Object> thirdMap : mapList) {
                    Map<String, Object> transMap = crmPreDrainageServiceManual.transMap(thirdMap,mapping);
                    //设置线索来源
                    JSONArray dictData = crmPreDrainageServiceManual.getDictData(ConfSourceEnum.DOU_YIN);
                    transMap.put(EsConst.CRM_DRAINAGE_SOURCE_JSON,dictData);
                    //保存DB数据
                    CrmPreDrainage crmPreDrainage = crmPreDrainageServiceManual.getDb(moduleInfo.getModuleSchema(),formColumnInfo,transMap);
                    crmPreDrainage.setModuleId(mappingBO.getModuleId());
                    crmPreDrainage.setDelFlag(YesOrNoEnum.YES.getValue());
                    if(existMap.containsKey(thirdMap.get(DouYinConst.CLUE_ID).toString())){
                        CrmPreDrainageThird third = existMap.get(thirdMap.get(DouYinConst.CLUE_ID).toString());
                        if(crmPreDrainage.getModuleId().equals(third.getModuleId())){
                            crmPreDrainage.setId(third.getDataId());
                        }else{
                            continue;
                        }
                    }
                    drainages.add(crmPreDrainage);
                    //保存DBData数据
                    List<CrmPreDrainageData> crmPreDrainageDataList = crmPreDrainageServiceManual.getDbData(crmPreDrainage,moduleInfo.getParentId(),formColumnInfo,transMap);
                    dataList.addAll(crmPreDrainageDataList);
                    //保存Es数据
                    this.getAppServiceManual().addEsData(moduleInfo.getModuleIndex(),crmPreDrainage,transMap);
                    //保存第三方数据
                    CrmPreDrainageThird drainageThird = new CrmPreDrainageThird();
                    drainageThird.setSourceFlag(ConfSourceEnum.DOU_YIN.getValue());
                    drainageThird.setModuleId(crmPreDrainage.getModuleId());
                    drainageThird.setDataId(crmPreDrainage.getId());
                    drainageThird.setThirdId(thirdMap.get(DouYinConst.CLUE_ID).toString());
                    drainageThird.setThirdData(thirdMap);
                    thirdList.add(drainageThird);
                    //手机号码
                    phones.add(crmPreDrainage.getPhone());
                }
                DataScopeHandler.setEnableDataScope(Boolean.FALSE);
                //添加db数据
                if(CollUtil.isEmpty(existList)){
                    this.saveBatch(drainages);
                }else{
                    this.saveOrUpdateBatch(drainages);
                }
                //保存Data数据
                crmPreDrainageServiceManual.addDbDataBatch(dataList);
                DataScopeHandler.unEnableDataScope();
                crmPreDrainageServiceManual.saveThirdData(ConfSourceEnum.DOU_YIN.getValue(),thirdList);
                pageNum ++;
            }
        }catch (Exception e){
            log.error(DbExceptionEnum.DB_TRANS_DATA_ERROR.getDesc() + SymbolConst.COLON +e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈同步企微线索〉
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void syncWxWorkContact(ConfSysSetBO sysSetBO) {
        //获取请求token
        JSONObject entries = JSONUtil.parseObj(sysSetBO.getConfValue());
        String corpId = entries.get("corpId").toString();
        String corpSecret = entries.get("corpSecret").toString();
        //获取Token
        String accessToken = crmPreDrainageServiceManual.getWxToken(corpId,corpSecret);
        if(ObjectUtil.isEmpty(accessToken)){
            throw CrmPreDrainageExceptionEnum.ADD_MODULE_SYNC_DATA_NO_TOKEN.getBaseException();
        }
        //获取对应字段
        List<ThirdFormColumnMappingBO> thirdFieldList = crmPreDrainageServiceManual.getThirdFieldMapping(ConfSourceEnum.WX_WORK.getValue());
        if(CollUtil.isEmpty(thirdFieldList)){
            throw CrmPreDrainageExceptionEnum.ADD_MODULE_SYNC_DATA_NO_FIELD.getBaseException();
        }
        //获取字段映射对象
        ThirdFormColumnMappingBO mappingBO = CollUtil.getFirst(thirdFieldList);
        //获取模块对象
        AppModuleBaseBO moduleInfo = this.getAppServiceManual().getModuleInfo(mappingBO.getModuleId());
        //获取模块表单字段
        List<AppFormColumnBO> formColumnInfo = this.getAppServiceManual().getFormColumnInfo(mappingBO.getModuleId(), mappingBO.getFormId());
        //获取字段映射关联
        Map<String, String> mapping = thirdFieldList.stream()
                .collect(Collectors.toMap(ThirdFormColumnMappingBO::getRelColumnMac, ThirdFormColumnMappingBO::getColumnMac));
        //组装查询信息
        String cursor = null;
        //获取企业成员userid列表
        List<String> followUser = crmPreDrainageServiceManual.getFollowUser(accessToken);
        if(CollUtil.isEmpty(followUser)){
            return;
        }
        try{
            while(true){
                Object wxExternalContact = crmPreDrainageServiceManual.getWxExternalContact(accessToken,followUser,cursor);
                //数据
                List<ExternalContactBO> externalContact = crmPreDrainageServiceManual.getWxExternalContact(wxExternalContact);
                if(CollUtil.isEmpty(externalContact)){
                    return;
                }
                //查询已经存在的第三方数据
                List<String> thirdIds = externalContact.stream().map(bo -> bo.getExternalContact().getExternalUserid()).distinct().toList();
                List<CrmPreDrainageThird> existList = crmPreDrainageServiceManual.getThirdFieldData(thirdIds);
                Map<String, CrmPreDrainageThird> existMap = existList.stream().collect(Collectors.toMap(CrmPreDrainageThird::getThirdId, Function.identity(),(v1,v2)->v2));
                List<String> phones = externalContact.stream()
                        .filter(bo -> ObjectUtil.isNotEmpty(bo.getFollowInfo()) &&  CollUtil.isNotEmpty(bo.getFollowInfo().getRemarkMobiles()))
                        .flatMap(bo -> bo.getFollowInfo().getRemarkMobiles().stream()).distinct().toList();
                //过滤已经存在的手机号码
                List<CrmPreDrainage> existPoList = this.lambdaQuery()
                        .eq(CrmPreDrainage::getModuleId, mappingBO.getModuleId())
                        .in(CrmPreDrainage::getPhone, phones)
                        .list();
                Map<String, Long> phoneMap = existPoList.stream().collect(Collectors.toMap(CrmPreDrainage::getPhone, CrmPreDrainage::getId,(v1,v2)->v2));
                //保存Db数据
                List<CrmPreDrainage> drainages = CollUtil.newArrayList();
                List<CrmPreDrainageData> dataList = CollUtil.newArrayList();
                List<CrmPreDrainageThird> thirdList = CollUtil.newArrayList();
                for (ExternalContactBO thirdBO : externalContact) {
                    ContactUserBO contact = thirdBO.getExternalContact();
                    FollowUserBO followInfo = thirdBO.getFollowInfo();
                    List<String> remarkMobiles = followInfo.getRemarkMobiles();
                    Map<String, Object> thirdMap = BeanUtil.beanToMap(contact, Boolean.TRUE, Boolean.TRUE);
                    Map<String, Object> transMap = crmPreDrainageServiceManual.transMap(thirdMap,mapping);
                    //设置线索来源
                    JSONArray dictData = crmPreDrainageServiceManual.getDictData(ConfSourceEnum.WX_WORK);
                    transMap.put(EsConst.CRM_DRAINAGE_SOURCE_JSON,dictData);
                    //保存DB数据
                    CrmPreDrainage crmPreDrainage = crmPreDrainageServiceManual.getDb(moduleInfo.getModuleSchema(),formColumnInfo,transMap);
                    crmPreDrainage.setModuleId(mappingBO.getModuleId());
                    crmPreDrainage.setDelFlag(YesOrNoEnum.YES.getValue());
                    if(existMap.containsKey(contact.getExternalUserid())){
                        continue;
//                        CrmPreDrainageThird third = existMap.get(contact.getExternalUserid());
//                        if(crmPreDrainage.getModuleId().equals(third.getModuleId())){
//                            crmPreDrainage.setId(third.getDataId());
//                        }else{
//                            continue;
//                        }
                    }
                    if(phoneMap.containsKey(crmPreDrainage.getPhone())){
                        crmPreDrainage.setId(phoneMap.get(crmPreDrainage.getPhone()));
                    }
                    if(CollUtil.isNotEmpty(remarkMobiles)){
                        crmPreDrainage.setPhone(CollUtil.getFirst(remarkMobiles));
                    }
                    drainages.add(crmPreDrainage);
                    //保存DBData数据
                    List<CrmPreDrainageData> crmPreDrainageDataList = crmPreDrainageServiceManual.getDbData(crmPreDrainage,moduleInfo.getParentId(),formColumnInfo,transMap);
                    dataList.addAll(crmPreDrainageDataList);
                    //保存Es数据
                    this.getAppServiceManual().addEsData(moduleInfo.getModuleIndex(),crmPreDrainage,transMap);
                    //保存第三方数据
                    CrmPreDrainageThird drainageThird = new CrmPreDrainageThird();
                    drainageThird.setSourceFlag(ConfSourceEnum.WX_WORK.getValue());
                    drainageThird.setModuleId(crmPreDrainage.getModuleId());
                    drainageThird.setDataId(crmPreDrainage.getId());
                    drainageThird.setThirdId(contact.getExternalUserid());
                    drainageThird.setThirdData(JSONUtil.parseObj(JSONUtil.toJsonStr(thirdBO)));
                    thirdList.add(drainageThird);
                }
                DataScopeHandler.setEnableDataScope(Boolean.FALSE);
                //添加db数据
                if(CollUtil.isEmpty(existList)){
                    this.saveBatch(drainages);
                }else{
                    this.saveOrUpdateBatch(drainages);
                }
                //保存Data数据
                crmPreDrainageServiceManual.addDbDataBatch(dataList);
                DataScopeHandler.unEnableDataScope();
                crmPreDrainageServiceManual.saveThirdData(ConfSourceEnum.WX_WORK.getValue(),thirdList);
                //游标
                cursor = crmPreDrainageServiceManual.getWxExternalContactCursor(wxExternalContact);
                if(ObjectUtil.isEmpty(cursor)){
                    return;
                }
            }
        }catch (Exception e){
            log.error(DbExceptionEnum.DB_TRANS_DATA_ERROR.getDesc() + SymbolConst.COLON +e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈无授权新增〉
     * @param appPO appPO
     * @author 蝉鸣
     */
    @Override
    public void addDataNoScope(CrmPreDrainage appPO) {
        //获取线索池模块信息
        AppModuleBaseBO moduleBaseBO = crmPreDrainageServiceManual.getOpenModule();
        if(ObjectUtil.isNull(moduleBaseBO)){
            return;
        }
        //查询是否存在
        CrmPreDrainage existByPhone = this.getBaseMapper().getExistByPhone(moduleBaseBO.getId(),appPO.getPhone());
        if(ObjectUtil.isEmpty(existByPhone)){
            //保存Db数据
            appPO.setModuleId(moduleBaseBO.getId());
            appPO.setScopeUserId(NumberConst.NUM_0.longValue());
            appPO.setScopeOrgId(NumberConst.NUM_0.longValue());
            this.save(appPO);
            //保存ES数据
            Map<String, Object> docMap = BeanUtil.beanToMap(appPO, Boolean.TRUE, Boolean.TRUE);
            this.getAppServiceManual().addEsData(moduleBaseBO.getModuleIndex(), appPO, docMap);
        }else {
            BeanUtil.copyProperties(existByPhone,appPO);
        }
        //向管理人员发送消息
        //查询拥有CRM 人员
        crmPreDrainageServiceManual.sendMsg(appPO);
    }

    /**
     * 功能描述:
     * 〈根据第三方ID查询线索/客户信息〉
     * @author 蝉鸣
     */
    @Override
    public Object getByThirdId(CrmPreDrainageGetDTO getDTO) {
        List<CrmPreDrainageThird> thirdList = crmPreDrainageServiceManual.getThirdFieldData(CollUtil.newArrayList(getDTO.getThirdId()));
        if(CollUtil.isEmpty(thirdList)){
            return null;
        }
        CrmPreDrainageThird byThirdId = CollUtil.getFirst(thirdList);
        //获取模块信息
        AppModuleBaseBO moduleInfo = this.getAppServiceManual().getModuleInfo(byThirdId.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return null;
        }
        //获取ES数据
        return this.getAppServiceManual().getEsData(moduleInfo.getModuleIndex(),byThirdId.getDataId());
    }
}
