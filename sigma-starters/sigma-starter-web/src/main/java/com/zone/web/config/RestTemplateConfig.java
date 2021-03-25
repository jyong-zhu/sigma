package com.zone.web.config;

import com.google.common.collect.Lists;
import com.zone.web.interceptor.RestClientHttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/19 12:20 上午
 * @Description: RestTemplate需要通过RestTemplateBuilder进行注入
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestClientHttpRequestInterceptor getRestClientHttpRequestInterceptor() {
        return new RestClientHttpRequestInterceptor();
    }

    @Bean
    @LoadBalanced // 添加该注解，可以直接通过服务名找到对应的IP地址
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // 需要配置 RestTemplate 的一些属性，否则会出现 org.apache.http.NoHttpResponseException
        // 详见 https://blog.csdn.net/tiantiandjava/article/details/72781561
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = (httpResponse, httpContext) -> {
            return 20 * 1000; // tomcat默认keepAliveTimeout为20s
        };
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(20, TimeUnit.SECONDS);
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(200);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10 * 1000)
                .setSocketTimeout(10 * 1000)
                .setConnectionRequestTimeout(10 * 1000)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(connManager);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler());
        httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy);
        HttpClient httpClient = httpClientBuilder.build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // 注册拦截器
        restTemplate.setInterceptors(Lists.newArrayList(getRestClientHttpRequestInterceptor()));

        return restTemplate;
    }
}
