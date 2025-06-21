package com.platform.mesh.upms.biz.modules.dict.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBaseDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBasePageDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import com.platform.mesh.upms.biz.modules.dict.base.domain.vo.DictBaseVO;
import com.platform.mesh.upms.biz.modules.dict.base.service.IDictBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 字典基础信息
 * @author 蝉鸣
 */
@Tag(description = "DictBaseController", name = "字典基础")
@RestController
@RequestMapping
public class DictBaseController extends BaseController{
    @Autowired
    private IDictBaseService dictBaseService;

    /**
	 * 功能描述:
	 * 〈获取字典基础列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DictBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取字典基础分页")
	@PostMapping("/dict/base/page")
	public Result<PageVO<DictBaseVO>> selectPage(@RequestBody DictBasePageDTO pageDTO) {
        MPage<DictBase> page = dictBaseService.selectPage(pageDTO);
        return Result.success(MPageUtil.convertToVO(page, DictBaseVO.class));
	}

    /**
     * 功能描述:
     * 〈获取当前字典基础信息〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<DictBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前字典基础信息")
    @GetMapping("/dict/base/info/{baseId}")
    public Result<DictBaseVO> getBaseInfoById(@PathVariable("baseId")Long baseId) {
        DictBaseVO dictBaseVO = dictBaseService.getBaseInfoById(baseId);
        return Result.success(dictBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<DictBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增字典基础")
    @Log(moduleName = "字典基础管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/dict/base/add")
    public Result<DictBaseVO> addBase(@Validated @RequestBody DictBaseDTO baseDTO) {
        return Result.success(dictBaseService.addBase(baseDTO));
    }

    /**
     * 功能描述:
     * 〈修改字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<DictBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改字典基础")
    @Log(moduleName = "字典基础管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/dict/base/edit")
    public Result<DictBaseVO> editBase(@Validated @RequestBody DictBaseDTO baseDTO) {
        return Result.success(dictBaseService.editBase(baseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除字典基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除字典基础")
    @Log(moduleName = "字典基础管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/dict/base/delete/{baseId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "baseId",required = false)Long baseId) {
        return Result.success(dictBaseService.deleteBase(baseId));
    }

    /**
     * 功能描述:
     * 〈获取所有的子项〉
     * @param baseId baseId
     * @return 正常返回:{@link List<DictBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取所有的子项")
    @PostMapping("/dict/base/child/{baseId}")
    public Result<List<DictBaseVO>> getChildDict(@PathVariable(value = "baseId",required = false)Long baseId) {
        List<DictBase> childDict = dictBaseService.getChildDict(baseId);
        return Result.success(BeanUtil.copyToList(childDict, DictBaseVO.class));
    }

}