package com.platform.mesh.crm.biz.bi.tmp.service;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;

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
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO todoNumTodayPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO overdueNumPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈预警数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO warnNumPanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO completeRatePanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO overdueRatePanel(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈平均处理时长〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    List<SimpVO> handleTimeChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈任务完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    List<SimpVO> completeRateChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈逾期任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    List<SimpVO> overdueNumChart(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈风险任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    List<SimpVO> warnNumChart(BiDTO biDTO);
}