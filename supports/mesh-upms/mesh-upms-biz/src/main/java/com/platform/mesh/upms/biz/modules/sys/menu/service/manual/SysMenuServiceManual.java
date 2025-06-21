package com.platform.mesh.upms.biz.modules.sys.menu.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.bo.RouteItemBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.bo.RouteMetaBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.RouteItemVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysRouteVO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.po.SysRoleMenuRel;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.service.ISysRoleMenuRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SysMenuServiceManual {

	private static final Logger log = LoggerFactory.getLogger(SysMenuServiceManual.class);

    @Autowired
    private ISysRoleMenuRelService sysRoleMenuRelService;


    /**
     * 功能描述:
     * 〈获取树形结构数据〉
     * @param sysMenus sysMenus
     * @return 正常返回:{@link List<SysMenuVO>}
     * @author 蝉鸣
     */
    public List<SysMenuVO> getMenuTree(List<SysMenu> sysMenus) {
        if(CollUtil.isEmpty(sysMenus)){
            return CollUtil.newArrayList();
        }
        //转换VO
        List<SysMenuVO> sysMenuVos = sysMenus.stream().map(this::getMenuInfoById).collect(Collectors.toList());
        //封装树结构
        return this.packageTree(sysMenuVos, 0L);
    }

    /**
     * 功能描述:
     * 〈获取菜单详情〉
     * @param sysMenu sysMenu
     * @return 正常返回:{@link SysMenuVO}
     * @author 蝉鸣
     */
    public SysMenuVO getMenuInfoById(SysMenu sysMenu) {
        SysMenuVO sysMenuVO = new SysMenuVO();
        if(ObjectUtil.isEmpty(sysMenu)){
            return sysMenuVO;
        }
        //转换VO
        BeanUtil.copyProperties(sysMenu, sysMenuVO);
        //转换RouteItem
        RouteItemBO routeItemBO = BeanUtil.copyProperties(sysMenu, RouteItemBO.class);
        //转换RouteMate
        RouteMetaBO routeMetaBO = BeanUtil.copyProperties(sysMenu, RouteMetaBO.class);
        routeItemBO.setRouteMeta(routeMetaBO);
        sysMenuVO.setRouteItem(routeItemBO);
        return sysMenuVO;
    }


    /**
     * 功能描述:
     * 〈封装树形机构〉
     * @param sysMenuVos sysMenuVos
     * @param parentId parentId
     * @return 正常返回:{@link List<SysMenuVO>}
     * @author 蝉鸣
     */
    public List<SysMenuVO> packageTree(List<SysMenuVO> sysMenuVos,Long parentId) {
        if(CollUtil.isEmpty(sysMenuVos)){
            return CollUtil.newArrayList();
        }
        //封装树形结构
        return sysMenuVos.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .peek(item -> item.setChildren(getChildrens(item, sysMenuVos)))
                .collect(Collectors.toList());
    }

    /**
     * 功能描述:
     * 〈递归查询子节点〉
     * @param root root
     * @param allList allList
     * @return 正常返回:{@link List<SysMenuVO>}
     * @author 蝉鸣
     */
    private List<SysMenuVO> getChildrens(SysMenuVO root, List<SysMenuVO> allList) {
        return allList.stream().filter(item -> Objects.equals(item.getParentId(), root.getId())).peek(
                (item) -> item.setChildren(getChildrens(item, allList))
        ).collect(Collectors.toList());
    }



    /**
     * 功能描述:
     * 〈获取路由信息〉
     * @param sysMenus sysMenus
     * @return 正常返回:{@link SysRouteVO}
     * @author 蝉鸣
     */
    public SysRouteVO getMenuRouteInfo(List<SysMenu> sysMenus) {
        SysRouteVO sysRouteVO = new SysRouteVO();
        sysRouteVO.setHome("dashboard_analysis");
        //封装路由信息
        //转换VO
        List<RouteItemVO> sysRouteVos = sysMenus.stream().map(this::getRouteInfoById).collect(Collectors.toList());
        //封装树结构
        List<RouteItemVO> routeTreeVO = this.packageRouteTree(sysRouteVos, 0L);
        sysRouteVO.setRoutes(routeTreeVO);
        return sysRouteVO;
    }

    /**
     * 功能描述:
     * 〈获取菜单详情〉
     * @param sysMenu sysMenu
     * @return 正常返回:{@link RouteItemVO}
     * @author 蝉鸣
     */
    public RouteItemVO getRouteInfoById(SysMenu sysMenu) {
        //转换RouteItem
        RouteItemVO routeItemVO = BeanUtil.copyProperties(sysMenu, RouteItemVO.class,"params");
        if(ObjectUtil.isEmpty(sysMenu)){
            return routeItemVO;
        }
        if(ObjectUtil.isEmpty(routeItemVO.getOrderNo())){
            routeItemVO.setOrderNo(NumberConst.NUM_0);
        }
        //转换RouteMate
        RouteMetaBO routeMetaBO = BeanUtil.copyProperties(sysMenu, RouteMetaBO.class,"params");
        JSONArray params = JSONUtil.parseArray(sysMenu.getParams());
        routeMetaBO.setParams(params);
        routeItemVO.setMeta(routeMetaBO);
        return routeItemVO;
    }


    /**
     * 功能描述:
     * 〈封装树形机构〉
     * @param sysRouteVos sysRouteVos
     * @param parentId parentId
     * @return 正常返回:{@link List<RouteItemVO>}
     * @author 蝉鸣
     */
    public List<RouteItemVO> packageRouteTree(List<RouteItemVO> sysRouteVos,Long parentId) {
        if(CollUtil.isEmpty(sysRouteVos)){
            return CollUtil.newArrayList();
        }
        //封装树形结构
        return sysRouteVos.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .peek(item -> item.setChildren(getRouteChildrens(item, sysRouteVos)))
                .sorted(Comparator.comparingInt(RouteItemVO::getOrderNo))
                .collect(Collectors.toList());
    }

    /**
     * 功能描述:
     * 〈递归查询子节点〉
     * @param root 根节点
     * @param allList 所有节点
     * @return 正常返回:{@link List<RouteItemVO>}
     * @author 蝉鸣
     */
    private List<RouteItemVO> getRouteChildrens(RouteItemVO root, List<RouteItemVO> allList) {
        return allList.stream().filter(item -> Objects.equals(item.getParentId(), root.getId())).peek(
                (item) -> item.setChildren(getRouteChildrens(item, allList))
        ).sorted(Comparator.comparingInt(RouteItemVO::getOrderNo)).collect(Collectors.toList());
    }

    /**
     * 功能描述:
     * 〈获取新增转换对象〉
     * @param appMenuBO appMenuBO
     * @return 正常返回:{@link List<RouteItemVO>}
     * @author 蝉鸣
     */
    public SysMenu getAddMenu(AppMenuBO appMenuBO) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setModuleId(appMenuBO.getModuleId());
        sysMenu.setParentId(appMenuBO.getParentModuleId());
        sysMenu.setMenuType(appMenuBO.getMenuType());
        sysMenu.setName(IdUtil.fastSimpleUUID());
        sysMenu.setTitle(appMenuBO.getTitle());
        sysMenu.setPath(appMenuBO.getPath());
        sysMenu.setComponent(appMenuBO.getComponent());
        if(CollUtil.isNotEmpty(appMenuBO.getParams())){
            String params = JSONUtil.toJsonStr(appMenuBO.getParams());
            sysMenu.setParams(params);
        }
        sysMenu.setKeepAlive(YesOrNoEnum.YES.getValue());
        sysMenu.setHideMenu(YesOrNoEnum.INIT.getValue());
        if(MenuTypeEnum.MENU.getValue().equals(sysMenu.getMenuType())) {
            sysMenu.setAlwaysShow(YesOrNoEnum.INIT.getValue());
        }else{
            sysMenu.setAlwaysShow(YesOrNoEnum.YES.getValue());
        }
        //todo 先随机生成
        String uuid = IdUtil.fastUUID();
        //路由路径
        if(MenuTypeEnum.APP.getValue().equals(sysMenu.getMenuType())) {
            sysMenu.setPath(SymbolConst.FORWARD_SLASH + uuid);
        }else{
            sysMenu.setPath(uuid);
        }
        return sysMenu;
    }

    /**
     * 功能描述:
     * 〈获取新增转换对象〉
     * @param appMenuBO appMenuBO
     * @return 正常返回:{@link List<RouteItemVO>}
     * @author 蝉鸣
     */
    public List<SysMenu> getEditMenu(List<SysMenu> editMenus,AppMenuBO appMenuBO) {
        if(CollUtil.isEmpty(editMenus)){
            return CollUtil.newArrayList();
        }
        return editMenus.stream().peek(menu->{
            menu.setId(menu.getId());
            menu.setModuleId(appMenuBO.getModuleId());
            menu.setMenuType(appMenuBO.getMenuType());
            menu.setComponent(appMenuBO.getComponent());
            menu.setTitle(appMenuBO.getTitle());
            menu.setPath(appMenuBO.getPath());
            if(CollUtil.isNotEmpty(appMenuBO.getParams())){
                String params = JSONUtil.toJsonStr(appMenuBO.getParams());
                menu.setParams(params);
            }
            menu.setKeepAlive(YesOrNoEnum.YES.getValue());
            if(MenuTypeEnum.MENU.getValue().equals(appMenuBO.getMenuType())) {
                menu.setAlwaysShow(YesOrNoEnum.INIT.getValue());
            }else {
                menu.setAlwaysShow(YesOrNoEnum.YES.getValue());
            }
            menu.setHideMenu(YesOrNoEnum.INIT.getValue());
        }).toList();
    }

    /**
     * 功能描述:
     * 〈删除角色与菜单关系〉
     * @param menuIds menuIds
     * @author 蝉鸣
     */
    public void clearRoleMenuRel(List<Long> menuIds) {
        if(CollUtil.isEmpty(menuIds)){
            return;
        }
        sysRoleMenuRelService.lambdaUpdate().in(SysRoleMenuRel::getMenuId,menuIds).remove();
    }

}
