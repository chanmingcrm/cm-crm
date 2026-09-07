package com.platform.mesh.gen.biz.modules.code.tablecolunm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po.CodeTableColumn;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.mapper.CodeTableColumnMapper;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.service.ICodeTableColumnService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 表字段信息
 * @author 蝉鸣
 */
@Service
public class CodeTableColumnServiceImpl extends ServiceImpl<CodeTableColumnMapper, CodeTableColumn> implements ICodeTableColumnService {

    /**
     * 功能描述:
     * 〈根据表空间和表名获取表信息〉
     * @param columnQueryDTO columnQueryDTO
     * @return 正常返回:{@link MPage<DbTableColumnBO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DbTableColumnBO> getDBTableColumnPage(TableColumnQueryDTO columnQueryDTO) {
        MPage<CodeTableColumn> mPage = MPageUtil.pageEntityToMPage(columnQueryDTO, CodeTableColumn.class);
        return this.getBaseMapper().getDBTableColumnPage(mPage,columnQueryDTO.getTableSchema(),columnQueryDTO.getTableNames());
    }

    /**
     * 功能描述:
     * 〈查询业务字段列表〉
     * @param tableId tableId
     * @return 正常返回:{@link List<CodeTableColumn>}
     * @author 蝉鸣
     */
    @Override
    public List<CodeTableColumn> getListByTableId(Long tableId) {
        return this.lambdaQuery().eq(CodeTableColumn::getTableId,tableId).list();
    }

    /**
     * 功能描述:
     * 〈新增业务字段〉
     * @param CodeTableColumn CodeTableColumn
     * @author 蝉鸣
     */
    @Override
    public void addCodeTableColumn(CodeTableColumn CodeTableColumn) {
        this.save(CodeTableColumn);
    }

    /**
     * 功能描述:
     * 〈修改业务字段〉
     * @param CodeTableColumn CodeTableColumn
     * @author 蝉鸣
     */
    @Override
    public void editCodeTableColumn(CodeTableColumn CodeTableColumn) {
        this.updateById(CodeTableColumn);
    }

    /**
     * 功能描述:
     * 〈删除业务字段信息〉
     * @param id id
     * @author 蝉鸣
     */
    @Override
    public void deleteCodeTableColumnById(Long id) {
        this.removeById(id);
    }

}
