package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.pojo.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.FieldInfo;

import java.util.List;

/**
 * <p>
 *  文件服务类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
public interface FileService{
	/**
	 * 文件上传
	 * @param file 客户端文件
	 * @param userId 用户Id
	 * @return 文件信息
	 */
	FileInfo upload(MultipartFile file, Integer userId);

	/**
	 * 用户删除文件
	 * @param fileId 文件Id
	 * @param userId 用户Id
	 * @return 成功/失败
	 */
	boolean deleteFile(Long fileId, Integer userId);

	/**
	 * 批量删除文件
	 * @param fileIds 文件id列表
	 * @return 删除结果
	 */
	boolean deleteFile(List<Long> fileIds);

	/**
	 * 删除单个文件
	 * @param fileId 文件id
	 * @return 删除结果
	 */
	boolean deleteFile(Long fileId);

	/**
	 * 根据课题id获取文件列表
	 * @param topicId 课题Id
	 * @return 文件信息列表
	 */
	List<FileInfo> getFileListByTopicId(Integer topicId);

	int  updateFileDownload(Long fileId);
}
