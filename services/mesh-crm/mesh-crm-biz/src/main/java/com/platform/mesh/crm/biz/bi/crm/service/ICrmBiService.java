package com.platform.mesh.crm.biz.bi.crm.service;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.app.api.modules.bi.domain.dto.ModuleBiDTO;
import com.platform.mesh.app.api.modules.bi.domain.vo.BiIdSimpVO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.utils.result.Result;

import java.util.List;
import java.util.Map;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系分组信息
 * @author 蝉鸣
 */
public interface ICrmBiService{

    /**
     * 功能描述:
     * 〈获取新增客户统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biCustomerNew(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取新增联系人统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biContactsNew(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取新增商机统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biBusinessNew(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取新增合同统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biContractNew(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取新增跟进统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biFollowNew(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取合同金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biContractMoney(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取商机金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biBusinessMoney(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈获取回款金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biPaymentMoney(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈合同目标及完成情况〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List< SimpVO >}
     * @author 蝉鸣
     */
    List<SimpVO> biContractEstAndAct(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈业绩指标完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biAchieveEstAndAct(ModuleBiDTO biDTO);

    /**
     * 功能描述:
     * 〈排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result <SimpVO>}
     * @author 蝉鸣
     */
    List<BiIdSimpVO> biRankEstAndAct(ModuleBiDTO biDTO);

    /**
     * 功能描述:
     * 〈遗忘提醒〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    Map<String, Integer> biNotice(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈数据汇总〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<Map<String,Object>>}
     * @author 蝉鸣
     */
    Map<String, Object> biDataCount(BiDTO biDTO);
}