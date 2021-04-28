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
@ApiModel(value="FileType对象", description="")
public class FileType implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "'文件类型id'")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer typeId;

    @ApiModelProperty(value = "'文件类型名'")
    @TableField(value = "typeName")
    private String typeName;

    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableLogic
    private Integer status;

    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
