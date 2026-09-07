package com.platform.mesh.crm.biz.init.db.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.app.api.modules.init.db.mapper.DbMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description 客户关系分组
 * @author 蝉鸣
 */
@Mapper
public interface CrmDbMapper extends DbMapper {

    @Override
    List<String> selectAppTables();

    @Override
    void dynamicDbInsert(@Param("dataMap") Map<String, Object> dataMap);

    @Override
    void dynamicDbUpdate(@Param("moduleId") Long moduleId, @Param("dataIds") List<Long> dataIds);

    @Override
    void dynamicDbDataUpdate(@Param("moduleId") Long moduleId, @Param("dataIds") List<Long> dataIds);

    @Override
    void dynamicDbDelete(@Param("dataIds") List<Long> dataIds);

    @Override
    void dynamicDbDataDelete(@Param("dataIds") List<Long> dataIds);

    @Override
    Map<String,Object> dynamicDBMaxOne(@Param("createTime") LocalDateTime createTime);

    @Override
    MPage<Long> getTransDataIdsPage(MPage<Long> longMPage
            ,@Param("ruleMac") String ruleMac
            ,@Param("dateTime") LocalDateTime dateTime
            ,@Param("fromModuleId") Long fromModuleId);

    @Override
    MPage<Long> getIds(IPage<Long> page, @Param("sourceUserId") Long sourceUserId);

    @Override
    void dynamicDbUpdateOrg(@Param("targetUserId") Long targetUserId,@Param("targetLevelId") Long targetLevelId,@Param("ids") List<Long> ids);

    @Override
    void dynamicDbDataUpdateOrg(@Param("ids") List<Long> ids, @Param("targetUserId") Long targetUserId,@Param("targetLevelId") Long targetLevelId,
                                @Param("userJson") Object userJson,@Param("orgJson") Object orgJson );
}
