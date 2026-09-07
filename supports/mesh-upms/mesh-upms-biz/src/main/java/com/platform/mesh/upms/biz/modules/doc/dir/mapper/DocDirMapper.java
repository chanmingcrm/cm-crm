package com.platform.mesh.upms.biz.modules.doc.dir.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.dto.DocDirPageDTO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import org.apache.ibatis.annotations.Param;

/**
 * @description 文件目录
 * @author 蝉鸣
 */
public interface DocDirMapper extends BaseMapper<DocDir> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<DocDir> selectHomePage(MPage<DocDir> dirMPage,@Param("pageDTO") DocDirPageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    DocDir getParentDir(@Param("parentId") Long parentId);

    @InterceptorIgnore(tenantLine = "true")
    DocDir getOneOpenDir(@Param("openFlag") Integer openFlag);
}