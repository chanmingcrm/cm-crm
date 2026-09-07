package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po.ThirdFormColumnMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
public interface ThirdFormColumnMappingMapper extends BaseMapper<ThirdFormColumnMapping> {

    List<ThirdFormColumnMapping> getThirdFormColumnMapping(@Param("sourceFlag") Integer sourceFlag);

}
