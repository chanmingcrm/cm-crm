package com.platform.mesh.upms.biz.modules.conf.syssafe.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.dto.ConfSysSafeDTO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.po.ConfSysSafe;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.vo.ConfSysSafeVO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.mapper.ConfSysSafeMapper;
import com.platform.mesh.upms.biz.modules.conf.syssafe.service.IConfSysSafeService;
import com.platform.mesh.upms.biz.modules.conf.syssafe.service.manual.ConfSysSafeServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置安全性
 * @author 蝉鸣
 */
@Service
public class ConfSysSafeServiceImpl extends ServiceImpl<ConfSysSafeMapper, ConfSysSafe> implements IConfSysSafeService  {

    @Autowired
    private ConfSysSafeServiceManual confSysSafeServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前配置安全性信息〉
     * @param sysSafeId sysSafeId  
     * @return 正常返回:{@link ConfSysSafeVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSafeVO getSysSafeInfoById(Long sysSafeId) {
        ConfSysSafe confSysSafe = this.getById(sysSafeId);
        return confSysSafeServiceManual.getSysSafeInfoById(confSysSafe);
    }

    /**
     * 功能描述:
     * 〈新增配置安全性〉
     * @param sysSafeDTO sysSafeDTO
     * @return 正常返回:{@link ConfSysSafeVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSafeVO addSysSafe(ConfSysSafeDTO sysSafeDTO) {
        ConfSysSafe confSysSafe = BeanUtil.copyProperties(sysSafeDTO, ConfSysSafe.class);
        //删除旧的
        Long accountId = UserCacheUtil.getAccountId();
        SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(accountId);
        this.lambdaUpdate()
                .eq(ConfSysSafe::getScopeOrgId,accountBO.getScopeRootId())
                .remove();
        confSysSafe.setScopeOrgId(accountBO.getScopeRootId());
        this.save(confSysSafe);
        return BeanUtil.copyProperties(confSysSafe, ConfSysSafeVO.class);
    }

}
