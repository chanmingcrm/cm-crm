package com.platform.mesh.upms.biz.modules.conf.userui.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.vo.ConfUserUiVO;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.po.ConfUserUi;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 配置UI
 * @author 蝉鸣
 */
@Service
public class ConfUserUiServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前配置UI信息〉
     * @param confUserUi confUserUi 
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    public ConfUserUiVO getUserUiInfoById(ConfUserUi confUserUi) {
        ConfUserUiVO confUserUiVO = new ConfUserUiVO();
        if(ObjectUtil.isEmpty(confUserUiVO)){
            return confUserUiVO;
        }
        //转换VO
        BeanUtil.copyProperties(confUserUi, confUserUiVO);
        return confUserUiVO;
    }

}