package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
public interface RoleService {
	/**
	 * 根据用户id获取角色信息
	 * @param userId 用户id
	 * @return 角色信息
	 */
	Role getRoleByUserId(int userId);


	/**
	 * 根据角色id获取角色信息
	 * @param roleId 角色id
	 * @return 角色信息
	 */
	Role getRoleById(int roleId);


	int addUserRole(int userId, int roleId);
}
