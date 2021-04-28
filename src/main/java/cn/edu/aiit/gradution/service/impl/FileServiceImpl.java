package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.constant.FileInfoConstant;
import cn.edu.aiit.gradution.mapper.FileMapper;
import cn.edu.aiit.gradution.pojo.entity.FileInfo;
import cn.edu.aiit.gradution.service.FileService;
import cn.edu.aiit.gradution.util.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.FieldInfo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Service
public class FileServiceImpl implements FileService {

	/**
	 * fileMapper
	 */
	@Autowired
	private FileMapper fileMapper;

	@Override
	public FileInfo upload(MultipartFile file, Integer userId) {
		// 雪花算法，生成唯一文件Id
		Snowflake snowflake = IdUtil.getSnowflake(1, 1);
		Long fileId = snowflake.nextId();
		System.out.println(fileId);
		// 封装文件信息
		FileInfo info = new FileInfo();
		String originName = file.getOriginalFilename();
		if(originName == null){
			originName = "";
		}
		String newName = FileUtil.getFileName(originName, fileId);
		System.out.println(file.getName());
		System.out.println(originName);
		info.setFileId(fileId)
			.setFileName(newName)
			.setPublisherId(userId)
			.setUploadTime(new Date())
			.setDownloadNum(0)
			.setViewNum(0)
			.setFileUrl(FileUtil.getFileUrl(newName, userId, "/temp"));
		// 保存文件到服务器
		FileUtil.save(file, FileUtil.getFileUrl(newName, userId, FileInfoConstant.TEMP_DIR));
		// 保存文件信息到数据库
		int rlt = fileMapper.insert(info);
		if(rlt != 1) return null;

		System.out.println(info.getFileId());
		return info;
	}

	@Override
	public boolean deleteFile(Long fileId, Integer userId) {
		// 获取文件信息
		FileInfo info = fileMapper.selectById(fileId);
		// 从数据库中对文件信息进行逻辑删除
		QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
		wrapper.eq("fileId", fileId).eq("publisherId", userId);
		int rlt = fileMapper.delete(wrapper);
		if(rlt != 1) return false;
		// 从硬盘中删除文件信息
		String filePath = FileInfoConstant.ROOT + info.getFileUrl();
		return FileUtil.deleteFile(filePath);
	}

	@Override
	public boolean deleteFile(List<Long> fileIds) {
		// 获取文件列表
		List<FileInfo> infoList = fileMapper.selectBatchIds(fileIds);
		QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
		// 从数据库中删除
		int rlt = fileMapper.deleteBatchIds(fileIds);
		if(rlt < 1) return false;
		// 遍历文件信息列表
		for (FileInfo info : infoList){
			// 从硬盘中删除文件信息
			String filePath = FileInfoConstant.ROOT + info.getFileUrl();
			if(!FileUtil.deleteFile(filePath)) return false;
		}
		return true;
	}

	@Override
	public boolean deleteFile(Long fileId) {
		try {
			// 获取文件信息
			FileInfo fileInfo = fileMapper.selectById(fileId);
			// 删除数据库信息
			fileMapper.deleteById(fileId);
			// 删除本地文件
			FileUtil.deleteFile(FileInfoConstant.ROOT + fileInfo.getFileUrl());
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<FileInfo> getFileListByTopicId(Integer topicId) {
		return fileMapper.getFileListByRef(topicId, FileInfoConstant.TOPIC);
	}

	@Override
	public synchronized int updateFileDownload(Long fileId) {
		FileInfo fileInfo = fileMapper.selectById(fileId);
		if(fileInfo == null) return 0;
		fileInfo.setDownloadNum(fileInfo.getDownloadNum() + 1);
		System.out.println(fileInfo.getDownloadNum());
		return fileMapper.updateById(fileInfo);
	}
}
