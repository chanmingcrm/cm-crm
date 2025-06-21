package com.platform.mesh.core.enums.custom;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 业务操作类型
 * @author 蝉鸣
 */
public enum OperateTypeEnum implements BaseEnum<OperateTypeEnum, Integer> {

	/**
	 * 其它
	 */
	OTHER(0,"其它"),

	/**
	 * 查询
	 */
	SELECT(1,"查询"),

	/**
	 * 新增
	 */
	INSERT(2,"新增"),

	/**
	 * 修改
	 */
	UPDATE(3,"修改"),

	/**
	 * 删除
	 */
	DELETE(4,"删除"),

	/**
	 * 导出
	 */
	EXPORT(5,"导出"),

	/**
	 * 导入
	 */
	IMPORT(6,"导入"),

	/**
	 * 授权
	 */
	GRANT(7,"授权"),

	/**
	 * 强退
	 */
	FORCE(8,"强退"),

	/**
	 * 生成代码
	 */
	GEN_CODE(9,"生成代码"),

	/**
	 * 清空数据
	 */
	CLEAN(10,"清空数据"),
	;

	private final Integer value;

	private final String desc;

	OperateTypeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
	@Override
	public String getDesc() {
		return this.desc;
	}
}
