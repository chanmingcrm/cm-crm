package com.platform.mesh.uaa.biz.auth.api;

import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.uaa.biz.auth.service.ITokenService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 统一管理API
 * @author 蝉鸣
 */
@Hidden
@RestController
public class TokenApi {

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	/**
	 * 功能描述:
	 * 〈删除token〉
	 * @param token token
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@DeleteMapping("/api/token/{token}")
	public Result<Void> removeToken(@PathVariable("token") String token) {
		tokenService.removeToken(token);
		return Result.success();
	}

//	/**
//	 * 分页查询Token
//	 * @param tokenBO TokenDTO
//	 * @return R<TableDataInfo>
//	 */
//	@Operation(summary = "分页token 信息")
//	@AuthIgnore
//	@GetMapping("/api/token/pageQuery")
//	public R<TableDataInfo> tokenList(TokenBO tokenBO) {
//		String username = tokenBO.getUsername();
//		Integer current = tokenBO.getCurrent();
//		Integer pageSize = tokenBO.getPageSize();
//
//		// 根据分页参数获取对应数据
//		String key = String.format("%s::*", CacheConstants.PROJECT_OAUTH_ACCESS);
//		if (StringUtils.isNotEmpty(username)) {
//			key = String.format("%s::*%s*", CacheConstants.PROJECT_OAUTH_ACCESS, username);
//		}
//		Set<String> keys = redisTemplate.keys(key);
//		List<String> pages = keys.stream().skip((long) (current - 1) * pageSize).limit(pageSize)
//				.collect(Collectors.toList());
//
//		TableDataInfo tableDataInfo = new TableDataInfo();
//
//		List<TokenVO> tokenVOList = redisTemplate.opsForValue().multiGet(pages).stream().map(obj -> {
//			OAuth2Authorization authorization = (OAuth2Authorization) obj;
//			TokenVO tokenVo = new TokenVO();
//			tokenVo.setClientId(authorization.getRegisteredClientId());
//			tokenVo.setId(authorization.getId());
//			tokenVo.setUsername(authorization.getPrincipalName());
//			tokenVo.setTokenType(authorization.getAuthorizationGrantType().toString());
//			tokenVo.setScope(authorization.getAuthorizationGrantType().toString());
//
//			OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
//			tokenVo.setAccessToken(accessToken.getToken().getTokenValue());
//
//			OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
//			tokenVo.setRefreshToken(refreshToken.getToken().getTokenValue());
//
//			String expiresAt = TemporalAccessorUtil.format(accessToken.getToken().getExpiresAt(),
//					DatePattern.NORM_DATETIME_PATTERN);
//			tokenVo.setExpiresAt(expiresAt);
//
//			String issuedAt = TemporalAccessorUtil.format(accessToken.getToken().getIssuedAt(),
//					DatePattern.NORM_DATETIME_PATTERN);
//			tokenVo.setIssuedAt(issuedAt);
//			return tokenVo;
//		}).collect(Collectors.toList());
//
//		tableDataInfo.setRecords(tokenVOList);
//		tableDataInfo.setTotal(keys.size());
//
//		return R.ok(tableDataInfo);
//	}

}