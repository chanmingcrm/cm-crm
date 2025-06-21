package com.platform.mesh.upms.biz.modules.conf.userui.controller;

import com.platform.mesh.upms.biz.modules.conf.userui.domain.dto.ConfUserUiDTO;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.po.ConfUserUi;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.vo.ConfUserUiVO;
import com.platform.mesh.upms.biz.modules.conf.userui.service.IConfUserUiService;
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
 * @description 配置UI信息
 * @author 蝉鸣
 */
@Tag(description = "ConfUserUiController", name = "配置UI")
@RestController
@RequestMapping
public class ConfUserUiController extends BaseController{
    @Autowired
    private IConfUserUiService  confUserUiService;

    /**
	 * 功能描述:
	 * 〈获取配置UI列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<ConfUserUiVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取配置UI分页")
	@PostMapping("/conf/user/ui/page")
	public Result<PageVO<ConfUserUiVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<ConfUserUi> userUiMPage = MPageUtil.pageEntityToMPage(pageDTO, ConfUserUi.class);
        MPage<ConfUserUi> page = confUserUiService.page(userUiMPage);
        PageVO<ConfUserUiVO> voPage = MPageUtil.convertToVO(page, ConfUserUiVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前配置UI信息〉
     * @param userUiId userUiId
     * @return 正常返回:{@link Result<ConfUserUiVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前配置UI信息")
    @GetMapping("/conf/user/ui/info/{userUiId}")
    public Result<ConfUserUiVO> getUserUiInfoById(@PathVariable("userUiId")Long userUiId) {
        ConfUserUiVO confUserUiVO = confUserUiService.getUserUiInfoById(userUiId);
        return Result.success(confUserUiVO);
    }

    /**
     * 功能描述:
     * 〈新增配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link Result<ConfUserUiVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增配置UI")
    @Log(moduleName = "配置UI管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/conf/user/ui/add")
    public Result<ConfUserUiVO> addUserUi(@Validated @RequestBody ConfUserUiDTO userUiDTO) {
        return Result.success(confUserUiService.addUserUi(userUiDTO));
    }

    /**
     * 功能描述:
     * 〈修改配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link Result<ConfUserUiVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改配置UI")
    @Log(moduleName = "配置UI管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/conf/user/ui/edit")
    public Result<ConfUserUiVO> editUserUi(@Validated @RequestBody ConfUserUiDTO userUiDTO) {
        return Result.success(confUserUiService.editUserUi(userUiDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除配置UI〉
     * @param userUiId userUiId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除配置UI")
    @Log(moduleName = "配置UI管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/conf/user/ui/delete/{userUiId}")
    public Result<Boolean> deleteUserUi(@PathVariable(value = "userUiId",required = false)Long userUiId) {
        return Result.success(confUserUiService.deleteUserUi(userUiId));
    }

}