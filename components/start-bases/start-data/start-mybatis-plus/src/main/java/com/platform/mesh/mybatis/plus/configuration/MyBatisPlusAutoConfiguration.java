package com.platform.mesh.mybatis.plus.configuration;

import com.platform.mesh.mybatis.plus.properties.MybatisPlusDataProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @description MyBatisPlus自动装配
 * @author 蝉鸣
 */
@Import(MyBatisPlusConfig.class)
@MapperScan("com.platform.**.mapper")
@EnableConfigurationProperties(MybatisPlusDataProperties.class)
public class MyBatisPlusAutoConfiguration {

}


