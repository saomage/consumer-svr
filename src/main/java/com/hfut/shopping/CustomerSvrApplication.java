package com.hfut.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.hfut.shopping.mapper")
@ServletComponentScan
@EnableFeignClients
public class CustomerSvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSvrApplication.class, args);
	}

}

