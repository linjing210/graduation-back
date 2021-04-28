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
@ApiModel(value="User对象")
public class User implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'学生的id'")
    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "'学生的学号'")
    private String loginname;

    @ApiModelProperty(value = "'学生绑定的邮箱'")
    private String email;

    @ApiModelProperty(value = "'学生的姓名'")
    private String username;

    @ApiModelProperty(value = "'加密后的密码'")
    private String password;

    @ApiModelProperty(value = "'学生的行政班级'")
    @TableField("originalClass")
    private String originalClass;

    @ApiModelProperty(value = "'账号状态 0 禁用， 1 启用 默认 1'")
    @TableLogic
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty(value = "上次更新时间")
    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "头像地址")
    @TableField(value = "headerImage", fill = FieldFill.INSERT)
    private String headerImage;

}
