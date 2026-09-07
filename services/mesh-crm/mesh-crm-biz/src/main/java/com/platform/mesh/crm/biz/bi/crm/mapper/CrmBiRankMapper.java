package com.platform.mesh.crm.biz.bi.crm.mapper;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
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
    SimpVO biCustomerRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增联系人
     */
    SimpVO biContactsRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增商机
     */
    SimpVO biBusinessRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增合同
     */
    SimpVO biContractRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增跟进
     */
    SimpVO biFollowRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 合同金额
     */
    SimpVO biContractMoneyRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 商机金额
     */
    SimpVO biBusinessMoneyRank(@Param("biDTO") BiDTO biDTO);

    /**
     * 回款金额
     */
    SimpVO biPaymentMoneyRank(@Param("biDTO") BiDTO biDTO);
}