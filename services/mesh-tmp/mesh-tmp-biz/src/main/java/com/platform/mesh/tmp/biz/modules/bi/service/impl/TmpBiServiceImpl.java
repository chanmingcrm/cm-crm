package com.platform.mesh.tmp.biz.modules.bi.service.impl;

import com.platform.mesh.tmp.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.tmp.biz.modules.bi.domain.vo.BiSimpVO;
import com.platform.mesh.tmp.biz.modules.bi.mapper.TmpBiMapper;
import com.platform.mesh.tmp.biz.modules.bi.service.ITmpBiService;
import com.platform.mesh.tmp.biz.modules.bi.service.manual.TmpBiServiceManual;
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

    @Autowired
    private TmpBiServiceManual tmpBiServiceManual;


    /**
     * 功能描述:
     * 〈今日事项〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public BiSimpVO todoNumTodayPanel(BiDTO biDTO) {
        return tmpBiMapper.todoNumTodayPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public BiSimpVO overdueNumPanel(BiDTO biDTO) {
        return tmpBiMapper.overdueNumPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈预警数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public BiSimpVO warnNumPanel(BiDTO biDTO) {
        return tmpBiMapper.warnNumPanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public BiSimpVO completeRatePanel(BiDTO biDTO) {
        return tmpBiMapper.completeRatePanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public BiSimpVO overdueRatePanel(BiDTO biDTO) {
        return tmpBiMapper.overdueRatePanel(biDTO);
    }

    /**
     * 功能描述:
     * 〈平均处理时长〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<BiSimpVO> handleTimeChart(BiDTO biDTO) {
        return tmpBiMapper.handleTimeChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈任务完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<BiSimpVO> completeRateChart(BiDTO biDTO) {
        return tmpBiMapper.completeRateChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈逾期任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<BiSimpVO> overdueNumChart(BiDTO biDTO) {
        return tmpBiMapper.overdueNumChart(biDTO);
    }

    /**
     * 功能描述:
     * 〈风险任务数〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<BiSimpVO> warnNumChart(BiDTO biDTO) {
        return tmpBiMapper.warnNumChart(biDTO);
    }
}