package cn.edu.aiit.gradution.config;


import cn.edu.aiit.gradution.pojo.entity.Module;
import cn.edu.aiit.gradution.service.ModuleService;
import cn.edu.aiit.gradution.service.UserService;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

	private ModuleService moduleService;

	@Autowired
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Autowired DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		Map<String, String> map = new HashMap<>();
		List<Module> moduleList = moduleService.getAllModules();
		moduleList.forEach((e)->{
			if(!"authc".equals(e.getPerms())){
				map.put(e.getUrl(), "perms[" + e.getPerms() + "]");
			}else{
				map.put(e.getUrl(), e.getPerms());
			}
		});
		bean.setFilterChainDefinitionMap(map);
		bean.setLoginUrl("/login");
		bean.setUnauthorizedUrl("/unauth");
		return bean;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		return securityManager;
	}

	@Bean
	public UserRealm userRealm(CredentialsMatcher matcher){
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(matcher);
		return userRealm;
	}

//	@Bean
//	HashedCredentialsMatcher credentialsMatcher(){
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//		matcher.setHashAlgorithmName("md5");
//		matcher.setHashSalted(false);
//		matcher.setStoredCredentialsHexEncoded(false);
//		matcher.setHashIterations(1);
//		return matcher;
//	}

}
