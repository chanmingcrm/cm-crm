package com.platform.mesh.bpm.biz.modules.data.form.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.bpm.biz.modules.data.form.domain.dto.BpmDataFormRelAddDTO;
import com.platform.mesh.bpm.biz.modules.data.form.domain.dto.BpmDataFormRelDTO;
import com.platform.mesh.bpm.biz.modules.data.form.domain.dto.BpmDataFormRelEditDTO;
import com.platform.mesh.bpm.biz.modules.data.form.domain.dto.BpmDataFormRelPageDTO;
import com.platform.mesh.bpm.biz.modules.data.form.domain.po.BpmDataFormRel;
import com.platform.mesh.bpm.biz.modules.data.form.domain.vo.BpmDataFormRelVO;
import com.platform.mesh.bpm.biz.modules.data.form.service.IBpmDataFormRelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 业务数据流程表单关系信息
 * @author 蝉鸣
 */
@Tag(description = "BpmDataFormRelController", name = "业务数据流程表单关系信息")
@RestController
public class BpmDataFormRelController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmDataFormRelService bpmDataFormRelService;

    /**
     * 功能描述:
     * 〈获取分页信息〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取分页信息")
    @PostMapping("/bpm/data/form/rel/page")
    public Result<PageVO<BpmDataFormRelVO>> selectPage(@RequestBody BpmDataFormRelPageDTO pageDTO) {
        return Result.success(bpmDataFormRelService.selectPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈根据流程Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<BpmDataFormRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据流程Id获取流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/by/process/{processId}")
    public Result<BpmDataFormRelVO> getDataFormRelByProcessId(@PathVariable("processId")Long processId) {
        BpmDataFormRel dataFormRel = bpmDataFormRelService.getDataFormRelByProcessId(processId);
        return Result.success(BeanUtil.copyProperties(dataFormRel, BpmDataFormRelVO.class));
    }

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<List<BpmDataFormRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据表单Id获取流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/by/form")
    public Result<List<BpmDataFormRelVO>> getDataFormRel(@RequestBody BpmDataFormRelDTO relDTO) {
        List<BpmDataFormRel> dataFormRels = bpmDataFormRelService.getDataFormRel(relDTO);
        return Result.success(BeanUtil.copyToList(dataFormRels, BpmDataFormRelVO.class));
    }

    /**
     * 功能描述:
     * 〈根据应用Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<List<BpmDataFormRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据应用Id获取流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/by/app/{appId}")
    public Result<List<BpmDataFormRelVO>> getDataFormRelByAppId(@PathVariable("appId")Long appId) {
        List<BpmDataFormRel> dataFormRels = bpmDataFormRelService.getDataFormRelByAppId(appId);
        return Result.success(BeanUtil.copyToList(dataFormRels, BpmDataFormRelVO.class));
    }

    /**
     * 功能描述:
     * 〈添加流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<BpmDataFormRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "添加流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/add")
    public Result<BpmDataFormRelVO> addDataFormRel(@RequestBody BpmDataFormRelAddDTO addDTO) {
        BpmDataFormRel dataFormRel = bpmDataFormRelService.addDataFormRel(addDTO);
        return Result.success(BeanUtil.copyProperties(dataFormRel, BpmDataFormRelVO.class));
    }

    /**
     * 功能描述:
     * 〈修改流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<BpmDataFormRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/edit")
    public Result<BpmDataFormRelVO> editDataFormRel(@RequestBody BpmDataFormRelEditDTO editDTO) {
        BpmDataFormRel dataFormRel = bpmDataFormRelService.editDataFormRel(editDTO);
        return Result.success(BeanUtil.copyProperties(dataFormRel, BpmDataFormRelVO.class));
    }

    /**
     * 功能描述:
     * 〈删除流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除流程与业务表单绑定关系")
    @PostMapping("/bpm/data/form/rel/delete/{relId}")
    public Result<Boolean> deleteDataFormRel(@PathVariable("relId") Long relId) {
        return Result.success(bpmDataFormRelService.deleteDataFormRel(relId));
    }

}
