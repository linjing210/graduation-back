package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.pojo.entity.Topic;
import cn.edu.aiit.gradution.pojo.entity.TopicType;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.sf.jsqlparser.statement.select.Top;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
public interface TopicService {
	int addTopicBatch(List<Topic> topic);
	int addTopic(Topic topic, List<Integer> tagIds, List<Long> fileIds);
	int updateTopic(Topic topic);
	Page<Topic> queryTopicList(Topic topic, Integer current, Integer size);


	int getTopicCount(Integer tagId, Integer userId, Integer status);

	List<TopicVo> getTopicList(Integer tagId, Integer userId, Integer status, Integer current, Integer size);


	int deleteTopic(Integer topic);

	int deleteTopics(List<Integer> list);

	/**
	 * 导出符合条件的选题信息
	 * @param response http响应
	 * @param tagId 标签id
	 * @param userId 用户id
	 * @param status 选题状态
	 */
	void exportExcel(HttpServletResponse response, Integer tagId, Integer userId, Integer status);

	/**
	 * 导入选题信息
	 * @param fileId 文件Id
	 */
	void importTopic(Long fileId);

	/**
	 * 更新选题状态
	 * @param topicId 选题Id
	 * @param status 选题状态
	 * @return 更新结果
	 */
	int updateTopicStatus(Integer topicId, Integer status);

	/**
	 * 批量更新选题状态
	 * @param topicIds 选题id列表
	 * @param status 状态Id
	 * @return 更新条数
	 */
	int updateTopicStatusBatch(List<Integer> topicIds, Integer status);


	List<TopicVo> queryTopicList(List<Integer> tagIds, List<Integer> userIds, Integer current, Integer size);

	int queryTopicListTotal(List<Integer> tagIds, List<Integer> userIds);

	TopicVo getTopicById(Integer topicId);
}
