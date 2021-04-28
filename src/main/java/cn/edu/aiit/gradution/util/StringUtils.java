package cn.edu.aiit.gradution.util;

import java.util.UUID;

public class StringUtils {
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
