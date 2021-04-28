package cn.edu.aiit.gradution.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * 创建一个API组
	 * @param environment SpringBoot运行环境
	 * @return Docket对象
	 */
	@Bean
	Docket docket(Environment environment){
		// 获取生产环境
		Profiles profiles = Profiles.of("dev");
		// 检测当前环境是否为生产环境
		boolean flag = environment.acceptsProfiles(profiles);
		// 创建Docket对象
		return new Docket(DocumentationType.SWAGGER_2)
			// 设置组名
			.groupName("一曲离殇")
			// 设置api信息
			.apiInfo(apiInfo())
			// 是否能通过浏览器访问Swagger接口文档，一般生产/开发、测试环境开启，发布时关闭
			.enable(true)
			// 开启接口扫描
			.select()
			// 指定要扫描的包
			.apis(RequestHandlerSelectors.basePackage("cn.edu.aiit.gradution.controller"))
			.build();
	}

	/**
	 * api作者信息
	 * @return
	 */
	@Bean
	ApiInfo apiInfo(){
		return new ApiInfo(
			"一曲离殇、的文档",
			"毕业设计双选平台API文档",
			"v1.0",
			"www.qslinjing.com",
			new Contact("一曲离殇", "www.qslinjing.com", "1848480294@qq.com"),
			"Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0",
			new ArrayList<>());
	}
}
