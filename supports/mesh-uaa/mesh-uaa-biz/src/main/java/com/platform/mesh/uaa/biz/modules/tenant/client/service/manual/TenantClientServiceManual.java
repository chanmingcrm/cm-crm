package com.platform.mesh.uaa.biz.modules.tenant.client.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.vo.TenantClientVO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 授权客户端租户关系
 * @author 蝉鸣
 */
@Service
public class TenantClientServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前授权客户端租户关系信息〉
     * @param tenantClient tenantClient 
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    public TenantClientVO getClientInfoById(TenantClient tenantClient) {
        TenantClientVO tenantClientVO = new TenantClientVO();
        if(ObjectUtil.isEmpty(tenantClientVO)){
            return tenantClientVO;
        }
        //转换VO
        BeanUtil.copyProperties(tenantClient, tenantClientVO);
        return tenantClientVO;
    }

}