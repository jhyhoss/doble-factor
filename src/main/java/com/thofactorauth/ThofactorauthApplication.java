package com.thofactorauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class ThofactorauthApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {SpringApplication.run(ThofactorauthApplication.class, args);}

	private static Class<ThofactorauthApplication> thofactorauthApplicationn = ThofactorauthApplication.class;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(thofactorauthApplicationn);
	}

}
