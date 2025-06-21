package com.platform.mesh.uaa.biz.modules.authorization.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.security.domain.bo.Oauth2AuthorizationBO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.dto.AuthorizationPageDTO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.po.Oauth2Authorization;
import com.platform.mesh.uaa.biz.modules.authorization.service.manual.Oauth2AuthorizationServiceManual;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;

/**
 * @description 授权Service接口
 * @author 蝉鸣
 */
public interface IOauth2AuthorizationService extends IService<Oauth2Authorization> {

	/**
	 * 功能描述:
	 * 〈获取自定义操作类〉
	 * @return 正常返回:{@link Oauth2AuthorizationServiceManual}
	 * @author 蝉鸣
	 */
	Oauth2AuthorizationServiceManual getServiceManual();

	/**
	 * 功能描述:
	 * 〈获取授权详细信息〉
	 * @param authorizationPageDTO authorizationPageDTO
	 * @return 正常返回:{@link MPage<Oauth2Authorization>}
	 * @author 蝉鸣
	 */
	MPage<Oauth2Authorization> selectPage(AuthorizationPageDTO authorizationPageDTO);

	/**
	 * 功能描述:
	 * 〈获取授权详细信息〉
	 * @param authorizationId authorizationId
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	Oauth2Authorization getById(String authorizationId);

	/**
	 * 功能描述:
	 * 〈修改授权〉
	 * @param oauth2AuthorizationBO oauth2AuthorizationBO
	 * @return 正常返回:{@link Oauth2Authorization}
	 * @author 蝉鸣
	 */
	Oauth2Authorization editAuthorization(Oauth2AuthorizationBO oauth2AuthorizationBO);

	/**
	 * 功能描述:
	 * 〈删除授权〉
	 * @param authorizationId authorizationId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	Boolean deleteAuthorization(String authorizationId);
}