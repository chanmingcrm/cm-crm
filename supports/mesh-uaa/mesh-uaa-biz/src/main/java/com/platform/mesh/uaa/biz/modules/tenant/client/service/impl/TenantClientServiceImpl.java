package com.platform.mesh.uaa.biz.modules.tenant.client.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientAddDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientEditDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientQueryDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.vo.TenantClientVO;
import com.platform.mesh.uaa.biz.modules.tenant.client.exception.TenantClientExceptionEnum;
import com.platform.mesh.uaa.biz.modules.tenant.client.mapper.TenantClientMapper;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.ITenantClientService;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.manual.TenantClientServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 授权客户端系统关系
 * @author 蝉鸣
 */
@Service
public class TenantClientServiceImpl extends ServiceImpl<TenantClientMapper, TenantClient> implements ITenantClientService  {

    @Autowired
    private TenantClientServiceManual tenantClientServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前授权客户端系统关系信息〉
     * @param clientId clientId  
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    @Override
    public TenantClientVO getClientInfoByClientId(Long clientId) {
        TenantClient tenantClient = this.lambdaQuery()
                .eq(TenantClient::getId, clientId)
                .one();
        return tenantClientServiceManual.getClientInfoById(tenantClient);
    }

    /**
     * 功能描述:
     * 〈获取当前授权客户端系统关系信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    @Override
    public TenantClientVO getClientInfoByTenant(TenantClientQueryDTO queryDTO) {
        TenantClient tenantClient = this.lambdaQuery()
                .eq(TenantClient::getClientSource,queryDTO.getClientSource())
                .one();
        return tenantClientServiceManual.getClientInfoById(tenantClient);
    }

    /**
     * 功能描述:
     * 〈新增授权客户端系统关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    @Override
    public TenantClientVO addClient(TenantClientAddDTO clientDTO) {
        TenantClient tenantClient = BeanUtil.copyProperties(clientDTO, TenantClient.class);
        tenantClient.setCreateUserId(UserCacheUtil.getUserId());
        tenantClient.setCreateTime(LocalDateTime.now());
        this.save(tenantClient);
        return BeanUtil.copyProperties(tenantClient, TenantClientVO.class);
    }

    /**
     * 功能描述:
     * 〈修改授权客户端系统关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link TenantClientVO}
     * @author 蝉鸣
     */
    @Override
    public TenantClientVO editClient(TenantClientEditDTO clientDTO) {
        if(ObjectUtil.isEmpty(clientDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TenantClientAddDTO::getId);
            throw TenantClientExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TenantClient tenantClient = BeanUtil.copyProperties(clientDTO, TenantClient.class);
        this.updateById(tenantClient);
        return BeanUtil.copyProperties(tenantClient, TenantClientVO.class);
    }

    /**
     * 功能描述:
     * 〈删除授权客户端系统关系〉
     * @param clientId clientId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteClient(Long clientId) {
        return this.removeById(clientId);
    }
}