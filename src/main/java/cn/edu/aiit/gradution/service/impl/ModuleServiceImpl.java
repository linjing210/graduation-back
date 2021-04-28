package cn.edu.aiit.gradution.service.impl;

import cn.edu.aiit.gradution.mapper.ModuleMapper;
import cn.edu.aiit.gradution.pojo.entity.Module;
import cn.edu.aiit.gradution.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 仇冬林
 * @since 2021-03-16
 */
@Service
public class ModuleServiceImpl implements ModuleService {


	private ModuleMapper moduleMapper;

	@Autowired
	public void setModuleMapper(ModuleMapper moduleMapper) {
		this.moduleMapper = moduleMapper;
	}

	@Override
	public List<Module> getAllModules() {
		return moduleMapper.selectList(null);
	}
}
