package com.platform.mesh.app.biz.modules.app.baseuser.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.baseuser.domain.dto.AppBaseUserDTO;
import com.platform.mesh.app.biz.modules.app.baseuser.domain.po.AppBaseUser;
import com.platform.mesh.app.biz.modules.app.baseuser.domain.vo.AppBaseUserVO;
import com.platform.mesh.app.biz.modules.app.baseuser.service.IAppBaseUserService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 快捷应用信息
 * @author 蝉鸣
 */
@Tag(description = "AppBaseUserController", name = "快捷应用")
@RestController
public class AppBaseUserController extends BaseController{

    @Autowired
    private IAppBaseUserService appBaseUserService;

    /**
	 * 功能描述:
	 * 〈获取快捷应用列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppBaseUserVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取快捷应用分页")
	@PostMapping("/app/base/user/page")
	public Result<PageVO<AppBaseUserVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<AppBaseUser> userMPage = MPageUtil.pageEntityToMPage(pageDTO, AppBaseUser.class);
        MPage<AppBaseUser> page = appBaseUserService.lambdaQuery().orderByAsc(AppBaseUser::getSort).page(userMPage);
        PageVO<AppBaseUserVO> voPage = MPageUtil.convertToVO(page, AppBaseUserVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈新增快捷应用〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<AppBaseUserVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增快捷应用")
    @Log(moduleName = "快捷应用管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/base/user/add")
    public Result<AppBaseUserVO> addBase(@Validated @RequestBody AppBaseUserDTO baseDTO) {
        AppBaseUser appBaseUser = BeanUtil.copyProperties(baseDTO, AppBaseUser.class);
        appBaseUserService.save(appBaseUser);
        return Result.success(BeanUtil.copyProperties(appBaseUser, AppBaseUserVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除快捷应用〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除快捷应用")
    @Log(moduleName = "快捷应用管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/base/user/delete/{baseId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "baseId",required = false)Long baseId) {
        return Result.success(appBaseUserService.removeById(baseId));
    }

}