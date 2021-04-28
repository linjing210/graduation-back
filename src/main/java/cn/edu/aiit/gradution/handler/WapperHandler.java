package cn.edu.aiit.gradution.handler;

import cn.edu.aiit.gradution.pojo.dto.UserWapper;
import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 将用于传输、展示、查询的其他类型转换为实体类
 */
public class WapperHandler {

	/**
	 * 将用户信息查询条件转换成用户实体
	 * @param userWapper 用户信息查询条件
	 * @return 用户实体
	 */
	public static User convertToUser(UserWapper userWapper){
		User user = new User();
		user.setLoginname(userWapper.getLoginname());
		user.setOriginalClass(userWapper.getOriginalClass());
		user.setUsername(userWapper.getUsername());
		return user;
	}

	/**
	 * 将excel表格中读取的用户信息转换成用户实体
	 * @param infoList excel数据
	 * @return 用户信息列表
	 */
	public static List<User> getUserInfo(List<Map<String ,Object>> infoList){
		List<User> userList = new ArrayList<>();
		infoList.forEach((e)->{
			User user = new User();
			user.setUsername(e.get("学号/工号").toString());
			user.setLoginname(e.get("用户名").toString());
			user.setOriginalClass(e.get("行政班级/院系/部门").toString());
			userList.add(user);
		});
		return userList;
	}

	public static List<Map<String ,Object>> getUserInfoMap(List<UserVo> infoList){
		List<Map<String ,Object>> userList = new ArrayList<>();
		if(infoList == null) return userList;
		infoList.forEach((e)->{
			Map<String, Object> user = new LinkedHashMap<>();
			user.put("学号/工号", e.getLoginname());
			user.put("用户名", e.getUsername());
			user.put("行政班级/院系/部门", e.getOriginalClass());
			user.put("邮箱", e.getEmail());
			user.put("状态", e.getStatus());
			if(e.getRoleId() == 3){
				user.put("用户类别", "老师");
			}else if (e.getRoleId() == 4){
				user.put("用户类别", "学生");
			}
			userList.add(user);
		});
		return userList;
	}


	/**
	 * 将选题模板表格数据转换成选题信息
	 * @param info 选题模板表格数据
	 * @return 选题信息列表
	 */
	public static List<TopicVo> getTopicInfo(List<Map<String, Object>> info){
		List<TopicVo> topicList = new ArrayList<>();
		if(info == null || info.size() == 0) return topicList;
		info.forEach((e)->{
			TopicVo topicVo = new TopicVo();
			topicVo.setTopicName(e.get("标题").toString());
			topicVo.setContext(e.get("选题简介").toString());
			topicVo.setCount(((Long)e.get("选题人数")).intValue());
			UserVo userVo = new UserVo();
			userVo.setLoginname(e.get("工号").toString());
			userVo.setUsername(e.get("发布人姓名").toString());
			topicVo.setUser(userVo);
			topicVo.setCover(e.get("选题标签").toString());

			topicList.add(topicVo);
		});
		return topicList;
	}


	/**
	 * 将选题新转换成导出表格数据
	 * @param info 选题信息
	 * @return 导出表格数据
	 */
	public static List<Map<String, Object>> getTopicInfoMap(List<TopicVo> info){
		List<Map<String, Object>> topicList = new ArrayList<>();
		if(info == null || info.size() == 0) return topicList;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		info.forEach((e)->{
			Map<String , Object> map = new LinkedHashMap<>();
			map.put("工号", e.getUser().getLoginname());
			map.put("发布人姓名", e.getUser().getUsername());
			map.put("标题", e.getTopicName());
			map.put("选题简介", e.getContext());
			map.put("选题人数", e.getCount());
			List<TopicType> tagList = e.getTagList();
			StringBuilder builder = new StringBuilder();
			if(tagList != null &&tagList.size() != 0){
				builder.append(tagList.get(0).getTypeName());
				for (int i = 1; i < tagList.size(); i++) {
					builder.append('、').append(tagList.get(i).getTypeName());
				}
			}
			map.put("选题标签", builder.toString());
			if(e.getGmtCreate() != null)
			map.put("状态", e.getStatus());
			if(e.getStartTime() != null){
				map.put("开始时间", sdf.format(e.getStatus()));
			}
			if(e.getEndTime() != null){
				map.put("结束时间", sdf.format(e.getEndTime()));
			}
			topicList.add(map);
		});
		return topicList;
	}


}
