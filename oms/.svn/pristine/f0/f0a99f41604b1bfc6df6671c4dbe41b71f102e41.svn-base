package com.iwhalecloud.retail.oms;

import com.github.tobato.fastdfs.FdfsClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Slf4j
@Import(FdfsClientConfig.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class OmsWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OmsWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		log.info("start--SpringBoot---web");
		return builder.sources(OmsWebApplication.class);
	}
}
