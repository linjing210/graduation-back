package cn.edu.aiit.gradution.mapper;

import cn.edu.aiit.gradution.pojo.entity.Role;
import cn.edu.aiit.gradution.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
	@Select("select role.* from role, user_role where userId = #{userId} and role.roleId = user_role.roleId")
	Role getRoleByUserId(@Param("userId") int userId);

	@Insert("insert into user_role(userId, roleId) values(#{userId}, #{roleId})")
	int addUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

	int addUserRoleBatch(@Param("userList")List<User> userList, Integer roleId);
}
