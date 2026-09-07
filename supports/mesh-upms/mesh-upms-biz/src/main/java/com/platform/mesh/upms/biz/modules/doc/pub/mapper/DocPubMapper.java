package com.platform.mesh.upms.biz.modules.doc.pub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.doc.pub.domain.po.DocPub;
import org.apache.ibatis.annotations.Param;

/**
 * @description 在线文档
 * @author 蝉鸣
 */
public interface DocPubMapper extends BaseMapper<DocPub> {

    DocPub getByTenantId(@Param("tenantId") Long tenantId);
}