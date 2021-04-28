package cn.edu.aiit.gradution.mapper;

import cn.edu.aiit.gradution.pojo.entity.TopicType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicTypeMapper extends BaseMapper<TopicType> {

	@Select("select tp.* from topic_type tp, topic_type_ref tr where tr.topicId = #{topicId} and tr.topicTypeId = tp.typeId")
	List<TopicType> getTagListByTopicId(@Param("topicId") Integer topicId);
}
