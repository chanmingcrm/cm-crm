package com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.dto.BpmAllGroupRelDTO;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.po.BpmAllGroupRel;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.vo.BpmAllGroupRelVO;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.exception.BpmAllGroupRelExceptionEnum;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.mapper.BpmAllGroupRelMapper;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.IBpmAllGroupRelService;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.service.manual.BpmAllGroupRelServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块分组关联
 * @author 蝉鸣
 */
@Service
public class BpmAllGroupRelServiceImpl extends ServiceImpl<BpmAllGroupRelMapper, BpmAllGroupRel> implements IBpmAllGroupRelService {

    @Autowired
    private BpmAllGroupRelServiceManual appAllGroupRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupRelId allGroupRelId  
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId) {
        BpmAllGroupRel appAllGroupRel = this.getById(allGroupRelId);
        return appAllGroupRelServiceManual.getAllGroupRelInfoById(appAllGroupRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupRelVO addAllGroupRel(BpmAllGroupRelDTO allGroupRelDTO) {
        BpmAllGroupRel appAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, BpmAllGroupRel.class);
        this.save(appAllGroupRel);
        return BeanUtil.copyProperties(appAllGroupRel, BpmAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupRelVO editAllGroupRel(BpmAllGroupRelDTO allGroupRelDTO) {
        if(ObjectUtil.isEmpty(allGroupRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(BpmAllGroupRelDTO::getId);
            throw BpmAllGroupRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        BpmAllGroupRel appAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, BpmAllGroupRel.class);
        this.updateById(appAllGroupRel);
        return BeanUtil.copyProperties(appAllGroupRel, BpmAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroupRel(Long allGroupRelId) {
        
        return this.removeById(allGroupRelId);
    }
}
