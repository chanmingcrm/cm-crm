package com.platform.mesh.crm.api.modules.crm.constants;

/**
 * @description 数据库检索常量
 * @author 蝉鸣
 */
public interface CrmConst {

	/**
	 * customer
	 */
	String CUSTOMER = "customer";

	/**
	 * business
	 */
	String BUSINESS = "business";

	/**
	 * contract
	 */
	String CONTRACT = "contract";

	/**
	 * proposal
	 */
	String PROPOSAL = "proposal";

	/**
	 * order
	 */
	String ORDER = "order";

	/**
	 * product
	 */
	String PRODUCT = "product";

	/**
	 * supplier
	 */
	String SUPPLIER = "supplier";

    /**
     * 应收
     */
    String RECEIVABLE = "receivable";

    /**
     * 应付
     */
    String PAYABLE = "payable";

    /**
     * 产品列表
     */
    String PRODUCT_LIST = "product_list";

    /**
     * 产品列表
     */
    String PRODUCT_DESIGN = "product_design_json";

    /**
     * 旅游团
     */
    String TRS_GROUP = "trs_group";

    /**
     * 旅游团成员列表
     */
    String TRS_COLLECT_CUSTOMER = "collect_customer_json";
    String TRS_COLLECT_CUSTOMER_NAME = "sub_customer_name";
    String TRS_COLLECT_CUSTOMER_ID_CARD = "sub_customer_idcard";
    String TRS_COLLECT_CUSTOMER_PHONE = "sub_customer_phone";
    String TRS_COLLECT_CUSTOMER_GENDER = "sub_customer_gender_json";
    String TRS_COLLECT_CUSTOMER_AGE_TYPE = "sub_customer_age_type_json";

	/**
	 * 旅游产品明细需要的字段
	 */
	String TRS_PRODUCT_DAY_NUM = "day_num";
}

