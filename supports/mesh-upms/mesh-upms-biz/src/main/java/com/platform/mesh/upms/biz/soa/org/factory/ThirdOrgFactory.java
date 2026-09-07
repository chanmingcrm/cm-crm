package com.platform.mesh.upms.biz.soa.org.factory;

import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.soa.org.ThirdOrgService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 获取第三方工厂
 * @author 蝉鸣
 */
@Service
public class ThirdOrgFactory implements InitializingBean {

    @Autowired
    private List<ThirdOrgService> thirdOrgServiceList;

    private final Map<SourceFlagEnum, ThirdOrgService> thirdOrgMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈获取第三方组织服务〉
     * @param sourceFlagEnum sourceFlagEnum
     * @return 正常返回:{@link ThirdOrgService}
     * @author 蝉鸣
     */
    public ThirdOrgService getThirdOrgService(SourceFlagEnum sourceFlagEnum){
        return thirdOrgMaps.get(sourceFlagEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (ThirdOrgService service : thirdOrgServiceList){
            thirdOrgMaps.put(service.sourceFlag(), service);
        }
    }
}
