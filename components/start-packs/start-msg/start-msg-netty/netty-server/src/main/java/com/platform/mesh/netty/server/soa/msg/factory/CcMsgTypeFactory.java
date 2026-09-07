package com.platform.mesh.netty.server.soa.msg.factory;

import com.platform.mesh.netty.server.soa.msg.CcMsgTypeService;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 消息平台工厂
 * @author 蝉鸣
 */
@Service
public class CcMsgTypeFactory implements InitializingBean {

    @Autowired
    private List<CcMsgTypeService> ccMsgTypeServiceList;

    private final Map<CcMsgTypeEnum, CcMsgTypeService> ccMsgMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈获取消息处理服务〉
     * @param ccMsgTypeEnum ccMsgTypeEnum
     * @return 正常返回:{@link CcMsgTypeService}
     * @author 蝉鸣
     */
    public CcMsgTypeService getCcMsgService(CcMsgTypeEnum ccMsgTypeEnum){
        return ccMsgMaps.get(ccMsgTypeEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (CcMsgTypeService service : ccMsgTypeServiceList){
            ccMsgMaps.put(service.ccMsgType(), service);
        }
    }
}
