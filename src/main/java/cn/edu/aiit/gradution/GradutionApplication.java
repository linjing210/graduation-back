package cn.edu.aiit.gradution;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.edu.aiit.gradution.mapper")
@EnableAsync
@EnableTransactionManagement
public class GradutionApplication {
	public static void main(String[] args) {
		SpringApplication.run(GradutionApplication.class, args);
	}
}
