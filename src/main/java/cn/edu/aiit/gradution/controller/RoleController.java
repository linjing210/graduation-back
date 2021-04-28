package cn.edu.aiit.gradution.controller;


import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Controller
public class RoleController {
	@GetMapping("/auth/error")
	@ResponseBody
	public ResultVo unauth(){
		return ResultUtil.error(401, "权限不足！请联系管理员！");
	}

	@GetMapping("/login")
	public String toLogin(){
		return "user/login";
	}
}

