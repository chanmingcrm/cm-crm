package com.platform.mesh.crm.biz.bi.tmp.service.impl;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.crm.biz.bi.tmp.mapper.TmpBiMapper;
import com.platform.mesh.crm.biz.bi.tmp.service.ITmpBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务计划BI
 * @author 蝉鸣
 */
@Service
public class TmpBiServiceImpl implements ITmpBiService {


    @Autowired
    private TmpBiMapper tmpBiMapper;

    /**
     * 功能描述:
     * 〈今日事项〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO todoNumTodayPanel(BiDTO biDTO) {
        return tmpBiMapper.todoNumTodayPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO overdueNumPanel(BiDTO biDTO) {
        return tmpBiMapper.overdueNumPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈预警数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO warnNumPanel(BiDTO biDTO) {
        return tmpBiMapper.warnNumPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO completeRatePanel(BiDTO biDTO) {
        return tmpBiMapper.completeRatePanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO overdueRatePanel(BiDTO biDTO) {
        return tmpBiMapper.overdueRatePanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈平均处理时长〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> handleTimeChart(BiDTO biDTO) {
        return tmpBiMapper.handleTimeChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈任务完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> completeRateChart(BiDTO biDTO) {
        return tmpBiMapper.completeRateChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> overdueNumChart(BiDTO biDTO) {
        return tmpBiMapper.overdueNumChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈风险任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> warnNumChart(BiDTO biDTO) {
        return tmpBiMapper.warnNumChart(biDTO);
    }
}