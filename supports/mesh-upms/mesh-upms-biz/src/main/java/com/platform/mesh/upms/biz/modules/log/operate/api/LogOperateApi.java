package com.platform.mesh.upms.biz.modules.log.operate.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
import com.platform.mesh.upms.biz.modules.log.operate.domain.po.LogOperate;
import com.platform.mesh.upms.biz.modules.log.operate.service.ILogOperateService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 约定当前controller 只引入当前service
 * @description 操作日志信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "LogOperateApi", name = "操作日志信息")
@RestController
public class LogOperateApi extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private ILogOperateService logOperateService;

    /**
     * 功能描述:
     * 〈新增登录日志〉
     * @param logOperateBO logOperateBO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "新增操作日志")
    @PostMapping(value ="/api/log/operate/add", headers = HttpConst.HEADER_FROM_IN)
    public Result<Boolean> addOperateLog(@Validated @RequestBody LogOperateBO logOperateBO) {
        LoginUserBO loginUser = UserCacheUtil.getLoginUser();
        if (loginUser != null && loginUser.getAccountId() != null
                && logOperateBO.getAccountId() != null
                && !loginUser.getAccountId().equals(logOperateBO.getAccountId())) {
            return Result.error(HttpStatus.FORBIDDEN.value(), "操作日志账号与调用身份不一致");
        }
        LogOperate logOperate = BeanUtil.copyProperties(logOperateBO, LogOperate.class);
        SysAccountBO sysAccountBO = UserCacheUtil.getAccountInfoCache(logOperateBO.getAccountId());
        if(ObjectUtil.isNotEmpty(sysAccountBO)){
            logOperate.setScopeUserId(sysAccountBO.getUserId());
            logOperate.setScopeOrgId(sysAccountBO.getScopeOrgId());
        }
        logOperate.setCreateTime(LocalDateTime.now());
        logOperateService.save(logOperate);
        return Result.success();
    }


}
