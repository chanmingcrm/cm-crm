package com.platform.mesh.app.biz.modules.app.allgroup.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.vo.AppAllGroupVO;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.po.AppAllGroup;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块分组
 * @author 蝉鸣
 */
@Service
public class AppAllGroupServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appAllGroup appAllGroup 
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    public AppAllGroupVO getAllGroupInfoById(AppAllGroup appAllGroup) {
        AppAllGroupVO appAllGroupVO = new AppAllGroupVO();
        if(ObjectUtil.isEmpty(appAllGroupVO)){
            return appAllGroupVO;
        }
        //转换VO
        BeanUtil.copyProperties(appAllGroup, appAllGroupVO);
        return appAllGroupVO;
    }

}