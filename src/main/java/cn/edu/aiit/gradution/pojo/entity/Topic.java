package cn.edu.aiit.gradution.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Topic对象", description="")
public class Topic implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'选题id'")
    @TableId(value = "topicId", type = IdType.AUTO)
    private Integer topicId;

    @ApiModelProperty(value = "'发布选题的老师id'")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "'选题名称'")
    @TableField("topicName")
    private String topicName;

    @ApiModelProperty(value = "'选题开始时间'")
    @TableField("startTime")
    private Date startTime;

    @ApiModelProperty(value = "'选题结束时间'")
    @TableField("endTime")
    private Date endTime;

    @ApiModelProperty(value = "'选题描述内容'")
    private String context;

    @ApiModelProperty(value = "'封面内容地址'")
    @TableField(value = "cover", fill = FieldFill.INSERT)
    private String cover;

    @ApiModelProperty(value = "'选题状态 0 已删除 ，1 未审批, 2 通过(未开始) 3、驳回， 4 进行中， 5 已结束 默认1'")
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty(value = "上次更新时间")
    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "选题人数")
    @TableField(value = "count")
    private Integer count;


}
