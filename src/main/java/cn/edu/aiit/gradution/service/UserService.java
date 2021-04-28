package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.exception.TransactionFailedException;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.ResultVo;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.omg.CORBA.INTERNAL;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 管理员重置用户密码
	 * @param userId 用户id
	 * @return 处理结果
	 */
	ResultVo resetPassword(int userId);

	/**
	 * 用户通过邮箱重置密码
	 * @param email 邮箱
	 * @return 处理结果
	 */
	ResultVo resetPassword(String email);


	ResultVo resetPassword(String oldPassword, String newPassword, User user);


	/**
	 * 添加单个用户
	 * @param user 用户信息
	 * @return 受影响行数
	 */
	int addUser(User user, int type) throws TransactionFailedException;

	/**
	 * 添加过个用户
	 * @param userList 用户信息列表
	 * @return 受影响行数
	 */
	int addUser(List<User> userList);

	/**
	 * 通过学号/工号/邮箱获取用户信息
	 * @param loginname 学号/工号/邮箱
	 * @return 用户信息
	 */
	User getUserByLoginname(String loginname);

	/**
	 * 获取用户列表
	 * @param user 用户信息
	 * @param current 当前页
	 * @param size 每页条数
	 * @return 用户信息列表
	 */
	Page<User> getUserList(User user, Integer current, Integer size);

	/**
	 * 通过id获取用户信息
	 * @param userId 用户id
	 * @return 用户信息
	 */
	User getUserById(int userId);

	/**
	 * 更新用户信息
	 * @param user 用户信息
	 * @return 受影响行数
	 */
	int updateUser(User user);


	/**
	 * 获取所有教师信息
	 * @return
	 */
	List<UserVo> getAllTeachers();
//	User getCurrentUser();

	/**
	 * 查询用户列表
	 * @param params 筛选条件
	 * @return 用户信息列表
	 */
	List<UserVo> getUserList(Map<String, Object> params);

	/**
	 * 获取符合条件的用户数量
	 * @param params 筛选条件
	 * @return 符合条件的用户数量
	 */
	int getUserTotal(Map<String, Object> params);

	/**
	 * 导出用户数据
	 * @param response 响应流
	 * @param params 查询条件
	 */
	void exportUser(HttpServletResponse response, Map<String, Object> params);

	void importUser(Long fileId, Integer roleId);
}
