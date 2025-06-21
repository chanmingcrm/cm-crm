package com.platform.mesh.tmp.biz.modules.bi.service;

import com.platform.mesh.tmp.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.tmp.biz.modules.bi.domain.vo.BiSimpVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务计划信息
 * @author 蝉鸣
 */
public interface ITmpBiService {


    /**
     * 功能描述:
     * 〈今日事项〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    BiSimpVO todoNumTodayPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    BiSimpVO overdueNumPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈预警数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    BiSimpVO warnNumPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    BiSimpVO completeRatePanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    BiSimpVO overdueRatePanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈平均处理时长〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    List<BiSimpVO> handleTimeChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈任务完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    List<BiSimpVO> completeRateChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    List<BiSimpVO> overdueNumChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈风险任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    List<BiSimpVO> warnNumChart(BiDTO biDTO);
}