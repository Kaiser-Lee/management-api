package com.iwhalecloud.retail.oms;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iwhalecloud.retail.oms"})
@Import(FdfsClientConfig.class)
public class OmsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OmsApplication.class, args);
	}
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OmsApplication.class);
    }
}
