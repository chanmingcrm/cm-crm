package com.platform.mesh.serial.type.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.serial.domain.bo.SerialSetBO;
import com.platform.mesh.serial.enums.SerialTypeEnum;
import com.platform.mesh.serial.type.SerialTypeService;
import com.platform.mesh.core.constants.DateConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SerialTypeDateFactoryImpl implements SerialTypeService {

    private final static Logger log = LoggerFactory.getLogger(SerialTypeDateFactoryImpl.class);

    /**
     * 功能描述:
     * 〈序列号类型〉
     * @return 正常返回:{@link SerialTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public SerialTypeEnum serialType() {
        return SerialTypeEnum.DATE;
    }

    /**
     * 功能描述:
     * 〈日期类型处理〉
     * @author 蝉鸣
     */
    @Override
    public String handle(SerialSetBO setBO, Map<String, Object> instData) {
        //解析规则：type 日期类型，origin：日期值，format:日期格式，role:转换当前对象属性名称
        //如果不存在需要解析规则返回原始值
        if(ObjectUtil.isEmpty(setBO.getRule())) {
            LocalDateTime parse = LocalDateTimeUtil.parse(setBO.getOrigin(), DateConst.YYYY_MM_DD_HH_MM_SS);
            //转换格式
            return LocalDateTimeUtil.format(parse, setBO.getFormat());
        }else{
            String role = StrUtil.toUnderlineCase(setBO.getRule());
            //如果存在需要解析规则返回原始值
            if(instData.containsKey(role)){
                LocalDateTime parse;
                if(ObjectUtil.isEmpty(instData.get(role))){
                    parse = LocalDateTime.now();
                }else{
                    parse = LocalDateTimeUtil.parse(StrUtil.toString(instData.get(role)), DateConst.YYYY_MM_DD_HH_MM_SS);
                }
                //转换格式
                return LocalDateTimeUtil.format(parse, setBO.getFormat());
            }else{
                if(StrConst.CREATE_TIME.equals(role)) {
                    //转换格式
                    LocalDateTime now = LocalDateTime.now();
                    instData.put(StrConst.CREATE_TIME,now);
                    return LocalDateTimeUtil.format(now, setBO.getFormat());
                } else if (StrConst.UPDATE_TIME.equals(role)) {
                    //转换格式
                    LocalDateTime now = LocalDateTime.now();
                    instData.put(StrConst.UPDATE_TIME,now);
                    return LocalDateTimeUtil.format(now, setBO.getFormat());
                }else{
                    return StrUtil.EMPTY;
                }
            }
        }
    }

}
