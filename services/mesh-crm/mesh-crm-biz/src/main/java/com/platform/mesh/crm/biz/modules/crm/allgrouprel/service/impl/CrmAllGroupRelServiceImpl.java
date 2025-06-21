package com.platform.mesh.crm.biz.modules.crm.allgrouprel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.dto.CrmAllGroupRelDTO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.vo.CrmAllGroupRelVO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.exception.CrmAllGroupRelExceptionEnum;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.mapper.CrmAllGroupRelMapper;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.service.ICrmAllGroupRelService;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.service.manual.CrmAllGroupRelServiceManual;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.po.CrmAllGroupRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系分组关联
 * @author 蝉鸣
 */
@Service
public class CrmAllGroupRelServiceImpl extends ServiceImpl<CrmAllGroupRelMapper, CrmAllGroupRel> implements ICrmAllGroupRelService  {

    @Autowired
    private CrmAllGroupRelServiceManual crmAllGroupRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前客户关系分组关联信息〉
     * @param allGroupRelId allGroupRelId  
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId) {
        CrmAllGroupRel crmAllGroupRel = this.getById(allGroupRelId);
        return crmAllGroupRelServiceManual.getAllGroupRelInfoById(crmAllGroupRel);
    }

    /**
     * 功能描述:
     * 〈新增客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupRelVO addAllGroupRel(CrmAllGroupRelDTO allGroupRelDTO) {
        CrmAllGroupRel crmAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, CrmAllGroupRel.class);
        this.save(crmAllGroupRel);
        return BeanUtil.copyProperties(crmAllGroupRel, CrmAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupRelVO editAllGroupRel(CrmAllGroupRelDTO allGroupRelDTO) {
        if(ObjectUtil.isEmpty(allGroupRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CrmAllGroupRelDTO::getId);
            throw CrmAllGroupRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CrmAllGroupRel crmAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, CrmAllGroupRel.class);
        this.updateById(crmAllGroupRel);
        return BeanUtil.copyProperties(crmAllGroupRel, CrmAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客户关系分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroupRel(Long allGroupRelId) {
        
        return this.removeById(allGroupRelId);
    }
}