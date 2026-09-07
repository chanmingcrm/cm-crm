package com.platform.mesh.crm.biz.bi.tmp.mapper;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 任务计划BI
 * @author 蝉鸣
 */
@Mapper
public interface TmpBiMapper {


    SimpVO todoNumTodayPanel(@Param("biDTO") BiDTO biDTO);

    SimpVO overdueNumPanel(@Param("biDTO") BiDTO biDTO);

    SimpVO warnNumPanel(@Param("biDTO") BiDTO biDTO);

    SimpVO completeRatePanel(@Param("biDTO") BiDTO biDTO);

    SimpVO overdueRatePanel(@Param("biDTO") BiDTO biDTO);

    List<SimpVO> handleTimeChart(@Param("biDTO") BiDTO biDTO);

    List<SimpVO> completeRateChart(@Param("biDTO") BiDTO biDTO);

    List<SimpVO> overdueNumChart(@Param("biDTO") BiDTO biDTO);

    List<SimpVO> warnNumChart(@Param("biDTO") BiDTO biDTO);
}