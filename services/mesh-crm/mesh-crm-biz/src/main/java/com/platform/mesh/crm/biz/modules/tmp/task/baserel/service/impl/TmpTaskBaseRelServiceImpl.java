package com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.po.TmpTaskBaseRel;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.vo.TmpTaskBaseRelVO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.mapper.TmpTaskBaseRelMapper;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.ITmpTaskBaseRelService;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.manual.TmpTaskBaseRelServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务数据关联
 * @author 蝉鸣
 */
@Service
public class TmpTaskBaseRelServiceImpl extends ServiceImpl<TmpTaskBaseRelMapper, TmpTaskBaseRel> implements ITmpTaskBaseRelService {

    @Autowired
    private TmpTaskBaseRelServiceManual tmpTaskBaseRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param dataRelId dataRelId  
     * @return 正常返回:{@link TmpTaskBaseRelVO}
     * @author 蝉鸣
     */
    @Override
    public TmpTaskBaseRelVO getDataRelInfoById(Long dataRelId) {
        TmpTaskBaseRel tmpTaskBaseRel = this.getById(dataRelId);
        return tmpTaskBaseRelServiceManual.getDataRelInfoById(tmpTaskBaseRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TmpTaskBaseRelVO}
     * @author 蝉鸣
     */
    @Override
    public TmpTaskBaseRelVO addDataRel(TmpTaskBaseRelDTO dataRelDTO) {
        TmpTaskBaseRel tmpTaskBaseRel = BeanUtil.copyProperties(dataRelDTO, TmpTaskBaseRel.class);
        this.save(tmpTaskBaseRel);
        return BeanUtil.copyProperties(tmpTaskBaseRel, TmpTaskBaseRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDataRel(Long dataRelId) {
        
        return this.removeById(dataRelId);
    }
}
