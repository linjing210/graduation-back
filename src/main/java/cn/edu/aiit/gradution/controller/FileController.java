package cn.edu.aiit.gradution.controller;


import cn.edu.aiit.gradution.pojo.entity.FileInfo;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.service.FileService;
import cn.edu.aiit.gradution.util.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@RestController
public class FileController {

	@Autowired
	private FileService fileService;
	@PostMapping("/file/uplaod")
	@ResponseBody
	public ResultVo uploadFile(MultipartFile file, String token) {
		System.out.println(token);
		// 封装文件信息对象
		FileInfo info = fileService.upload(file, 1);
		// 调用上传服务，返回上传结果
		if(info == null) return ResultUtil.error(400, "上传失败");
		// 返回文件Id及访问地址
		JSONObject data = new JSONObject();
		data.put("fileId", info.getFileId().toString());
		data.put("fileUrl", info.getFileUrl());
		return ResultUtil.ok("上传成功", data);
	}

	@PostMapping("/user/file/del/{fileId}")
	public ResultVo deleteFile(@PathVariable Long fileId, String token){
		System.out.println(token);
		System.out.println(fileId);
		if(!fileService.deleteFile(fileId, 1)) return ResultUtil.error(400, "删除文件失败");
		return ResultUtil.ok("删除成功");
	}

	@GetMapping("/topic/file/info/{topicId}")
	public ResultVo getFileListByTopicId(@PathVariable Integer topicId){
		return ResultUtil.ok(fileService.getFileListByTopicId(topicId));
	}

	@PostMapping("/file/update/download/{fileId}")
	public ResultVo updateFileDownload(@PathVariable  Long fileId){
		int rlt = fileService.updateFileDownload(fileId);
		if(rlt != 1){
			return ResultUtil.error(400, "更新失败！");
		}
		return ResultUtil.ok("更新成功");
	}
}

