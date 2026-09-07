package com.platform.mesh.bpm.biz.modules.data.nodedata.controller;

import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.dto.BpmDataFormNodeDataDTO;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.po.BpmDataFormNodeData;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.vo.BpmDataFormNodeDataVO;
import com.platform.mesh.bpm.biz.modules.data.nodedata.service.IBpmDataFormNodeDataService;
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
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
@Tag(description = "BpmDataFormNodeDataController", name = "业务数据实例流程节点表单数据")
@RestController
@RequestMapping
public class BpmDataFormNodeDataController extends BaseController{
    @Autowired
    private IBpmDataFormNodeDataService bpmDataFormNodeDataService;

    /**
	 * 功能描述:
	 * 〈获取业务数据实例流程节点表单数据列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<BpmDataFormNodeDataVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取业务数据实例流程节点表单数据分页")
	@PostMapping("/bpm/data/form/node/data/page")
	public Result<PageVO<BpmDataFormNodeDataVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<BpmDataFormNodeData> dataFormNodeDataMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmDataFormNodeData.class);
        MPage<BpmDataFormNodeData> page = bpmDataFormNodeDataService.page(dataFormNodeDataMPage);
        PageVO<BpmDataFormNodeDataVO> voPage = MPageUtil.convertToVO(page, BpmDataFormNodeDataVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前业务数据实例流程节点表单数据信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link Result<BpmDataFormNodeDataVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前业务数据实例流程节点表单数据信息")
    @GetMapping("/bpm/data/form/node/data/info/{instNodeId}")
    public Result<BpmDataFormNodeDataVO> getDataFormNodeDataInfoById(@PathVariable("instNodeId")Long instNodeId) {
        BpmDataFormNodeDataVO bpmDataFormNodeDataVO = bpmDataFormNodeDataService.getDataFormNodeDataInfoByInstNodeId(instNodeId);
        return Result.success(bpmDataFormNodeDataVO);
    }

    /**
     * 功能描述:
     * 〈新增业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link Result<BpmDataFormNodeDataVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增业务数据实例流程节点表单数据")
    @Log(moduleName = "业务数据实例流程节点表单数据管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/bpm/data/form/node/data/add")
    public Result<BpmDataFormNodeDataVO> addDataFormNodeData(@Validated @RequestBody BpmDataFormNodeDataDTO dataFormNodeDataDTO) {
        return Result.success(bpmDataFormNodeDataService.addDataFormNodeData(dataFormNodeDataDTO));
    }

    /**
     * 功能描述:
     * 〈修改业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link Result<BpmDataFormNodeDataVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改业务数据实例流程节点表单数据")
    @Log(moduleName = "业务数据实例流程节点表单数据管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/bpm/data/form/node/data/edit")
    public Result<BpmDataFormNodeDataVO> editDataFormNodeData(@Validated @RequestBody BpmDataFormNodeDataDTO dataFormNodeDataDTO) {
        return Result.success(bpmDataFormNodeDataService.editDataFormNodeData(dataFormNodeDataDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataId dataFormNodeDataId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除业务数据实例流程节点表单数据")
    @Log(moduleName = "业务数据实例流程节点表单数据管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/bpm/data/form/node/data/delete/{dataFormNodeDataId}")
    public Result<Boolean> deleteDataFormNodeData(@PathVariable(value = "dataFormNodeDataId",required = false)Long dataFormNodeDataId) {
        return Result.success(bpmDataFormNodeDataService.deleteDataFormNodeData(dataFormNodeDataId));
    }

}