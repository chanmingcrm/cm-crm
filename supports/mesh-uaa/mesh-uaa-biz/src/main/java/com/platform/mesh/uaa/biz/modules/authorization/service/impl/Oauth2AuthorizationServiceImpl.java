package com.platform.mesh.uaa.biz.modules.authorization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.domain.bo.Oauth2AuthorizationBO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.dto.AuthorizationPageDTO;
import com.platform.mesh.uaa.biz.modules.authorization.domain.po.Oauth2Authorization;
import com.platform.mesh.uaa.biz.modules.authorization.mapper.Oauth2AuthorizationMapper;
import com.platform.mesh.uaa.biz.modules.authorization.service.IOauth2AuthorizationService;
import com.platform.mesh.uaa.biz.modules.authorization.service.manual.Oauth2AuthorizationServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 授权Service接口
 * @author 蝉鸣
 */
@Service
public class Oauth2AuthorizationServiceImpl extends ServiceImpl<Oauth2AuthorizationMapper, Oauth2Authorization> implements IOauth2AuthorizationService {

	@Autowired
	private Oauth2AuthorizationServiceManual oauth2AuthorizationServiceManual;

	/**
	 * 功能描述:
	 * 〈获取自定义操作类〉
	 * @return 正常返回:{@link Oauth2AuthorizationServiceManual}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2AuthorizationServiceManual getServiceManual() {
		return oauth2AuthorizationServiceManual;
	}

	/**
	 * 功能描述:
	 * 〈授权分页〉
	 * @param authorizationPageDTO 授权分页对象
	 * @return 正常返回:{@link MPage<Oauth2Authorization>}
	 * @author 蝉鸣
	 */
	@Override
	public MPage<Oauth2Authorization> selectPage(AuthorizationPageDTO authorizationPageDTO) {
		MPage<Oauth2Authorization> clientMPage = MPageUtil.pageEntityToMPage(authorizationPageDTO, Oauth2Authorization.class);
		return clientMPage;
	}

	/**
	 * 功能描述:
	 * 〈查询授权〉
	 * @param authorizationId 授权ID
	 * @return 正常返回:{@link Oauth2Authorization}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2Authorization getById(String authorizationId) {
		return this.lambdaQuery().eq(Oauth2Authorization::getId,authorizationId).one();

	}

	/**
	 * 功能描述:
	 * 〈修改授权〉
	 * @param oauth2AuthorizationBO 修改终端
	 * @return 正常返回:{@link Oauth2Authorization}}
	 * @author 蝉鸣
	 */
	@Override
	public Oauth2Authorization editAuthorization(Oauth2AuthorizationBO oauth2AuthorizationBO) {
		Oauth2Authorization oauth2Authorization = BeanUtil.copyProperties(oauth2AuthorizationBO, Oauth2Authorization.class);
		LambdaUpdateWrapper<Oauth2Authorization> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(Oauth2Authorization::getId, oauth2AuthorizationBO.getId());
		this.update(oauth2Authorization,updateWrapper);
		return oauth2Authorization;
	}

	/**
	 * 功能描述:
	 * 〈删除终端〉
	 * @param authorizationId 授权ID
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean deleteAuthorization(String authorizationId) {
		return Boolean.TRUE;
	}


}
