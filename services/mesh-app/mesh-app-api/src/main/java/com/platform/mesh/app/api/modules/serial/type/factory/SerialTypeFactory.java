package com.platform.mesh.app.api.modules.serial.type.factory;

import com.platform.mesh.app.api.modules.serial.enums.SerialTypeEnum;
import com.platform.mesh.app.api.modules.serial.type.SerialTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 序列号类型工厂
 * @author 蝉鸣
 */
@Service
public class SerialTypeFactory implements InitializingBean {

    @Autowired
    private List<SerialTypeService> serialTypeServiceList;

    private final Map<SerialTypeEnum, SerialTypeService> serialTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的序列号实现〉
     * @param serialType serialType
     * @return 正常返回:{@link SerialTypeService}
     * @author 蝉鸣
     */
    public SerialTypeService getSerialTypeService(SerialTypeEnum serialType){
        return serialTypeMaps.get(serialType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (SerialTypeService service : serialTypeServiceList){
            serialTypeMaps.put(service.serialType(), service);
        }
    }
}
