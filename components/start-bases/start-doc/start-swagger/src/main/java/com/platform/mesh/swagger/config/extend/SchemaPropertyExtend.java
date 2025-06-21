package com.platform.mesh.swagger.config.extend;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.utils.format.FormatUtil;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * @description 枚举类说明解析
 * @author 蝉鸣
 */
@Component
public class SchemaPropertyExtend implements PropertyCustomizer {

    /**
     * 功能描述:
     * 〈重写〉
     * @param schema schema
     * @param type type
     * @return 正常返回:{@link Schema}
     * @author 蝉鸣
     */
    @Override
    public Schema customize(Schema schema, AnnotatedType type) {
        if (ObjectUtil.isEmpty(type.getCtxAnnotations())) {
            return schema;
        }
        //初始化描述
        StringBuilder description = new StringBuilder();
        //设置注解信息
        for (Annotation ctxAnnotation : type.getCtxAnnotations()) {
            if (ctxAnnotation.annotationType().equals(SchemaEnum.class)) {
                //设置必填项
                if(((SchemaEnum) ctxAnnotation).required()){
                    schema.setRequired(CollUtil.newArrayList(Boolean.TRUE.toString()));
                }
                //设置描述信息
                description.append(((SchemaEnum) ctxAnnotation).description());
                Class<? extends BaseEnum> clazz = ((SchemaEnum) ctxAnnotation).value();
                description.append(FormatUtil.getEnumDescJSON(clazz));
            }
        }
        //返回描述
        if (StrUtil.isNotEmpty(description)) {
            schema.setDescription(description.toString());
        }
        return schema;
    }

}
