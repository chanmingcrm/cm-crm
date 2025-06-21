package com.platform.mesh.crm.biz.modules.crm.allgroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.dto.CrmAllGroupDTO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.vo.CrmAllGroupVO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.exception.CrmAllGroupExceptionEnum;
import com.platform.mesh.crm.biz.modules.crm.allgroup.mapper.CrmAllGroupMapper;
import com.platform.mesh.crm.biz.modules.crm.allgroup.service.ICrmAllGroupService;
import com.platform.mesh.crm.biz.modules.crm.allgroup.service.manual.CrmAllGroupServiceManual;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.po.CrmAllGroup;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系分组
 * @author 蝉鸣
 */
@Service
public class CrmAllGroupServiceImpl extends ServiceImpl<CrmAllGroupMapper, CrmAllGroup> implements ICrmAllGroupService  {

    @Autowired
    private CrmAllGroupServiceManual crmAllGroupServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前客户关系分组信息〉
     * @param allGroupId allGroupId  
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupVO getAllGroupInfoById(Long allGroupId) {
        CrmAllGroup crmAllGroup = this.getById(allGroupId);
        return crmAllGroupServiceManual.getAllGroupInfoById(crmAllGroup);
    }

    /**
     * 功能描述:
     * 〈新增客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupVO addAllGroup(CrmAllGroupDTO allGroupDTO) {
        CrmAllGroup crmAllGroup = BeanUtil.copyProperties(allGroupDTO, CrmAllGroup.class);
        this.save(crmAllGroup);
        return BeanUtil.copyProperties(crmAllGroup, CrmAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CrmAllGroupVO editAllGroup(CrmAllGroupDTO allGroupDTO) {
        if(ObjectUtil.isEmpty(allGroupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CrmAllGroupDTO::getId);
            throw CrmAllGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CrmAllGroup crmAllGroup = BeanUtil.copyProperties(allGroupDTO, CrmAllGroup.class);
        this.updateById(crmAllGroup);
        return BeanUtil.copyProperties(crmAllGroup, CrmAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客户关系分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroup(Long allGroupId) {
        
        return this.removeById(allGroupId);
    }
}