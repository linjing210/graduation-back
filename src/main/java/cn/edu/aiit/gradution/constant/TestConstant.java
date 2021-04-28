package cn.edu.aiit.gradution.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestConstant {
	public static Integer TASK;
	public static Integer TOPIC;
	public static Integer TEMP;
	public static Integer COMMON;
	public static Integer IMAGE;

	@Value("${custom.file.type.task}")
	public void setTASK(Integer TASK) {
		TestConstant.TASK = TASK;
	}

	@Value("${custom.file.type.topic}")
	public void setTOPIC(Integer TOPIC) {
		TestConstant.TOPIC = TOPIC;
	}

	@Value("${custom.file.type.temp}")
	public void setTEMP(Integer TEMP) {
		TestConstant.TEMP = TEMP;
	}

	@Value("${custom.file.type.common}")
	public void setCOMMON(Integer COMMON) {
		TestConstant.COMMON = COMMON;
	}

	@Value("${custom.file.type.image}")
	public void setIMAGE(Integer IMAGE) {
		TestConstant.IMAGE = IMAGE;
	}
}
