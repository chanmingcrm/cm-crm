package com.platform.mesh.upms.biz.modules.sys.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.RouteDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuPageDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.RouteVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuSVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 用户信息
 * @author 蝉鸣
 */
public interface ISysMenuService extends IService<SysMenu> {


    /***
     * 功能描述:
     * 〈查询菜单页面列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<SysMenu>}
     * @author 蝉鸣
     * @since 2024/8/29 15:06
     */
    MPage<SysMenu> getMenuPage(SysMenuPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈通过菜单id查询菜单〉
     * @param menuId menuId
     * @return 正常返回:{@link SysMenuVO}
     * @author 蝉鸣
     */
    SysMenuVO getMenuInfoById(Long menuId);

    /**
     * 功能描述:
     * 〈通过账户id查询菜单〉
     * @param accountId accountId
     * @param filters filters
     * @param ignoreTypes ignoreTypes
     * @return 正常返回:{@link List<SysMenu>}
     * @author 蝉鸣
     */
    List<SysMenu> getMenuInfoByAccountId(Long accountId, List<Integer> filters,List<Integer> ignoreTypes);

    /***
     * 功能描述:
     * 〈获取单层菜单对象-方便配置编辑使用〉
     * @param menuId menuId
     * @return 正常返回:{@link SysMenuSVO}
     * @author 蝉鸣
     * @since 2024/9/2 17:01
     */
    SysMenuSVO getSMenuInfoById(Long menuId);
    /**
     * 功能描述:
     * 〈获取菜单树结构〉
     * @param menuId menuId
     * @return 正常返回:{@link List<SysMenuVO>}
     * @author 蝉鸣
     */
    List<SysMenuVO> getMenuTree(Long menuId);

    /**
     * 功能描述:
     * 〈获取路由信息〉
     * @param routeDTO routeDTO
     * @return 正常返回:{@link RouteVO}
     * @author 蝉鸣
     */
    RouteVO getMenuRouteInfo(RouteDTO routeDTO);

    /**
     * 功能描述:
     * 〈新增菜单〉
     * @param menuDTO menuDTO
     * @return 正常返回:{@link SysMenuVO}
     * @author 蝉鸣
     */
    SysMenuVO addMenu(SysMenuDTO menuDTO);

    /**
     * 功能描述:
     * 〈修改菜单〉
     * @param menuDTO menuDTO
     * @return 正常返回:{@link SysMenuVO}
     * @author 蝉鸣
     */
    SysMenuVO editMenu(SysMenuDTO menuDTO);

    /**
     * 功能描述:
     * 〈删除菜单〉
     * @param menuId menuId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMenu(Long menuId);

    /**
     * 功能描述:
     * 〈删除菜单〉
     * @param menuId menuId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean clearMenu(Long menuId);

    /***
     * 功能描述:
     * 〈操作业务模块自动同步至菜单〉
     * @param appMenuBO appMenuBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     * @since 2024/11/1 17:38
     */
    Boolean addOrEditMenu(AppMenuBO appMenuBO);

    /***
     * 功能描述:
     * 〈应用模块删除级联菜单〉
     * @param moduleIds moduleIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean appModuleMenuDelete(List<Long> moduleIds);

    /***
     * 功能描述:
     * 〈应用模块清除级联菜单〉
     * @param moduleIds moduleIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean appModuleMenuClear(List<Long> moduleIds);

    List<Long> getAppModules();
}
