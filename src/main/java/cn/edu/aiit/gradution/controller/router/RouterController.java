package cn.edu.aiit.gradution.controller.router;

import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.service.UserService;
import cn.edu.aiit.gradution.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class RouterController {

	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String toIndex(){
		return "index";
	}

	@RequestMapping("/teacher/index")
	public String toTeacherIndex(){
		return "teacher/index";
	}

	@RequestMapping("/teacher/header")
	public String toHeader(){
		return "teacher/header";
	}
	@RequestMapping("/teacher/footer")
	public String toFooter(){
		return "teacher/footer";
	}

	@GetMapping("/user/profile")
	public String toUserProfile(@SessionAttribute User user, Model model){
		model.addAttribute("user", user);
		return "user/profile";
	}

	@GetMapping("/user/info")
	public String toUserBaseInfo(Model model){
		int userId = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userId");
		model.addAttribute("user", userService.getUserById(userId));
		return "user/info";
	}

	@GetMapping("/user/login")
	public String toUserLogin(){
		return "user/login";
	}

	@PostMapping("/user/logout")
	@ResponseBody
	public ResultVo userLogout(SessionStatus sessionStatus){
		sessionStatus.setComplete();
		return ResultUtil.ok();
	}

}
