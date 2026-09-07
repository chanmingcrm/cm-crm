package com.platform.mesh.mybatis.plus.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description SQL工具
 * @author 蝉鸣
 */
public class SqlUtil {


    /**
     * 功能描述:
     * 〈获取有TableName注解的所有对象〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public static Map<String, TableInfo> getTableInfo() {
        List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        return tableInfos.stream().collect(Collectors.toMap(TableInfo::getTableName, Function.identity()));
    }

    /**
     * 功能描述:
     * 〈获取特定表名的对象〉
     * @return 正常返回:{@link TableInfo}
     * @author 蝉鸣
     */
    public static TableInfo getTableInfo(String tableName) {
        // 获取所有已注册的 TableInfo
        return TableInfoHelper.getTableInfo(tableName);
    }

    /**
     * 功能描述:
     * 〈获取有注解的字段属性名称〉
     * @param beanClass beanClass
     * @param annotationType annotationType
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static List<String> getAnnoField(Class<?> beanClass,Class<? extends Annotation> annotationType){
        Field[] fields = ReflectUtil.getFields(beanClass);
        if(ArrayUtil.isEmpty(fields)){
            return CollUtil.newArrayList();

        }
        List<Field> fieldList = Arrays.stream(fields).filter(item -> item.isAnnotationPresent(annotationType)).collect(Collectors.toList());
        if(CollUtil.isEmpty(fieldList)){
            return CollUtil.newArrayList();
        }
        return fieldList.stream().map(field -> StrUtil.toUnderlineCase(field.getName())).toList();
    }

    /**
     * 功能描述:
     * 〈获取PO对应数据库表名〉
     * @param beanClass beanClass
     * @param annotationType annotationType
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getTableName(Class<?> beanClass, Class<? extends Annotation> annotationType){
        Object value = AnnotationUtil.getAnnotationValue(beanClass, annotationType);
        return value.toString();
    }

    /**
     * 功能描述:
     * 〈获取PO对应数据库表主键名〉
     * @param beanClass beanClass
     * @param annotationType annotationType
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getIdName(Class<?> beanClass,Class<? extends Annotation> annotationType){
        Field[] fields = ReflectUtil.getFields(beanClass);
        if(ArrayUtil.isEmpty(fields)){
            return StrUtil.EMPTY;

        }
        List<Field> collect = Arrays.stream(fields).filter(item -> item.isAnnotationPresent(annotationType)).collect(Collectors.toList());
        if(CollUtil.isEmpty(collect)){
            return StrUtil.EMPTY;
        }
        String fieldName = AnnotationUtil.getAnnotationValue(CollUtil.getFirst(collect), annotationType).toString();
        if(StrUtil.isBlank(fieldName)){
            fieldName = StrUtil.toUnderlineCase(CollUtil.getFirst(collect).getName());
        }
        return fieldName;
    }


    /**
     * 功能描述:
     * 〈获取通用sql〉
     * @param beanClass beanClass
     * @param parentIdValue parentIdValue
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getCommonChildrenSql(Class<?> beanClass,Object parentIdValue){
        String tableName = getTableName(beanClass, TableName.class);
        String idName = getIdName(beanClass, TableId.class);
        String parentIdName = getIdName(beanClass, TableParentId.class);

        return getChildrensSql(tableName, idName,parentIdName, parentIdValue);
    }

    /**
     * 功能描述:
     * 〈获取通用sql〉
     * @param beanClass beanClass
     * @param parentIdValue parentIdValue
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getCommonParentSql(Class<?> beanClass,Object parentIdValue){
        String tableName = getTableName(beanClass, TableName.class);
        String idName = getIdName(beanClass, TableId.class);
        String parentIdName = getIdName(beanClass, TableParentId.class);
        return getParentsSql(tableName, idName,parentIdName, parentIdValue);
    }

    /**
     * 功能描述:
     * 〈查询子项不包含当前id〉
     * @param tableName tableName
     * @param idName idName
     * @param parentIdName parentIdName
     * @param parentIdValue parentIdValue
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getChildrensSql(String tableName,String idName,String parentIdName,Object parentIdValue){
        String sql= StrUtil.EMPTY;
        sql+= idName +
                "\tIN (\n" +
                "\tSELECT\n" +
                "\t\tid \n" +
                "\tFROM\n" +
                "\t\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tt1.id,\n" +
                "\t\tIF\n" +
                "\t\t\t( find_in_set( "+ parentIdName +", @pids ) > 0, @pids := concat( @pids, ',', id ), 0 ) AS ischild \n" +
                "\t\tFROM\n" +
                "\t\t\t( SELECT "+ idName +" AS id, "+ parentIdName +" FROM "+ tableName +" t ORDER BY "+ parentIdName +", id ) t1,\n" +
                "\t\t\t( SELECT @pids := '"+ parentIdValue +"' ) t2 \n" +
                "\t\t) t3 \n" +
                "\tWHERE\n" +
                "\tischild != '0' \n" +
                "\t)";
        return sql;
    }

    /**
     * 功能描述:
     * 〈查询父项包含当前id〉
     * @param tableName tableName
     * @param idName idName
     * @param parentIdName parentIdName
     * @param parentIdValue parentIdValue
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public static String getParentsSql(String tableName,String idName,String parentIdName,Object parentIdValue){
        String sql= StrUtil.EMPTY;
        sql+= "FIND_IN_SET(\n" +
                "\t"+ idName +",(\n" +
                "\t\tSELECT\n" +
                "\t\t\tisParent \n" +
                "\t\tFROM\n" +
                "\t\t\t(\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tt1.id,\n" +
                "\t\t\t\tt1."+ parentIdName +",\n" +
                "\t\t\t\t@pids,\n" +
                "\t\t\tIF\n" +
                "\t\t\t\t( find_in_set( id, @pids ) > 0, @pids := concat( "+ parentIdName +" ), 0 ) AS parent,\n" +
                "\t\t\tIF\n" +
                "\t\t\t\t( find_in_set( "+ parentIdName +", @pids ) > 0, @pids := concat( @pids, ',', id ), 0 ) AS isParent \n" +
                "\t\t\tFROM\n" +
                "\t\t\t\t( SELECT "+ idName +" AS id, "+ parentIdName +" FROM "+ tableName +" t ORDER BY "+ parentIdName +", "+ idName +" ) t1,\n" +
                "\t\t\t\t( SELECT @pids := "+ parentIdValue +" ) t2 \n" +
                "\t\t\t) t3 \n" +
                "\t\tWHERE\n" +
                "\t\tid = "+ parentIdValue +" \n" +
                "\t))";
        return sql;
    }

    /**
     * 功能描述:
     * 〈获取最新的一条数据〉
     * @param clazz clazz
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    public static <T> T getMaxOne(Class<T> clazz, Function<QueryChainWrapper<T>, QueryChainWrapper<T>> wrapper) {
        //如果存在序列号组件则需要查询最新的数据获取序列号
        QueryChainWrapper<T> query = new QueryChainWrapper<>(clazz);
        if(ObjectUtil.isNotNull(wrapper)){
            query = wrapper.apply(query);
        }
        query.orderByDesc(StrConst.ID).last(MybatisPlusConst.LIMIT_1);
        return query.one();
    }
}
