package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface TopicTypeService {

	/**
	 * 获取所有分类
	 * @return 分类列表
	 */
	List<TopicType> getTopicTypeList();

	/**
	 * 按层级获取分类
	 * @return 分类列表
	 */
	List<CategoryVo> getCategoryList();
	List<TopicType> getTagList();
	Page<TopicType> getTagList(TopicType topicType, int current, int size);

	int addTopicType(TopicType topicType);

	int updateTopicTypeStatus(Integer typeId, Integer Status);

	int deleteTopicTypeTyId(Integer typeId);

	int updateTopicTypeById(TopicType topicType);

	TopicType getTopicTypeByTypeId(Integer typeId);

	int deleteTags(List<Integer> ids);
}
