package com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段事件
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetEventServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param AppFormColumnSetEvent AppFormColumnSetEvent 
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    public AppFormColumnSetEventVO getFormColumnSetEventInfoById(AppFormColumnSetEvent AppFormColumnSetEvent) {
        AppFormColumnSetEventVO AppFormColumnSetEventVO = new AppFormColumnSetEventVO();
        if(ObjectUtil.isEmpty(AppFormColumnSetEventVO)){
            return AppFormColumnSetEventVO;
        }
        //转换VO
        BeanUtil.copyProperties(AppFormColumnSetEvent, AppFormColumnSetEventVO);
        return AppFormColumnSetEventVO;
    }

}