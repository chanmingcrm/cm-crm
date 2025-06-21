package com.platform.mesh.upms.biz.modules.conf.userset.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.vo.ConfUserSetVO;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.po.ConfUserSet;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 配置用户
 * @author 蝉鸣
 */
@Service
public class ConfUserSetServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前配置用户信息〉
     * @param confUserSet confUserSet 
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    public ConfUserSetVO getUserSetInfoById(ConfUserSet confUserSet) {
        ConfUserSetVO confUserSetVO = new ConfUserSetVO();
        if(ObjectUtil.isEmpty(confUserSetVO)){
            return confUserSetVO;
        }
        //转换VO
        BeanUtil.copyProperties(confUserSet, confUserSetVO);
        return confUserSetVO;
    }

}