package com.platform.mesh.upms.biz.modules.doc.file.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 文件
 * @author 蝉鸣
 */
public interface DocFileMapper extends BaseMapper<DocFile> {

    @InterceptorIgnore(tenantLine = "true")
    List<DocFileVO> selectListNoAuth(@Param("fileFlag") Integer fileFlag);
}