package com.platform.mesh.ai.biz.bi.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description CcGroup
 * @author 蝉鸣
 */
public interface CcBiMapper extends BaseMapper<CcSessionMsg> {

    @InterceptorIgnore(tenantLine = "true")
    SimpVO biAllTotal(@Param("biDTO") BiDTO biDTO);


    @InterceptorIgnore(tenantLine = "true")
    SimpVO biUserTotal(@Param("biDTO") BiDTO biDTO, @Param("userId") Long userId);

    @InterceptorIgnore(tenantLine = "true")
    SimpVO biStar(@Param("biDTO") BiDTO biDTO,@Param("userId") Long userId);

    @InterceptorIgnore(tenantLine = "true")
    List<SimpVO> biNumRank(@Param("biDTO") BiDTO biDTO);

    @InterceptorIgnore(tenantLine = "true")
    List<SimpVO> biStarRank(@Param("biDTO") BiDTO biDTO);
}