package com.platform.mesh.app.biz.modules.app.compbase.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.compbase.domain.vo.AppCompBaseVO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.po.AppCompBase;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 页面组件
 * @author 蝉鸣
 */
@Service
public class AppCompBaseServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appCompBase appCompBase 
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    public AppCompBaseVO getCompBaseInfoById(AppCompBase appCompBase) {
        AppCompBaseVO appCompBaseVO = new AppCompBaseVO();
        if(ObjectUtil.isEmpty(appCompBaseVO)){
            return appCompBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(appCompBase, appCompBaseVO);
        return appCompBaseVO;
    }

}