package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.constant.FileInfoConstant;
import cn.edu.aiit.gradution.constant.TopicConstant;
import cn.edu.aiit.gradution.exception.TransactionFailedException;
import cn.edu.aiit.gradution.handler.WapperHandler;
import cn.edu.aiit.gradution.mapper.ApplyTopicMapper;
import cn.edu.aiit.gradution.mapper.FileMapper;
import cn.edu.aiit.gradution.mapper.TopicMapper;
import cn.edu.aiit.gradution.mapper.UserMapper;
import cn.edu.aiit.gradution.pojo.entity.*;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import cn.edu.aiit.gradution.service.TopicService;
import cn.edu.aiit.gradution.util.ExcelUtils;
import cn.edu.aiit.gradution.util.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import net.sf.jsqlparser.statement.select.Top;
import org.apache.xmlbeans.impl.store.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicMapper topicMapper;

	@Autowired
	private ApplyTopicMapper applyTopicMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public int addTopicBatch(List<Topic> topicList) {
		return topicMapper.addTopicBatch(topicList);
	}

	public int addTopic(Topic topic, List<Integer> tagIds, List<Long> fileIds){
//		添加到topic表
		int rlt = topicMapper.insert(topic);

		if(rlt < 1){
			throw new TransactionFailedException("添加选题失败");
		}

//		添加标签信息
		if(tagIds != null && tagIds.size() > 0) {
			rlt = topicMapper.addTopicTag(tagIds, topic.getTopicId());
		}

		if(rlt < 1){
			throw new TransactionFailedException("添加选题失败");
		}
//			添加选题申请
		ApplyTopic applyTopic = new ApplyTopic();
		applyTopic.setUserId(topic.getUserId());
		applyTopic.setSubmitTime(new Date());
		applyTopic.setTopicId(topic.getTopicId());
		rlt = applyTopicMapper.insert(applyTopic);

		if(rlt != 1){
			throw new TransactionFailedException("添加选题失败");
		}

//		若有附件，添加附件信息到文件表

		if(fileIds != null&&fileIds.size() > 0){
			rlt = fileMapper.addFileRelation(fileIds, topic.getTopicId(), FileInfoConstant.TOPIC);
		}

		if(rlt < 1){
			throw new TransactionFailedException("添加选题失败");
		}
//		消息（先不做）
		return rlt;
	}

	@Override
	public int updateTopic(Topic topic) {
		return topicMapper.updateById(topic);
	}

	@Override
	public Page<Topic> queryTopicList(Topic topic, Integer current, Integer size) {
		QueryWrapper<Topic> wrapper = new QueryWrapper<>();
		String topicName = topic.getTopicName();
		wrapper.like(topicName != null, "topicName", topicName);
		wrapper.like(topicName != null, "topicName", topicName);
		Page<Topic> topicPage = new Page<>(current, size);
		return topicMapper.selectPage(topicPage, wrapper);
	}

	@Override
	public List<TopicVo> getTopicList(Integer tagId, Integer userId, Integer status, Integer current, Integer size) {
		if(current < 0) current = 0;
		if(size < 5 || size > 200) size = 10;
		int offset = (current - 1) * size;
		return topicMapper.getTopicList(tagId, userId, status, offset, size);
	}

	@Override
	public int getTopicCount(Integer tagId, Integer userId, Integer status) {
		return topicMapper.getTopicCount(tagId, userId, status);
	}

	@Override
	public int deleteTopic(Integer topicId) {
		Topic topic = new Topic();
		topic.setStatus(0);
		QueryWrapper<Topic> wrapper = new QueryWrapper<>();
		wrapper.eq("topicId", topicId);
		return topicMapper.update(topic, wrapper);
	}

	@Override
	public int deleteTopics(List<Integer> list) {
		Topic topic = new Topic();
		topic.setStatus(0);
		QueryWrapper<Topic> wrapper = new QueryWrapper<>();
		wrapper.in("topicId", list);
		return topicMapper.update(topic, wrapper);
	}


	@Override
	public void exportExcel(HttpServletResponse response, Integer tagId, Integer userId, Integer status){
		List<TopicVo> topicVoList = topicMapper.getTopicList(tagId, userId, status, null, null);
		List<Map<String, Object>> data = WapperHandler.getTopicInfoMap(topicVoList);
		ExcelUtils.export(response, "topic.xlsx", data);
	}

	@Override
	public void importTopic(Long fileId) {
		// 获取文件信息
		FileInfo fileInfo = fileMapper.selectById(fileId);

		// 读取临时文件夹下的excel
		ExcelReader reader = ExcelUtil.getReader(FileInfoConstant.ROOT + fileInfo.getFileUrl());
		List<Map<String,Object>> infos = reader.readAll();
		List<TopicVo> topicVoList = WapperHandler.getTopicInfo(infos);
		// 提取选题信息
		List<Topic> topicList = new ArrayList<>();
		topicVoList.forEach((e)->{
			Topic topic = new Topic();
			topic.setStatus(5);
			topic.setContext(e.getContext());
			topic.setCount(e.getCount());
			topic.setTopicName(e.getTopicName());
			User user = userMapper.getUserByLoginname(e.getUser().getLoginname());
			topic.setUserId(user.getUserId());
			topic.setCover(TopicConstant.DEFAULT_COVER);
			topicList.add(topic);
		});
		if(topicList.size() == 0) return;
		// 批量插入到数据库
		topicMapper.addTopicBatch(topicList);
		// 删除临时文件
		fileMapper.deleteById(fileId);
		FileUtil.deleteFile(FileInfoConstant.ROOT + fileInfo.getFileUrl());
	}

	@Override
	public int updateTopicStatus(Integer topicId, Integer status) {
		Topic topic = topicMapper.selectById(topicId);
		topic.setStatus(status);
		return topicMapper.updateById(topic);
	}

	@Override
	public int updateTopicStatusBatch(List<Integer> topicIds, Integer status) {
		Topic topic = new Topic();
		topic.setStatus(status);
		topic.setGmtModified(new Date());
		QueryWrapper<Topic> wrapper = new QueryWrapper<>();
		wrapper.in("topicId", topicIds);
		return topicMapper.update(topic, wrapper);
	}

	@Override
	public List<TopicVo> queryTopicList(List<Integer> tagIds, List<Integer> userIds, Integer current, Integer size) {
		if(current == null) current = 1;
		if(size == null || size <= 0) size = 15;
		int offset  = (current - 1) * size;
		return topicMapper.queryTopicList(tagIds, userIds, offset, size);
	}

	@Override
	public int queryTopicListTotal(List<Integer> tagIds, List<Integer> userIds) {
		return topicMapper.queryTopicListTotal(tagIds, userIds);
	}

	@Override
	public TopicVo getTopicById(Integer topicId) {
		return topicMapper.getTopicById(topicId);
	}
}
