package com.platform.mesh.mybatis.plus.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.extention.MInExpression;
import com.platform.mesh.mybatis.plus.properties.MybatisPlusDataProperties;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.util.cnfexpression.MultiOrExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @description 数据权限处理
 * @author 蝉鸣
 */
@Component
@AllArgsConstructor
public class DataScopeHandler implements MultiDataPermissionHandler {

    private final static Logger log = LoggerFactory.getLogger(DataScopeHandler.class);

    /**
     * 线程隔离，避免多线程数据冲突
     */
    private static final ThreadLocal<Boolean> ENABLE_DATA_SCOPE = new ThreadLocal<>();

    private final MybatisPlusDataProperties mybatisPlusDataProperties;

    /**
     * 开启数据权限设定
     * @param enable enable
     */
    public static void setEnableDataScope(Boolean enable) {
        ENABLE_DATA_SCOPE.set(enable);
    }

    /**
     * 清除数据权限设定
     */
    public static void unEnableDataScope() {
        ENABLE_DATA_SCOPE.remove();
    }


    /**
     * 功能描述:
     * 〈数据权限处理〉
     * @param table table
     * @param expression expression
     * @param mappedStatementId mappedStatementId
     * @return 正常返回:{@link Expression}
     * @author 蝉鸣
     */
    @Override
    public Expression getSqlSegment(Table table, Expression expression, String mappedStatementId) {
        //过滤隔离方法
        if(ignoreSegment(table.getName(),mappedStatementId)){
            return null;
        }
        // 获取当前的用户
        //从数据权限上下文获取数据权限参数列表
        Map<Integer, Map<String, List<Long>>> dataScopeMap = DataScopeUtil.handleDataScopeWithScope();
        //获取实体对象上有忽略权限注解的字段
        List<String> ignoreFields = entityIgnoreDataScope(table.getName(), mappedStatementId);
        return dataScopeExpression(table,dataScopeMap,ignoreFields);
    }

    /**
     * 功能描述:
     * 〈构建过滤条件〉
     * @param conditions 条件列表
     * @return 正常返回:{@link Expression}
     * @author 蝉鸣
     */
    private Expression dataScopeExpression(Table table,Map<Integer, Map<String, List<Long>>> conditions,List<String> ignoreFields) {
        if(CollUtil.isEmpty(conditions)){
            return null;
        }
        AtomicReference<Expression> expressionAtomic = new AtomicReference<>();
        List<Expression> multiOrList = CollUtil.newArrayList();
        conditions.forEach((scope,data)->{
            //所有权限直接使用或条件
            data.forEach((key,value)->{
                if(CollUtil.isNotEmpty(ignoreFields) && ignoreFields.contains(key)){
                    //如果没有指定忽略字段或者忽略字段包含则跳过
                }else{
                    String column;
                    Alias alias = table.getAlias();
                    if(ObjectUtil.isEmpty(alias)){
                        column = key;
                    }else{
                        column = alias.getName().concat(SymbolConst.PERIOD).concat(key);
                    }
                    if(value.size() == NumberConst.NUM_1){
                        multiOrList.add(new EqualsTo(new Column(column), new StringValue(String.valueOf(CollUtil.getFirst(value)))));
                    }else{
                        //集合数量少于约定
                        if(value.size() <= MybatisPlusConst.SPLIT_NUM){
                            //拼接条件
                            Expression inExpression = this.packInExpression(column, value);
                            if(ObjectUtil.isNotEmpty(inExpression)){
                                multiOrList.add(inExpression);
                            }
                        }else{
                            List<List<Long>> splitList = CollUtil.split(value, MybatisPlusConst.SPLIT_NUM);
                            splitList.forEach(item->{
                                Expression inExpression = this.packInExpression(column, item);
                                if(ObjectUtil.isNotEmpty(inExpression)){
                                    multiOrList.add(inExpression);
                                }
                            });
                        }
                    }
                }
            });
        });
        //定义条件
        if(CollUtil.isEmpty(multiOrList)){
            return null;
        }
        MultiOrExpression multiOrExpression = new MultiOrExpression(multiOrList);
        expressionAtomic.set(this.concatExpression(expressionAtomic.get(),multiOrExpression));
        return expressionAtomic.get();
    }

    /**
     * 功能描述:
     * 〈条件拼接〉
     * @param source source
     * @param target target
     * @return 正常返回:{@link Expression}
     * @author 蝉鸣
     */
    private Expression concatExpression(Expression source, Expression target){
        if(ObjectUtil.isEmpty(source)){
            return target;
        }else{
            return new AndExpression(source, target);
        }
    }

    /**
     * 功能描述:
     * 〈条件拼接〉
     * @param key key
     * @param itemList itemList
     * @return 正常返回:{@link Expression}
     * @author 蝉鸣
     */
    private Expression packInExpression(String key, List<Long> itemList){
        if(ObjectUtil.isEmpty(key) || CollUtil.isEmpty(itemList)){
            return null;
        }
        InExpression inExpression = new MInExpression();
        inExpression.setLeftExpression(new Column(key));
        Expression rightExpression = new ExpressionList<>(itemList.stream().map(LongValue::new).collect(Collectors.toList()));
        inExpression.setRightExpression(rightExpression);
        return inExpression;
    }

    /**
     * 功能描述:
     * 〈判定是否需要隔离〉
     * @param tableName tableName
     * @param mappedStatementId mappedStatementId
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private boolean ignoreSegment(String tableName,String mappedStatementId) {
        //未开启数据权限
        if(ObjectUtil.isNotEmpty(ENABLE_DATA_SCOPE.get()) && !ENABLE_DATA_SCOPE.get()){
            return true;
        }
        //是否开启数据权限
        if(!mybatisPlusDataProperties.getScope().getEnable()){
            return true;
        }
        //校验参数是否正常
        if(StrUtil.isBlank(tableName) || StrUtil.isBlank(mappedStatementId)){
            return true;
        }
        //过滤内置静态表
        if(MybatisPlusConst.IGNORE_TABLES_SCOPE.contains(tableName)){
            return true;
        }
        //过滤动态配置表
        if(mybatisPlusDataProperties.getScope().getIgnoreTables().contains(tableName)){
            return true;
        }
        //过滤注解配置表
        if(hasIgnoreDataScope(tableName,mappedStatementId)){
            return true;
        }
        //过滤动态配置方法
        return mybatisPlusDataProperties.getScope().getIgnoreSegments().contains(mappedStatementId);
    }

    /**
     * 功能描述:
     * 〈是否有忽略数据权限注解〉
     * @param tableName tableName
     * @param mappedStatementId mappedStatementId
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private boolean hasIgnoreDataScope(String tableName,String mappedStatementId) {
        //是否有忽略数据权限注解
        boolean ignoreFlag = false;
        try {
            String className = mappedStatementId.substring(NumberConst.NUM_0, mappedStatementId.lastIndexOf(SymbolConst.PERIOD));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(SymbolConst.PERIOD)+NumberConst.NUM_1);
            Class<?> mapperClass = Class.forName(className);
            Method[] methods = mapperClass.getMethods();
            for (Method method : methods) {
                if(method.getName().equals(methodName)){
                    IgnoreDataScope ignoreDataScope = method.getAnnotation(IgnoreDataScope.class);
                    if(ObjectUtil.isNotEmpty(ignoreDataScope)){
                        ignoreFlag = true;
                        break;
                    }
                }
            }
        } catch (Exception ignored){
        }
        return ignoreFlag;
    }

    /**
     * 功能描述:
     * 〈是否有忽略数据权限注解〉
     * @param tableName tableName
     * @param mappedStatementId mappedStatementId
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private List<String> entityIgnoreDataScope(String tableName,String mappedStatementId) {
        if(!mybatisPlusDataProperties.getScope().getEnableAnno()){
            return CollUtil.newArrayList();
        }
        //是否有忽略数据权限注解
        try {
            String className = mappedStatementId.substring(NumberConst.NUM_0, mappedStatementId.lastIndexOf(SymbolConst.PERIOD));
            Class<?> mapperClass = Class.forName(className);
            Class<?> entityClass = extractEntityTypes(mapperClass);
            return SqlUtil.getAnnoField(entityClass,IgnoreDataScope.class);
        } catch (Exception ignored){
        }
        return CollUtil.newArrayList();
    }


    /**
     * 提取 Mapper 接口的泛型实体类型
     * @param mapperInterface 继承 BaseMapper<T> 的接口集合
     * @return Mapper 接口及其泛型实体类型的映射
     */
    private static Class<?> extractEntityTypes(Class<?> mapperInterface) {
        Type[] genericInterfaces = mapperInterface.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType().equals(BaseMapper.class)) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    if (typeArguments.length > NumberConst.NUM_0 && typeArguments[NumberConst.NUM_0] instanceof Class) {
                        return (Class<?>) typeArguments[NumberConst.NUM_0];
                    }
                }
            }
        }
        return null;
    }

}
