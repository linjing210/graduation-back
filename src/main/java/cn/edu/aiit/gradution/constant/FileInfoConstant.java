package cn.edu.aiit.gradution.constant;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@PropertySource("classpath:system.properties")
@Component
public class FileInfoConstant {

	public static Integer TASK;
	public static Integer TOPIC;
	public static Integer TEMP;
	public static Integer COMMON;
	public static Integer IMAGE;


	public static String ROOT;
	public static String TASK_DIR;
	public static String TOPIC_DIR;
	public static String TEMP_DIR;
	public static String COMMON_DIR;
	public static String IMAGE_DIR;


	@Value("${custom.file.type.topic}")
	public void setTopic(Integer topic) {
		TOPIC = topic;
	}

	@Value("${custom.file.type.temp}")
	public void setTemp(Integer temp) {
	    TEMP = temp;
	}

	@Value("${custom.file.type.common}")
	public void setCommon(Integer common) {
		COMMON = common;
	}

	@Value("${custom.file.type.image}")
	public void setImage(Integer image) {
		IMAGE = image;
	}

	@Value("${custom.file.type.task}")
	public void setTasK(Integer tasK) {
		TASK = tasK;
	}

	@Value("${custom.file.upload.task}")
	public void setTsakDir(String taskDir) {
		TASK_DIR = taskDir;
	}
	@Value("${custom.file.upload.topic}")
	public void setTopicDir(String topicDir) {
		TOPIC_DIR = topicDir;
	}
	@Value("${custom.file.upload.temp}")
	public void setTempDir(String tempDir) {
		TEMP_DIR = tempDir;
	}
	@Value("${custom.file.upload.common}")
	public void setCommonDir(String commonDir) {
		COMMON_DIR = commonDir;
	}
	@Value("${custom.file.upload.image}")
	public void setImageDir(String imageDir) {
		IMAGE_DIR = imageDir;
	}

	@Value("${custom.file.upload.root}")
	public void setRoot(String ROOT) {
		FileInfoConstant.ROOT = ROOT;
	}
}
