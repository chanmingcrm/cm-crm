package com.platform.mesh.gen.biz.modules.code.tablecolunm.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po.CodeTableColumn;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 表字段信息
 * @author 蝉鸣
 */
public interface ICodeTableColumnService extends IService<CodeTableColumn> {


    /**
     * 功能描述:
     * 〈根据表空间和表名获取表信息〉
     * @param columnQueryDTO columnQueryDTO
     * @return 正常返回:{@link MPage<DbTableColumnBO>}
     * @author 蝉鸣
     */
    MPage<DbTableColumnBO> getDBTableColumnPage(TableColumnQueryDTO columnQueryDTO);

    /**
     * 功能描述:
     * 〈查询业务字段列表〉
     * @param tableId tableId
     * @return 正常返回:{@link List<CodeTableColumn>}
     * @author 蝉鸣
     */
    List<CodeTableColumn> getListByTableId(Long tableId);

    /**
     * 功能描述:
     * 〈新增业务字段〉
     * @param CodeTableColumn CodeTableColumn
     * @author 蝉鸣
     */
    void addCodeTableColumn(CodeTableColumn CodeTableColumn);

    /**
     * 功能描述:
     * 〈修改业务字段〉
     * @param CodeTableColumn CodeTableColumn
     * @author 蝉鸣
     */
    void editCodeTableColumn(CodeTableColumn CodeTableColumn);

    /**
     * 功能描述:
     * 〈删除业务字段信息〉
     * @param id id
     * @author 蝉鸣
     */
    void deleteCodeTableColumnById(Long id);

}
