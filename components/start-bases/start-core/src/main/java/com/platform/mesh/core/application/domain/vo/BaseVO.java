package com.platform.mesh.core.application.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @description 实体基类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="实体基类VO")
public class BaseVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

}
