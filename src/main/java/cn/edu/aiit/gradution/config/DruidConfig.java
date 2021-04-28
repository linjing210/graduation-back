package cn.edu.aiit.gradution.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DruidConfig {
	// 创建并自动装配数据源
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DruidDataSource dataSource(){
		return new DruidDataSource();
	}

	// 配置sql监控平台参数
	@Bean
	public ServletRegistrationBean<StatViewServlet> a(){

		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		// 给定登录sql监控平台时所需的账号密码
		HashMap<String, String> initParameters = new HashMap<>();
		initParameters.put("loginUsername", "admin");// key值必须为loginUsername
		initParameters.put("loginPassword", "123456");// key值必须为loginPassword
//        设置初始化参数
		bean.setInitParameters(initParameters);
		return bean;
	}
}
