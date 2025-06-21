package com.platform.mesh.core.application.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @description 实体BO对象,不填充任何信息，只作为顶层对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="实体BO对象")
public class BaseBO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

}
