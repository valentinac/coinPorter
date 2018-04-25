package com.qidiancamp;

import com.qidiancamp.application.UserSecurityInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@SpringBootApplication
//@EnableAutoConfiguration
@MapperScan(basePackages = {"com.qidiancamp.modules.*.dao"})
@ComponentScan(basePackages = {"com.qidiancamp"})
public class MembersApplication extends SpringBootServletInitializer {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MembersApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MembersApplication.class, args);
	}

	@Configuration
	static class WebMvcConfigurer extends WebMvcConfigurerAdapter{
		/**
		 * 配置拦截器
		 * @author lance
		 * @param registry
		 */
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/wap/**");
		}
	}

}
