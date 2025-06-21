package com.platform.mesh.uaa.biz.modules.registered.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.service.impl.Oauth2RegisteredClientServiceImpl;
import com.platform.mesh.uaa.biz.modules.registered.service.manual.RegisteredClientServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

/**
 * @description 〈实现储存redis个性化〉
 * @author 蝉鸣
 */
@Service
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

	@Autowired
	private RegisteredClientServiceManual registeredClientServiceManual;

	@Autowired
	private Oauth2RegisteredClientServiceImpl sysClientDetailsService;

	/**
	 * 功能描述:
	 * 〈添加终端〉
	 * @param registeredClient registeredClient
	 * @author 蝉鸣
	 */
	@Override
	public void save(RegisteredClient registeredClient) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 功能描述:
	 * 〈查询终端信息〉
	 * @param id id
	 * @return 正常返回:{@link RegisteredClient}
	 * @author 蝉鸣
	 */
	@Override
	public RegisteredClient findById(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 功能描述:
	 * 〈查询终端信息〉
	 * 重写原生方法支持redis缓存
	 * @param clientId clientId
	 * @return 正常返回:{@link RegisteredClient}
	 * @author 蝉鸣
	 */
	@Override
//	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public RegisteredClient findByClientId(String clientId) {
		if(ObjectUtil.isEmpty(clientId)){
			return null;
		}
		//获取Oauth2RegisteredClient
		Oauth2RegisteredClient clientDetails = sysClientDetailsService.selectSysClientDetailsById(clientId);
		//转换OAuth2 RegisteredClient
		RegisteredClient registeredClient  = registeredClientServiceManual.toRegisteredClient(clientDetails);

		return registeredClient;


	}

}