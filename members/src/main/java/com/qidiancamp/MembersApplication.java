package com.qidiancamp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = {"com.qidiancamp.modules.*.dao"})
public class MembersApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(MembersApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(MembersApplication.class);
  }
}
