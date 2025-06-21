package com.platform.mesh.upms.biz.modules.conf.userset.controller;

import com.platform.mesh.upms.biz.modules.conf.userset.domain.dto.ConfUserSetDTO;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.po.ConfUserSet;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.vo.ConfUserSetVO;
import com.platform.mesh.upms.biz.modules.conf.userset.service.IConfUserSetService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 配置用户信息
 * @author 蝉鸣
 */
@Tag(description = "ConfUserSetController", name = "配置用户")
@RestController
@RequestMapping
public class ConfUserSetController extends BaseController{
    @Autowired
    private IConfUserSetService  confUserSetService;

    /**
	 * 功能描述:
	 * 〈获取配置用户列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<ConfUserSetVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取配置用户分页")
	@PostMapping("/conf/user/set/page")
	public Result<PageVO<ConfUserSetVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<ConfUserSet> userSetMPage = MPageUtil.pageEntityToMPage(pageDTO, ConfUserSet.class);
        MPage<ConfUserSet> page = confUserSetService.page(userSetMPage);
        PageVO<ConfUserSetVO> voPage = MPageUtil.convertToVO(page, ConfUserSetVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前配置用户信息〉
     * @param userSetId userSetId
     * @return 正常返回:{@link Result<ConfUserSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前配置用户信息")
    @GetMapping("/conf/user/set/info/{userSetId}")
    public Result<ConfUserSetVO> getUserSetInfoById(@PathVariable("userSetId")Long userSetId) {
        ConfUserSetVO confUserSetVO = confUserSetService.getUserSetInfoById(userSetId);
        return Result.success(confUserSetVO);
    }

    /**
     * 功能描述:
     * 〈新增配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link Result<ConfUserSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增配置用户")
    @Log(moduleName = "配置用户管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/conf/user/set/add")
    public Result<ConfUserSetVO> addUserSet(@Validated @RequestBody ConfUserSetDTO userSetDTO) {
        return Result.success(confUserSetService.addUserSet(userSetDTO));
    }

    /**
     * 功能描述:
     * 〈修改配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link Result<ConfUserSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改配置用户")
    @Log(moduleName = "配置用户管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/conf/user/set/edit")
    public Result<ConfUserSetVO> editUserSet(@Validated @RequestBody ConfUserSetDTO userSetDTO) {
        return Result.success(confUserSetService.editUserSet(userSetDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除配置用户〉
     * @param userSetId userSetId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除配置用户")
    @Log(moduleName = "配置用户管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/conf/user/set/delete/{userSetId}")
    public Result<Boolean> deleteUserSet(@PathVariable(value = "userSetId",required = false)Long userSetId) {
        return Result.success(confUserSetService.deleteUserSet(userSetId));
    }

}