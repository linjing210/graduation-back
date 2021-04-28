package cn.edu.aiit.gradution.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:/system.properties")
@Component
public class UserInfoConstant {
	/**
	 * 默认头像图片
	 */
	public static String DEFAULT_HEADER_IMAGE_URL;

	/**
	 * 默认密码
	 */
	public static String DEFAULT_USER_PASSWORD;
	/**
	 *  学生角色信息
	 */
	public static Integer ROLE_STUDENT;

	/**
	 *  老师角色信息
	 */
	public static Integer ROLE_TEACHER;

	@Value("${custom.user.header.url}")
	public void setDefaultHeaderImageUrl(String defaultHeaderImageUrl) {
		DEFAULT_HEADER_IMAGE_URL = defaultHeaderImageUrl;
	}
	
	@Value("${custom.user.password}")
	public void setDefaultUserPassword(String defaultUserPassword) {
		DEFAULT_USER_PASSWORD = defaultUserPassword;
	}

	@Value("${custom.user.role.student}")
	public void setRoleStudent(Integer roleStudent) {
		ROLE_STUDENT = roleStudent;
	}

	@Value("${custom.user.role.teacher}")
	public void setRoleTeacher(Integer roleTeacher) {
		ROLE_TEACHER = roleTeacher;
	}
}
