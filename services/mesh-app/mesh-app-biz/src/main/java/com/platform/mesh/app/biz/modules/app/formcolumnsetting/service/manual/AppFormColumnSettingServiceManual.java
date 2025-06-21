package com.platform.mesh.app.biz.modules.app.formcolumnsetting.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.po.AppFormColumnSetting;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段配置
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSettingServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param AppFormColumnSetting AppFormColumnSetting 
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    public AppFormColumnSettingVO getFormColumnSettingInfoById(AppFormColumnSetting AppFormColumnSetting) {
        AppFormColumnSettingVO AppFormColumnSettingVO = new AppFormColumnSettingVO();
        if(ObjectUtil.isEmpty(AppFormColumnSettingVO)){
            return AppFormColumnSettingVO;
        }
        //转换VO
        BeanUtil.copyProperties(AppFormColumnSetting, AppFormColumnSettingVO);
        return AppFormColumnSettingVO;
    }

}