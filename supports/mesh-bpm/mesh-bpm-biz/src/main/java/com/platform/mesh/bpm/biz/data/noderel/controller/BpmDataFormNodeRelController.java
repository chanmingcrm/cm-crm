package com.platform.mesh.bpm.biz.data.noderel.controller;

import com.platform.mesh.bpm.biz.data.noderel.domain.dto.BpmDataFormNodeRelDTO;
import com.platform.mesh.bpm.biz.data.noderel.domain.po.BpmDataFormNodeRel;
import com.platform.mesh.bpm.biz.data.noderel.domain.vo.BpmDataFormNodeRelVO;
import com.platform.mesh.bpm.biz.data.noderel.service.IBpmDataFormNodeRelService;
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
 * @description 业务数据模板流程节点表单关系
 * @author 蝉鸣
 */
@Tag(description = "BpmDataFormNodeRelController", name = "业务数据模板流程节点表单关系")
@RestController
@RequestMapping
public class BpmDataFormNodeRelController extends BaseController{
    @Autowired
    private IBpmDataFormNodeRelService bpmDataFormNodeRelService;

    /**
	 * 功能描述:
	 * 〈业务数据模板流程节点表单关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<BpmDataFormNodeRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "业务数据模板流程节点表单关系分页")
	@PostMapping("/bpm/data/form/node/rel/page")
	public Result<PageVO<BpmDataFormNodeRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<BpmDataFormNodeRel> dataFormNodeRelMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmDataFormNodeRel.class);
        MPage<BpmDataFormNodeRel> page = bpmDataFormNodeRelService.page(dataFormNodeRelMPage);
        PageVO<BpmDataFormNodeRelVO> voPage = MPageUtil.convertToVO(page, BpmDataFormNodeRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈根据模板节点ID获取当前业务数据模板流程节点表单关系信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link Result<BpmDataFormNodeRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据模板节点ID获取当前业务数据模板流程节点表单关系信息")
    @GetMapping("/bpm/data/form/node/rel/info/{tempNodeId}")
    public Result<BpmDataFormNodeRelVO> getDataFormNodeRelInfoByTempNodeId(@PathVariable("tempNodeId")Long tempNodeId) {
        BpmDataFormNodeRelVO bpmDataFormNodeRelVO = bpmDataFormNodeRelService.getDataFormNodeRelInfoByTempNodeId(tempNodeId);
        return Result.success(bpmDataFormNodeRelVO);
    }

    /**
     * 功能描述:
     * 〈新增业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link Result<BpmDataFormNodeRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增业务数据模板流程节点表单关系")
    @Log(moduleName = "业务数据模板流程节点表单关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/bpm/data/form/node/rel/add")
    public Result<BpmDataFormNodeRelVO> addDataFormNodeRel(@Validated @RequestBody BpmDataFormNodeRelDTO dataFormNodeRelDTO) {
        return Result.success(bpmDataFormNodeRelService.addDataFormNodeRel(dataFormNodeRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link Result<BpmDataFormNodeRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改业务数据模板流程节点表单关系")
    @Log(moduleName = "业务数据模板流程节点表单关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/bpm/data/form/node/rel/edit")
    public Result<BpmDataFormNodeRelVO> editDataFormNodeRel(@Validated @RequestBody BpmDataFormNodeRelDTO dataFormNodeRelDTO) {
        return Result.success(bpmDataFormNodeRelService.editDataFormNodeRel(dataFormNodeRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelId dataFormNodeRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除业务数据模板流程节点表单关系")
    @Log(moduleName = "业务数据模板流程节点表单关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/bpm/data/form/node/rel/delete/{dataFormNodeRelId}")
    public Result<Boolean> deleteDataFormNodeRel(@PathVariable(value = "dataFormNodeRelId",required = false)Long dataFormNodeRelId) {
        return Result.success(bpmDataFormNodeRelService.deleteDataFormNodeRel(dataFormNodeRelId));
    }

}