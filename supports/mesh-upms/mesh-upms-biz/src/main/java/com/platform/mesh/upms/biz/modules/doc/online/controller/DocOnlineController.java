package com.platform.mesh.upms.biz.modules.doc.online.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineLastDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlinePageDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.po.DocOnline;
import com.platform.mesh.upms.biz.modules.doc.online.domain.vo.DocOnlineVO;
import com.platform.mesh.upms.biz.modules.doc.online.service.IDocOnlineService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 在线文档信息
 * @author 蝉鸣
 */
@Tag(description = "DocOnlineController", name = "在线文档")
@RestController
@RequestMapping
public class DocOnlineController extends BaseController{

    @Autowired
    private IDocOnlineService docOnlineService;


    /**
     * 功能描述:
     * 〈获取在线文档列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<DocOnlineVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取官网在线文档分页")
    @PostMapping("/doc/online/home/page")
    public Result<PageVO<DocOnlineVO>> selectHomePage(@RequestBody DocOnlinePageDTO pageDTO) {
        MPage<DocOnline> page = docOnlineService.selectHomePage(pageDTO);
        PageVO<DocOnlineVO> voPage = MPageUtil.convertToVO(page, DocOnlineVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 〈获取官网在线文档信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Result<DocOnlineVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取官网在线文档信息")
    @GetMapping("/doc/online/home/info/{onlineId}")
    public Result<DocOnlineVO> getOnlineHomeInfoById(@PathVariable("onlineId")Long onlineId) {
        DocOnlineVO docFileVO = docOnlineService.getOnlineHomeInfoById(onlineId);
        return Result.success(docFileVO);
    }


    /**
	 * 功能描述:
	 * 〈获取在线文档列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DocOnlineVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取在线文档分页")
	@PostMapping("/doc/online/page")
	public Result<PageVO<DocOnlineVO>> selectPage(@RequestBody DocOnlinePageDTO pageDTO) {
        MPage<DocOnline> page = docOnlineService.selectPage(pageDTO);
        PageVO<DocOnlineVO> voPage = MPageUtil.convertToVO(page, DocOnlineVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前在线文档信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Result<DocOnlineVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前在线文档信息")
    @GetMapping("/doc/online/info/{onlineId}")
    public Result<DocOnlineVO> getOnlineInfoById(@PathVariable("onlineId")Long onlineId) {
        DocOnlineVO docFileVO = docOnlineService.getOnlineInfoById(onlineId);
        return Result.success(docFileVO);
    }

    /**
     * 功能描述:
     * 〈新增在线文档〉
     * @param docOnlineDTO docOnlineDTO
     * @return 正常返回:{@link Result<DocOnlineVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增在线文档")
    @PostMapping("/doc/online/add")
    public Result<Boolean> addOnLine(@RequestBody DocOnlineDTO docOnlineDTO) {
        return Result.success(docOnlineService.addOnLine(docOnlineDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除在线文档〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除在线文档")
    @Log(moduleName = "在线文档管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/doc/online/delete/{onlineId}")
    public Result<Boolean> deleteOnLine(@PathVariable("onlineId")Long onlineId) {
        return Result.success(docOnlineService.deleteOnLine(onlineId));
    }

    /**
     * 功能描述:
     * 〈查询上/下一条数据〉
     * @param lastDTO lastDTO
     * @return 正常返回:{@link Result<DocOnlineVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "查询上/下一条数据")
    @PostMapping("/doc/online/home/last/one")
    public Result<DocOnlineVO> selectLastOne(@RequestBody DocOnlineLastDTO lastDTO) {
        DocOnline online = docOnlineService.selectLastOne(lastDTO);
        return Result.success(BeanUtil.copyProperties(online, DocOnlineVO.class));
    }

    /**
     * 功能描述:
     * 〈发布文章〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "发布文章")
    @PostMapping("/doc/online/home/pub/{onlineId}")
    public Result<Boolean> pubOne(@PathVariable("onlineId")Long onlineId) {
        Boolean pub = docOnlineService.pubOne(onlineId);
        return Result.success(pub);
    }

}