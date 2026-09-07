package com.platform.mesh.app.biz.modules.data.common.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import com.platform.mesh.app.biz.modules.data.commondata.service.IDataCommonDataService;
import com.platform.mesh.app.biz.modules.data.commonrel.domain.po.DataCommonRel;
import com.platform.mesh.app.biz.modules.data.commonrel.service.IDataCommonRelService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocUGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.feign.RemoteTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段数据
 * @author 蝉鸣
 */
@Service
public class DataCommonServiceManual {

    private final static Logger log = LoggerFactory.getLogger(DataCommonServiceManual.class);

    @Autowired
    private IDataCommonDataService dataCommonDataService;

    @Autowired
    private IDataCommonRelService dataCommonRelService;

    @Autowired
    private IEsDocService esDocService;

    @Autowired
    private IAppModuleBaseService appModuleBaseService;

    @Autowired
    private IAppFormColumnService appFormColumnService;

    @Autowired
    private RemoteTeamService remoteTeamService;

    @Autowired
    private RemoteMsgService remoteMsgService;

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param commonDataList commonDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<DataCommonData> commonDataList) {
        if(CollUtil.isEmpty(commonDataList)){
            return;
        }
        DataCommonData data = CollUtil.getFirst(commonDataList);
        //删除旧数据
        dataCommonDataService.lambdaUpdate().eq(DataCommonData::getDataId,data.getDataId()).remove();
        //批量扩展数据
        dataCommonDataService.saveBatch(commonDataList);
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param commonRelList commonRelList
     * @author 蝉鸣
     */
    public void addDbRelBatch(List<DataCommonRel> commonRelList) {
        if (CollUtil.isEmpty(commonRelList)) {
            return;
        }
        DataCommonRel commonRel = CollUtil.getFirst(commonRelList);
        //删除旧数据
        dataCommonRelService.lambdaUpdate()
                .eq(DataCommonRel::getModuleId, commonRel.getModuleId())
                .eq(DataCommonRel::getDataId, commonRel.getDataId())
                .remove();
        //修改关联数据
        dataCommonRelService.saveBatch(commonRelList);
    }

    /**
     * 功能描述:
     * 〈多索引联合过滤查询〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    public PageVO<Object> selectUniPage(EsDocUGetDTO pageDTO) {
        Map<Long, List<Long>> idFieldMap = pageDTO.getIdFieldMap();
        Set<Long> longSet = idFieldMap.keySet();
        //获取模块信息
        List<AppModuleBase> appModuleBases = appModuleBaseService.lambdaQuery().in(AppModuleBase::getId, longSet).list();
        if(CollUtil.isEmpty(appModuleBases)){
            return new PageVO<>();
        }
        //获取字段信息
        List<AppFormColumn> appFormColumns = appFormColumnService.lambdaQuery()
                .or(orWrapper -> {
                    idFieldMap.forEach((key, value) -> {
                        orWrapper.and(andWrapper -> {
                            andWrapper.eq(AppFormColumn::getModuleId, key);
                            andWrapper.in(AppFormColumn::getId, value);
                        });
                    });
                }).list();
        if(CollUtil.isEmpty(appFormColumns)){
            return new PageVO<>();
        }
        //转换查询信息
        Map<String, List<String>> searchMap = selectUniPage(appModuleBases, appFormColumns);
        pageDTO.setIndexFieldMap(searchMap);
        //获取查询数据
        PageVO<Object> pageVO = esDocService.uniDocument(pageDTO);
        if(CollUtil.isEmpty(pageVO.getRecords())){
            return pageVO;
        }
        //封装结果
        return packPage(pageVO,idFieldMap,appModuleBases,appFormColumns);

    }

    /**
     * 功能描述:
     * 〈获取查询条件〉
     * @param appModuleBases appModuleBases
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public Map<String,List<String>> selectUniPage(List<AppModuleBase> appModuleBases,List<AppFormColumn> appFormColumns) {
        Map<Long, List<String>> columnMap = appFormColumns.stream()
                .collect(Collectors.groupingBy(AppFormColumn::getModuleId, Collectors.collectingAndThen(
                        // 先将分组结果收集为List
                        Collectors.toList(),
                        groupList -> groupList.stream().map(AppFormColumn::getColumnMac).toList())
                ));
        Map<String,List<String>> searchMap = new HashMap<>();
        for (AppModuleBase appModuleBase : appModuleBases) {
            List<String> fieldList = columnMap.get(appModuleBase.getId());
            if(searchMap.containsKey(appModuleBase.getModuleIndex())){
                List<String> searchList = searchMap.get(appModuleBase.getModuleIndex());
                fieldList.addAll(searchList);
            }
            searchMap.put(appModuleBase.getModuleIndex(), fieldList);
        }
        return searchMap;
    }

    /**
     * 功能描述:
     * 〈封装返回数据〉
     * @param appModuleBases appModuleBases
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public PageVO<Object> packPage(PageVO<Object> pageVO,Map<Long, List<Long>> idFieldMap
            , List<AppModuleBase> appModuleBases,List<AppFormColumn> appFormColumns) {
        //组装提醒信息
        Map<Long, String> moduleMap = appModuleBases.stream().collect(Collectors.toMap(AppModuleBase::getId, AppModuleBase::getModuleName));
        Map<Long, Map<String, AppFormColumn>> columnMap = appFormColumns.stream()
                .collect(Collectors.groupingBy(AppFormColumn::getModuleId, Collectors.collectingAndThen(
                        // 先将分组结果收集为List
                        Collectors.toList(),
                        groupList -> groupList.stream().collect(Collectors.toMap(AppFormColumn::getColumnMac, Function.identity()))
                )));
        List<Object> newObj = pageVO.getRecords().stream().peek(record -> {
            JSONObject jsonObject = JSONUtil.parseObj(record);
            //获取模块ID
            Long moduleId = Convert.toLong(jsonObject.get(StrConst.MODULE_ID).toString());
            //设置字段名称
            Map<String, AppFormColumn> formColumnMap = columnMap.get(moduleId);
            List<Long> fieldIds = idFieldMap.get(moduleId);
            JSONObject noticeJson = new JSONObject();
            jsonObject.forEach((key, value) -> {
                if (formColumnMap.containsKey(key)) {
                    AppFormColumn appFormColumn = formColumnMap.get(key);
                    jsonObject.set(key.concat(StrConst.ES_SUFFIX_NAME), appFormColumn.getColumnName());
                    //如果包含搜索字段则拼接提示
                    if (fieldIds.contains(appFormColumn.getId())) {
                        noticeJson.set(appFormColumn.getColumnName(), value);
                    }
                }
            });
            jsonObject.set(StrConst.UNI_QUERY.concat(StrConst.ES_SUFFIX_NAME), noticeJson);
            //设置模块名称
            String moduleName = MapUtil.getStr(moduleMap, moduleId);
            jsonObject.set(StrConst.MODULE_ID.concat(StrConst.ES_SUFFIX_NAME), moduleName);
        }).toList();
        return pageVO.setRecords(newObj);
    }

    /**
     * 功能描述:
     * 〈添加团队成员〉
     * @param baseDTO baseDTO
     * @author 蝉鸣
     */
    public void addTeamMember(TeamBaseDTO baseDTO) {
        //查询模块信息
        AppModuleBase appModuleBase = appModuleBaseService.getById(baseDTO.getLinkDTO().getModuleId());
        //
        if(ObjectUtil.isEmpty(appModuleBase)){
            return;
        }
        //先获取数据
        Object document = esDocService.getDocumentById(appModuleBase.getModuleIndex(), baseDTO.getLinkDTO().getDataId());
        if(ObjectUtil.isEmpty(document)){
            return;
        }
        //获取已存在团队成员
        List<TeamMemberRelBO> teamMemberRelBOS = CollUtil.newArrayList();
        JSONObject parseObj = JSONUtil.parseObj(document);
        if(parseObj.containsKey(StrConst.MEMBER_USER)){
            teamMemberRelBOS.addAll(parseObj.getBeanList(StrConst.MEMBER_USER, TeamMemberRelBO.class));
        }
        //更新新增团队成员
        List<Long> userIds = CollUtil.newArrayList();
        List<TeamMemberRelBO> memberRelList = baseDTO.getMemberRelDTOS().stream().map(rel -> {
            userIds.add(rel.getUserId());
            TeamMemberRelBO memberRelBO = BeanUtil.copyProperties(rel, TeamMemberRelBO.class);
            memberRelBO.setId(rel.getUserId());
            memberRelBO.setName(rel.getMemberName());
            return memberRelBO;
        }).toList();
        teamMemberRelBOS.addAll(memberRelList);
        //过滤重复数据并以最新的为准
        List<Map<String, Object>> memberRelMap = teamMemberRelBOS.stream()
                .collect(Collectors.toMap(
                        TeamMemberRelBO::getId,
                        Function.identity(),
                        (first, last) -> {
                            if (ObjectUtil.isEmpty(last.getStartTime()) || ObjectUtil.isEmpty(last.getEndTime())) {
                                last.setStartTime(first.getStartTime());
                                last.setEndTime(first.getEndTime());
                            }
                            return last;
                        }
                ))
                .values()
                .stream()
                .map(AppUtil::beanToMap)
                .toList();
        //创建更新值
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(StrConst.MEMBER_USER, memberRelMap);
        //更新数据
        EsDocPutBO docPutBO = new EsDocPutBO();
        docPutBO.setIndexName(appModuleBase.getModuleIndex());
        docPutBO.setDataIds(CollUtil.newArrayList(baseDTO.getLinkDTO().getDataId()));
        docPutBO.setDocMap(jsonObject);
        //更新ES
        esDocService.updateDocument(docPutBO);
        //保存团队成员信息
        remoteTeamService.addTeamMember(baseDTO);
        //发送提醒消息
        MsgBaseBO msgBaseBO = this.getMsgBO(appModuleBase,baseDTO.getLinkDTO().getDataId(),parseObj,userIds);
        remoteMsgService.sendMsg(msgBaseBO);
    }

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @author 蝉鸣
     */
    public void deleteTeamMember(TeamBaseDelDTO delDTO) {
        //删除ES团队成员数据
        //查询模块信息
        AppModuleBase appModuleBase = appModuleBaseService.getById(delDTO.getModuleId());
        if(ObjectUtil.isEmpty(appModuleBase)){
            return;
        }
        //删除DB团队成员
        remoteTeamService.deleteTeamMember(delDTO);
        //先获取数据
        Object document = esDocService.getDocumentById(appModuleBase.getModuleIndex(), delDTO.getDataId());
        if(ObjectUtil.isEmpty(document)){
            return;
        }
        //获取已存在团队成员
        List<TeamMemberRelBO> teamMemberRelBOS = CollUtil.newArrayList();
        JSONObject parseObj = JSONUtil.parseObj(document);
        if(parseObj.containsKey(StrConst.MEMBER_USER)){
            teamMemberRelBOS.addAll(parseObj.getBeanList(StrConst.MEMBER_USER, TeamMemberRelBO.class));
        }
        if(CollUtil.isEmpty(teamMemberRelBOS)){
            return;
        }
        //过滤删除数据
        List<Map<String, Object>> memberRelMap = teamMemberRelBOS.stream()
                .filter(rel -> !delDTO.getMemberIds().contains(rel.getMemberId()))
                .map(item -> AppUtil.beanToMap(item))
                .toList();
        //创建更新值
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(StrConst.MEMBER_USER, memberRelMap);
        //更新数据
        EsDocPutBO docPutBO = new EsDocPutBO();
        docPutBO.setIndexName(appModuleBase.getModuleIndex());
        docPutBO.setDataIds(CollUtil.newArrayList(delDTO.getDataId()));
        docPutBO.setDocMap(jsonObject);
        //更新ES
        esDocService.updateDocument(docPutBO);
    }

    /**
     * 功能描述:
     * 〈组装提醒消息〉
     * @param appModuleBase appModuleBase
     * @param dataId dataId
     * @param parseObj parseObj
     * @param userIds userIds
     * @return MsgBaseBO
     * @author 蝉鸣
     */
    public MsgBaseBO getMsgBO(AppModuleBase appModuleBase,Long dataId,JSONObject parseObj,List<Long> userIds){
        MsgBaseBO msgBaseBO = new MsgBaseBO();
        msgBaseBO.setModuleId(appModuleBase.getId());
        msgBaseBO.setDataId(dataId);
        msgBaseBO.setMsgFlag(MsgFlagEnum.TEAM_JOIN.getValue());
        msgBaseBO.setMsgType(MsgTypeEnum.INIT.getValue());
        msgBaseBO.setMsgTitle(appModuleBase.getModuleName());
        msgBaseBO.setMsgBody(parseObj.get(StrConst.DATA_NAME).toString());
        msgBaseBO.setMsgUserIds(userIds);
        return msgBaseBO;
    }
}