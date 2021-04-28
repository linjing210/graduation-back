package cn.edu.aiit.gradution.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo implements Serializable {
	private static final long serialVersionUID=1L;
	private Integer code;
	private String msg;
	private Object data;

}
