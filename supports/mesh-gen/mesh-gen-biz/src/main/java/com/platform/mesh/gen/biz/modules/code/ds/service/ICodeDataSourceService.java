package com.platform.mesh.gen.biz.modules.code.ds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.ds.domain.po.CodeDataSource;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 数据源信息
 * @author 蝉鸣
 */
public interface ICodeDataSourceService extends IService<CodeDataSource> {

    /**
     * 获取数据库表单分页
     * @param tableQueryDTO tableQueryDTO
     */
    MPage<DbTableBO> getDBTable(TableQueryDTO tableQueryDTO);

    /**
     * 获取数据库表单分页
     * @param columnQueryDTO columnQueryDTO
     */
    MPage<DbTableColumnBO> getDBTableColumn(TableColumnQueryDTO columnQueryDTO);
}
