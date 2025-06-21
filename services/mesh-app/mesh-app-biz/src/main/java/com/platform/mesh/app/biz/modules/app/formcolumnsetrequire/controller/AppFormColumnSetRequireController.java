package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireQueryDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.IAppFormColumnSetRequireService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 单字段请求信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSetRequireController", name = "单字段请求")
@RestController
@RequestMapping
public class AppFormColumnSetRequireController extends BaseController{
    @Autowired
    private IAppFormColumnSetRequireService appFormColumnSetRequireService;


    /**
     * 功能描述:
     * 〈获取请求分页列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<AppFormColumnSetEventVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取单字段事件分页")
    @PostMapping("/app/form/column/set/require/page")
    public Result<PageVO<AppFormColumnSetRequireVO>> selectPage(@RequestBody AppFormColumnSetRequireQueryDTO pageDTO) {
        MPage<AppFormColumnSetRequire> page = appFormColumnSetRequireService.getFormColumnSetRequirePage(pageDTO);
        PageVO<AppFormColumnSetRequireVO> voPage = MPageUtil.convertToVO(page, AppFormColumnSetRequireVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈根据ID获取信息获取当前单字段请求信息〉
     * @param requireId requireId
     * @return 正常返回:{@link Result<AppFormColumnSetRequireVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段请求信息")
    @GetMapping("/app/form/column/set/require/info/by/{requireId}")
    public Result<AppFormColumnSetRequireVO> getFormColumnSetRequireInfo(@PathVariable(value = "requireId",required = false) Long requireId) {
        AppFormColumnSetRequire appFormColumnSetRequire = appFormColumnSetRequireService.getById(requireId);
        return Result.success(BeanUtil.copyProperties(appFormColumnSetRequire, AppFormColumnSetRequireVO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前单字段请求信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link Result<AppFormColumnSetRequireVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段请求信息")
    @GetMapping("/app/form/column/set/require/info")
    public Result<List<AppFormColumnSetRequireVO>> getFormColumnSetRequireInfo(@RequestBody AppFormColumnSetRequireQueryDTO queryDTO) {
        List<AppFormColumnSetRequireVO> appFormColumnSetRequireVOs = appFormColumnSetRequireService.getFormColumnSetRequireInfo(queryDTO);
        return Result.success(appFormColumnSetRequireVOs);
    }

    /**
     * 功能描述:
     * 〈新增单字段请求〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link Result<AppFormColumnSetRequireVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段请求")
    @Log(moduleName = "单字段请求管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/set/require/add")
    public Result<AppFormColumnSetRequireVO> addFormColumnSetRequire(@Validated @RequestBody AppFormColumnSetRequireDTO formColumnSetRequireDTO) {
        return Result.success(appFormColumnSetRequireService.addFormColumnSetRequire(formColumnSetRequireDTO));
    }

    /**
     * 功能描述:
     * 〈修改单字段请求〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link Result<AppFormColumnSetRequireVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改单字段请求")
    @Log(moduleName = "单字段请求管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/column/set/require/edit")
    public Result<AppFormColumnSetRequireVO> editFormColumnSetRequire(@Validated @RequestBody AppFormColumnSetRequireDTO formColumnSetRequireDTO) {
        return Result.success(appFormColumnSetRequireService.editFormColumnSetRequire(formColumnSetRequireDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除单字段请求〉
     * @param formColumnSetRequireId formColumnSetRequireId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段请求")
    @Log(moduleName = "单字段请求管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/set/require/delete/{formColumnSetRequireId}")
    public Result<Boolean> deleteFormColumnSetRequire(@PathVariable(value = "formColumnSetRequireId",required = false)Long formColumnSetRequireId) {
        return Result.success(appFormColumnSetRequireService.deleteFormColumnSetRequire(formColumnSetRequireId));
    }

}