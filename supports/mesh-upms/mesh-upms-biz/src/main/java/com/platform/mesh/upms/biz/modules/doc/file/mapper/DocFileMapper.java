package com.platform.mesh.upms.biz.modules.doc.file.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocPageDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 文件
 * @author 蝉鸣
 */
public interface DocFileMapper extends BaseMapper<DocFile> {


    @InterceptorIgnore(tenantLine = "true")
    MPage<DocFile> selectPageWithDir(MPage<DocFile> fileMPage,@Param("dirIds") List<Long> dirIds,@Param("pageDTO") DocPageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    List<DocFileVO> selectListNoAuth(@Param("fileFlag") Integer fileFlag);

    @InterceptorIgnore(tenantLine = "true")
    DocFile getFileById(@Param("fileId") Long fileId,@Param("tenantId") Long tenantId);

    @InterceptorIgnore(tenantLine = "true")
    MPage<DocFile> selectOpenPage(MPage<DocFile> fileMPage,@Param("dirIds") List<Long> dirIds,@Param("openFlag") Integer openFlag);
}