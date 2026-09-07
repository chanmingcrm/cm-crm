package com.platform.mesh.crm.biz.bi.plm.service;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 产品看板BI统计信息
 * @author 蝉鸣
 */
public interface IPlmProductBiService {

    /**
     * 功能描述:
     * 【获取产品汇总统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> productSummary(BiDTO biDTO);

    /**
     * 功能描述:
     * 【获取生命周期结构统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> productLifecycle(BiDTO biDTO);

    /**
     * 功能描述:
     * 【获取产品健康度统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> productHealth(BiDTO biDTO);

    /**
     * 功能描述:
     * 【获取产品运营指标统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> productOperation(BiDTO biDTO);

    /**
     * 功能描述:
     * 【获取产品预警统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> productWarningItems(BiDTO biDTO);

}
