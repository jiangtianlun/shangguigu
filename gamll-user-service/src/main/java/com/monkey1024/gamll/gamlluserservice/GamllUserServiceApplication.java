package com.monkey1024.gamll.gamlluserservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDubbo
@MapperScan(basePackages = "com.monkey1024.gamll.gamlluserservice.dao.mapper")
@SpringBootApplication
public class GamllUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamllUserServiceApplication.class, args);
	}

}
