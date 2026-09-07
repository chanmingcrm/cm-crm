package com.platform.mesh.upms.biz.third.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.lark.oapi.Client;
import com.lark.oapi.core.enums.AppType;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.service.contact.v3.model.*;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.uaa.api.modules.tenant.RemoteTenantService;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.service.ISysUserService;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.ISysUserRoleRelService;
import com.platform.mesh.upms.biz.soa.org.ThirdOrgService;
import com.platform.mesh.upms.biz.soa.org.domain.bo.SyncUserBO;
import com.platform.mesh.upms.biz.soa.org.factory.ThirdOrgFactory;
import com.platform.mesh.upms.biz.third.service.IThirdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 第三方服务对接
 * @author 蝉鸣
 */
@Service
public class ThirdServiceImpl implements IThirdService {

    private final static Logger log = LoggerFactory.getLogger(ThirdServiceImpl.class);

    @Autowired
    private ThirdOrgFactory thirdOrgFactory;

    @Autowired
    private IOrgLevelService orgLevelService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysAccountService sysAccountService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleRelService sysUserRoleRelService;

    @Autowired
    private IOrgMemberService orgMemberService;

    @Autowired
    private IOrgMemberUserRelService orgMemberUserRelService;

    @Autowired
    private IOrgLevelPostRelService orgLevelPostRelService;

    @Autowired
    private IOrgMemberPostRelService orgMemberPostRelService;

    @Autowired
    private RemoteTenantService remoteTenantService;

    @Override
    public void thirdTest(Integer levelSource) {
        if(1==levelSource){

        } else if (2 == levelSource) {
            dingtalkTest();
        }else if (3 == levelSource) {
            wxWorkTest();
        }else if (4==levelSource) {
            feishuTest();
        }
        thirdOrgSync(levelSource);
    }

    /**
     * 功能描述:
     * 〈同步组织架构〉
     * @param levelSource levelSource
     * @author 蝉鸣
     */
    public void thirdOrgSync(Integer levelSource) {
        SourceFlagEnum enumByValue = BaseEnum.getEnumByValue(SourceFlagEnum.class, levelSource);
        ThirdOrgService thirdOrgService = thirdOrgFactory.getThirdOrgService(enumByValue);
        if(ObjectUtil.isEmpty(thirdOrgService)){
            return;
        }
        //获取当前配置
        TenantClientBO clientBO = remoteTenantService.getTenantClientInfo(levelSource).getData();
        if(ObjectUtil.isEmpty(clientBO)){
            return;
        }
        //拉取组织信息
        List<OrgLevel> orgLevels = thirdOrgService.syncDeptList(clientBO);
        //同步组织信息
        thirdLevelSync(orgLevels);
        //拉取人员信息
        List<SyncUserBO> syncUserBOS = thirdOrgService.syncUserList(clientBO);
        //同步人员信息
        thirdUserSync(syncUserBOS);
    }

    /**
     * 功能描述:
     * 〈同步组织〉
     * @param orgLevels orgLevels
     * @author 蝉鸣
     */
    public void thirdLevelSync(List<OrgLevel> orgLevels) {
        if(CollUtil.isEmpty(orgLevels)){
            return;
        }
        OrgLevel orgLevel = CollUtil.getFirst(orgLevels);
        //排除已经存在的组织部门
        List<String> thirdIds = orgLevels.stream().map(OrgLevel::getThirdId).distinct().toList();
        List<OrgLevel> exists = orgLevelService.lambdaQuery()
                .eq(OrgLevel::getLevelSource, orgLevel.getLevelSource())
                .in(OrgLevel::getThirdId, thirdIds)
                .list();
        if(CollUtil.isNotEmpty(exists)){
            List<String> existIds = exists.stream().map(OrgLevel::getThirdId).distinct().toList();
            orgLevels.removeIf(level->existIds.contains(level.getThirdId()));
        }
        //全部已存在
        if(CollUtil.isEmpty(orgLevels)){
            return;
        }
        //不存在的进行新增
        List<String> levelNames = orgLevels.stream().map(OrgLevel::getLevelName).distinct().toList();
        List<OrgLevel> sysLevels = orgLevelService.lambdaQuery()
                .eq(OrgLevel::getLevelSource, SourceFlagEnum.SYSTEM.getValue())
                .in(OrgLevel::getLevelName, levelNames)
                .list();
        if(CollUtil.isNotEmpty(sysLevels)){
            Map<String, Long> idMap = sysLevels.stream().collect(Collectors.toMap(OrgLevel::getLevelName, OrgLevel::getId, (v1, v2) -> v1));
            for (OrgLevel level : orgLevels) {
                if(idMap.containsKey(orgLevel.getLevelName())) {
                    level.setSysRelId(idMap.get(orgLevel.getLevelName()));
                }
            }
        }
        //添加新的组织数据
        orgLevelService.saveBatch(orgLevels);
    }

    /**
     * 功能描述:
     * 〈同步人员〉
     * @param syncUserBOS syncUserBOS
     * @author 蝉鸣
     */
    public void thirdUserSync(List<SyncUserBO> syncUserBOS) {
        //初始化人员信息
        List<SysUser> sysUsers = syncUser(syncUserBOS);
        //初始化账户信息
        List<SysAccount> sysAccounts = syncAccount(syncUserBOS, sysUsers);
        //初始化人员角色
        syncUserRoleRel(sysUsers);
        //初始化成员信息
        List<OrgMemberUserRel> memberUserRels = syncUserMemberRel(sysUsers);
        //初始化成员岗位信息
        Map<Long, OrgMemberPostRel> postRelMap = syncUserPostRel(syncUserBOS, sysUsers, memberUserRels);
    }

    /**
     * 功能描述:
     * 〈同步人员〉
     * @param syncUserBOS syncUserBOS
     * @author 蝉鸣
     */
    public List<SysUser> syncUser(List<SyncUserBO> syncUserBOS) {
        if(CollUtil.isEmpty(syncUserBOS)){
            return CollUtil.newArrayList();
        }
        List<String> phoneList = syncUserBOS.stream().map(SyncUserBO::getPhone).filter(StrUtil::isNotBlank).toList();
        if(CollUtil.isEmpty(phoneList)){
            return CollUtil.newArrayList();
        }
        //查询存在的手机号码
        List<SysUser> sysUsers = sysUserService.lambdaQuery().in(SysUser::getPhone, phoneList).list();
        Map<String, SysUser> userMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getPhone, Function.identity()));
        List<SysUser> userList = syncUserBOS.stream()
                .filter(userBO->!userMap.containsKey(userBO.getPhone()))
                .map(userBO -> {
                    SysUser sysUser = new SysUser();
                    sysUser.setNickName(userBO.getName());
                    sysUser.setPhone(userBO.getPhone());
                    sysUser.setAdminFlag(userBO.getIsAdmin());
                    sysUser.setUserFlag(userBO.getIsActive());
                    return sysUser;
        }).toList();
        sysUserService.saveOrUpdateBatch(userList);
        sysUsers.addAll(userList);
        return sysUsers;
    }

    /**
     * 功能描述:
     * 〈同步账户〉
     * @param syncUserBOS syncUserBOS
     * @author 蝉鸣
     */
    public List<SysAccount> syncAccount(List<SyncUserBO> syncUserBOS,List<SysUser> sysUsers) {
        if(CollUtil.isEmpty(sysUsers)){
            return CollUtil.newArrayList();
        }
        List<Long> userIds = sysUsers.stream().map(SysUser::getUserId).toList();
        Map<String, SyncUserBO> syncMap = syncUserBOS.stream().collect(Collectors.toMap(SyncUserBO::getPhone, Function.identity(),(v1,v2) -> v1));
        List<SysAccount> sysAccounts = sysAccountService.lambdaQuery().in(SysAccount::getUserId, userIds).list();
        Map<Long, Map<Integer, SysAccount>> accountMap = sysAccounts
                .stream()
                .collect(Collectors.groupingBy(SysAccount::getUserId, Collectors.toMap(SysAccount::getSourceFlag, Function.identity())));
        List<SysAccount> syncAccounts = CollUtil.newArrayList();
        for (SysUser sysUser : sysUsers) {
            Map<Integer, SysAccount> sysAccountMap = accountMap.get(sysUser.getUserId());
            if(CollUtil.isEmpty(sysAccountMap)){
                continue;
            }
            //是否有短信账户
            SysAccount sysAccount;
            if(sysAccountMap.containsKey(SourceFlagEnum.SMS.getValue())){
                sysAccount = sysAccountMap.get(SourceFlagEnum.SMS.getValue());
            }else{
                sysAccount = getSysAccount(sysUser, SourceFlagEnum.SMS.getValue());
                syncAccounts.add(sysAccount);
            }
            //是否有第三方账户
            SyncUserBO syncUserBO = syncMap.get(sysUser.getPhone());
            if(!sysAccountMap.containsKey(syncUserBO.getSourceFlag())){
                SysAccount thirdAccount = getSysAccount(sysUser, syncUserBO.getSourceFlag());
                thirdAccount.setThirdUserId(syncUserBO.getUserId());
                thirdAccount.setThirdUnionId(syncUserBO.getUnionId());
                thirdAccount.setAccountCode(syncUserBO.getUnionId());
                thirdAccount.setAccountCode(syncUserBO.getUnionId());
                thirdAccount.setScopeRootId(sysAccount.getScopeRootId());
                thirdAccount.setScopeOrgId(sysAccount.getScopeOrgId());
                syncAccounts.add(thirdAccount);
            }
        }
        sysAccountService.saveOrUpdateBatch(syncAccounts);
        return syncAccounts;
    }

    /**
     * 功能描述:
     * 〈同步用户角色信息〉
     * @param sysUsers sysUsers
     * @author 蝉鸣
     */
    public void syncUserRoleRel(List<SysUser> sysUsers) {
        if(CollUtil.isEmpty(sysUsers)){
            return;
        }
        //查询默认角色
        List<SysRole> roleList = sysRoleService.lambdaQuery()
                .eq(SysRole::getInitFlag, YesOrNoEnum.NO.getValue())
                .eq(SysRole::getDelFlag, YesOrNoEnum.YES.getValue())
                .list();
        if(roleList.isEmpty()){
            return;
        }
        SysRole sysRole = CollUtil.getFirst(roleList);
        List<Long> userIds = sysUsers.stream().map(SysUser::getUserId).toList();
        List<SysUserRoleRel> roleRels = sysUserRoleRelService.lambdaQuery().in(SysUserRoleRel::getUserId, userIds).list();
        Map<Long, List<SysUserRoleRel>> relMap = roleRels.stream().collect(Collectors.groupingBy(SysUserRoleRel::getUserId));
        List<SysUserRoleRel> relList = CollUtil.newArrayList();
        sysUsers.stream().filter(sysUser -> !relMap.containsKey(sysUser.getUserId())).forEach(sysUser -> {
            SysUserRoleRel roleRel = new SysUserRoleRel();
            roleRel.setUserId(sysUser.getUserId());
            roleRel.setRoleId(sysRole.getId());
            roleRel.setRoleId(sysRole.getId());
            roleRel.setInitFlag(YesOrNoEnum.NO.getValue());
            relList.add(roleRel);
        });
        sysUserRoleRelService.saveBatch(relList);
    }

    /**
     * 功能描述:
     * 〈同步成员信息〉
     * @param sysUsers sysUsers
     * @author 蝉鸣
     */
    public List<OrgMemberUserRel> syncUserMemberRel(List<SysUser> sysUsers) {
        List<OrgMemberUserRel> relList = CollUtil.newArrayList();
        if(CollUtil.isEmpty(sysUsers)){
            return relList;
        }
        List<Long> userIds = sysUsers.stream().map(SysUser::getUserId).toList();
        if(CollUtil.isEmpty(userIds)){
            return relList;
        }
        List<OrgMemberUserRel> memberUserRels = orgMemberUserRelService.lambdaQuery().in(OrgMemberUserRel::getUserId, userIds).list();
        Map<Long, List<OrgMemberUserRel>> relMap = memberUserRels.stream().collect(Collectors.groupingBy(OrgMemberUserRel::getUserId));
        List<OrgMember> memList = CollUtil.newArrayList();
        sysUsers.stream().filter(sysUser -> !relMap.containsKey(sysUser.getUserId())).forEach(sysUser -> {
            OrgMember member = new OrgMember();
            member.setId(IdUtil.getSnowflakeNextId());
            member.setMemberName(sysUser.getNickName());
            memList.add(member);
            OrgMemberUserRel rel = new OrgMemberUserRel();
            rel.setUserId(sysUser.getUserId());
            rel.setMemberId(member.getId());
            relList.add(rel);
        });
        orgMemberService.saveBatch(memList);
        orgMemberUserRelService.saveBatch(relList);
        relList.addAll(memberUserRels);
        return relList;
    }

    /**
     * 功能描述:
     * 〈同步组织岗位信息〉
     * @param sysUsers sysUsers
     * @author 蝉鸣
     */
    public  Map<Long,OrgMemberPostRel> syncUserPostRel(List<SyncUserBO> syncUserBOS,List<SysUser> sysUsers,List<OrgMemberUserRel> memberUserRels) {
        if(CollUtil.isEmpty(syncUserBOS) || CollUtil.isEmpty(sysUsers) || CollUtil.isEmpty(memberUserRels)){
            return new HashMap<>();
        }
        //所有的第三方部门ID
        List<String> thirdIds = syncUserBOS.stream().map(SyncUserBO::getLevelIds).flatMap(Collection::stream).toList();
        //根据第三方部门ID查询部门信息
        List<OrgLevel> levelList = orgLevelService.lambdaQuery().in(OrgLevel::getThirdId, thirdIds).list();
        if(CollUtil.isEmpty(levelList)){
            return new HashMap<>();
        }
        //第三方部门关联ID对应的系统部门ID
        List<Long> levelIds = levelList.stream().map(OrgLevel::getSysRelId).filter(ObjectUtil::isNotEmpty).toList();
        if(CollUtil.isEmpty(levelIds)){
            //未绑定任何系统部门
            return new HashMap<>();
        }
        //部门ID关联的所有岗位信息
        List<OrgLevelPostRel> levelPostRelList = orgLevelPostRelService.lambdaQuery().in(OrgLevelPostRel::getLevelId, levelIds).list();
        //第三方部门ID与本系统部门ID关联
        Map<String, Long> levelMap = levelList.stream().collect(Collectors.toMap(OrgLevel::getThirdId,OrgLevel::getSysRelId));
        //部门与岗位关联Map
        Map<Long, List<OrgLevelPostRel>> postRelMap = levelPostRelList.stream().collect(Collectors.groupingBy(OrgLevelPostRel::getLevelId));
        //第三方与用户与部门关联Map
        Map<String, List<String>> syncMap = syncUserBOS.stream().collect(Collectors.toMap(SyncUserBO::getPhone, SyncUserBO::getLevelIds));
        //用户手机号码Map
        Map<Long, String> userMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getPhone));
        //需要添加的成员
        List<Long> memberIds = memberUserRels.stream().map(OrgMemberUserRel::getMemberId).toList();
        if(CollUtil.isEmpty(memberIds)){
            //未添加的成员
            return new HashMap<>();
        }
        //查询已经存在的岗位关联信息
        List<OrgMemberPostRel> memberPostRels = orgMemberPostRelService.lambdaQuery().eq(OrgMemberPostRel::getMemberId, memberIds).list();
        Map<Long, List<OrgMemberPostRel>> memRelMap = memberPostRels.stream().collect(Collectors.groupingBy(OrgMemberPostRel::getMemberId));
        //不存在岗位关联则新增
        List<OrgMemberPostRel> relList = CollUtil.newArrayList();
        Map<Long,OrgMemberPostRel> userLevelMap = new HashMap<>();
        memberUserRels.stream().filter(member -> !memRelMap.containsKey(member.getMemberId())).forEach(member -> {
            //设置部门
            String phone = userMap.get(member.getUserId());
            List<String> levels = syncMap.get(phone);
            for (String thirdId : levels) {
                Long levelId = levelMap.get(thirdId);
                if(postRelMap.containsKey(levelId)) {
                    OrgMemberPostRel rel = new OrgMemberPostRel();
                    //设置成员
                    rel.setMemberId(member.getMemberId());
                    List<OrgLevelPostRel> levelPostRels = postRelMap.get(levelId);
                    OrgLevelPostRel postRel = CollUtil.getFirst(levelPostRels);
                    rel.setLevelRootId(postRel.getLevelRootId());
                    rel.setLevelId(postRel.getLevelId());
                    rel.setPostId(postRel.getPostId());
                    relList.add(rel);
                    if(!userLevelMap.containsKey(member.getUserId())){
                        userLevelMap.put(member.getUserId(),rel);
                    }
                }
            }
        });
        //同步岗位关系
        orgMemberPostRelService.saveBatch(relList);
        //返回人员与层级关系
        return userLevelMap;
    }

    /**
     * 功能描述:
     * 〈获取账户信息〉
     * @param sysUser sysUser
     * @param sourceFlag sourceFlag
     * @author 蝉鸣
     */
    public SysAccount getSysAccount(SysUser sysUser,Integer sourceFlag) {
        //初始化短信
        SysAccount sysAccount = new SysAccount();
        sysAccount.setUserId(sysUser.getUserId());
        sysAccount.setAccountCode(sysUser.getPhone());
        sysAccount.setAccountFlag(ActiveFlagEnum.USING.getValue());
        sysAccount.setDelFlag(YesOrNoEnum.YES.getValue());
        sysAccount.setNickName(sysUser.getNickName());
        sysAccount.setSourceFlag(sourceFlag);
        sysAccount.setCreateTime(LocalDateTime.now());
        sysAccount.setUpdateTime(LocalDateTime.now());
        sysAccount.setScopeRootId(NumberConst.NUM_0.longValue());
        sysAccount.setScopeOrgId(NumberConst.NUM_0.longValue());
        return sysAccount;
    }



    public void feishuTest() {
        String appId = "cli_a7fb40801e59500c";
        String appSecret = "jWyZgC2fmJroAoYOmo1Az5FxNxFegh2v";
        // 3. 构建部门列表请求 - 使用正确的类
        Client client = Client.newBuilder(appId, appSecret)
                .openBaseUrl(BaseUrlEnum.FeiShu)
                .appType(AppType.SELF_BUILT).build();
        ListDepartmentReq req = ListDepartmentReq.newBuilder()
                .pageToken("") // 分页token，首次请求为空
                .pageSize(50) // 每页大小
                .build();
        List<Department> departmentList = new ArrayList<>();
        try {
            ListDepartmentResp resp = client.contact().v3().department().list(req);
            if (resp.getCode() == 0) {
                ListDepartmentRespBody data = resp.getData();
                Department[] items = data.getItems();
                if (ArrayUtil.isEmpty(items)) {
                    return;
                }
                for (Department department : data.getItems()) {
                    getDepartmentList(department.getDepartmentId(), departmentList, client);
                }
            } else {
                System.out.println("错误: " + resp.getMsg());
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        departmentList.forEach(item -> {
            System.out.println("部门上级ID: " + item.getParentDepartmentId());
            System.out.println("部门ID: " + item.getDepartmentId());
            System.out.println("部门名称: " + item.getName());
            System.out.println("部门对象: " + JSONUtil.toJsonStr(item));
        });

        List<User> userList = getUserList(departmentList, client);
        userList.forEach(item -> {
            System.out.println("用户ID: " + item.getUserId());
            System.out.println("OpenID: " + item.getOpenId());
            System.out.println("UnionID: " + item.getUnionId());
            System.out.println("用户名称: " + item.getName());
            System.out.println("用户对象: " + JSONUtil.toJsonStr(item));
        });

    }

    void getDepartmentList(String departmentId, List<Department> departmentList, Client client){
        ChildrenDepartmentReq childrenDepartmentReq = new ChildrenDepartmentReq();
        childrenDepartmentReq.setDepartmentId(departmentId);
        try {
            ChildrenDepartmentResp childrenDepartmentResp = client.contact().v3().department().children(childrenDepartmentReq);
            Department[] items = childrenDepartmentResp.getData().getItems();
            if (items == null) {
                return;
            }
            departmentList.addAll(Arrays.asList(items));
            for (Department item : items) {
                getDepartmentList(item.getOpenDepartmentId(), departmentList, client);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


    public List<User> getUserList(List<Department> departmentList, Client client) {
        List<User> userList = new ArrayList<>();
        try {
            if (CollUtil.isEmpty(departmentList)) {
                return userList;
            }
            for (Department department : departmentList) {
                ListUserResp listUserResp = client.contact().v3().user().list(new ListUserReq(ListUserReq.newBuilder().departmentId(department.getOpenDepartmentId())));
                User[] items = listUserResp.getData().getItems();
                if(ArrayUtil.isEmpty(userList)){
                    continue;
                }
                userList.addAll(Arrays.asList(items));
            }
            return userList;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userList;
    }


    public void wxWorkTest() {
        String accessToken = getAccessToken();

        getDeptList(accessToken);

        getUserList(accessToken);
    }

    public String getAccessToken() {
        String uri = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        HashMap<String, Object> map = new HashMap<>();
        map.put("corpid","ww2e363bc0f46f8497");
        map.put("corpsecret","sL8O41fk9YJKbMRv_7Kk5a0rdNdKhLXPd0YYgnZj3aY");
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        String accessToken = response.get("access_token").toString();
        log.info(accessToken);
        return accessToken;
    }

    public void getDeptList(String accessToken) {
        String uri = "https://qyapi.weixin.qq.com/cgi-bin/department/simplelist";
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token",accessToken);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        log.info(response.toString());
    }

    public void getUserList(String accessToken) {
        String uri = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token",accessToken);
        map.put("department_id","1");
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        log.info(response.toString());
    }

    public void dingtalkTest() {
        try {
            getDingClient();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


    public void getDingClient() throws Exception {
        // 应用凭证，应用 Client ID 和 Client Secret
        String APP_KEY = "dingvsx8ogwnvxjez8of";
        String APP_SECRET = "Ik3pL3xfc9hcQ48Xv4cmXMwOm-rfdVcNb2DppKQ0rbakvmWuysEcHOOvqDiqH9CR";
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";

        com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(config);

        // 构建获取access_token的请求
        GetAccessTokenRequest request = new GetAccessTokenRequest()
                .setAppKey(APP_KEY)
                .setAppSecret(APP_SECRET);
        // 发送请求获取access_token
        String accessToken = client.getAccessToken(request).getBody().getAccessToken();
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList = CollUtil.newArrayList();
        getDingDeptList(accessToken,1L,deptList);

        System.out.println("部门："+JSONUtil.toJsonStr(deptList));

        List<OapiV2UserListResponse.ListUserResponse> userList = CollUtil.newArrayList();
        for (OapiV2DepartmentListsubResponse.DeptBaseResponse baseResponse : deptList) {
            getDingUserList(accessToken,baseResponse.getDeptId(),0L,userList);
        }
        System.out.println("成员："+JSONUtil.toJsonStr(userList));
    }

    public void getDingDeptList(String accessToken,Long deptId, List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList) {
        DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        req.setLanguage("zh_CN");
        try {
            OapiV2DepartmentListsubResponse rsp = client1.execute(req, accessToken);
            List<OapiV2DepartmentListsubResponse.DeptBaseResponse> result = rsp.getResult();
            if(CollUtil.isEmpty(result)){
                return;
            }
            deptList.addAll(result);
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse response : result) {
                getDingDeptList(accessToken, response.getDeptId(), deptList);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void getDingUserList(String accessToken,Long deptId,Long nextCursor,List<OapiV2UserListResponse.ListUserResponse> userList) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
            OapiV2UserListRequest req = new OapiV2UserListRequest();
            req.setDeptId(deptId);
            req.setCursor(nextCursor);
            req.setSize(10L);
            req.setOrderField("modify_desc");
            req.setContainAccessLimit(false);
            req.setLanguage("zh_CN");
            OapiV2UserListResponse rsp = client.execute(req, accessToken);
            OapiV2UserListResponse.PageResult result = rsp.getResult();
            List<OapiV2UserListResponse.ListUserResponse> resultList = result.getList();
            userList.addAll(resultList);
            if(result.getHasMore()){
                getDingUserList(accessToken,deptId,result.getNextCursor(),resultList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
