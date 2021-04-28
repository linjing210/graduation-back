package cn.edu.aiit.gradution.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
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
@ToString
public class TopicType implements Serializable {
	private static final long serialVersionUID=1L;
	@TableId(value = "typeId", type = IdType.AUTO)
	private Integer typeId;
	@TableField("typeName")
	private String typeName;
	@TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
	private Date gmtModified;
	@TableField(value = "gmtCreate", fill = FieldFill.INSERT)
	private Date gmtCreate;
	@TableField(value = "status", fill = FieldFill.INSERT)
	private Integer status;
}
