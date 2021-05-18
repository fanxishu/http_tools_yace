//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newland.research.serviceKeeper.utils;

import com.newland.research.serviceKeeper.error.GlobaleException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    @Value("${sys.service.url}")
    private String sysServiceUrl;
    @Autowired
    private RestTemplate restTemplate;

    public HttpUtils() {
    }

    private String doSend(String url, String paramJson, HttpMethod method) throws GlobaleException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> formEntity = new HttpEntity(paramJson, headers);
        this.restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        try {
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, method, formEntity, String.class, new Object[0]);
            HttpStatus httpStatus = responseEntity.getStatusCode();
            if (httpStatus.isError()) {
                new StringBuffer();
                throw new GlobaleException(httpStatus.value(), "请求错误");
            } else {
                return (String)responseEntity.getBody();
            }
        } catch (Exception var9) {
            throw new GlobaleException(HttpStatus.BAD_REQUEST.value(), var9.getMessage());
        }
    }

    public String initsysService(String params, String sysServiceUrl) throws GlobaleException {
        return this.doSend(sysServiceUrl, params, HttpMethod.POST);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException var4) {
                    var4.printStackTrace();
                }

                ipAddress = inet.getHostAddress();
            }
        }

        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }
}
