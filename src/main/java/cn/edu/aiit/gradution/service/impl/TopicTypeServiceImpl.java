package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.mapper.TopicTypeMapper;
import cn.edu.aiit.gradution.pojo.entity.Topic;
import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.vo.CategoryVo;
import cn.edu.aiit.gradution.service.TopicTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TopicTypeServiceImpl implements TopicTypeService {
	@Autowired
	private TopicTypeMapper topicTypeMapper;

	@Override
	public List<TopicType> getTopicTypeList() {
		return topicTypeMapper.selectList(null);
	}

	@Override
	public List<TopicType> getTagList() {
		return topicTypeMapper.selectList(null);
	}

	public List<CategoryVo> getCategoryList(){
		return null;
	}

	@Override
	public Page<TopicType> getTagList(TopicType topicType, int current, int size) {
		Page<TopicType> topicTypePage = new Page<>(current, size);
		QueryWrapper<TopicType> wrapper = new QueryWrapper<>();
		String typeName = topicType.getTypeName();
		Integer status = topicType.getStatus();
		wrapper.like(!Objects.isNull(typeName), "typeName", typeName)
			.eq(!Objects.isNull(status)&&!status.equals(-1), "status", status);
 		return topicTypeMapper.selectPage(topicTypePage, wrapper);
	}

	@Override
	public int addTopicType(TopicType topicType) {
		return topicTypeMapper.insert(topicType);
	}

	@Override
	public int updateTopicTypeStatus(Integer typeId, Integer status) {
		TopicType topicType = topicTypeMapper.selectById(typeId);
		topicType.setStatus(status);
		return topicTypeMapper.updateById(topicType);
	}

	@Override
	public int deleteTopicTypeTyId(Integer typeId) {
		return topicTypeMapper.deleteById(typeId);
	}

	@Override
	public int updateTopicTypeById(TopicType topicType) {
		return topicTypeMapper.updateById(topicType);
	}

	@Override
	public TopicType getTopicTypeByTypeId(Integer typeId) {
		return topicTypeMapper.selectById(typeId);
	}

	@Override
	public int deleteTags(List<Integer> ids) {
		return topicTypeMapper.deleteBatchIds(ids);
	}

}
