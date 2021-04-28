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
@ApiModel(value="ApplyTopic对象", description="")
public class ApplyTopic implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'申请id'")
    @TableId(value = "applyId", type = IdType.AUTO)
    private Integer applyId;

    @ApiModelProperty(value = "'学生表中学生id'")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "'选题表中选题id'")
    @TableField("topicId")
    private Integer topicId;

    @ApiModelProperty(value = "'申请创建选题的时间'")
    @TableField("submitTime")
    private Date submitTime;

    @ApiModelProperty(value = "'admin表中教师id'")
    @TableField("approverId")
    private Integer approverId;

    @ApiModelProperty(value = "'教师审核时间'")
    @TableField("approveTime")
    private Date approveTime;

    @ApiModelProperty(value = "'申请状态 0 已拒绝，1 已提交，2 已通过 默认1'")
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
