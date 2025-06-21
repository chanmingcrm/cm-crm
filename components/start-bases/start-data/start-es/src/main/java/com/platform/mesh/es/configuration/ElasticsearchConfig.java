package com.platform.mesh.es.configuration;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.mesh.core.config.JavaTimeModule;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.properties.EsProperties;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    public ElasticsearchClient elasticsearchClient() {
        // 创建 REST 客户端
        RestClient restClient = restClient();
        // 创建传输层
        ElasticsearchTransport transport = transport(restClient);
        // 创建操作项
        RestClientOptions options = options();
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
    public ElasticsearchAsyncClient elasticsearchAsyncClient() {
        // 创建 REST 客户端
        RestClient restClient = restClient();
        // 创建传输层
        ElasticsearchTransport transport = transport(restClient);
        // 创建操作项
        RestClientOptions options = options();
        // 创建 Elasticsearch 客户端
        return new ElasticsearchAsyncClient(transport,options);
    }

    /**
     * 功能描述:
     * 〈创建REST客户端〉
     * @return 正常返回:{@link ElasticsearchClient}
     * @author 蝉鸣
     */
    public RestClient restClient() {
        //可以多个httpHost实例
        HttpHost[] httpHosts = toHttpHost();
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        if(StrUtil.isNotBlank(esProperties.getUsername()) && StrUtil.isNotBlank(esProperties.getPassword())) {
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esProperties.getUsername(), esProperties.getPassword()));
        }
        //Rest客户端配置
        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = httpClientBuilder ->
                httpClientBuilder
                        .setDefaultRequestConfig(RequestConfig.custom()
                                .setConnectTimeout(esProperties.getConnectTimeout())
                                .setSocketTimeout(esProperties.getSocketTimeout())
                                .build())
                        .setDefaultIOReactorConfig(IOReactorConfig.custom()
                                .setSoKeepAlive(true)
                                .build())
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setKeepAliveStrategy((response, context) -> esProperties.getKeepAliveStrategy())
                        .addInterceptorLast((HttpResponseInterceptor) (response, context) -> response.addHeader(EsConst.URL_HEADER_NAME, EsConst.URL_HEADER_VALUE));
        RestClientBuilder.RequestConfigCallback requestConfigCallback =  configCallback->configCallback
                .setConnectTimeout(esProperties.getConnectTimeout())
                .setSocketTimeout(esProperties.getSocketTimeout());
        //构造Rest客户端
        RestClient restClient = RestClient.builder(httpHosts)
                .setRequestConfigCallback(requestConfigCallback)
                .setHttpClientConfigCallback(httpClientConfigCallback)
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
    public ElasticsearchTransport transport(RestClient restClient) {
        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper();
        ObjectMapper objectMapper = jacksonJsonpMapper.objectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
        return new RestClientTransport(restClient, jacksonJsonpMapper);
    }

    /**
     * 功能描述:
     * 〈创建操作项〉
     * @return 正常返回:{@link RestClientOptions}
     * @author 蝉鸣
     */
    public RestClientOptions options() {
        RequestOptions.Builder requestOptionsBuilder = RequestOptions.DEFAULT.toBuilder();
        requestOptionsBuilder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(esProperties.getBufferLimitBytes()));
        return new RestClientOptions(requestOptionsBuilder.build(),Boolean.TRUE);
    }


    /**
     * 功能描述:
     * 〈es客户端地址〉
     * @return 正常返回:{@link HttpHost}
     * @author 蝉鸣
     */
    private HttpHost[] toHttpHost() {
        List<HttpHost> httpHostList = CollUtil.newArrayList();
        if (!StrUtil.isNotBlank(esProperties.getUris())) {
            HttpHost httpHost = new HttpHost(EsConst.DEFAULT_HOST, EsConst.DEFAULT_PORT);
            httpHostList.add(httpHost);
        }
        // 多个IP逗号隔开
        String[] hostArray = esProperties.getUris().split(SymbolConst.COMMA);
        for (String url : hostArray) {
            String[] strings = url.split(SymbolConst.COLON);
            HttpHost httpHost = new HttpHost(strings[NumberConst.NUM_0], Integer.parseInt(strings[NumberConst.NUM_1]), HttpConst.HTTP);
            httpHostList.add(httpHost);
        }
        return ArrayUtil.toArray(httpHostList, HttpHost.class);
    }
}
