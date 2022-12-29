package com.example.demo;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.scrappers.CoordenadasGPS;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws SQLException {
		Crud.getInstance();
		CoordenadasGPS.getInstance();
		SpringApplication.run(DemoApplication.class, args);
	}

	// @Configuration
	// @EnableWebMvc
	// public class WebConfig implements WebMvcConfigurer {

	// 	@Override
	// 	public void addCorsMappings(CorsRegistry registry) {
	// 		registry.addMapping("/**")
	// 				.allowedOrigins("*")
	// 				.allowedMethods("*")
	// 				.allowedHeaders("*");
	// 	}
	// }
}
