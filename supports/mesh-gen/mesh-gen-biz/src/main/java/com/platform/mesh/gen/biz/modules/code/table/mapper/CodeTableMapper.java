package com.platform.mesh.gen.biz.modules.code.table.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 表Mapper
 * @author 蝉鸣
 */
public interface CodeTableMapper extends BaseMapper<CodeTable> {

    /**
     * 功能描述:
     * 〈获取数据库表信息〉
     * @param tableQueryDTO tableQueryDTO
     * @return 正常返回:{@link List<DbTableBO>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    MPage<DbTableBO> selectDbTableList(IPage<?> page, @Param("tableDTO") TableQueryDTO tableQueryDTO);
}
