package com.platform.mesh.gen.biz.modules.code.table.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 表信息
 * @author 蝉鸣
 */
public interface ICodeTableService extends IService<CodeTable> {


    /**
     * 功能描述:
     * 〈获取数据库表信息〉
     * @param tableQueryDTO tableQueryDTO
     * @return 正常返回:{@link List<DbTableBO>}
     * @author 蝉鸣
     */
    MPage<DbTableBO> selectDbTablePage(TableQueryDTO tableQueryDTO);
}
