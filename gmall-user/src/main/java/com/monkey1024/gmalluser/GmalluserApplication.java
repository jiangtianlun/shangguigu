package com.monkey1024.gmalluser;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@MapperScan(basePackages = {"com.monkey1024.gmalluser.dao.mapper"})
@MapperScan(basePackages = {"com.monkey1024.gmalluser.dao.mapper"})
public class GmalluserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmalluserApplication.class, args);
	}

}
