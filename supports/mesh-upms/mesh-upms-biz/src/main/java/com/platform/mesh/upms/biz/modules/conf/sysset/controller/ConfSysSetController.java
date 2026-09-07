package com.platform.mesh.upms.biz.modules.conf.sysset.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetPageDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.vo.ConfSysSetVO;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.IConfSysSetService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 配置系统信息
 * @author 蝉鸣
 */
@Tag(description = "ConfSysSetController", name = "配置系统")
@RestController
@RequestMapping
public class ConfSysSetController extends BaseController{
    @Autowired
    private IConfSysSetService  confSysSetService;

    /**
	 * 功能描述:
	 * 〈获取配置系统列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<ConfSysSetVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取配置系统分页")
	@PostMapping("/conf/sys/set/page")
	public Result<PageVO<ConfSysSetVO>> selectPage(@RequestBody ConfSysSetPageDTO pageDTO) {
        PageVO<ConfSysSetVO> voPage = confSysSetService.selectPage(pageDTO);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前配置系统信息〉
     * @param sysSetId sysSetId
     * @return 正常返回:{@link Result<ConfSysSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前配置系统信息")
    @GetMapping("/conf/sys/set/info/{sysSetId}")
    public Result<ConfSysSetVO> getSysSetInfoById(@PathVariable("sysSetId")Long sysSetId) {
        ConfSysSetVO confSysSetVO = confSysSetService.getSysSetInfoById(sysSetId);
        return Result.success(confSysSetVO);
    }

    /**
     * 功能描述:
     * 〈获取当前配置系统信息〉
     * @param confMac confMac
     * @return 正常返回:{@link Result<ConfSysSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前配置系统信息")
    @GetMapping("/conf/sys/set/by/mac/{confMac}")
    public Result<ConfSysSetVO> getSysSetInfoByMac(@PathVariable("confMac")String confMac) {
        ConfSysSetVO confSysSetVO = confSysSetService.getSysSetInfoByMac(confMac);
        return Result.success(confSysSetVO);
    }

    /**
     * 功能描述:
     * 〈新增配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link Result<ConfSysSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增配置系统")
    @Log(moduleName = "配置系统管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/conf/sys/set/add")
    public Result<ConfSysSetVO> addSysSet(@Validated @RequestBody ConfSysSetDTO sysSetDTO) {
        return Result.success(confSysSetService.addSysSet(sysSetDTO));
    }

    /**
     * 功能描述:
     * 〈修改配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link Result<ConfSysSetVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改配置系统")
    @Log(moduleName = "配置系统管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/conf/sys/set/edit")
    public Result<ConfSysSetVO> editSysSet(@Validated @RequestBody ConfSysSetDTO sysSetDTO) {
        return Result.success(confSysSetService.editSysSet(sysSetDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除配置系统〉
     * @param sysSetId sysSetId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除配置系统")
    @Log(moduleName = "配置系统管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/conf/sys/set/delete/{sysSetId}")
    public Result<Boolean> deleteSysSet(@PathVariable(value = "sysSetId",required = false)Long sysSetId) {
        return Result.success(confSysSetService.deleteSysSet(sysSetId));
    }

}
