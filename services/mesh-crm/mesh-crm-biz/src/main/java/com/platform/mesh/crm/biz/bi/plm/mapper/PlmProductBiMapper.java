package com.platform.mesh.crm.biz.bi.plm.mapper;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 产品看板BI统计
 * @author 蝉鸣
 */
@Mapper
public interface PlmProductBiMapper {

    /**
     * 获取产品汇总统计
     */
    List<SimpVO> productSummary(@Param("biDTO") BiDTO biDTO);

    /**
     * 获取生命周期结构统计
     */
    List<SimpVO> productLifecycle(@Param("biDTO") BiDTO biDTO);

    /**
     * 获取产品健康度统计
     */
    List<SimpVO> productHealth(@Param("biDTO") BiDTO biDTO);

    /**
     * 获取产品运营指标统计
     */
    List<SimpVO> productOperation(@Param("biDTO") BiDTO biDTO);

    /**
     * 获取产品预警统计
     */
    List<SimpVO> productWarningItems(@Param("biDTO") BiDTO biDTO);

}
