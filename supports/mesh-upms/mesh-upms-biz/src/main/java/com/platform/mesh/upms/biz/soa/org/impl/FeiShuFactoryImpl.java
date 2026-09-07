package com.platform.mesh.upms.biz.soa.org.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lark.oapi.Client;
import com.lark.oapi.core.enums.AppType;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.service.contact.v3.model.*;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.sys.user.enums.AdminFlagEnum;
import com.platform.mesh.upms.biz.soa.org.ThirdOrgService;
import com.platform.mesh.upms.biz.soa.org.domain.bo.SyncUserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class FeiShuFactoryImpl implements ThirdOrgService {

    private static final Logger log = LoggerFactory.getLogger(FeiShuFactoryImpl.class);

    @Autowired
    private IOrgLevelService orgLevelService;

    @Override
    public SourceFlagEnum sourceFlag() {
        return SourceFlagEnum.FEI_SHU;
    }

    /**
     * 功能描述:
     * 〈同步部门〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    @Override
    public List<OrgLevel> syncDeptList(TenantClientBO clientBO) {
        Client client = getClient(clientBO);
        List<OrgLevel> orgLevels = CollUtil.newArrayList();
        //查询部门
        getDepartmentList(orgLevels, client,StrUtil.EMPTY);
        //返回部门
        return orgLevels;
    }

    /**
     * 功能描述:
     * 〈同步用户〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    @Override
    public List<SyncUserBO> syncUserList(TenantClientBO clientBO) {
        List<SyncUserBO> userBOS = CollUtil.newArrayList();
        //获取同步的部门列表
        List<OrgLevel> orgLevels = orgLevelService.lambdaQuery().eq(OrgLevel::getLevelSource, this.sourceFlag().getValue()).list();
        if(CollUtil.isEmpty(orgLevels)){
            return CollUtil.newArrayList();
        }
        List<String> deptIds = orgLevels.stream().map(OrgLevel::getThirdId).distinct().toList();
        Client client = getClient(clientBO);
        for (String deptId : deptIds) {
            getUserList(client, deptId, userBOS, StrUtil.EMPTY);
        }
        return userBOS;
    }

    /**
     * 功能描述:
     * 〈获取客户端〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    public Client getClient(TenantClientBO clientBO) {
        String appId = clientBO.getClientId();
        String appSecret = clientBO.getClientSecret();
        // 3. 构建部门列表请求 - 使用正确的类
        return Client.newBuilder(appId, appSecret)
                .openBaseUrl(BaseUrlEnum.FeiShu)
                .appType(AppType.SELF_BUILT).build();
    }

    /**
     * 功能描述:
     * 〈获取组织部门〉
     * @param orgLevels orgLevels
     * @param client client
     * @param pageToken pageToken
     * @author 蝉鸣
     */
    void getDepartmentList(List<OrgLevel> orgLevels, Client client, String pageToken) {
        ListDepartmentReq req = ListDepartmentReq.newBuilder()
                // 分页token，首次请求为空
                .pageToken(pageToken)
                // 每页大小
                .pageSize(NumberConst.NUM_50)
                .build();
        try {
            ListDepartmentResp resp = client.contact().v3().department().list(req);
            if (resp.getCode() != NumberConst.NUM_0) {
                log.error(resp.getMsg());
                return;
            }
            ListDepartmentRespBody data = resp.getData();
            pageToken = data.getPageToken();
            Department[] items = data.getItems();
            if (ArrayUtil.isEmpty(items)) {
                return;
            }
            for (Department item : items) {
                OrgLevel orgLevel = getOrgLevel(item, NumberConst.NUM_0.longValue());
                orgLevels.add(orgLevel);
                getDepartmentList(item.getOpenDepartmentId(), orgLevel.getId(), orgLevels, client,StrUtil.EMPTY);
            }
            //收集顶层部门
            if(data.getHasMore()){
                getDepartmentList(orgLevels, client,pageToken);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈获取组织部门〉
     * @param departmentId departmentId
     * @param orgLevels orgLevels
     * @param client client
     * @param pageToken pageToken
     * @author 蝉鸣
     */
    void getDepartmentList(String departmentId, Long levelId, List<OrgLevel> orgLevels, Client client,String pageToken) {
        ChildrenDepartmentReq childrenDepartmentReq = ChildrenDepartmentReq.newBuilder()
                .pageToken(pageToken)
                .pageSize(NumberConst.NUM_50)
                .build();
        childrenDepartmentReq.setDepartmentId(departmentId);
        try {
            ChildrenDepartmentResp childrenDepartmentResp = client.contact().v3().department().children(childrenDepartmentReq);
            if (childrenDepartmentResp.getCode() != NumberConst.NUM_0) {
                log.error(childrenDepartmentResp.getMsg());
                return;
            }
            ChildrenDepartmentRespBody respData = childrenDepartmentResp.getData();
            Department[] items = respData.getItems();
            if (ArrayUtil.isEmpty(items)) {
                return;
            }
            for (Department item : items) {
                OrgLevel orgLevel = getOrgLevel(item, levelId);
                orgLevels.add(orgLevel);
                getDepartmentList(item.getOpenDepartmentId(), orgLevel.getId(), orgLevels, client,StrUtil.EMPTY);
            }
            if(respData.getHasMore()){
                getDepartmentList(departmentId, levelId, orgLevels, client, respData.getPageToken());
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈获取组织部门〉
     * @param department department
     * @author 蝉鸣
     */
    OrgLevel getOrgLevel(Department department,Long parentId) {
        if(ObjectUtil.isEmpty(department)){
            return null;
        }
        OrgLevel orgLevel = new OrgLevel();
        orgLevel.setId(IdUtil.getSnowflakeNextId());
        orgLevel.setRootId(NumberConst.NUM_0.longValue());
        orgLevel.setParentId(parentId);
        orgLevel.setLevelName(department.getName());
        orgLevel.setLevelFlag(LevelFlagEnum.DEPT.getValue());
        orgLevel.setLevelSource(this.sourceFlag().getValue());
        orgLevel.setThirdId(department.getParentDepartmentId());
        return orgLevel;
    }

    /**
     * 功能描述:
     * 〈获取人员〉
     * @param client client
     * @param deptId deptId
     * @param userList userList
     * @param pageToken pageToken
     * @author 蝉鸣
     */
    public void getUserList(Client client, String deptId, List<SyncUserBO> userList, String pageToken) {
        if (ObjectUtil.isEmpty(deptId)) {
            return;
        }
        try {
            ListUserReq userReq = ListUserReq.newBuilder()
                    .pageToken(pageToken)
                    .pageSize(NumberConst.NUM_50)
                    .departmentId(deptId)
                    .build();
            ListUserResp listUserResp = client.contact().v3().user().list(userReq);
            ListUserRespBody respData = listUserResp.getData();
            User[] items = respData.getItems();
            if(ArrayUtil.isEmpty(items)){
                return;
            }
            List<SyncUserBO> userBOS = getUserList(items);
            userList.addAll(userBOS);
            if(respData.getHasMore()){
                getUserList(client,deptId,userList,respData.getPageToken());
            }
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈获取人员对象〉
     * @param users users
     * @author 蝉鸣
     */
    List<SyncUserBO> getUserList(User[] users) {
        if(ArrayUtil.isEmpty(users)){
            return CollUtil.newArrayList();
        }
        List<SyncUserBO> userBOS = CollUtil.newArrayList();
        for (User user : users) {
            SyncUserBO userBO = new SyncUserBO();
            userBO.setUserId(user.getUserId());
            userBO.setUnionId(user.getUnionId());
            userBO.setName(user.getName());
            userBO.setPhone(user.getMobile());
            userBO.setEmail(user.getEmail());
            userBO.setLevelIds(List.of(user.getDepartmentIds()));
            userBO.setAvatar(user.getAvatar().getAvatarOrigin());
            if(user.getLeaderUserId().equals(userBO.getUserId())){
                userBO.setIsAdmin(AdminFlagEnum.TENANT.getValue());
            }else{
                userBO.setIsAdmin(AdminFlagEnum.COMMON.getValue());
            }
            userBO.setSourceFlag(this.sourceFlag().getValue());
            userBOS.add(userBO);
        }
        return userBOS;
    }

}
