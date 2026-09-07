package com.platform.mesh.app.biz.modules.app.search.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchPageDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.po.AppSearch;
import com.platform.mesh.app.biz.modules.app.search.domain.vo.AppSearchVO;
import com.platform.mesh.app.biz.modules.app.search.service.IAppSearchService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 查询信息
 * @author 蝉鸣
 */
@Tag(description = "AppSearchController", name = "查询")
@RestController
public class AppSearchController extends BaseController {


    @Autowired
    private IAppSearchService appSearchService;

    /**
	 * 功能描述:
	 * 〈获取查询列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppSearchVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取查询分页")
	@PostMapping("/app/search/page")
	public Result<PageVO<AppSearchVO>> selectPage(@RequestBody AppSearchPageDTO pageDTO) {
        MPage<AppSearchVO> page = appSearchService.selectPage(pageDTO);
        PageVO<AppSearchVO> voPage = MPageUtil.convertToVO(page, AppSearchVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前查询信息〉
     * @param searchId searchId
     * @return 正常返回:{@link Result<AppSearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前查询信息")
    @GetMapping("/app/search/info/{searchId}")
    public Result<AppSearchVO> getSearchInfoById(@PathVariable("searchId")Long searchId) {
        AppSearchVO appSearchVO = appSearchService.getSearchInfoById(searchId);
        return Result.success(appSearchVO);
    }

    /**
     * 功能描述:
     * 〈新增查询〉
     * @param searchDTO searchDTO
     * @return 正常返回:{@link Result<AppSearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增查询")
    @PostMapping("/app/search/add")
    public Result<AppSearchVO> addSearch(@Validated @RequestBody AppSearchDTO searchDTO) {
        AppSearch appSearch = appSearchService.addSearch(searchDTO);
        return Result.success(BeanUtil.copyProperties(appSearch, AppSearchVO.class));
    }

    /**
     * 功能描述:
     * 〈修改查询〉
     * @param searchDTO searchDTO
     * @return 正常返回:{@link Result<AppSearchVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改查询")
    @PostMapping("/app/search/edit")
    public Result<AppSearchVO> editSearch(@Validated @RequestBody AppSearchDTO searchDTO) {
        AppSearch appSearch = appSearchService.editSearch(searchDTO);
        return Result.success(BeanUtil.copyProperties(appSearch, AppSearchVO.class));
    }
    
   /**
     * 功能描述:
     * 〈删除查询〉
     * @param searchId searchId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除查询")
    @PostMapping("/app/search/delete/{searchId}")
    public Result<Boolean> deleteSearch(@PathVariable(value = "searchId",required = false)Long searchId) {
        return Result.success(appSearchService.deleteSearch(searchId));
    }


}