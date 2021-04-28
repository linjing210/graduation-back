package cn.edu.aiit.gradution.mapper;

import cn.edu.aiit.gradution.pojo.entity.User;
import cn.edu.aiit.gradution.pojo.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

	int addUserBatch(@Param("userList") List<User> userList);

	@Select("select ur.roleId, u.* from user u, user_role ur where u.userId = #{userId} and u.userId = ur.userId")
	UserVo getUserById(@Param("userId") Integer userId);

	@Select("select ur.roleId, u.* from user u, user_role ur where ur.roleId = 3 and u.userId = ur.userId")
	List<UserVo> getAllTeachers();

	@Select("select * from user where loginname = #{loginname}")
	User getUserByLoginname(@Param("loginname") String loginname);
	@Select("<script>select u.*, ur.roleId  from user u, user_role ur where u.userId = ur.userId\n" +
		"        <if test=\"loginname != null and loginname.length > 0\">and loginname like '%${loginname}%'</if>\n" +
		"            <if test=\"username != null and username.length > 0\">and username like '%${username}%'</if>\n" +
		"            <if test=\"status != null\">and status = #{status}</if>\n" +
		"            <if test=\"roleId != null\">and roleId = #{roleId}</if>\n" +
		"        <if test=\"offset != null and size != null\">limit #{offset}, #{size}</if></script>")
	List<UserVo> queryUserList(Map<String, Object> params);

	@Select("<script>select count(*) from user u, user_role ur where u.userId = ur.userId\n" +
		"        <if test=\"loginname != null and loginname.length > 0\"> and loginname like '%${loginname}%'</if>\n" +
		"            <if test=\"username != null and username.length > 0\">and username like '%${username}%'</if>\n" +
		"            <if test=\"status != null\">and status = #{status}</if>\n" +
		"            <if test=\"roleId != null\">and roleId = #{roleId}</if>\n" +
		"        </script>")
	int queryUserTotal(Map<String, Object> params);
}
