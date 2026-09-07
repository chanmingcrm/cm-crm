package com.platform.mesh.gen.biz.modules.code.table.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.gen.biz.modules.code.table.mapper.CodeTableMapper;
import com.platform.mesh.gen.biz.modules.code.table.service.ICodeTableService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 表信息
 * @author 蝉鸣
 */
@Service
public class CodeTableServiceImpl extends ServiceImpl<CodeTableMapper, CodeTable> implements ICodeTableService {


    /**
     * 功能描述:
     * 〈获取数据库表信息〉
     * @param tableQueryDTO tableQueryDTO
     * @return 正常返回:{@link MPage<DbTableBO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DbTableBO> selectDbTablePage(TableQueryDTO tableQueryDTO) {
        MPage<CodeTable> mPage = MPageUtil.pageEntityToMPage(tableQueryDTO, CodeTable.class);
        return this.getBaseMapper().selectDbTableList(mPage,tableQueryDTO);
    }
}
