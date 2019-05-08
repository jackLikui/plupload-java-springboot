package com.lik.upload.mutipartupload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: mutipartupload
 * @Package: com.lik.upload.mutipartupload.properties
 * @ClassName: PanProperties
 * @Author: chinasoft.k.li
 * @Description: 项目的配置文件
 * @Date: 2019/5/7 9:45
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(
        prefix = "pan"
)
@EnableConfigurationProperties(PanProperties.class)
public class PanProperties {

    private String uploadDir = "/data/upload";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
