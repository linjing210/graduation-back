package cn.edu.aiit.gradution.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ToString
@ApiModel("用户信息对象")
public class UserVo implements Serializable {
	private static final long serialVersionUID=1L;
	private Integer userId;
	private String loginname;
	private String email;
	private String username;
	private String originalClass;
	private String headerImage;
	private Integer status;
	private Integer roleId;
}
