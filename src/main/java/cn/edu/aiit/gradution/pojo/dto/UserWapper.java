package cn.edu.aiit.gradution.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("添加用户所需要的用户信息")
@Data
@Accessors(chain = true)
public class UserWapper {
	@ApiModelProperty("账号，老师的工号或学生的学号")
	private String loginname;
	@ApiModelProperty("用户昵称，老师或学生的姓名")
	private String username;
	@ApiModelProperty("原始班级，学生的行政班级")
	private String originalClass;
}
