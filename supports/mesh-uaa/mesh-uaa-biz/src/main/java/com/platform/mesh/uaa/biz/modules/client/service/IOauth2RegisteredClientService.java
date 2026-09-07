package com.platform.mesh.uaa.biz.modules.client.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientPageDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientAddDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientEditDTO;

/**
 * @description 终端配置Service接口
 * @author 蝉鸣
 */
public interface IOauth2RegisteredClientService extends IService<Oauth2RegisteredClient> {



	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientPageDTO clientPageDTO
	 * @return 正常返回:{@link MPage<Oauth2RegisteredClient>}
	 * @author 蝉鸣
	 */
	MPage<Oauth2RegisteredClient> selectPage(ClientPageDTO clientPageDTO);

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientId clientId
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	Oauth2RegisteredClient selectSysClientDetailsById(String clientId);


	/**
	 * 功能描述:
	 * 〈新增终端配置〉
	 * @param clientAddDTO clientAddDTO
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	Oauth2RegisteredClient addClient(ClientAddDTO clientAddDTO);

	/**
	 * 功能描述:
	 * 〈修改终端配置〉
	 * @param clientEditDTO clientEditDTO
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	Oauth2RegisteredClient editClient(ClientEditDTO clientEditDTO);

	/**
	 * 功能描述:
	 * 〈删除终端配置〉
	 * @param clientId clientId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	Boolean deleteClient(String clientId);
}
