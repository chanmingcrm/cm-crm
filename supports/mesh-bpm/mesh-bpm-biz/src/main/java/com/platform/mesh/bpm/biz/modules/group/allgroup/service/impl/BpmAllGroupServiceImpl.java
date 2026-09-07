package com.platform.mesh.bpm.biz.modules.group.allgroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.dto.BpmAllGroupDTO;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.po.BpmAllGroup;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.vo.BpmAllGroupVO;
import com.platform.mesh.bpm.biz.modules.group.allgroup.exception.BpmAllGroupExceptionEnum;
import com.platform.mesh.bpm.biz.modules.group.allgroup.mapper.BpmAllGroupMapper;
import com.platform.mesh.bpm.biz.modules.group.allgroup.service.IBpmAllGroupService;
import com.platform.mesh.bpm.biz.modules.group.allgroup.service.manual.BpmAllGroupServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块分组
 * @author 蝉鸣
 */
@Service
public class BpmAllGroupServiceImpl extends ServiceImpl<BpmAllGroupMapper, BpmAllGroup> implements IBpmAllGroupService {

    @Autowired
    private BpmAllGroupServiceManual appAllGroupServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupId allGroupId  
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupVO getAllGroupInfoById(Long allGroupId) {
        BpmAllGroup appAllGroup = this.getById(allGroupId);
        return BeanUtil.toBean(appAllGroup, BpmAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupVO addAllGroup(BpmAllGroupDTO allGroupDTO) {
        BpmAllGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, BpmAllGroup.class);
        this.save(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, BpmAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public BpmAllGroupVO editAllGroup(BpmAllGroupDTO allGroupDTO) {
        if(ObjectUtil.isEmpty(allGroupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(BpmAllGroupDTO::getId);
            throw BpmAllGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        BpmAllGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, BpmAllGroup.class);
        this.updateById(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, BpmAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroup(Long allGroupId) {
        
        return this.removeById(allGroupId);
    }
}
