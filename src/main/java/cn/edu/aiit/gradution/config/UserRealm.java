package cn.edu.aiit.gradution.config;

import cn.edu.aiit.gradution.constant.RedisConstant;
import cn.edu.aiit.gradution.pojo.entity.Role;
import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.service.RoleService;
import cn.edu.aiit.gradution.service.UserService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;


public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("执行了=》principalCollection");
		Subject subject = SecurityUtils.getSubject();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Role role = (Role) subject.getPrincipal();
		info.addStringPermission(role.getPerms());
		info.addRole(role.getRoleName());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("执行了=》authenticationToken");
		// 获取token
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 检测用户是否存在
		User user = userService.getUserByLoginname(token.getUsername());
		if(user == null){
			return null;
		}
		// 获取用户所属角色
		Role role = roleService.getRoleByUserId(user.getUserId());
		System.out.println(token.getPassword());
		// 添加用户信息到缓存
		String tokenString = RandomUtil.randomString(16);
		redisTemplate.opsForHash().put(RedisConstant.ONLINE_USER, tokenString, user);
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("user", user);
		subject.getSession().setAttribute("role", role);
		return new SimpleAuthenticationInfo(role, user.getPassword(), "UserRealm");
	}
}
