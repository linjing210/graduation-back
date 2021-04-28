package cn.edu.aiit.gradution.service;

import cn.edu.aiit.gradution.pojo.entity.Module;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
public interface ModuleService {
	List<Module> getAllModules();
}
