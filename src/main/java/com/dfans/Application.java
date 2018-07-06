package com.dfans;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */
// Spring Boot 应用的标识
// mapper 接口类扫描包配置
@Configuration
@EnableAutoConfiguration
@MapperScan("com.dfans.dao")
@ComponentScan(basePackages = "com.dfans") // 默认扫描是当前包下的路径
@ImportResource(locations = { "classpath:app-ehcache.xml" })
@EnableScheduling
@EnableTransactionManagement // 启用事务管理，相当于xml中的<tx:annotion-driven>
@SpringBootApplication
public class Application extends SpringBootServletInitializer { // WebMvcConfigurerAdapter

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {

		// java.lang.String SDKService_address
		// =PropertyResourceBundle.getBundle("application").getString("uri");
		SpringApplication.run(Application.class, args);
	}

}
