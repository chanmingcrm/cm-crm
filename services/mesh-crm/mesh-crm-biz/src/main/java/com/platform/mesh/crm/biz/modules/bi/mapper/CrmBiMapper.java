package com.platform.mesh.crm.biz.modules.bi.mapper;

import com.platform.mesh.crm.biz.modules.bi.domain.bo.ModuleBiTimeBO;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.ModuleBiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiIdSimpVO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiSimpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description 客户关系BI
 * @author 蝉鸣
 */
@Mapper
public interface CrmBiMapper{

    /**
     * 新增客户
     */
    BiSimpVO biCustomerNew(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增联系人
     */
    BiSimpVO biContactsNew(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增商机
     */
    BiSimpVO biBusinessNew(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增合同
     */
    BiSimpVO biContractNew(@Param("biDTO") BiDTO biDTO);

    /**
     * 新增跟进
     */
    BiSimpVO biFollowNew(@Param("biDTO") BiDTO biDTO);

    /**
     * 合同金额
     */
    BiSimpVO biContractMoney(@Param("biDTO") BiDTO biDTO);

    /**
     * 商机金额
     */
    BiSimpVO biBusinessMoney(@Param("biDTO") BiDTO biDTO);

    /**
     * 回款金额
     */
    BiSimpVO biPaymentMoney(@Param("biDTO") BiDTO biDTO);

    /**
     * 遗忘提醒
     */
    Map<String,Integer> biFollowNotice(@Param("biDTO") BiDTO biDTO);

    /**
     * 合同目标及完成情况
     */
    List<ModuleBiTimeBO> biContractWithTime(@Param("biDTO") BiDTO biDTO);

    /**
     * 获取排行榜
     */
    BigDecimal countAchieveData(@Param("biDTO") ModuleBiDTO biDTO);

    /**
     * 获取排行榜
     */
    List<BiIdSimpVO> biRankEstAndAct(@Param("biDTO") ModuleBiDTO biDTO);

    /**
     * 数据汇总
     */
    Map<String, Object> biDataCount(@Param("biDTO") BiDTO biDTO,@Param("openModuleIds") List<Long> moduleIds);
}