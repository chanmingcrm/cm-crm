package com.platform.mesh.upms.biz.modules.doc.pub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.doc.pub.domain.po.DocPub;
import com.platform.mesh.upms.biz.modules.doc.pub.mapper.DocPubMapper;
import com.platform.mesh.upms.biz.modules.doc.pub.service.IDocPubService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 在线文档
 * @author 蝉鸣
 */
@Service
public class DocPubServiceImpl extends ServiceImpl<DocPubMapper, DocPub> implements IDocPubService {

    @Override
    public DocPub getByTenantId(Long tenantId) {
        return this.getBaseMapper().getByTenantId(tenantId);
    }
}
