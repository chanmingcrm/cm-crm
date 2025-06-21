package com.platform.mesh.tmp.biz.modules.init.db.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.platform.mesh.app.api.modules.init.db.mapper.DbMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 客户关系分组
 * @author 蝉鸣
 */
@Mapper
public interface TmpDbMapper extends DbMapper {

    @Override
    @InterceptorIgnore(tenantLine = "true")
    List<String> selectAppTables();
}