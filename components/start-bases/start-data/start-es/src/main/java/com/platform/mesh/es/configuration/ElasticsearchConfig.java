package com.platform.mesh.es.configuration;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.Jackson3JsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest5_client.Rest5ClientOptions;
import co.elastic.clients.transport.rest5_client.Rest5ClientTransport;
import co.elastic.clients.transport.rest5_client.low_level.RequestOptions;
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;
import co.elastic.clients.transport.rest_client.RestClientOptions;
import com.platform.mesh.core.config.JavaTimeModule;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.properties.EsProperties;
import lombok.AllArgsConstructor;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class ElasticsearchConfig {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private final EsProperties esProperties;

    /**
     * 功能描述:
     * 〈获取同步客户端〉
     * @return 正常返回:{@link ElasticsearchClient}
     * @author 蝉鸣
     */
    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        // 创建操作项
        Rest5ClientOptions options = options();
        // 创建 Elasticsearch 客户端
        return new ElasticsearchClient(transport,options);
    }

    /**
     * 功能描述:
     * 〈获取异步客户端〉
     * @return 正常返回:{@link ElasticsearchAsyncClient}
     * @author 蝉鸣
     */
    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(ElasticsearchTransport transport) {
        // 创建操作项
        Rest5ClientOptions options = options();
        // 创建 Elasticsearch 客户端
        return new ElasticsearchAsyncClient(transport,options);
    }

    /**
     * 功能描述:
     * 〈创建REST客户端〉
     * @return 正常返回:{@link ElasticsearchClient}
     * @author 蝉鸣
     */
    private Rest5Client createRestClient() {
        //可以多个httpHost实例
        HttpHost[] httpHosts = toHttpHost();
        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        if(StrUtil.isNotBlank(esProperties.getUsername()) && StrUtil.isNotBlank(esProperties.getPassword())) {
            for (HttpHost httpHost : httpHosts) {
                try {
                    AuthScope authScope = new AuthScope(httpHost);
                    basicCredentialsProvider.setCredentials(authScope, new UsernamePasswordCredentials(esProperties.getUsername(), esProperties.getPassword().toCharArray()));
                } catch (Exception e) {
                    log.warn("Failed to parse URI: {}", httpHost, e);
                }
            }
        }
        //构造Rest客户端
        Rest5Client restClient = Rest5Client.builder(httpHosts)
                // 请求配置
                .setRequestConfigCallback(requestConfigCallback->{
                    requestConfigCallback.setConnectionRequestTimeout(
                            esProperties.getConnectTimeout().toMillis(), TimeUnit.MILLISECONDS);
                })
                // Http客户端配置
                .setHttpClientConfigCallback(httpAsyncClientBuilder->{
                    httpAsyncClientBuilder
                            .setDefaultRequestConfig(RequestConfig.custom()
                                    .setConnectionRequestTimeout(esProperties.getConnectTimeout().toMillis(),
                                            TimeUnit.MILLISECONDS)
                                    .setResponseTimeout(esProperties.getSocketTimeout().toMillis(),
                                            TimeUnit.MILLISECONDS)
                                    .build())
                             .setIOReactorConfig(IOReactorConfig.custom()
                                     .setIoThreadCount(esProperties.getIoThreadCount())
                                     .setSoTimeout(Math.toIntExact(esProperties.getSocketTimeout().toMillis()),
                                             TimeUnit.MILLISECONDS)
                                     .setSoKeepAlive(true).build())
                            .setDefaultCredentialsProvider(basicCredentialsProvider)
                            ;
                })
                // 连接池配置
                .setConnectionManagerCallback(poolingAsyncClientConnectionManagerBuilder->{
                    poolingAsyncClientConnectionManagerBuilder
                            .setMaxConnTotal(esProperties.getMaxConnTotal())
                            .setMaxConnPerRoute(esProperties.getMaxConnPerRoute());
                })
                .build();
        log.info(restClient.toString());
        return restClient;
    }

    /**
     * 功能描述:
     * 〈创建传输层〉
     * @return 正常返回:{@link ElasticsearchTransport}
     * @author 蝉鸣
     */
    @Bean
    public ElasticsearchTransport transport() {
        Rest5Client restClient = createRestClient();
        return new Rest5ClientTransport(restClient, createJsonpMapper());
    }

    /**
     * 功能描述:
     * 〈创建 Elasticsearch JSON 映射器〉
     * @return Elasticsearch JSON 映射器
     * @author qingfeng
     */
    private Jackson3JsonpMapper createJsonpMapper() {
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .build();
        return new Jackson3JsonpMapper(jsonMapper);
    }

    /**
     * 功能描述:
     * 〈创建操作项〉
     * @return 正常返回:{@link RestClientOptions}
     * @author 蝉鸣
     */
    public Rest5ClientOptions options() {
        RequestOptions.Builder requestOptionsBuilder = RequestOptions.DEFAULT.toBuilder();
        return new Rest5ClientOptions(requestOptionsBuilder.build(),Boolean.TRUE);
    }


    /**
     * 功能描述:
     * 〈es客户端地址〉
     * @return 正常返回:{@link HttpHost}
     * @author 蝉鸣
     */
    private HttpHost[] toHttpHost() {
        List<HttpHost> httpHostList = CollUtil.newArrayList();
        if (StrUtil.isBlank(esProperties.getUris())) {
            HttpHost httpHost = new HttpHost(EsConst.DEFAULT_HOST, EsConst.DEFAULT_PORT);
            httpHostList.add(httpHost);
        }else{
            // 多个IP逗号隔开
            String[] hostArray = esProperties.getUris().split(SymbolConst.COMMA);
            for (String url : hostArray) {
                String[] strings = url.split(SymbolConst.COLON);
                HttpHost httpHost = new HttpHost(HttpConst.HTTP, strings[NumberConst.NUM_0], Integer.parseInt(strings[NumberConst.NUM_1]));
                httpHostList.add(httpHost);
            }
        }
        return ArrayUtil.toArray(httpHostList, HttpHost.class);
    }

}
