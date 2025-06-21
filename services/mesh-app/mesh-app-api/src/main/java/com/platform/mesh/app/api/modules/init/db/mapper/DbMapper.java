package com.platform.mesh.app.api.modules.init.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description 数据初始化
 * @author 蝉鸣
 */
@Mapper
@Component("baseDbMapper")
public interface DbMapper {

    List<String> selectAppTables();

    void dynamicDbInsert(@Param("dataMap") Map<String, Object> dataMap);

    void dynamicDbUpdate(@Param("moduleId") Long moduleId, @Param("dataIds") List<Long> dataIds);

    void dynamicDbDelete(@Param("dataIds") List<Long> dataIds);

    void dynamicDbDataDelete(@Param("dataIds") List<Long> dataIds);

    Map<String,Object> dynamicDBMaxOne(@Param("createTime") LocalDateTime createTime);
}