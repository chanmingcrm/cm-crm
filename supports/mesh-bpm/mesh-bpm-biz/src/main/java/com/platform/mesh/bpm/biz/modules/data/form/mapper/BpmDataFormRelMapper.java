package com.platform.mesh.bpm.biz.modules.data.form.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.biz.modules.data.form.domain.dto.BpmDataFormRelDTO;
import com.platform.mesh.bpm.biz.modules.data.form.domain.po.BpmDataFormRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 流程数据表达那关系
 * @author 蝉鸣
 */
public interface BpmDataFormRelMapper extends BaseMapper<BpmDataFormRel> {

    List<BpmDataFormRel> getDataFormRelByFormId(@Param("relDTO") BpmDataFormRelDTO relDTO);
}

