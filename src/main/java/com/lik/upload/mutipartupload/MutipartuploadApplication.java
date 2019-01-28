package com.lik.upload.mutipartupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.lik.upload.mutipartupload.mapper")
@ComponentScan("com.lik.upload")
@SpringBootApplication
public class MutipartuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutipartuploadApplication.class, args);
    }

}

