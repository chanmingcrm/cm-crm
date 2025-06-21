package com.platform.mesh.app.biz.modules.map.log.controller;

import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogPageDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.po.MapLog;
import com.platform.mesh.app.biz.modules.map.log.domain.vo.MapLogVO;
import com.platform.mesh.app.biz.modules.map.log.service.IMapLogService;
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
 * @description 地图打卡记录
 * @author 蝉鸣
 */
@Tag(description = "MapLogController", name = "地图打卡记录")
@RestController
@RequestMapping
public class MapLogController extends BaseController{

    @Autowired
    private IMapLogService mapLogService;

    /**
	 * 功能描述:
	 * 〈获取地图打卡记录列表〉
	 * @param mapLogPageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<MapLogVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取地图打卡记录分页")
	@PostMapping("/map/log/page")
	public Result<PageVO<MapLogVO>> selectPage(@RequestBody MapLogPageDTO mapLogPageDTO) {
        MPage<MapLogVO> page = mapLogService.selectPage(mapLogPageDTO);
        PageVO<MapLogVO> voPage = MPageUtil.convertToVO(page, MapLogVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前地图打卡记录信息〉
     * @param logId logId
     * @return 正常返回:{@link Result<MapLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前地图打卡记录信息")
    @GetMapping("/map/log/info/{logId}")
    public Result<MapLogVO> getMapLogById(@PathVariable("logId")Long logId) {
        MapLogVO mapLogVO = mapLogService.getMapLogInfoById(logId);
        return Result.success(mapLogVO);
    }

    /**
     * 功能描述:
     * 〈新增地图打卡记录〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link Result<MapLogVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增地图打卡记录")
    @Log(moduleName = "地图打卡记录管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/map/log/add")
    public Result<MapLogVO> addMapLog(@Validated @RequestBody MapLogDTO mapLogDTO) {
        return Result.success(mapLogService.addMapLog(mapLogDTO));
    }

    /**
     * 功能描述:
     * 〈修改地图打卡记录〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改地图打卡记录")
    @Log(moduleName = "地图打卡记录管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/map/log/edit")
    public Result<MapLogVO> editMapLog(@Validated @RequestBody MapLogDTO mapLogDTO) {
        return Result.success(mapLogService.editMapLog(mapLogDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除地图打卡记录〉
     * @param logId logId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除地图打卡记录")
    @Log(moduleName = "地图打卡记录管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/map/log/delete/{logId}")
    public Result<Boolean> deleteMapLog(@PathVariable(value = "logId",required = false)Long logId) {
        return Result.success(mapLogService.deleteMapLog(logId));
    }
}