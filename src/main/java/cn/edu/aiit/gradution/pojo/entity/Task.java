package cn.edu.aiit.gradution.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="Task对象", description="")
public class Task implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'任务id'")
    @TableId(value = "taskId", type = IdType.AUTO)
    private Integer taskId;

    @ApiModelProperty(value = "'任务名称'")
    private String taskName;

    @ApiModelProperty(value = "'admin表中老师id'")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty("选题班级id")
    @TableField("classId")
    private Integer classId;

    @ApiModelProperty(value = "'任务内容简介'")
    private String context;

    @ApiModelProperty(value = "'任务开始时间'")
    @TableField("startTime")
    private Date startTime;

    @ApiModelProperty(value = "'任务截止时间'")
    @TableField("endTime")
    private Date endTime;

    @ApiModelProperty(value = "'任务发布时间'")
    @TableField("publisherTime")
    private Date publisherTime;

    @ApiModelProperty(value = "'任务状态 0 未开始， 1 进行中， 2 已结束 默认0'")
    @TableLogic
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
