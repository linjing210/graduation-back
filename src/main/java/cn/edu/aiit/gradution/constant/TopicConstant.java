package cn.edu.aiit.gradution.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class TopicConstant {
	public static String DEFAULT_COVER;

	@Value("${custom.topic.cover}")
	public void setDefaultCover(String defaultCover) {
		DEFAULT_COVER = defaultCover;
	}
}
