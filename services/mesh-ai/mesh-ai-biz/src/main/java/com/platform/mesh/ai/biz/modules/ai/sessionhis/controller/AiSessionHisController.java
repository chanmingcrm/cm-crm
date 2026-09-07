package com.platform.mesh.ai.biz.modules.ai.sessionhis.controller;

import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.dto.AiSessionHisDTO;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.po.AiSessionHis;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.vo.AiSessionHisVO;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.service.IAiSessionHisService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description AI会话信息
 * @author 蝉鸣
 */
@Tag(description = "AiSessionHisController", name = "AI会话历史")
@RestController
@RequestMapping
public class AiSessionHisController extends BaseController{
    
    @Autowired
    private IAiSessionHisService aiSessionHisService;

    /**
	 * 功能描述:
	 * 〈获取AI会话历史记录分页〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiSessionHisVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI会话历史记录分页")
	@PostMapping("/ai/session/his/page")
	public Result<PageVO<AiSessionHisVO>> selectPage(@RequestBody AiSessionHisDTO pageDTO) {
		MPage<AiSessionHis> page = aiSessionHisService.selectPage(pageDTO);
        PageVO<AiSessionHisVO> voPage = MPageUtil.convertToVO(page, AiSessionHisVO.class);
        // 显式回填大文本字段，避免通用分页转换遗漏会话问题和回复内容。
        for (int index = 0; index < page.getRecords().size(); index++) {
            // 按分页记录顺序将持久化内容写入对应的返回对象。
            AiSessionHis source = page.getRecords().get(index);
            AiSessionHisVO target = voPage.getRecords().get(index);
            target.setRequireParams(source.getRequireParams());
            target.setResponseMsg(source.getResponseMsg());
        }
        return Result.success(voPage);
	}

}
