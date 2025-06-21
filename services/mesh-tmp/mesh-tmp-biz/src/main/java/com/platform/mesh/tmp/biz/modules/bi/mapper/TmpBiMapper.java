package com.platform.mesh.tmp.biz.modules.bi.mapper;

import com.platform.mesh.tmp.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.tmp.biz.modules.bi.domain.vo.BiSimpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 任务计划BI
 * @author 蝉鸣
 */
@Mapper
public interface TmpBiMapper {


    BiSimpVO todoNumTodayPanel(@Param("biDTO") BiDTO biDTO);

    BiSimpVO overdueNumPanel(BiDTO biDTO);

    BiSimpVO warnNumPanel(BiDTO biDTO);

    BiSimpVO completeRatePanel(BiDTO biDTO);

    BiSimpVO overdueRatePanel(BiDTO biDTO);

    List<BiSimpVO> handleTimeChart(BiDTO biDTO);

    List<BiSimpVO> completeRateChart(BiDTO biDTO);

    List<BiSimpVO> overdueNumChart(BiDTO biDTO);

    List<BiSimpVO> warnNumChart(BiDTO biDTO);
}