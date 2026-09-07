package com.platform.mesh.ai.biz.modules.cc.userworkrel.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import com.platform.mesh.ai.biz.modules.cc.setwork.enums.WorkRuleEnum;
import com.platform.mesh.ai.biz.modules.cc.setwork.service.ICcSetWorkService;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.user.service.ICcUserService;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.vo.CcUserWorkRelVO;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服人员
 * @author 蝉鸣
 */
@Service
public class CcUserWorkRelServiceManual {

    @Autowired
    private ICcUserService ccUserService;

    @Autowired
    private ICcSetWorkService ccSetWorkService;

    /**
     * 功能描述:
     * 〈获取当前排班〉
     * @return 正常返回:{@link List<CcSetWork>}
     * @author 蝉鸣
     */
    public List<CcSetWork> getCurrentWork(){
        //获取当前时间段的排班
        return ccSetWorkService.getCurrentSetWork(LocalTime.now());
    }

}
