package com.platform.mesh.gen.biz.modules.code.tablecolunm.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po.CodeTableColumn;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 表字段Mapper
 * @author 蝉鸣
 */
public interface CodeTableColumnMapper extends BaseMapper<CodeTableColumn> {

    /**
     * 功能描述:
     * 〈根据表空间和表名获取表信息〉
     * @param tableSchema tableSchema
     * @param tableNameList tableNameList
     * @return 正常返回:{@link MPage<DbTableColumnBO>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    MPage<DbTableColumnBO> getDBTableColumnPage(IPage<?> page, @Param("tableSchema") String tableSchema, @Param("tableNameList") List<String> tableNameList);
}
