package com.platform.mesh.upms.biz.modules.sys.menu.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuPageDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 菜单信息Mapper
 * @author 蝉鸣
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 功能描述:
     * 〈查询菜单分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<SysMenu>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    MPage<SysMenu> getMenuPage(IPage<SysMenu> iPage,@Param("pageDTO") SysMenuPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈根据用户ID获取关联菜单〉
     * @param userId userId
     * @return 正常返回:{@link List<SysMenu>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    List<SysMenu> getMenuInfo(@Param("userId") Long userId,@Param("filters") List<Integer> filters,@Param("ignores") List<Integer> ignoreMenuTypes);

    /**
     * 功能描述:
     * 〈查询模块变更需要修改的菜单〉
     * @param moduleId moduleId
     * @param menuType menuType
     * @param mac mac
     * @return 正常返回:{@link List<SysMenu>}
     * @author 蝉鸣
     */
    List<SysMenu> selectUpdateMenuList(@Param("moduleId") Long moduleId,@Param("menuType") Integer menuType, @Param("mac") String mac);

    /**
     * 功能描述:
     * 〈查询模块变更需要修改的菜单〉
     * @param moduleId moduleId
     * @param menuType menuType
     * @param mac mac
     * @return 正常返回:{@link SysMenu}
     * @author 蝉鸣
     */
    SysMenu selectUpdateMenu(@Param("moduleId") Long moduleId,@Param("menuType") Integer menuType, @Param("mac") String mac);

    /**
     * 功能描述:
     * 〈根据ID获取菜单〉
     * @param menuId menuId
     * @return 正常返回:{@link List<SysMenu>}
     * @author 蝉鸣
     */
    SysMenu getUniById(@Param("menuId") Long menuId);

    List<Long> getAppModules();
}
