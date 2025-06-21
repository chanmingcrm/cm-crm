package com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.po.BpmAllGroupRel;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.vo.BpmAllGroupRelVO;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块分组关联
 * @author 蝉鸣
 */
@Service
public class BpmAllGroupRelServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appAllGroupRel appAllGroupRel 
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    public BpmAllGroupRelVO getAllGroupRelInfoById(BpmAllGroupRel appAllGroupRel) {
        BpmAllGroupRelVO appAllGroupRelVO = new BpmAllGroupRelVO();
        if(ObjectUtil.isEmpty(appAllGroupRelVO)){
            return appAllGroupRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(appAllGroupRel, appAllGroupRelVO);
        return appAllGroupRelVO;
    }

}