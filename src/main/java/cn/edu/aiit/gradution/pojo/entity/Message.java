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
@ApiModel(value="Message对象", description="")
public class Message implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'message表唯一主键'")
    @TableId(value = "messageId", type = IdType.AUTO)
    private Integer messageId;

    @ApiModelProperty(value = "'admin表中的id'")
    @TableField("fromId")
    private Integer fromId;

    @ApiModelProperty(value = "'student表中的id'")
    @TableField("toId")
    private Integer toId;

    @ApiModelProperty(value = "'消息标题'")
    private String title;

    @ApiModelProperty(value = "'消息内容'")
    private String context;

    @ApiModelProperty(value = "'消息发送时间'")
    private Date time;

    @ApiModelProperty(value = "'消息状态 0 已删除 1 未读 2 已读 默认1'")
    @TableLogic
    private String status;

    @ApiModelProperty(value = "'消息类型 0 系统消息 1 普通消息 默认0'")
    private String type;

    @ApiModelProperty(value = "上次更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;


}
