package com.platform.mesh.serial.type;


import com.platform.mesh.serial.domain.bo.SerialSetBO;
import com.platform.mesh.serial.enums.SerialTypeEnum;

import java.util.Map;

/**
 * @description 节点工厂
 * @author 蝉鸣
 */
public interface SerialTypeService {

    /**
     * 功能描述:
     * 〈序列号类型〉
     * @return 正常返回:{@link SerialTypeEnum}
     * @author 蝉鸣
     */
    SerialTypeEnum serialType();

    /**
     * 功能描述:
     * 〈序列号处理〉
     * @param setBO setBO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String handle(SerialSetBO setBO, Map<String, Object> instData);
}
