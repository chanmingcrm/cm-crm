package com.platform.mesh.upms.biz.modules.sys.role.service.manual;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class SysRoleServiceManual {

    /**
     * 功能描述:
     * 〈封装列表角色信息〉
     * @param rolePage rolePage
     * @return 正常返回:{@link MPage<SysRoleVO>}
     * @author 蝉鸣
     */
    public PageVO<SysRoleVO> packUserPackVO(MPage<SysRole> rolePage) {
        if(CollUtil.isEmpty(rolePage.getRecords())){
            return new PageVO<>();
        }
        return MPageUtil.convertToVO(rolePage, SysRoleVO.class);
    }

}

