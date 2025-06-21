package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.vo.AppFormColumnSetActionVO;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段动作
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetActionServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param AppFormColumnSetAction AppFormColumnSetAction 
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    public AppFormColumnSetActionVO getFormColumnSetActionInfoById(AppFormColumnSetAction AppFormColumnSetAction) {
        AppFormColumnSetActionVO AppFormColumnSetActionVO = new AppFormColumnSetActionVO();
        if(ObjectUtil.isEmpty(AppFormColumnSetActionVO)){
            return AppFormColumnSetActionVO;
        }
        //转换VO
        BeanUtil.copyProperties(AppFormColumnSetAction, AppFormColumnSetActionVO);
        return AppFormColumnSetActionVO;
    }

}