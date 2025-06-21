package com.platform.mesh.crm.biz.modules.bi.mapper;

import com.platform.mesh.crm.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiSimpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description 客户关系BI
 * @author 蝉鸣
 */
@Mapper
public interface CrmBiRankMapper {

    /**
     * 新增客户
     */
    BiSimpVO biCustomerRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增联系人
     */
    BiSimpVO biContactsRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增商机
     */
    BiSimpVO biBusinessRank(BiDTO biDTO);

    /**
     * 新增合同
     */
    BiSimpVO biContractRank(BiDTO biDTO);

    /**
     * 新增跟进
     */
    BiSimpVO biFollowRank(BiDTO biDTO);

    /**
     * 合同金额
     */
    BiSimpVO biContractMoneyRank(BiDTO biDTO);

    /**
     * 商机金额
     */
    BiSimpVO biBusinessMoneyRank(BiDTO biDTO);

    /**
     * 回款金额
     */
    BiSimpVO biPaymentMoneyRank(BiDTO biDTO);
}