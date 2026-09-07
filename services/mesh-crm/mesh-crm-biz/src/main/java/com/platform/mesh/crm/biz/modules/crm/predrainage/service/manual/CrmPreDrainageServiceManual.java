package com.platform.mesh.crm.biz.modules.crm.predrainage.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.ThirdFormColumnMappingBO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.init.db.service.manual.DbServiceManual;
import com.platform.mesh.app.api.modules.serial.SerialUtil;
import com.platform.mesh.app.api.modules.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.app.api.modules.serial.domain.bo.SerialBO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.crm.biz.modules.crm.predrainage.exception.CrmPreDrainageExceptionEnum;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.service.ICrmPreDrainageDataService;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po.CrmPreDrainageThird;
import com.platform.mesh.crm.biz.modules.crm.predrainagethird.service.ICrmPreDrainageThirdService;
import com.platform.mesh.douyin.domain.feiyu.dto.DouYinPageDTO;
import com.platform.mesh.douyin.domain.feiyu.dto.DouyinAppDTO;
import com.platform.mesh.douyin.service.IAuthService;
import com.platform.mesh.douyin.service.IDouYinAppService;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import com.platform.mesh.upms.api.modules.conf.feign.RemoteConfService;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.UserMenuBO;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.wxwork.app.domain.ExternalContactBO;
import com.platform.mesh.wxwork.app.service.IWxWorkAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系活动引流
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreDrainageServiceManual.class);


    @Autowired
    private ICrmPreDrainageDataService crmPreDrainageDataService;

    @Autowired
    private ICrmPreDrainageThirdService crmPreDrainageThirdService;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IDouYinAppService douYinAppService;

    @Autowired
    private IWxWorkAppService wxWorkAppService;

    @Autowired
    private RemoteConfService remoteConfService;

    @Autowired
    private RemoteAppService remoteAppService;

    @Autowired
    private DbServiceManual dbServiceManual;

    @Autowired
    private RemoteMsgService remoteMsgService;

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preDrainageDataList preDrainageDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreDrainageData> preDrainageDataList) {
        if(CollUtil.isEmpty(preDrainageDataList)){
            return;
        }
        CrmPreDrainageData data = CollUtil.getFirst(preDrainageDataList);
        //删除旧数据
        crmPreDrainageDataService.lambdaUpdate().eq(CrmPreDrainageData::getDataId, data.getDataId()).remove();
        //批量新增信息
        crmPreDrainageDataService.saveBatch(preDrainageDataList);
    }

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    public List<Map<String, Object>> syncDouYinClue(String authCode) {
//        Long appId = 1862054519999579L;
//        String secret = "c59eee35746324bf54b48d2382d896ed80162088";
////        String accessToken = authService.getAccessToken(appId, secret, authCode);
//        String accessToken = "821deb0f7a1a3e831b89d6e252c2183a96632851";
//        FeiYuDTO feiYuDTO = new FeiYuDTO();
//        feiYuDTO.setAccessToken(accessToken);
//        feiYuDTO.setAdvertiserIds(CollUtil.newArrayList(1837770530550858L));
//        feiYuDTO.setStartTime(LocalDateTime.now().minusDays(7));
//        feiYuDTO.setEndTime(LocalDateTime.now());
//        //飞鱼线索
//        feiYuService.getClueList(feiYuDTO);
        return null;
    }


    /**
     * 功能描述:
     * 〈获取抖音请求token〉
     * @author 蝉鸣
     */
    public String getClientToken(DouyinAppDTO appDTO) {

        return authService.getClientToken(appDTO.getClientKey(), appDTO.getClientSecret(), appDTO.getGrantType());
    }

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    public List<Map<String, Object>> syncDouYinClue(DouyinAppDTO appDTO, DouYinPageDTO pageDTO) {
        //进行查询
        return douYinAppService.getClueList(appDTO,pageDTO);
    }

    /**
     * 功能描述:
     * 〈获取企微请求token〉
     * @author 蝉鸣
     */
    public String getWxToken(String corpId,String corpSecret) {
        return wxWorkAppService.getToken(corpId,corpSecret);
    }

    /**
     * 功能描述:
     * 〈获取配置了客户联系功能的成员列表〉
     * @author 蝉鸣
     */
    public List<String> getFollowUser(String accessToken) {
        return wxWorkAppService.getFollowUser(accessToken);
    }

    /**
     * 功能描述:
     * 〈获取企微客户详情〉
     * @author 蝉鸣
     */
    public Object getWxExternalContact(String accessToken,List<String> userList,String cursor) {
        return wxWorkAppService.getExternalContact(accessToken, userList, cursor, NumberConst.NUM_100);
    }

    /**
     * 功能描述:
     * 〈获取企微客户详情〉
     * @author 蝉鸣
     */
    public String getWxExternalContactCursor(Object data) {
        if(ObjectUtil.isEmpty(data)){
            return null;
        }
        JSONObject parseObj = JSONUtil.parseObj(data);
        Object cursor = parseObj.get("next_cursor");
        if(ObjectUtil.isEmpty(cursor)){
            return null;
        }
        return cursor.toString();
    }

    /**
     * 功能描述:
     * 〈获取企微客户详情〉
     * @author 蝉鸣
     */
    public List<ExternalContactBO> getWxExternalContact(Object data) {
        if(ObjectUtil.isEmpty(data)){
            return List.of();
        }
        JSONObject parseObj = JSONUtil.parseObj(data);
        JSONArray jsonArray = JSONUtil.parseArray(parseObj.get("external_contact_list"));
        return JSONUtil.toList(jsonArray, ExternalContactBO.class);
    }


    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    public Map<String, Object> getConfig() {
        //先手动映射字段信息
        List<ConfSysSetBO> sysSetBOS = remoteConfService.selectList(ConfSourceEnum.DOU_YIN.getValue()).getData();
        if(CollUtil.isEmpty(sysSetBOS)){
            return new HashMap<>();
        }
        ConfSysSetBO sysSetBO = CollUtil.getFirst(sysSetBOS);
        JSONObject entries = JSONUtil.parseObj(sysSetBO.getConfValue());
        entries.set("clientKey","awz3pvb8p22gvokx");
        entries.set("clientSecret","f8d3f71b38e9ac27699a671a25699583");
        entries.set("grantType","client_credential");
        entries.set("accountId","7626302175710332954");
        return entries;
    }

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    public DouyinAppDTO getDouyinAppDTO(ConfSysSetBO sysSetBO) {
        JSONObject entries = JSONUtil.parseObj(sysSetBO.getConfValue());
        entries.set("grantType","client_credential");
        return BeanUtil.toBean(entries,DouyinAppDTO.class);
    }

    /**
     * 功能描述:
     * 〈转换结果信息〉
     * @author 蝉鸣
     */
    public Map<String, Object> transMap(Map<String, Object> oldMap,Map<String, String> mapping) {
        Map<String, Object> newMap = new HashMap<>();
        mapping.forEach((key,value)->{
            newMap.put(value,oldMap.get(key));
        });
        return newMap;
    }

    /**
     * 功能描述:
     * 〈获取流程审批状态〉
     * @author 蝉鸣
     */
    public JSONArray getDictData(ConfSourceEnum dictValue){
        JSONArray array = JSONUtil.createArray();
        if(ObjectUtil.isEmpty(dictValue)){
            return array;
        }
        //获取字典值
        DictBaseValueBO dictByMac = UserCacheUtil.getSysDictByMac(EsConst.CRM_DRAINAGE_SOURCE_JSON, dictValue.getValue());
        String name = dictValue.getDesc();
        String color = StrUtil.EMPTY;
        if(ObjectUtil.isNotEmpty(dictByMac)){
            name = dictByMac.getDictName();
            color = dictByMac.getDictColor();
        }
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(StrConst.ID, dictValue.getValue().toString());
        jsonObject.set(StrConst.NAME, name);
        jsonObject.set(StrConst.DATA_COLOR, color);
        array.add(jsonObject);
        return array;
    }

    /**
     * 功能描述:
     * 〈保存第三方数据〉
     * @author 蝉鸣
     */
    public void saveThirdData(Integer sourceFlag,List<CrmPreDrainageThird> thirdList) {
        if(CollUtil.isEmpty(thirdList)){
            return;
        }
        List<String> thirdIds = thirdList.stream().map(CrmPreDrainageThird::getThirdId).toList();
        if(CollUtil.isEmpty(thirdIds)){
            return;
        }
        //先删除旧数据
        crmPreDrainageThirdService.lambdaUpdate()
                .eq(CrmPreDrainageThird::getSourceFlag,sourceFlag)
                .in(CrmPreDrainageThird::getThirdId,thirdIds)
                .remove();
        //保存新数据
        crmPreDrainageThirdService.saveBatch(thirdList);
    }

    /**
     * 功能描述:
     * 〈获取第三方字段映射〉
     * @author 蝉鸣
     */
    public List<ThirdFormColumnMappingBO> getThirdFieldMapping(Integer sourceFlag) {
        return remoteAppService.getThirdFormColumnMapping(sourceFlag).getData();
    }

    /**
     * 功能描述:
     * 〈新增db固定表数据〉
     * @param tableName tableName
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public CrmPreDrainage getDb(String tableName, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
        if(ObjectUtil.isEmpty(dataMap) || CollUtil.isEmpty(dataMap)) {
            return null;
        }
        //保存DB数据
        if(CollUtil.isNotEmpty(columnBOS)) {
            //生成序列号
            ColumnCompBO serialComp = SerialUtil.getSerialComp(BeanUtil.copyToList(columnBOS, ColumnCompBO.class));
            LocalDateTime reSetTime = SerialUtil.getReSetTime(serialComp);
            Map<String,Object> maxMap = dbServiceManual.dynamicDBMaxOne(tableName, reSetTime);
            if(CollUtil.isNotEmpty(maxMap) && maxMap.containsKey(StrConst.DATA_SERIAL)) {
                dataMap.put(StrConst.DATA_SERIAL,maxMap.get(StrConst.DATA_SERIAL));
            }
            SerialBO serialBO = SerialUtil.genDataSerial(serialComp,dataMap);
            dataMap.put(StrConst.DATA_MAC, serialBO.getDataMac());
            dataMap.put(StrConst.DATA_SERIAL, serialBO.getDataSerial());
        }
        //获取当前表单信息
        TableInfo tableInfo = SqlUtil.getTableInfo(tableName);
        Object obj = BeanUtil.fillBeanWithMap(dataMap, tableInfo.newInstance(), Boolean.TRUE);;
        CrmPreDrainage drainage = BeanUtil.toBean(obj, CrmPreDrainage.class);
        if(ObjectUtil.isEmpty(drainage.getId())){
            drainage.setId(IdUtil.getSnowflakeNextId());
        }
        drainage.setScopeOrgId(NumberConst.NUM_0.longValue());
        drainage.setScopeUserId(NumberConst.NUM_0.longValue());
        return drainage;
    }

    /**
     * 功能描述:
     * 〈新增DB  data表数据〉
     * @param crmPreDrainage crmPreDrainage
     * @param parentModuleId parentModuleId
     * @param columnBOS columnBOS
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public List<CrmPreDrainageData> getDbData(CrmPreDrainage crmPreDrainage,Long parentModuleId, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
        List<CrmPreDrainageData> dataList = CollUtil.newArrayList();
        if(ObjectUtil.isEmpty(crmPreDrainage) || ObjectUtil.isEmpty(dataMap)){
            return dataList;
        }
        //db字段
        Set<String> keySet = BeanUtil.beanToMap(crmPreDrainage,Boolean.TRUE,Boolean.TRUE).keySet();
        //表单字段
        Map<String, AppFormColumnBO> columnMap = columnBOS.stream()
                .filter(item-> !keySet.contains(item.getColumnMac()))
                .filter(item-> !DataTypeEnum.INIT.getValue().equals(item.getDefaultDataType()))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity(),(v1, v2)->v2));
        dataMap.forEach((key,value)->{
            if(columnMap.containsKey(StrUtil.toUnderlineCase(key))) {
                AppFormColumnBO formColumnBO = columnMap.get(StrUtil.toUnderlineCase(key));
                if(ObjectUtil.isNotEmpty(formColumnBO) && ObjectUtil.isNotEmpty(formColumnBO.getId())) {
                    CrmPreDrainageData preDrainageData = new CrmPreDrainageData();
                    preDrainageData.setModuleId(crmPreDrainage.getModuleId());
                    preDrainageData.setParentModuleId(parentModuleId);
                    preDrainageData.setDataId(crmPreDrainage.getId());
                    preDrainageData.setColumnId(formColumnBO.getId());
                    preDrainageData.setColumnMac(formColumnBO.getColumnMac());
                    preDrainageData.setColumnName(formColumnBO.getColumnName());
                    preDrainageData.setDataType(formColumnBO.getDefaultDataType());
                    preDrainageData.setDataValue(formColumnBO.getDefaultDataType());
                    preDrainageData.setDataValue(BaseEnum.getEnumByValue(DataTypeEnum.class, formColumnBO.getDefaultDataType(),DataTypeEnum.STRING).getDefaultValueStr(value));
                    dataList.add(preDrainageData);
                }
            }
        });
        return dataList;
    }

    /**
     * 功能描述:
     * 〈获取线索池模块〉
     * @author 蝉鸣
     */
    public AppModuleBaseBO getOpenModule() {
        //获取线索池模块
        List<AppModuleBaseBO> moduleBaseBOS = remoteAppService.getModuleBaseInfoBySchema(CollUtil.newArrayList(SqlUtil.getTableName(CrmPreDrainage.class, TableName.class))).getData();
        if(CollUtil.isEmpty(moduleBaseBOS)){
            return null;
        }
        List<AppModuleBaseBO> openList = moduleBaseBOS.stream().filter(module -> module.getOpenFlag().equals(YesOrNoEnum.YES.getValue())).toList();
        if(CollUtil.isEmpty(openList)){
            return CollUtil.getFirst(moduleBaseBOS);
        }
        return CollUtil.getFirst(openList);
    }

    /**
     * 功能描述:
     * 〈发送消息〉
     * @author 蝉鸣
     */
    public void sendMsg(CrmPreDrainage appPO) {
        MsgBaseBO baseBO = new MsgBaseBO();
        baseBO.setModuleId(appPO.getModuleId());
        baseBO.setDataId(appPO.getId());
        baseBO.setMsgTitle(CrmPreDrainageExceptionEnum.ADD_MODULE_TODO_FOLLOW.getDesc());
        baseBO.setMsgBody(appPO.getDataName().concat(appPO.getDataDesc()));
        baseBO.setMsgFlag(MsgFlagEnum.NOTICE_TODO.getValue());
        if(appPO.getScopeUserId().equals(NumberConst.NUM_0.longValue())){
            //获取
            UserMenuBO menuBO = new UserMenuBO();
            menuBO.setModuleIds(CollUtil.newArrayList(appPO.getModuleId()));
            List<Long> userIds = remoteUserService.getUserIdsByModules(menuBO).getData();
            baseBO.setMsgUserIds(userIds);
        }else{
            baseBO.setMsgUserIds(CollUtil.newArrayList(appPO.getScopeUserId()));
        }
        if(CollUtil.isEmpty(baseBO.getMsgUserIds())){
            return;
        }
        remoteMsgService.sendMsg(baseBO);
    }

    /**
     * 功能描述:
     * 〈根据第三方ID获取数据〉
     * @author 蝉鸣
     */
    public List<CrmPreDrainageThird> getThirdFieldData(List<String> thirdIds) {
        return crmPreDrainageThirdService.lambdaQuery().in(CrmPreDrainageThird::getThirdId, thirdIds).list();
    }

}
