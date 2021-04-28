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
@ApiModel(value="Completion对象", description="")
public class Completion implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'完成任务表主键id'")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer completionId;

    @ApiModelProperty(value = "'task表中的id'")
    @TableField("taskId")
    private Integer taskId;

    @ApiModelProperty(value = "'用户Id'")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "'任务完成时间 默认为空'")
    @TableField("finishTime")
    private Date finishTime;

    @ApiModelProperty(value = "'0 未完成， 1 已完成 默认0'")
    @TableLogic
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "上次更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
