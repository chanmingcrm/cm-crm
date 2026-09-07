package com.platform.mesh.upms.biz.modules.doc.online.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineLastDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlinePageDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.po.DocOnline;
import org.apache.ibatis.annotations.Param;

/**
 * @description 在线文档
 * @author 蝉鸣
 */
public interface DocOnlineMapper extends BaseMapper<DocOnline> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<DocOnline> selectPage(MPage<DocOnline> docMPage,@Param("pageDTO") DocOnlinePageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    DocOnline getOnlineHomeInfoById(@Param("onlineId") Long onlineId,@Param("docFlag") Integer docFlag);

    @InterceptorIgnore(tenantLine = "true")
    DocOnline getLastOne(@Param("lastDTO") DocOnlineLastDTO lastDTO);
}