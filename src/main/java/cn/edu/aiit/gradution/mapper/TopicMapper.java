package cn.edu.aiit.gradution.mapper;

import cn.edu.aiit.gradution.pojo.entity.Topic;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Repository
public interface TopicMapper extends BaseMapper<Topic> {
//	String

	int addTopicTag(@Param("tagIds") List<Integer> tagIds, @Param("topicId") Integer topicId);

	int addTopicBatch(@Param("topicList") List<Topic> topicList);

	@Select("<script>select t.* from topic t <where>\n" +
		"            <if test=\"userId != null\"> and userId = #{userId}</if>\n" +
		"            <if test=\"tagId != null\">and t.topicId in (select topicId from topic_type_ref tr where tr.topicTypeId = #{tagId})</if>\n" +
		"            <if test=\"status != null\">and status = #{status}</if>\n" +
		"        </where>\n<if test=\"offset != null and size != null\">" +
		"        limit #{offset}, #{size}</if></script>")
	@Results({
		@Result(property = "topicId", column = "topicId"),
		@Result(property = "user", column = "userId", one = @One(select = "cn.edu.aiit.gradution.mapper.UserMapper.getUserById")),
		@Result(property = "tagList", column = "topicId", many = @Many(select = "cn.edu.aiit.gradution.mapper.TopicTypeMapper.getTagListByTopicId"))
//		@Result(property = "count", column = "")
	})
	List<TopicVo> getTopicList(
		@Param("tagId") Integer tagId, @Param("userId")Integer userId, @Param("status")Integer status,
		@Param("offset")Integer offset, @Param("size")Integer size);

	@Select("<script>select count(1) from topic t <where>\n" +
		"            <if test=\"userId != null\"> and userId = #{userId}</if>\n" +
		"            <if test=\"tagId != null\"> and t.topicId in (select topicId from topic_type_ref tr where tr.topicTypeId = #{tagId})</if>\n" +
		"            <if test=\"status != null\">and status = #{status}</if>\n" +
		"        </where> </script>")
	Integer getTopicCount(@Param("tagId") Integer tagId, @Param("userId")Integer userId, @Param("status")Integer status);

	@Select("<script>select * from topic where topicId in (select distinct t.topicId from topic t left join topic_type_ref tr on (t.topicId = tr.topicId )\n" +
		"<where> t.status in (2, 4, 5) \n" +
		"        <if test=\"tagIds != null and tagIds.size > 0\">\n" +
		"            <foreach collection=\"tagIds\" item=\"tagId\" separator=\",\" open=\"and topicTypeId in(\" close=\")\">\n" +
		"                #{tagId}\n" +
		"            </foreach></if><if test=\"userIds != null and userIds.size > 0\">\n" +
		"            <foreach collection=\"userIds\" item=\"userId\" separator=\",\" open=\"and userId in(\" close=\")\">\n" +
		"                #{userId}</foreach>\n" +
		"        </if></where>\n" +
		"        ) limit #{offset}, #{size}</script>")
	@Results({
		@Result(property = "topicId", column = "topicId"),
		@Result(property = "user", column = "userId", one = @One(select = "cn.edu.aiit.gradution.mapper.UserMapper.getUserById")),
		@Result(property = "tagList", column = "topicId", many = @Many(select = "cn.edu.aiit.gradution.mapper.TopicTypeMapper.getTagListByTopicId"))
	})
	List<TopicVo> queryTopicList(@Param("tagIds") List<Integer> tagIds, @Param("userIds") List<Integer> userIds, @Param("offset") Integer offset, @Param("size") Integer size);

	@Select("<script>select count(1) from (select distinct t.topicId from topic t left join topic_type_ref tr on (t.topicId = tr.topicId )\n" +
		"<where>  t.status in (2, 4, 5) \n" +
		"        <if test=\"tagIds != null and tagIds.size > 0\">\n" +
		"            <foreach collection=\"tagIds\" item=\"tagId\" separator=\",\" open=\"and topicTypeId in(\" close=\")\">\n" +
		"                #{tagId}\n" +
		"            </foreach></if><if test=\"userIds != null and userIds.size > 0\">\n" +
		"            <foreach collection=\"userIds\" item=\"userId\" separator=\",\" open=\"and userId in(\" close=\")\">\n" +
		"                #{userId}</foreach>\n" +
		"        </if> </where>) as t </script>")
	int queryTopicListTotal(List<Integer> tagIds, List<Integer> userIds);

	@Select("select * from topic t where t.topicId = #{topicId}")
	@Results({
		@Result(property = "topicId", column = "topicId"),
		@Result(property = "user", column = "userId", one = @One(select = "cn.edu.aiit.gradution.mapper.UserMapper.getUserById")),
		@Result(property = "tagList", column = "topicId", many = @Many(select = "cn.edu.aiit.gradution.mapper.TopicTypeMapper.getTagListByTopicId"))
	})
	TopicVo getTopicById(@Param("topicId") Integer topicId);

}
