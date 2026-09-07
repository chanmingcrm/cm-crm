package com.platform.mesh.uaa.biz.modules.tenant.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientAddDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientEditDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientQueryDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.vo.TenantClientVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 授权客户端租户关系信息
 * @author 蝉鸣
 */
public interface ITenantClientService extends IService<TenantClient> {

    /**
     * 功能描述:
     * 〈获取当前授权客户端租户关系信息〉
     * @param clientId clientId
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    TenantClientVO getClientInfoByClientId(Long clientId);

    /**
     * 功能描述:
     * 〈获取当前授权客户端租户关系信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    TenantClientVO getClientInfoByTenant(TenantClientQueryDTO queryDTO);

    /**
     * 功能描述:
     * 〈新增授权客户端租户关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    TenantClientVO addClient(TenantClientAddDTO clientDTO);

    /**
     * 功能描述:
     * 〈修改授权客户端租户关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    TenantClientVO editClient(TenantClientEditDTO clientDTO);

    /**
     * 功能描述:
     * 〈删除授权客户端租户关系〉
     * @param clientId clientId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteClient(Long clientId);

    /**
     * 功能描述:
     * 〈根据来源获取租户客户端配置〉
     * @param clientSource clientSource
     * @return 正常返回:{@link TenantClient}
     * @author 蝉鸣
     */
    TenantClient getClientInfoByClientSource(Integer clientSource);
}
