package com.platform.mesh.upms.biz.modules.sys.menu.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 * @description 路由实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="路由VO")
public class RouteVO extends BaseVO {

	/**
	 * home页路由
	 */
	@Schema(description = "home页路由")
	private String home;

	/**
	 * 路由信息
	 */
	@Schema(description = "路由信息")
	private List<RouteItemVO> routes;

}