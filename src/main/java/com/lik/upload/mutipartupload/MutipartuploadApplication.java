package com.lik.upload.mutipartupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author lk
 */
@MapperScan("com.lik.upload.mutipartupload.mapper")
@SpringBootApplication
@EnableSwagger2
public class MutipartuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutipartuploadApplication.class, args);
    }

}

