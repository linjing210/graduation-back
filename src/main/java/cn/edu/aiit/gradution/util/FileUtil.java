package cn.edu.aiit.gradution.util;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	/**
	 * 保存客户端上传文件到本地
	 * @param file 客户端上传文件
	 * @param filePath 文件路径
	 * @return 成功/失败
	 */
	public static boolean save(MultipartFile file, String filePath){
		// 获取文件
		File newFile = new File(filePath);
		File parent = newFile.getParentFile();
		try {
			// 如果父目录不存在，则创建对应目录
			if(!parent.exists()){
				if(!newFile.mkdirs()) return false;
			}
			// 转存到本地
			file.transferTo(newFile);
		}catch (IOException e){
			return false;
		}
		return true;
	}

	/**
	 * 文件重命名
	 * @param originName 原始文件名
	 * @param fileId 文件Id
	 * @return 新的文件名
	 */
	public static String getFileName(String originName, Long fileId){
		// 分离文件名与后缀名
		int index = originName.lastIndexOf('.');
		if(index == -1) return fileId.toString();
		String prefix = originName.substring(0, index);
		String suffix = originName.substring(index);

		// 将文件Id进行拼接
		return prefix + fileId + suffix;
	}

	/**
	 * 获取文件访问地址
	 * @param fileName 文件名
	 * @param userId 用户Id
	 * @param root 根路径
	 * @return 文件绝对路径
	 */
	public static String getFileUrl(String fileName, Integer userId, String root){
		return root + "/user_" + userId + "/" + fileName;
	}


	public static boolean deleteFile(String filePath){
		File file = new File(filePath);
		return file.delete();
	}

}
