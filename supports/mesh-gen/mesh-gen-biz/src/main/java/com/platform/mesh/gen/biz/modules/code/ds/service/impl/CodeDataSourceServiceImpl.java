package com.platform.mesh.gen.biz.modules.code.ds.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.ds.domain.po.CodeDataSource;
import com.platform.mesh.gen.biz.modules.code.ds.mapper.CodeDataSourceMapper;
import com.platform.mesh.gen.biz.modules.code.ds.service.ICodeDataSourceService;
import com.platform.mesh.gen.biz.modules.code.ds.service.manual.CodeDataSourceServiceManual;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 数据源信息
 * @author 蝉鸣
 */
@Service
public class CodeDataSourceServiceImpl extends ServiceImpl<CodeDataSourceMapper, CodeDataSource> implements ICodeDataSourceService {

    @Autowired
    private CodeDataSourceServiceManual codeDataSourceServiceManual;


    /**
     * 获取数据库表单分页
     * @param tableQueryDTO tableQueryDTO
     */
    @Override
    public MPage<DbTableBO> getDBTable(TableQueryDTO tableQueryDTO) {
        //获取配置
        CodeDataSource dataSource = getById(tableQueryDTO.getDsId());
        //获取数据库表
        return codeDataSourceServiceManual.getDBTable(dataSource,tableQueryDTO);
    }

    /**
     * 获取数据库表单分页
     * @param columnQueryDTO columnQueryDTO
     */
    @Override
    public MPage<DbTableColumnBO> getDBTableColumn(TableColumnQueryDTO columnQueryDTO) {
        //获取配置
        CodeDataSource dataSource = getById(columnQueryDTO.getDsId());
        //获取数据库表
        return codeDataSourceServiceManual.getDBTableColumn(dataSource,columnQueryDTO);
    }

}
