package com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.po.TmpTaskBaseRel;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.vo.TmpTaskBaseRelVO;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务数据关联
 * @author 蝉鸣
 */
@Service
public class TmpTaskBaseRelServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param tmpTaskBaseRel taskDataRel
     * @return 正常返回:{@link TmpTaskBaseRelVO}
     * @author 蝉鸣
     */
    public TmpTaskBaseRelVO getDataRelInfoById(TmpTaskBaseRel tmpTaskBaseRel) {
        TmpTaskBaseRelVO tmpTaskBaseRelVO = new TmpTaskBaseRelVO();
        if(ObjectUtil.isEmpty(tmpTaskBaseRelVO)){
            return tmpTaskBaseRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(tmpTaskBaseRel, tmpTaskBaseRelVO);
        return tmpTaskBaseRelVO;
    }

}