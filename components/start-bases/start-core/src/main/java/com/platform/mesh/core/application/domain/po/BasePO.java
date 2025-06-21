package com.platform.mesh.core.application.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description 实体基类,不填充任何信息，只作为顶层对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="实体基类PO")
public class BasePO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

}
