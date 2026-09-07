package com.platform.mesh.app.api.modules.init.db.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.mybatis.plus.extention.MPage;
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

    void dynamicDbDataUpdate(@Param("moduleId") Long moduleId, @Param("dataIds") List<Long> dataIds);

    void dynamicDbDelete(@Param("dataIds") List<Long> dataIds);

    void dynamicDbDataDelete(@Param("dataIds") List<Long> dataIds);

    Map<String,Object> dynamicDBMaxOne(@Param("createTime") LocalDateTime createTime);

    MPage<Long> getTransDataIdsPage(MPage<Long> longMPage
            ,@Param("ruleMac") String ruleMac
            ,@Param("dateTime") LocalDateTime dateTime
            ,@Param("fromModuleId") Long fromModuleId);

    MPage<Long> getIds(IPage<Long> page,@Param("sourceUserId") Long sourceUserId);

    void dynamicDbUpdateOrg(@Param("targetUserId") Long targetUserId,@Param("targetLevelId") Long targetLevelId,@Param("ids") List<Long> ids);

    void dynamicDbDataUpdateOrg(@Param("ids") List<Long> ids, @Param("targetUserId") Long targetUserId,@Param("targetLevelId") Long targetLevelId,
                                @Param("userJson") Object userJson,@Param("orgJson") Object orgJson );

}
