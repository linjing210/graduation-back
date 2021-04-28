package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.constant.FileInfoConstant;
import cn.edu.aiit.gradution.exception.TransactionFailedException;
import cn.edu.aiit.gradution.constant.UserInfoConstant;
import cn.edu.aiit.gradution.handler.WapperHandler;
import cn.edu.aiit.gradution.mapper.FileMapper;
import cn.edu.aiit.gradution.mapper.RoleMapper;
import cn.edu.aiit.gradution.mapper.UserMapper;
import cn.edu.aiit.gradution.pojo.dto.Mail;
import cn.edu.aiit.gradution.pojo.entity.FileInfo;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.pojo.vo.TopicVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import cn.edu.aiit.gradution.service.UserService;
import cn.edu.aiit.gradution.util.ExcelUtils;
import cn.edu.aiit.gradution.util.FileUtil;
import cn.edu.aiit.gradution.util.MailSender;
import cn.edu.aiit.gradution.util.ResultUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Service
public class UserServiceImpl implements UserService {

	//  userMapper
	@Autowired
	private UserMapper userMapper;
	//  发送邮件
	@Autowired
	private MailSender mailSender;

	// roleMapper
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Autowired
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	/**
	 * 用于用户在登录界面进行密码重置
	 * @param userId 邮件
	 * @return 结果视图
	 */
	@Override
	public ResultVo resetPassword(int userId) {
		// 获取用户信息
		User user = userMapper.selectById(userId);
		if(user.getEmail() == null){
			return ResultUtil.error(400, "邮箱未绑定");
		}

		// 重置密码，更新密码到数据库
		String newPassword = RandomUtil.randomString(8);
		String hashPassword = new Sha256Hash(newPassword).toBase64();
		user.setPassword(hashPassword);
		try {
			int rlt = userMapper.updateById(user);
			if(rlt != 1){
				return ResultUtil.error(400, "账号不存在");
			}
		}catch (Exception e){
			return ResultUtil.error(500, "服务器异常");
		}
		// 设置邮件内容
		Mail mail = new Mail();
		mail.setSubject("密码重置通知");
		mail.setTime(new Date());
		String[] tolist = new String[1];
		tolist[0] = user.getEmail();
		mail.setTolist(tolist);
		mail.setContext("您的密码已重置为：" + newPassword + "。若非本人操作，请及时更改密码！");
		mailSender.sendSimpleMail(mail);
		return ResultUtil.ok("重置密码成功");
	}

	@Override
	public ResultVo resetPassword(String loginname) {
		User user = getUserByLoginname(loginname);
		String hashPassword = new Sha256Hash(UserInfoConstant.DEFAULT_USER_PASSWORD).toBase64();
		user.setPassword(hashPassword);
		int result = userMapper.updateById(user);
		if(result != 1) {
			return ResultUtil.error(400, "用户不存在");
		}
		JSONObject data = new JSONObject();
		data.set("newPassword", UserInfoConstant.DEFAULT_USER_PASSWORD);
		return ResultUtil.ok("重置密码成功", data);
	}

	@Override
	public ResultVo resetPassword(String oldPassword, String newPassword, User user) {
		// 检查原密码是否正确
		String oldHash = new Sha256Hash(oldPassword).toBase64();
		if(!user.getPassword().equals(oldHash)){
			return ResultUtil.error(400, "密码错误");
		}
		// 更新密码
		String newHash = new Sha256Hash(newPassword).toBase64();
		user.setPassword(newHash);
		int rlt = userMapper.updateById(user);
		// 若密码更新失败，将session改回为原密码
		if(rlt != 1){
			user.setPassword(oldHash);
			return ResultUtil.error(400, "修改密码失败");
		}
		return ResultUtil.ok("修改密码成功");
	}

	@Override
	public int addUser(User user, int type) throws TransactionFailedException{
		// 用户密码加密
		String password = user.getPassword();
		password = new Md5Hash(password).toHex();
		user.setPassword(password);
		// 添加到数据库
		int rlt = userMapper.insert(user);
		if(rlt != 1){
			return rlt;
		}
		// 获取用户角色Id
		int roleId;
		if(type == 1){
			roleId = UserInfoConstant.ROLE_TEACHER;
		} else {
			roleId = UserInfoConstant.ROLE_STUDENT;
		}
		rlt = roleMapper.addUserRole(user.getUserId(), roleId);

		// 若添加失败则抛出异常，让事务回滚
		if(rlt != 1){
			throw new TransactionFailedException("添加用户角色权限失败");
		}
		return rlt;
	}

	@Override
	public int addUser(List<User> userList) {
		userList.forEach((user)->{
			String password = UserInfoConstant.DEFAULT_USER_PASSWORD;
			password = new Sha256Hash(password).toBase64();
			user.setPassword(password);
			user.setStatus(1);
			Date date = new Date();
			user.setGmtCreate(date);
			user.setGmtModified(date);
			user.setHeaderImage(UserInfoConstant.DEFAULT_HEADER_IMAGE_URL);
		});
		return userMapper.addUserBatch(userList);
	}

	@Override
	public User getUserByLoginname(String loginname) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("loginname", loginname)
		.or().eq("email", loginname);
		return userMapper.selectOne(wrapper);
	}

	@Override
	public Page<User> getUserList(User user, Integer current, Integer size) {
		Page<User> userPage = new Page<>(current, size);
		QueryWrapper<User> wrapper = new QueryWrapper<>(user);
		return userMapper.selectPage(userPage, wrapper);
	}

	@Override
	public User getUserById(int userId) {
		return userMapper.selectById(userId);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateById(user);
	}

	@Override
	public List<UserVo> getAllTeachers() {
		return userMapper.getAllTeachers();
	}

	@Override
	public List<UserVo> getUserList(Map<String, Object> params) {
		return userMapper.queryUserList(params);
	}

	@Override
	public int getUserTotal(Map<String, Object> params) {
		return userMapper.queryUserTotal(params);
	}

	public void exportUser(HttpServletResponse response, Map<String, Object> params){
		List<UserVo> userVoList = userMapper.queryUserList(params);
		List<Map<String, Object>> data = WapperHandler.getUserInfoMap(userVoList);
		ExcelUtils.export(response, "user.xlsx", data);

	}

	@Override
	public void importUser(Long fileId, Integer roleId) {
		// 获取文件信息
		FileInfo fileInfo = fileMapper.selectById(fileId);

		// 读取excel数据
		ExcelReader reader = ExcelUtil.getReader(FileInfoConstant.ROOT + fileInfo.getFileUrl());
		List<Map<String,Object>> infos = reader.readAll();

		// 转换成用户信息
		List<User> userList = WapperHandler.getUserInfo(infos);
		if(userList.size() == 0) return;
		// 批量插入到数据库
		int rlt1 = this.addUser(userList);
		// 添加学生信息
		int rlt2 = roleMapper.addUserRoleBatch(userList, roleId);

		// 检测添加是否全部成功，否则进行回滚
		if(rlt1 != rlt2){
			throw new TransactionFailedException("批量导入用户失败");
		}
		// 删除临时文件
		fileMapper.deleteById(fileId);
		FileUtil.deleteFile(FileInfoConstant.ROOT + fileInfo.getFileUrl());
	}
}
