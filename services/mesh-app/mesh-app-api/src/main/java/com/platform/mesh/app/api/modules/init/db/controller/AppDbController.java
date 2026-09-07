package com.platform.mesh.app.api.modules.init.db.controller;

import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 约定当前controller 只引入当前service
 * @description 任务计划数据操作
 * @author 蝉鸣
 */
public abstract class AppDbController extends BaseController {

    /**
	 * 功能描述:
	 * 〈获取客户关系数据转化〉
	 * @param transDTO transDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	public abstract Result<DbTransResBO> dbTransData(@RequestBody DbTransDTO transDTO);
}