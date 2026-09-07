package com.platform.mesh.es.configuration;

import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.properties.EsProperties;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @description Elasticsearch自动装配
 * @author 蝉鸣
 */
@Import(ElasticsearchConfig.class)
@AutoConfiguration
@ConditionalOnClass(ElasticsearchClient.class)
@EnableConfigurationProperties(EsProperties.class)
@ConditionalOnProperty(prefix = EsConst.CONFIG_PREFIX, name = EsConst.ENABLE,
        havingValue = EsConst.DEFAULT_ENABLE_VALUE)
public class ElasticsearchAutoConfiguration {


}


