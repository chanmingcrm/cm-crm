package com.platform.mesh.uaa.biz.modules.client.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientPageDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientAddDTO;
import com.platform.mesh.uaa.biz.modules.client.domain.dto.ClientEditDTO;
import com.platform.mesh.uaa.biz.modules.client.mapper.Oauth2RegisteredClientMapper;
import com.platform.mesh.uaa.biz.modules.client.service.IOauth2RegisteredClientService;
import org.springframework.stereotype.Service;

/**
 * @description 终端配置Service接口
 * @author 蝉鸣
 */
@Service
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClient> implements IOauth2RegisteredClientService {

	/**
	 * 功能描述:
	 * 〈终端配置分页〉
	 * @param clientPageDTO 终端配置分页对象
	 * @return 正常返回:{@link MPage<Oauth2RegisteredClient>}
	 * @author 蝉鸣
	 */
	@Override
	public MPage<Oauth2RegisteredClient> selectPage(ClientPageDTO clientPageDTO) {
		MPage<Oauth2RegisteredClient> clientMPage = MPageUtil.pageEntityToMPage(clientPageDTO, Oauth2RegisteredClient.class);
		LambdaQueryWrapper<Oauth2RegisteredClient> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.ne(Oauth2RegisteredClient::getDeleted,YesOrNoEnum.NO.getValue());
		return this.page(clientMPage,queryWrapper);
	}

	/**
	 * 功能描述:
	 * 〈查询终端配置〉
	 * @param clientId 终端配置ID
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2RegisteredClient selectSysClientDetailsById(String clientId) {
		return this.lambdaQuery().eq(Oauth2RegisteredClient::getClientId,clientId).one();

	}

	/**
	 * 功能描述:
	 * 〈新增终端〉
	 * @param clientAddDTO 新增终端
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2RegisteredClient addClient(ClientAddDTO clientAddDTO) {
		Oauth2RegisteredClient oauth2RegisteredClient = BeanUtil.copyProperties(clientAddDTO, Oauth2RegisteredClient.class);
		//加密校验码
		String encryptedPassword = SecurityUtils.encryptPassword(oauth2RegisteredClient.getClientSecret());
		oauth2RegisteredClient.setClientSecret(encryptedPassword);
		oauth2RegisteredClient.setDeleted(YesOrNoEnum.YES.getValue());
		this.save(oauth2RegisteredClient);
		return oauth2RegisteredClient;
	}

	/**
	 * 功能描述:
	 * 〈修改终端配置〉
	 * @param clientEditDTO 修改终端
	 * @return 正常返回:{@link Oauth2RegisteredClient}}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2RegisteredClient editClient(ClientEditDTO clientEditDTO) {
		Oauth2RegisteredClient registeredClient = selectSysClientDetailsById(clientEditDTO.getClientId());
		if(ObjectUtil.isEmpty(registeredClient)){
			return null;
		}
		BeanUtil.copyProperties(clientEditDTO, registeredClient);
		updateById(registeredClient);
		return registeredClient;
	}

	/**
	 * 功能描述:
	 * 〈删除终端〉
	 * @param clientId 终端配置ID
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean deleteClient(String clientId) {
		this.lambdaUpdate()
				.set(Oauth2RegisteredClient::getDeleted,YesOrNoEnum.NO.getValue())
				.eq(Oauth2RegisteredClient::getClientId, clientId)
				.update();
		return Boolean.TRUE;
	}


}
