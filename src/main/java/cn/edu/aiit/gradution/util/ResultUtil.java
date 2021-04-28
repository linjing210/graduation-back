package cn.edu.aiit.gradution.util;

import cn.edu.aiit.gradution.pojo.vo.ResultVo;

public class ResultUtil {
	public static ResultVo ok(Integer code, String msg, Object data){
		return new ResultVo(code, msg, data);
	}

	public static ResultVo ok(String msg, Object data){
		return ok(200, msg, data);
	}

	public static ResultVo ok(Object data){
		return ok(200, "ok", data);
	}

	public static ResultVo ok(){
		return new ResultVo(200, "ok", null);
	}

	public static ResultVo ok(String msg){
		return new ResultVo(200, msg, null);
	}

	public static ResultVo error(Integer code, String msg, Object data){
		return new ResultVo(code, msg, data);
	}

	public static ResultVo error(Integer code){
		return error(code, "", null);
	}

	public static ResultVo error(Integer code, String msg){
		return error(code, msg, null);
	}

}
