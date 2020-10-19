package com.qumingbo.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: qumingbo
 * @Date: 2020/2/10 21:40
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {

    private String baseUrl;
    private List<String> allowTypes;

}
