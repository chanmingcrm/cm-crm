package com.platform.mesh.serial.type.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.serial.domain.bo.SerialSetBO;
import com.platform.mesh.serial.enums.SerialTypeEnum;
import com.platform.mesh.serial.type.SerialTypeService;
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
        Object instIntObj;
        int instInt;
        if(instData.containsKey(StrConst.DATA_SERIAL)){
            instIntObj = instData.get(StrConst.DATA_SERIAL);
        }else{
            instIntObj = setBO.getOrigin();
        }
        if(ObjectUtil.isEmpty(instIntObj)){
            instInt = NumberConst.NUM_0;
        }else{
            instInt = Integer.parseInt(instIntObj.toString());
        }
        //获取初始值几位数
        int stepInt = Integer.parseInt(setBO.getFormat());
        int instInCre = instInt + stepInt;
        //如果起始值为空，则直接实例值+步长
        if(ObjectUtil.isEmpty(setBO.getOrigin())){
            return StrUtil.toString(instInCre);
        }else{
            //解析初始值位数，自动填充补位
            int length = setBO.getOrigin().length();
            return String.format("%0"+length+"d", instInCre);
        }
    }

}
