package com.platform.mesh.app.biz.modules.app.allgroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.dto.AppAllGroupDTO;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.vo.AppAllGroupVO;
import com.platform.mesh.app.biz.modules.app.allgroup.exception.AppAllGroupExceptionEnum;
import com.platform.mesh.app.biz.modules.app.allgroup.mapper.AppAllGroupMapper;
import com.platform.mesh.app.biz.modules.app.allgroup.service.IAppAllGroupService;
import com.platform.mesh.app.biz.modules.app.allgroup.service.manual.AppAllGroupServiceManual;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.po.AppAllGroup;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块分组
 * @author 蝉鸣
 */
@Service
public class AppAllGroupServiceImpl extends ServiceImpl<AppAllGroupMapper, AppAllGroup> implements IAppAllGroupService  {

    @Autowired
    private AppAllGroupServiceManual appAllGroupServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupId allGroupId  
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupVO getAllGroupInfoById(Long allGroupId) {
        AppAllGroup appAllGroup = this.getById(allGroupId);
        return appAllGroupServiceManual.getAllGroupInfoById(appAllGroup);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupVO addAllGroup(AppAllGroupDTO allGroupDTO) {
        AppAllGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, AppAllGroup.class);
        this.save(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, AppAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupVO editAllGroup(AppAllGroupDTO allGroupDTO) {
        if(ObjectUtil.isEmpty(allGroupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppAllGroupDTO::getId);
            throw AppAllGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppAllGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, AppAllGroup.class);
        this.updateById(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, AppAllGroupVO.class);
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
