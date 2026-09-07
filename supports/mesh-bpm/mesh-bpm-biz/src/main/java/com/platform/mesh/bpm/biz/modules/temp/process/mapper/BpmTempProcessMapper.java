package com.platform.mesh.bpm.biz.modules.temp.process.mapper;


import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 流程过程信息
 * @author 蝉鸣
 */
public interface BpmTempProcessMapper extends BaseMapper<BpmTempProcess> {

    MPage<BpmTempProcessVO> selectMPage(IPage<BpmTempProcess> iPage, @Param("pageDTO") BpmTempProcessPageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    List<BpmTempProcess> selectTenantList();
}

