package com.platform.mesh.app.biz.modules.app.formbase.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单
 * @author 蝉鸣
 */
@Service
public class AppFormBaseServiceManual{

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appFormBase appFormBase 
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    public AppFormBaseVO getFormBaseInfoById(AppFormBase appFormBase) {
        AppFormBaseVO appFormBaseVO = new AppFormBaseVO();
        if(ObjectUtil.isEmpty(appFormBaseVO)){
            return appFormBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(appFormBase, appFormBaseVO);
        return appFormBaseVO;
    }


}