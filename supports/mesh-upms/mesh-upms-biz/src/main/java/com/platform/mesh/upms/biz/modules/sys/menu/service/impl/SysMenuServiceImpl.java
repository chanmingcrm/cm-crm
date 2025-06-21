package com.platform.mesh.upms.biz.modules.sys.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuPageDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysRouteDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuSVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysRouteVO;
import com.platform.mesh.upms.biz.modules.sys.menu.exception.MenuExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.menu.mapper.SysMenuMapper;
import com.platform.mesh.upms.biz.modules.sys.menu.service.ISysMenuService;
import com.platform.mesh.upms.biz.modules.sys.menu.service.manual.SysMenuServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 用户信息
 * @author 蝉鸣
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	private static final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

	@Autowired
	private SysMenuServiceManual sysMenuServiceManual;

	@Autowired
	private CacheManager cacheManager;

	/**
	 * 功能描述:
	 * 〈获取菜单详情〉
	 * @param menuId menuId
	 * @return 正常返回:{@link SysMenuVO}
	 * @author 蝉鸣
	 */
	@Override
	public SysMenuVO getMenuInfoById(Long menuId) {
		SysMenu sysMenu = this.getById(menuId);
		return sysMenuServiceManual.getMenuInfoById(sysMenu);
	}

	/**
	 * 功能描述:
	 * 〈根据账户ID获取关联菜单〉
	 * @param accountId accountId
	 * @return 正常返回:{@link List<SysMenu>}
	 * @author 蝉鸣
	 */
	@Override
	public List<SysMenu> getMenuInfoByAccountId(Long accountId, List<Integer> filters,List<Integer> ignoreTypes) {
		//通过账户获取用户ID
		SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(accountId);
		if(ObjectUtil.isEmpty(accountBO) || ObjectUtil.isEmpty(accountBO.getUserId())) {
			return CollUtil.newArrayList();
		}
		if(SecurityUtils.isAdmin(accountBO.getUserId())){
			return this.lambdaQuery()
					.in(CollUtil.isNotEmpty(filters),SysMenu::getMenuType,filters)
					.notIn(CollUtil.isNotEmpty(ignoreTypes),SysMenu::getMenuType,ignoreTypes).list();
		}
		//查询当前用户下关联的菜单
		return this.getBaseMapper().getMenuInfo(accountBO.getUserId(),filters,ignoreTypes);
	}

	/***
	 * 功能描述:
	 * 〈根据菜单ID查询菜单信息-返回单层结构体〉
	 * @param menuId menuId
	 * @return 正常返回:{@link SysMenuSVO}
	 * @author 蝉鸣
	 * @since 2024/9/2 17:05
	 */
	@Override
	public SysMenuSVO getSMenuInfoById(Long menuId) {
		SysMenu sysMenu = this.getById(menuId);
		SysMenuSVO sysMenuSVO = new SysMenuSVO();
		if(ObjectUtil.isEmpty(sysMenu)){
			return sysMenuSVO;
		}
		//转换VO
		BeanUtil.copyProperties(sysMenu, sysMenuSVO,"params");
		if(!ObjectUtil.isEmpty(sysMenu.getParams())){
			JSONArray arrays = JSONUtil.parseArray(sysMenu.getParams());
			sysMenuSVO.setParams(arrays);
		}
		return sysMenuSVO;
	}

	/**
	 * 功能描述:
	 * 〈获取菜单树结构〉
	 * @param menuId menuId
	 * @return 正常返回:{@link List<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Override
	public List<SysMenuVO> getMenuTree(Long menuId) {
		if(ObjectUtil.isEmpty(menuId)){
			menuId= YesOrNoEnum.NO.getValue().longValue();
		}
//		String childrenSql= SqlUtil.getCommonChildrenSql(SysMenu.class, menuId);
//		//查询子项demo
//		List<SysMenu> childList = this.lambdaQuery().apply(childrenSql).list();
//
//		String parentsSql = SqlUtil.getCommonParentSql(SysMenu.class, menuId);
//		//查询父项demo
//		List<SysMenu> parentList = this.lambdaQuery().apply(parentsSql).list();
		List<SysMenu> sysMenus = this.lambdaQuery().eq(SysMenu::getDelFlag,YesOrNoEnum.YES.getValue()).list();
		return sysMenuServiceManual.getMenuTree(sysMenus);
	}

	/**
	 * 功能描述:
	 * 〈获取路由信息〉
	 * @param routeDTO routeDTO
	 * @return 正常返回:{@link SysRouteVO}
	 * @author 蝉鸣
	 */
	@Override
	public SysRouteVO getMenuRouteInfo(SysRouteDTO routeDTO) {
//		String childrenSql= SqlUtil.getCommonChildrenSql(SysMenu.class, routeDTO.getMenuId());
		//查询子项demo
//		List<SysMenu> childList = this.lambdaQuery().apply(childrenSql).list();

//		String parentsSql = SqlUtil.getCommonParentSql(SysMenu.class, routeDTO.getMenuId());
		//查询父项demo
//		List<SysMenu> parentList = this.lambdaQuery().apply(parentsSql).list();
//		List<SysMenu> sysMenus = this.list();
		//过滤组件类型
		List<Integer> ignoreMenuTypes = CollUtil.newArrayList();
		ignoreMenuTypes.add(MenuTypeEnum.CATEGORY.getValue());
		ignoreMenuTypes.add(MenuTypeEnum.COMPONENT.getValue());
		//返回当前角色下的菜单
		List<SysMenu> sysMenus = getMenuInfoByAccountId(routeDTO.getAccountId(),CollUtil.newArrayList(),ignoreMenuTypes);
		return sysMenuServiceManual.getMenuRouteInfo(sysMenus);
	}

	/**
	 * 功能描述:
	 * 〈添加菜单〉
	 * @param menuDTO menuDTO
	 * @return 正常返回:{@link SysMenuVO}
	 * @author 蝉鸣
	 */
	@Override
	public SysMenuVO addMenu(SysMenuDTO menuDTO) {
		SysMenu sysMenu = BeanUtil.copyProperties(menuDTO, SysMenu.class);
		if(CollUtil.isNotEmpty(menuDTO.getParams())){
			String params = JSONUtil.toJsonStr(menuDTO.getParams());
			sysMenu.setParams(params);
		}
		sysMenu.setAlwaysShow(YesOrNoEnum.YES.getValue());
		sysMenu.setDelFlag(YesOrNoEnum.YES.getValue());
		this.save(sysMenu);
		return BeanUtil.copyProperties(sysMenu, SysMenuVO.class);
	}

	/**
	 * 功能描述:
	 * 〈添加菜单〉
	 * @param menuDTO menuDTO
	 * @return 正常返回:{@link SysMenuVO}
	 * @author 蝉鸣
	 */
	@Override
	@Transactional(rollbackFor = BaseException.class)
	public SysMenuVO editMenu(SysMenuDTO menuDTO) {
		SysMenu sysMenu = getById(menuDTO.getId());
		if(ObjectUtil.isEmpty(sysMenu)){
			//获取字段名称
			String fieldName = ObjFieldUtil.getFieldName(SysMenuDTO::getId);
			throw MenuExceptionEnum.ADD_NO_INVALID.getBaseException(CollUtil.newArrayList(fieldName));
		}
		BeanUtil.copyProperties(menuDTO, sysMenu);
		if(CollUtil.isNotEmpty(menuDTO.getParams())){
			String params = JSONUtil.toJsonStr(menuDTO.getParams());
			sysMenu.setParams(params);
		}
		this.updateById(sysMenu);
		//修改子节点状态
		String childrenSql= SqlUtil.getCommonChildrenSql(SysMenu.class, menuDTO.getId());
		//查询子项demo
		List<SysMenu> childList = this.lambdaQuery().apply(childrenSql).list();
		if(CollUtil.isEmpty(childList)){
			return BeanUtil.copyProperties(sysMenu, SysMenuVO.class);
		}
		childList.forEach(menu->{menu.setHideMenu(menuDTO.getHideMenu());});
		this.updateBatchById(childList);
		return BeanUtil.copyProperties(sysMenu, SysMenuVO.class);
	}

	/**
	 * 功能描述:
	 * 〈删除菜单〉
	 * @param menuId menuId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	@Transactional(rollbackFor = BaseException.class)
	public Boolean deleteMenu(Long menuId) {
		// 清空userinfo缓存
		Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_MENU_DETAILS)).clear();
		// 删除菜单
		//修改子节点状态
		String childrenSql= SqlUtil.getCommonChildrenSql(SysMenu.class, menuId);
		//查询子项demo
		List<SysMenu> childList = this.lambdaQuery().apply(childrenSql).list();
		List<Long> childIds = childList.stream().map(SysMenu::getId).filter(ObjectUtil::isNotEmpty).toList();
		List<Long> menuIds = CollUtil.newArrayList(menuId);
		if(CollUtil.isNotEmpty(childIds)){
			menuIds.addAll(childIds);
		}
		return this.removeBatchByIds(menuIds);
	}

	/***
	 * 功能描述:
	 * 〈查询菜单页面列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link MPage<SysMenu>}
	 * @author 蝉鸣
	 * @since 2024/8/29 15:06
	 */
	@Override
	public MPage<SysMenu> getMenuPage(SysMenuPageDTO pageDTO) {
		MPage<SysMenu> mPage = MPageUtil.pageEntityToMPage(pageDTO, SysMenu.class);
		Long userId = UserCacheUtil.getUserId();
		//查询是否当前管理员
		Boolean tenantAdmin = UserCacheUtil.isAccountAdmin();
		pageDTO.setUserId(userId);
		pageDTO.setIsAdmin(tenantAdmin);
		pageDTO.setDelFlag(YesOrNoEnum.NO.getValue());
		return this.getBaseMapper().getMenuPage(mPage,pageDTO);
	}

	/***
	 * 功能描述:
	 * 〈操作业务模块自动同步至菜单〉
	 * @param appMenuBO appMenuBO
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 * @since 2024/11/1 17:38
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addOrEditMenu(AppMenuBO appMenuBO) {
		OperateTypeEnum operationTypeEnum = BaseEnum.getEnumByValue(OperateTypeEnum.class, appMenuBO.getOperateType());
		String mac = appMenuBO.getRelId().toString();
		switch (operationTypeEnum){
			case INSERT://新增模块
				List<SysMenu> sysMenus = this.lambdaQuery().eq(SysMenu::getModuleId, appMenuBO.getParentModuleId()).list();
				if (CollUtil.isNotEmpty(sysMenus)) {
					appMenuBO.setParentModuleId(sysMenus.getFirst().getId());
				}else{
					appMenuBO.setParentModuleId(NumberConst.NUM_0.longValue());
				}
				Long menuId = IdUtil.getSnowflake().nextId();
				SysMenu sysMenu = sysMenuServiceManual.getAddMenu(appMenuBO);
				sysMenu.setId(menuId);
				if(StrUtil.isBlank(sysMenu.getMac())){
					sysMenu.setMac(mac);
				}
				this.save(sysMenu);
				break;
			case UPDATE://编辑模块
				List<SysMenu> updateMenus;
				//如果是组件根据Mac查询
				if(MenuTypeEnum.COMPONENT.getValue().equals(appMenuBO.getMenuType())){
					updateMenus = this.lambdaQuery()
							.eq(SysMenu::getModuleId, appMenuBO.getModuleId())
							.eq(SysMenu::getMac, mac)
							.list();
				}else{
					//根据模块ID查询
					updateMenus = this.lambdaQuery().eq(SysMenu::getModuleId, appMenuBO.getModuleId()).list();
				}
				if(CollUtil.isNotEmpty(updateMenus)){
					List<SysMenu> editMenu = sysMenuServiceManual.getEditMenu(updateMenus, appMenuBO);
					this.updateBatchById(editMenu);
				}else{
					appMenuBO.setOperateType(OperateTypeEnum.INSERT.getValue());
					addOrEditMenu(appMenuBO);
				}
				break;
		}
		return true;
	}

	/***
	 * 功能描述:
	 * 〈应用模块删除级联菜单〉
	 * @param moduleIds moduleIds
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean appModuleMenuDelete(List<Long> moduleIds) {
		this.lambdaUpdate().set(SysMenu::getDelFlag,YesOrNoEnum.NO.getValue()).in(SysMenu::getModuleId, moduleIds).update();
		// 清空userinfo缓存
		Long accountId = UserCacheUtil.getAccountId();
		UserCacheUtil.delAccountMenuCache(accountId);
		return Boolean.TRUE;
	}

	/***
	 * 功能描述:
	 * 〈应用模块清除级联菜单〉
	 * @param moduleIds moduleIds
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean appModuleMenuClear(List<Long> moduleIds) {
		// 批量删除
		List<SysMenu> menus = this.lambdaQuery().in(SysMenu::getModuleId, moduleIds).list();
		if(CollUtil.isEmpty(menus)){
			return Boolean.FALSE;
		}
		// 清除角色菜单关系
		List<Long> menuIds = menus.stream().map(SysMenu::getId).distinct().toList();
		sysMenuServiceManual.clearRoleMenuRel(menuIds);
		this.lambdaUpdate().in(SysMenu::getModuleId, moduleIds).remove();
		// 清空userinfo缓存
		Long accountId = UserCacheUtil.getAccountId();
		UserCacheUtil.delAccountMenuCache(accountId);
		return Boolean.TRUE;
	}

}
