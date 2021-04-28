package cn.edu.aiit.gradution.pojo.vo;

import cn.edu.aiit.gradution.pojo.entity.TopicType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ToString
@JsonIgnoreProperties(value = { "handler" })
public class TopicVo implements Serializable {

	private static final long serialVersionUID=1L;

	@ApiModelProperty(value = "'选题id'")
	private Integer topicId;

	@ApiModelProperty(value = "发布人信息")
	private UserVo user;

	@ApiModelProperty(value = "'选题名称'")
	private String topicName;

	@ApiModelProperty(value = "'选题开始时间'")
	private Date startTime;

	@ApiModelProperty(value = "'选题结束时间'")
	private Date endTime;

	@ApiModelProperty(value = "'选题描述内容'")
	private String context;

	@ApiModelProperty(value = "'封面内容地址'")
	private String cover;

	@ApiModelProperty(value = "'选题状态 0 已删除 ，1 未审批, 2 通过(未开始) 3、驳回， 4 进行中， 5 已结束 默认1'")
	private Integer status;

	@ApiModelProperty(value = "上次更新时间")
	private Date gmtModified;

	@ApiModelProperty(value = "创建时间")
	private Date gmtCreate;

	@ApiModelProperty(value = "选择人数")
	private Integer count;

	@ApiModelProperty(value = "标签列表")
	private List<TopicType> tagList;
}
