package com.platform.mesh.app.biz.modules.app.allgrouprel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.vo.AppAllGroupRelVO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.po.AppAllGroupRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块分组关联
 * @author 蝉鸣
 */
@Service
public class AppAllGroupRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appAllGroupRel appAllGroupRel 
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    public AppAllGroupRelVO getAllGroupRelInfoById(AppAllGroupRel appAllGroupRel) {
        AppAllGroupRelVO appAllGroupRelVO = new AppAllGroupRelVO();
        if(ObjectUtil.isEmpty(appAllGroupRelVO)){
            return appAllGroupRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(appAllGroupRel, appAllGroupRelVO);
        return appAllGroupRelVO;
    }

}