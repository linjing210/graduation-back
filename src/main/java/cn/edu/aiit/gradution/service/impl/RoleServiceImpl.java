package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.mapper.RoleMapper;
import cn.edu.aiit.gradution.pojo.entity.Role;
import cn.edu.aiit.gradution.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Service
public class RoleServiceImpl implements RoleService {

	/**
	 * 角色数据操作
	 */
	private RoleMapper roleMapper;

	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	/**
	 * 根据用户id获取角色信息
	 * @param userId 用户id
	 * @return 角色信息
	 */
	@Override
	public Role getRoleByUserId(int userId) {
		return roleMapper.getRoleByUserId(userId);
	}

	/**
	 * 根据角色id获取角色信息
	 * @param roleId 角色id
	 * @return 角色信息
	 */
	@Override
	public Role getRoleById(int roleId) {
		return roleMapper.selectById(roleId);
	}

	/**
	 * 添加用户角色关系
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @return 受影响条数
	 */
	@Override
	public int addUserRole(int userId, int roleId) {
		return roleMapper.addUserRole(userId, roleId);
	}
}
