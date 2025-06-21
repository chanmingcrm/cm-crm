package com.platform.mesh.upms.biz.modules.conf.syssafe.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.vo.ConfSysSafeVO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.po.ConfSysSafe;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 配置安全性
 * @author 蝉鸣
 */
@Service
public class ConfSysSafeServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前配置安全性信息〉
     * @param confSysSafe confSysSafe 
     * @return 正常返回:{@link ConfSysSafeVO}
     * @author 蝉鸣
     */
    public ConfSysSafeVO getSysSafeInfoById(ConfSysSafe confSysSafe) {
        ConfSysSafeVO confSysSafeVO = new ConfSysSafeVO();
        if(ObjectUtil.isEmpty(confSysSafeVO)){
            return confSysSafeVO;
        }
        //转换VO
        BeanUtil.copyProperties(confSysSafe, confSysSafeVO);
        return confSysSafeVO;
    }

}