package com.platform.mesh.upms.biz.modules.conf.sysset.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.vo.ConfSysSetVO;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 配置系统
 * @author 蝉鸣
 */
@Service
public class ConfSysSetServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前配置系统信息〉
     * @param confSysSet confSysSet 
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    public ConfSysSetVO getSysSetInfoById(ConfSysSet confSysSet) {
        ConfSysSetVO confSysSetVO = new ConfSysSetVO();
        if(ObjectUtil.isEmpty(confSysSetVO)){
            return confSysSetVO;
        }
        //转换VO
        BeanUtil.copyProperties(confSysSet, confSysSetVO);
        return confSysSetVO;
    }

}