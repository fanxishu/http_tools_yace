//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newland.research.serviceKeeper.config;

import com.newland.research.serviceKeeper.common.Constant;
import com.newland.research.serviceKeeper.config.handler.RestTemplateErrorHandler;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Value("${rest.read-timeout}")
    private int readTimeOut;
    @Value("${rest.connection-timeout}")
    private int connectTimeOut;
    @Autowired
    protected RestTemplateErrorHandler restTemplateErrorHandler;

    public RestTemplateConfig() {
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(this.restTemplateErrorHandler);
        return restTemplate;
    }

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        System.out.println(poolingConnectionManager.getMaxTotal());
        System.out.println(poolingConnectionManager.getDefaultMaxPerRoute());
        poolingConnectionManager.setMaxTotal(Integer.valueOf(Constant.nums[5]));
        poolingConnectionManager.setDefaultMaxPerRoute(Integer.valueOf(Constant.nums[6]));
        return poolingConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(this.poolingConnectionManager());
        return httpClientBuilder;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(this.httpClientBuilder().build());
        this.readTimeOut = Integer.valueOf(Constant.nums[3]);
        factory.setReadTimeout(this.readTimeOut);
        this.connectTimeOut = Integer.valueOf(Constant.nums[4]);
        factory.setConnectTimeout(this.connectTimeOut);
        return factory;
    }
}
