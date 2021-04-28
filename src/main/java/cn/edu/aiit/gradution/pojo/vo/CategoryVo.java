package cn.edu.aiit.gradution.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel("课题分类")
@Accessors(chain = true)
@ToString
public class CategoryVo implements Serializable {
	private static final long serialVersionUID=1L;
	@ApiModelProperty("分类id")
	private Integer typeId;
	@ApiModelProperty("分类名")
	private String typeName;
	@ApiModelProperty("是否删除")
	private Integer status;
}
