package com.monkeu1024.gmall.gmallmanagerservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = {"com.monkeu1024.gmall.gmallmanagerservice.dao"})
@org.mybatis.spring.annotation.MapperScan(basePackages = {"com.monkeu1024.gmall.gmallmanagerservice.dao"})
public class GmallManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallManagerServiceApplication.class, args);
	}

}
