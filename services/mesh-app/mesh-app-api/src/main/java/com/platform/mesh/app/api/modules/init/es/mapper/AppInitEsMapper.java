package com.platform.mesh.app.api.modules.init.es.mapper;

import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description 客户关系分组
 * @author 蝉鸣
 */
public interface AppInitEsMapper {

    MPage<Map<String,Object>> selectPageMaps(MPage<?> page);

    List<Map<String,Object>> selectModelDataByDataIds(@Param("dataIds") List<Long> dataIds);

}