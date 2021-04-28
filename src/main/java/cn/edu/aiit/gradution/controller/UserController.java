package cn.edu.aiit.gradution.controller;


import cn.edu.aiit.gradution.pojo.dto.UserWapper;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import cn.edu.aiit.gradution.service.RoleService;
import cn.edu.aiit.gradution.service.UserService;
import cn.edu.aiit.gradution.util.ResultUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@RestController
@SessionAttributes({"user", "role"})
public class UserController {

	/**
	 * 用户服务
	 */
	@Autowired
	private UserService userService;

	/**
	 * 角色服务
	 */
	@Autowired
	private RoleService roleService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	@PostMapping("/user/repass/mail")
	public ResultVo resetPassword(@SessionAttribute User user){
		return userService.resetPassword(user.getUserId());
	}

	@PostMapping("/admin/user/repass")
	public ResultVo resetPassword(@RequestParam @ApiParam(value = "需要重置密码的学生学号或教师工号", example = "318102010632") String loginname){
		return userService.resetPassword(loginname);
	}

	@PostMapping("/user/repass")
	public ResultVo resetPassword(@SessionAttribute User user,
	    @ApiParam("原密码") String oldPassword,
		@ApiParam("新密码") String newPassword){
		return userService.resetPassword(oldPassword, newPassword, user);
	}

	@PostMapping("/login")
	public ResultVo login(String loginname, String password){
		UsernamePasswordToken token = new UsernamePasswordToken(loginname, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			JSONObject data = new JSONObject();
			data.put("token", token);
			return ResultUtil.ok();
		}catch (UnknownAccountException uae){
			return ResultUtil.error(400, "用户名不存在");
		}catch (IncorrectCredentialsException ice){
			return ResultUtil.error(400, "密码错误");
		}
	}

//	@PostMapping("/user/logout")
//	ResultVo logout(SessionStatus sessionStatus){
//		sessionStatus.setComplete();
//		return ResultUtil.ok("注销成功");
//	}

	/**
	 * 更新用户信息
	 * @param user session 中用户信息
	 * @param email 用户邮箱
	 * @param username 用户名
	 * @param originalClass 行政班级
	 * @return 更新结果
	 */
	@PostMapping("/user/profile/update")
	ResultVo updateProfile(@SessionAttribute User user,
		@ApiParam("用户邮箱") @RequestParam String email,
		@ApiParam("用户名") @RequestParam String username,
		@ApiParam("行政班级") @RequestParam String originalClass){
		user.setEmail(email);
		user.setOriginalClass(originalClass);
		user.setUsername(username);
		int rlt = userService.updateUser(user);
		if(rlt != 1){
			return ResultUtil.error(400, "修改用户信息失败");
		}
		return ResultUtil.ok("修改成功");
	}


	/**
	 * 查询用户列表
	 * @param userWapper 筛选条件
	 * @param current 当前页数
	 * @param size 每页条数
	 * @return 查询结果及用户列表
	 */
	@GetMapping("/admin/user/list")
	ResultVo getUserList(
		@ApiParam("筛选条件") UserWapper userWapper,
		@ApiParam("当前页数") @RequestParam Integer current,
		@ApiParam("每页条数") @RequestParam Integer size){
		User user = new User();
		Page<User> userPage = userService.getUserList(user, current, size);
		JSONObject data = new JSONObject();
		data.put("userList", userPage.getRecords());
		data.put("total", userPage.getTotal());
		data.put("size", userPage.getSize());
		data.put("current", userPage.getCurrent());
		return ResultUtil.ok(data);
	}

	/**
	 * 添加单个用户
	 * @param userWapper 用户信息
	 * @param type 用户类型
	 * @return 处理结果
	 */
	@PostMapping("/admin/user/add")
	ResultVo addUser(
		@ApiParam("用户信息") UserWapper userWapper,
		@ApiParam("用户类型 1: 教师, 2: 学生") @RequestParam int type){
		User user = new User();
		user.setLoginname(userWapper.getLoginname());
		user.setUsername(userWapper.getUsername());
		user.setOriginalClass(userWapper.getOriginalClass());
		user.setPassword(RandomUtil.randomString(8));
		try {
			userService.addUser(user, type);
		}catch (Exception e){
			return ResultUtil.error(400, "添加用户失败");
		}
		return ResultUtil.ok("添加成功");
	}

	/**
	 * 批量插入
	 * @return 处理结果
	 */
	@PostMapping("/admin/user/add/{fileId}")
	ResultVo addUserBatch(@PathVariable String fileId){

		return ResultUtil.ok("添加成功");
	}

	@GetMapping("/admin/teacher/all")
	public ResultVo getAllTeachers(){
		List<UserVo> teachers = userService.getAllTeachers();
		return ResultUtil.ok(teachers);
	}


	@GetMapping("/user/query")
	public ResultVo getUserList(String loginname, String username, Integer current, Integer size, Integer status, Integer roleId){
		Map<String, Object> params = new HashMap<>();
		params.put("loginname", loginname);
		params.put("username", username);
		if(status != null && status != -1) params.put("status", status);
		params.put("roleId", roleId);
		params.put("size", size);
		int offset = (current - 1) * size;
		params.put("offset", offset);
		System.out.println(params);
		List<UserVo> userList = userService.getUserList(params);
		int count = userService.getUserTotal(params);
		JSONObject data = new JSONObject();
		data.put("userList", userList);
		data.put("count", count);
		return ResultUtil.ok(data);
	}

	@GetMapping("/admin/user/info/export")
	public void exportUser(HttpServletResponse response, String loginname, String username, Integer status, Integer roleId){
		Map<String, Object> params = new HashMap<>();
		params.put("loginname", loginname);
		params.put("username", username);
		if(status != null && status != -1) params.put("status", status);
		params.put("roleId", roleId);
		userService.exportUser(response, params);
	}

	@PostMapping("/admin/user/import")
	public ResultVo importUser(@ApiParam("文件Id") Long fileId, @ApiParam("角色Id") Integer roleId){
		try {
			userService.importUser(fileId, roleId);
		}catch (Exception e){
			e.printStackTrace();
			return ResultUtil.error(400, "导入失败，请重试！");
		}
		return ResultUtil.ok("导入成功");
	}

}

