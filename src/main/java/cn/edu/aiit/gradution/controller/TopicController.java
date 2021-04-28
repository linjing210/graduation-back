package cn.edu.aiit.gradution.controller;


import cn.edu.aiit.gradution.pojo.entity.Topic;
import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import cn.edu.aiit.gradution.service.TopicService;
import cn.edu.aiit.gradution.service.TopicTypeService;
import cn.edu.aiit.gradution.util.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Controller
@RestController
public class TopicController {
	@Autowired
	private TopicTypeService topicTypeService;

	@Autowired
	private TopicService topicService;

	/**
	 * 获取选题分类
	 * @param status 状态
	 * @param typeName 分类名
	 * @param current 当前页
	 * @param size 每页条数
	 * @return 选题分类数据
	 */
	@GetMapping("/topic/tag/query")
	public ResultVo querytagList(
		@ApiParam("选题分类名") Integer status,
		@ApiParam("选题分类名") String typeName,
		@ApiParam("当前页") int current,
		@ApiParam("每页条数") int size){
		// 获取
		TopicType topicType = new TopicType();
		topicType.setStatus(status);
		topicType.setTypeName(typeName);
		// 获取查询结果
		Page<TopicType> tagPage = topicTypeService.getTagList(topicType, current, size);

		// 封装并返回结果
		JSONObject data = new JSONObject();
		data.put("tagList", tagPage.getRecords());
		data.put("count", tagPage.getTotal());
		return ResultUtil.ok(data);
	}

	/**
	 * 删除单个标签
	 * @param typeId 标签Id
	 * @return 操作结果
	 */
	@PostMapping("/topic/tag/delete/{typeId}")
	public ResultVo deleteTag(@PathVariable Integer typeId){
		// 删除分类信息
		int result = topicTypeService.deleteTopicTypeTyId(typeId);

		// 返回结果
		if(result != 1){
			return ResultUtil.error(400, "网络异常，请稍后重试！");
		}
		return ResultUtil.ok("删除成功");
	}

	/**
	 * 批量删除标签
	 * @param tagIds 标签Id列表
	 * @return 操作结果
	 */
	@PostMapping("/topic/tag/delete")
	public ResultVo deleteTag(String tagIds){
		List<Integer> ids = JSON.parseArray(tagIds, Integer.class);
		if(ids == null || ids.size() == 0) return ResultUtil.error(400, "删除失败");
		int rlt = topicTypeService.deleteTags(ids);
		if(rlt < ids.size()) ResultUtil.error(400, "删除失败");
		return ResultUtil.ok("删除成功");
	}

	/**
	 * 更新标签状态
	 * @param typeId 标签id
	 * @param status 标签状态
	 * @return 操作结果
	 */
	@PostMapping("/topic/tag/update/{typeId}/{status}")
	public ResultVo updateTagStatus(
		@PathVariable Integer typeId,
		@PathVariable Integer status){

		// 更新分类信息
		int result = topicTypeService.updateTopicTypeStatus(typeId, status);
		if(result != 1){
			return ResultUtil.error(400, "网络异常，请稍后重试！");
		}
		return ResultUtil.ok("修改成功");
	}

	/**
	 * 获取所有标签
	 * @return 标签列表信息
	 */
	@GetMapping("/topic/tag/all")
	public ResultVo getAllTags(){
		List<TopicType> topicTypes = topicTypeService.getTopicTypeList();
		return ResultUtil.ok(topicTypes);
	}

	/**
	 * 通过选题Id获取选题信息
	 * @param topicId 选题Id
	 * @return 选题信息
	 */
	@GetMapping("/topic/info/{topicId}")
	public ResultVo getTopicById(@PathVariable Integer topicId){

		return ResultUtil.ok(topicService.getTopicById(topicId));
	}

	/**
	 * 查询选题信息
	 * @param tagId 标签id
	 * @param userId 发布人Id
	 * @param status 选题状态
	 * @param current 当前页
	 * @param size 每页条数
	 * @return 选题信息列表
	 */
	@GetMapping("/topic/query")
	public ResultVo queryTopic(
		@ApiParam("标签Id") Integer tagId,
		@ApiParam("发布人Id") Integer userId,
		@ApiParam("状态") Integer status,
		@ApiParam("当前页") Integer current,
	    @ApiParam("每页条数") Integer size){
		System.out.println(tagId);
		System.out.println(userId);
		System.out.println(status);
		List<TopicVo> topicVoList = topicService.getTopicList(tagId, userId, status, current, size);
		Integer count = topicService.getTopicCount(tagId, userId, status);
		JSONObject data = new JSONObject();
		data.put("topicList", topicVoList);
		data.put("count", count);
		return ResultUtil.ok(data);
	}


	@PostMapping("/student/topic/query")
	public ResultVo queryTopic(
		@ApiParam("当前页") Integer current,
	    @ApiParam("每页条数") Integer size,
		@ApiParam("标签Id列表") String tagString,
		@ApiParam("用户Id列表") String userString){
		List<Integer> tagIds = JSON.parseArray(tagString, Integer.class);
		List<Integer> userIds =  JSON.parseArray(userString, Integer.class);
		List<TopicVo> topicVoList = topicService.queryTopicList(tagIds, userIds, current, size);
		int count = topicService.queryTopicListTotal(tagIds, userIds);
		JSONObject data = new JSONObject();
		data.put("topicList", topicVoList);
		data.put("count", count);
		return ResultUtil.ok(data);
	}

	/**
	 * 删除单个选题
	 * @param topicId 选题id
	 * @return 操作结果
	 */
	@PostMapping("/topic/delete/{topicId}")
	public ResultVo deleteTopicById(
		@ApiParam("选题Id") @PathVariable Integer topicId){

		// 删除选题
		int rlt = topicService.deleteTopic(topicId);
		if(rlt < 1){
			return ResultUtil.error(400, "删除失败");
		}
		return ResultUtil.ok("删除成功");
	}

	/**
	 * 批量删除选题
	 * @param ids 选题Id
	 * @return 选题
	 */
	@PostMapping("/topic/delete")
	public ResultVo deleteTopics(String ids){
		List<Integer> list = JSON.parseArray(ids, Integer.class);
		if(list == null || list.size() == 0) return ResultUtil.error(400, "删除失败");
		int rlt = topicService.deleteTopics(list);
		return ResultUtil.ok("删除成功");
	}

	/**
	 * 添加选题标签
	 * @param typeName 标签名
	 * @return 操作结果
	 */
	@PostMapping("/topic/tag/add")
	public ResultVo addTag(String typeName){
		TopicType topicType = new TopicType();
		topicType.setTypeName(typeName);
		int rlt = topicTypeService.addTopicType(topicType);
		if(rlt != 1) return ResultUtil.error(400, "添加标签失败");
		return ResultUtil.ok("添加成功");
	}

	/**
	 * 更新标签信息
	 * @param topicType 标签信息
	 * @return 更新结果
	 */
	@PostMapping("/topic/tag/update")
	public ResultVo updateTag(TopicType topicType){
		int rlt = topicTypeService.updateTopicTypeById(topicType);
		if(rlt != 1) return ResultUtil.error(400, "更新标签失败");
		return ResultUtil.ok("更新成功");
	}

	/**
	 * 获取标签信息
	 * @param tagId 标签Id
	 * @return 标签信息
	 */
	@GetMapping("/topic/tag/get/{tagId}")
	public ResultVo getTagById(@PathVariable Integer tagId){
		TopicType topicType = topicTypeService.getTopicTypeByTypeId(tagId);
		if(topicType == null) return ResultUtil.error(400, "获取标签失败");
		return ResultUtil.ok(topicType);
	}

	@GetMapping("/topic/export")
	public void exportTopic(
		HttpServletResponse response,
		@ApiParam("标签Id") Integer tagId,
		@ApiParam("发布人Id") Integer userId,
		@ApiParam("状态") Integer status){
		topicService.exportExcel(response, tagId, userId, status);
	}

	@PostMapping("/topic/import")
	public ResultVo importTopic(@ApiParam("文件id") Long fileId){
		try {
			topicService.importTopic(fileId);
		}catch (Exception e){
			return ResultUtil.error(400, "导入失败，请重试！");
		}
		return ResultUtil.ok();
	}

	@PostMapping("/topic/update/status/{topicId}/{status}")
	public ResultVo updateTopicStatus(
		@PathVariable @ApiParam("选题Id") Integer topicId,
		@PathVariable  @ApiParam("选题Id") Integer status){
		int rlt = topicService.updateTopicStatus(topicId, status);
		if(rlt != 1){
			return ResultUtil.error(400, "更新失败");
		}
		return ResultUtil.ok("更新成功");
	}

	@PostMapping("/topic/update/status")
	public ResultVo updateTopicStatusBatch(
		@ApiParam("选题Id") String ids,
		@ApiParam("选题Id") Integer status){
		System.out.println(ids);
		List<Integer> topicIds = JSON.parseArray(ids, Integer.class);
		System.out.println(topicIds);
		int rlt = topicService.updateTopicStatusBatch(topicIds, status);
		if(rlt < 1){
			return ResultUtil.error(400, "更新失败");
		}
		return ResultUtil.ok("更新成功");
	}

	@PostMapping("/topic/add")
	public ResultVo addTopic(Topic topic, List<Integer> tagIds, List<Long> fileIds){
		int rlt = topicService.addTopic(topic, tagIds, fileIds);
		if(rlt != 1) {
			return ResultUtil.error(400, "添加选题失败！");
		}
		return ResultUtil.ok("发布成功！请耐心等待管理员审核！");
	}

}

