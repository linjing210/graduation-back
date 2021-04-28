package cn.edu.aiit.gradution.handler;

import cn.edu.aiit.gradution.constant.UserInfoConstant;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class TypeHandler implements MetaObjectHandler {
	/**
	 * 插入时自动填充字段配置
	 * @param metaObject 数据库元数据
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("gmtModified", new Date(), metaObject);
		this.setFieldValByName("gmtCreate", new Date(), metaObject);
		this.setFieldValByName("headerImage", UserInfoConstant.DEFAULT_HEADER_IMAGE_URL, metaObject);
		this.setFieldValByName("status", 1, metaObject);

		this.setFieldValByName("cover", UserInfoConstant.DEFAULT_HEADER_IMAGE_URL, metaObject);
	}

	/**
	 * 更新时自动填充字段配置
	 * @param metaObject 数据库元数据
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("gmtModified", new Date(), metaObject);
	}
}
