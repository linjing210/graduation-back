package cn.edu.aiit.gradution.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置为信任任何来源
		registry.addMapping("/**")
			.allowedOrigins("http://www.qslinjing.com")
			.allowedOriginPatterns("*")
			.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
			.allowCredentials(true)
			.maxAge(3600)
			.allowedHeaders("*");
	}


}