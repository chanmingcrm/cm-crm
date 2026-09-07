package com.platform.mesh.mybatis.plus.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.handler.FormatTableNameHandler;
import com.platform.mesh.mybatis.plus.properties.MybatisPlusDataProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description MyBatisPlus配置
 * @author 蝉鸣
 */
@Configuration
@AllArgsConstructor
public class MyBatisPlusConfig {

    private final DataScopeHandler dataScopeHandler;

    private final FormatTableNameHandler formatTableNameHandler;

    private final MybatisPlusDataProperties mybatisPlusDataProperties;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if(mybatisPlusDataProperties.getScope().getEnable()){
            // 添加数据权限插件
            interceptor.addInnerInterceptor(new DataPermissionInterceptor(dataScopeHandler));
        }
        if(mybatisPlusDataProperties.getEnableBlockAttack()){
            // 防全表更新删除插件
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        }
        // 添加动态表明插件
        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor(formatTableNameHandler));

        if(mybatisPlusDataProperties.getEnablePage()){
            // 添加分页插件
            DbType dbType = DbType.getDbType(mybatisPlusDataProperties.getDbType());
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(dbType));
        }
        return interceptor;
    }
}
