package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//注解里面的内容表示：通知springboot启动时，不要加载数据源的相关配置
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBootRun1 {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun1.class,args);
	}
}
