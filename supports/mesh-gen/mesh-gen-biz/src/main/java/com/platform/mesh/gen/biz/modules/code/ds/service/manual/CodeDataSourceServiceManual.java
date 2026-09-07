package com.platform.mesh.gen.biz.modules.code.ds.service.manual;


import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.platform.mesh.gen.biz.modules.code.ds.domain.po.CodeDataSource;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.service.ICodeTableService;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.service.ICodeTableColumnService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class CodeDataSourceServiceManual{

    @Autowired
    private DynamicRoutingDataSource dynamicDataSource;

    @Autowired
    private ICodeTableService codeTableService;

    @Autowired
    private ICodeTableColumnService codeTableColumnService;

    /**
     * 添加数据源
     * @param dataSource dataSource
     */
    public void configureDataSource(CodeDataSource dataSource) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSource.getJdbcUrl());
        hikariDataSource.setUsername(dataSource.getJdbcUsername());
        hikariDataSource.setPassword(dataSource.getJdbcPassword());
        hikariDataSource.setDriverClassName(dataSource.getJdbcDriver());
        Map<String, DataSource> dataSources = dynamicDataSource.getDataSources();
        if(!dataSources.containsKey(dataSource.getDsName())){
            dynamicDataSource.addDataSource(dataSource.getDsName(),hikariDataSource);
            dynamicDataSource.afterPropertiesSet();
        }
    }

    /**
     * 获取数据库表单分页
     * @param dataSource dataSource
     */
    public MPage<DbTableBO> getDBTable(CodeDataSource dataSource,TableQueryDTO tableQueryDTO) {
        //设置数据源
        this.configureDataSource(dataSource);
        //设置当前线程使用的数据源
        DynamicDataSourceContextHolder.push(dataSource.getDsName());
        try {
            return codeTableService.selectDbTablePage(tableQueryDTO);
        } finally {
            // 清除当前线程使用的数据源
            DynamicDataSourceContextHolder.clear();
            //清除临时添加数据源
            dynamicDataSource.removeDataSource(dataSource.getDsName());
            dynamicDataSource.afterPropertiesSet();
        }
    }

    /**
     * 获取数据库表单分页
     * @param dataSource dataSource
     */
    public MPage<DbTableColumnBO> getDBTableColumn(CodeDataSource dataSource, TableColumnQueryDTO columnQueryDTO) {
        //设置数据源
        this.configureDataSource(dataSource);
        //设置当前线程使用的数据源
        DynamicDataSourceContextHolder.push(dataSource.getDsName());
        try {
            return codeTableColumnService.getDBTableColumnPage(columnQueryDTO);
        } finally {
            // 清除当前线程使用的数据源
            DynamicDataSourceContextHolder.clear();
            //清除临时添加数据源
            dynamicDataSource.removeDataSource(dataSource.getDsName());
        }
    }


}
