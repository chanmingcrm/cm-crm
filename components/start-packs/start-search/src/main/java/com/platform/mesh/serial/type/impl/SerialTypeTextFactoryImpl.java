package com.platform.mesh.serial.type.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.serial.domain.bo.SerialSetBO;
import com.platform.mesh.serial.enums.SerialTypeEnum;
import com.platform.mesh.serial.type.SerialTypeService;
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
public class SerialTypeTextFactoryImpl implements SerialTypeService {

    private final static Logger log = LoggerFactory.getLogger(SerialTypeTextFactoryImpl.class);

    /**
     * 功能描述:
     * 〈序列号类型〉
     * @return 正常返回:{@link SerialTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public SerialTypeEnum serialType() {
        return SerialTypeEnum.TEXT;
    }

    /**
     * 功能描述:
     * 〈文本类型处理〉
     * @author 蝉鸣
     */
    @Override
    public String handle(SerialSetBO setBO, Map<String, Object> instData) {
        //解析规则：type 文本类型，origin：文本值，format:无，role:转换当前对象属性名称
        //如果不存在需要解析规则返回原始值
        if(ObjectUtil.isEmpty(setBO.getRule())) {
            return setBO.getOrigin();
        }else{
            //如果存在需要解析规则从实例数据中取值
            if(instData.containsKey(setBO.getRule())) {
                return StrUtil.toString(instData.get(setBO.getRule()));
            }else{
                return StrUtil.EMPTY;
            }
        }
    }

}
