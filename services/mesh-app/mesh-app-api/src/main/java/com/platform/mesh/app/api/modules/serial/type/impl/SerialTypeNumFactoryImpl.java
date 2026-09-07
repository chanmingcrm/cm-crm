package com.platform.mesh.app.api.modules.serial.type.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.app.api.modules.serial.domain.bo.SerialSetBO;
import com.platform.mesh.app.api.modules.serial.enums.SerialTypeEnum;
import com.platform.mesh.app.api.modules.serial.type.SerialTypeService;
import com.platform.mesh.core.constants.StrConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SerialTypeNumFactoryImpl implements SerialTypeService {

    private final static Logger log = LoggerFactory.getLogger(SerialTypeNumFactoryImpl.class);

    /**
     * 功能描述:
     * 〈序列号类型〉
     * @return 正常返回:{@link SerialTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public SerialTypeEnum serialType() {
        return SerialTypeEnum.NUM;
    }

    /**
     * 功能描述:
     * 〈数字类型处理〉
     * @author 蝉鸣
     */
    @Override
    public String handle(SerialSetBO setBO, Map<String, Object> instData) {
        //解析规则：type 数值类型，value：初始值设置，format:自增步长值，role:重置规则
        // 获取当前序列值（优先从实例数据取，否则用初始值，空则置0）
        Object currentObj = instData.getOrDefault(StrConst.DATA_SERIAL, setBO.getOrigin());
        int current = ObjectUtil.isEmpty(currentObj) ? NumberConst.NUM_0 : Integer.parseInt(currentObj.toString());

        // 计算新值：如果实例数据中有序列，则叠加步长；否则保持当前值（首次生成）
        int step = instData.containsKey(StrConst.DATA_SERIAL) ? Integer.parseInt(setBO.getFormat()) : NumberConst.NUM_0;
        int newValue = current + step;

        // 若无初始值，直接返回数字字符串；否则按初始值长度补零
        if (ObjectUtil.isEmpty(setBO.getOrigin())) {
            return String.valueOf(newValue);
        }
        int length = setBO.getOrigin().length();
        return String.format("%0" + length + "d", newValue);
    }

}
