package com.platform.mesh.upms.biz.soa.org.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.teaopenapi.models.Config;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.sys.user.enums.AdminFlagEnum;
import com.platform.mesh.upms.biz.soa.org.ThirdOrgService;
import com.platform.mesh.upms.biz.soa.org.constant.DingConst;
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
public class DingTalkFactoryImpl implements ThirdOrgService {

    private static final Logger log = LoggerFactory.getLogger(DingTalkFactoryImpl.class);

    @Autowired
    private IOrgLevelService orgLevelService;

    @Override
    public SourceFlagEnum sourceFlag() {
        return SourceFlagEnum.DING;
    }

    /**
     * 功能描述:
     * 〈同步部门〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    @Override
    public List<OrgLevel> syncDeptList(TenantClientBO clientBO) {
        //获取access_token
        String accessToken = getAccessToken(clientBO);
        //收集返回数据
        List<OrgLevel> orgLevels = CollUtil.newArrayList();
        //查询部门
        this.getDingDeptList(accessToken,NumberConst.NUM_0.longValue(),NumberConst.NUM_1.longValue(), orgLevels);
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
        //获取同步的部门列表
        List<OrgLevel> orgLevels = orgLevelService.lambdaQuery().eq(OrgLevel::getLevelSource, this.sourceFlag().getValue()).list();
        if(CollUtil.isEmpty(orgLevels)){
            return CollUtil.newArrayList();
        }
        List<String> deptIds = orgLevels.stream().map(OrgLevel::getThirdId).distinct().toList();
        //获取access_token
        String accessToken = getAccessToken(clientBO);
        List<SyncUserBO> sysUsers = CollUtil.newArrayList();
        //循环查询部门下人员
        for (String deptId : deptIds) {
            this.getDingUserList(accessToken, deptId, NumberConst.NUM_0.longValue(), sysUsers);
        }
        return sysUsers;
    }

    /**
     * 功能描述:
     * 〈获取接口授权〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    public String getAccessToken(TenantClientBO clientBO) {
        // 应用凭证，应用 Client ID 和 Client Secret
        String APP_KEY = clientBO.getClientId();
        String APP_SECRET = clientBO.getClientSecret();
        Config config = new Config();
        config.protocol = DingConst.CONFIG_PROTOCOL;
        config.regionId = DingConst.CONFIG_REGIONID;
        try {
            Client client = new Client(config);
            // 构建获取access_token的请求
            GetAccessTokenRequest request = new GetAccessTokenRequest()
                    .setAppKey(APP_KEY)
                    .setAppSecret(APP_SECRET);
            // 发送请求获取access_token
            return client.getAccessToken(request).getBody().getAccessToken();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈获取部门列表〉
     * @param accessToken accessToken
     * @param deptId deptId
     * @author 蝉鸣
     */
    public void getDingDeptList(String accessToken,Long deptId, Long thirdId, List<OrgLevel> orgLevels) {
        DingTalkClient client = new DefaultDingTalkClient(DingConst.DEPT_URL);
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(thirdId);
        req.setLanguage(DingConst.SET_LANGUAGE);
        try {
            OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
            List<OapiV2DepartmentListsubResponse.DeptBaseResponse> result = rsp.getResult();
            if(CollUtil.isEmpty(result)){
                return;
            }
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse response : result) {
                OrgLevel orgLevel = new OrgLevel();
                orgLevel.setId(IdUtil.getSnowflakeNextId());
                orgLevel.setThirdId(response.getDeptId().toString());
                orgLevel.setRootId(NumberConst.NUM_0.longValue());
                orgLevel.setParentId(deptId);
                orgLevel.setLevelName(response.getName());
                orgLevel.setLevelFlag(LevelFlagEnum.DEPT.getValue());
                orgLevel.setLevelSource(this.sourceFlag().getValue());
                orgLevels.add(orgLevel);
                getDingDeptList(accessToken, orgLevel.getId(), response.getDeptId(),orgLevels);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈获取接口授权〉
     * @param accessToken accessToken
     * @param deptId deptId
     * @param nextCursor nextCursor
     * @author 蝉鸣
     */
    public void getDingUserList(String accessToken,String deptId,Long nextCursor,List<SyncUserBO> sysUsers) {
        DingTalkClient client = new DefaultDingTalkClient(DingConst.USER_URL);
        OapiV2UserListRequest req = new OapiV2UserListRequest();
        req.setDeptId(Long.parseLong(deptId));
        req.setCursor(nextCursor);
        req.setSize(NumberConst.NUM_10.longValue());
        req.setOrderField(DingConst.SET_ORDER_FIELD);
        req.setContainAccessLimit(Boolean.FALSE);
        req.setLanguage(DingConst.SET_LANGUAGE);
        try {
            OapiV2UserListResponse rsp = client.execute(req, accessToken);
            OapiV2UserListResponse.PageResult result = rsp.getResult();
            List<OapiV2UserListResponse.ListUserResponse> resultList = result.getList();
            for (OapiV2UserListResponse.ListUserResponse userResponse : resultList) {
                SyncUserBO sysUser = new SyncUserBO();
                sysUser.setName(userResponse.getName());
                sysUser.setPhone(userResponse.getMobile());
                sysUser.setAvatar(userResponse.getAvatar());
                sysUser.setSourceFlag(this.sourceFlag().getValue());
                sysUser.setUserId(userResponse.getUserid());
                sysUser.setUnionId(userResponse.getUnionid());
                sysUser.setLevelIds(userResponse.getDeptIdList().stream().map(StrUtil::toString).toList());
                if(userResponse.getAdmin()){
                    sysUser.setIsAdmin(AdminFlagEnum.TENANT.getValue());
                }else{
                    sysUser.setIsAdmin(AdminFlagEnum.COMMON.getValue());
                }
                sysUsers.add(sysUser);
            }
            if(result.getHasMore()){
                getDingUserList(accessToken,deptId,result.getNextCursor(),sysUsers);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
