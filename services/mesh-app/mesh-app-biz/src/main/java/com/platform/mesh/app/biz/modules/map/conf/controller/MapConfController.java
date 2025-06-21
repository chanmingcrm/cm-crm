package com.platform.mesh.app.biz.modules.map.conf.controller;

import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfPageDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.po.MapConf;
import com.platform.mesh.app.biz.modules.map.conf.domain.vo.MapConfVO;
import com.platform.mesh.app.biz.modules.map.conf.service.IMapConfService;
import com.platform.mesh.core.application.controller.BaseController;
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

/**
 * 约定当前controller 只引入当前service
 * @description 地图配置记录
 * @author 蝉鸣
 */
@Tag(description = "MapConfController", name = "地图配置记录")
@RestController
@RequestMapping
public class MapConfController extends BaseController{

    @Autowired
    private IMapConfService mapConfService;

    /**
	 * 功能描述:
	 * 〈获取地图配置列表〉
	 * @param mapConfPageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<MapConfVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取地图配置分页")
	@PostMapping("/map/conf/page")
	public Result<PageVO<MapConfVO>> selectPage(@RequestBody MapConfPageDTO mapConfPageDTO) {
        MPage<MapConf> page = mapConfService.selectPage(mapConfPageDTO);
        PageVO<MapConfVO> voPage = MPageUtil.convertToVO(page, MapConfVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前地图配置信息〉
     * @param confId confId
     * @return 正常返回:{@link Result<MapConfVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前地图配置信息")
    @GetMapping("/map/conf/info/{confId}")
    public Result<MapConfVO> getMapConfById(@PathVariable("confId")Long confId) {
        MapConfVO mapConfVO = mapConfService.getMapConfInfoById(confId);
        return Result.success(mapConfVO);
    }

    /**
     * 功能描述:
     * 〈新增地图配置〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link Result<MapConfVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增地图配置")
    @Log(moduleName = "地图配置管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/map/conf/add")
    public Result<MapConfVO> addMapConf(@Validated @RequestBody MapConfDTO mapConfDTO) {
        return Result.success(mapConfService.addMapConf(mapConfDTO));
    }

    /**
     * 功能描述:
     * 〈修改地图配置〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改地图配置")
    @Log(moduleName = "地图配置管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/map/conf/edit")
    public Result<MapConfVO> editMapConf(@Validated @RequestBody MapConfDTO mapConfDTO) {
        return Result.success(mapConfService.editMapConf(mapConfDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除地图配置〉
     * @param confId confId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除地图配置")
    @Log(moduleName = "地图配置管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/map/conf/delete/{confId}")
    public Result<Boolean> deleteMapConf(@PathVariable(value = "confId",required = false)Long confId) {
        return Result.success(mapConfService.deleteMapConf(confId));
    }
}