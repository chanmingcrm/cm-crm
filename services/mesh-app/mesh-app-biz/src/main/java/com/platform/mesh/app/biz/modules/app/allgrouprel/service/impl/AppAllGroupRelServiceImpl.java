package com.platform.mesh.app.biz.modules.app.allgrouprel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.dto.AppAllGroupRelDTO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.vo.AppAllGroupRelVO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.exception.AppAllGroupRelExceptionEnum;
import com.platform.mesh.app.biz.modules.app.allgrouprel.mapper.AppAllGroupRelMapper;
import com.platform.mesh.app.biz.modules.app.allgrouprel.service.IAppAllGroupRelService;
import com.platform.mesh.app.biz.modules.app.allgrouprel.service.manual.AppAllGroupRelServiceManual;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.po.AppAllGroupRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块分组关联
 * @author 蝉鸣
 */
@Service
public class AppAllGroupRelServiceImpl extends ServiceImpl<AppAllGroupRelMapper, AppAllGroupRel> implements IAppAllGroupRelService  {

    @Autowired
    private AppAllGroupRelServiceManual appAllGroupRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupRelId allGroupRelId  
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId) {
        AppAllGroupRel appAllGroupRel = this.getById(allGroupRelId);
        return appAllGroupRelServiceManual.getAllGroupRelInfoById(appAllGroupRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupRelVO addAllGroupRel(AppAllGroupRelDTO allGroupRelDTO) {
        AppAllGroupRel appAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, AppAllGroupRel.class);
        this.save(appAllGroupRel);
        return BeanUtil.copyProperties(appAllGroupRel, AppAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public AppAllGroupRelVO editAllGroupRel(AppAllGroupRelDTO allGroupRelDTO) {
        if(ObjectUtil.isEmpty(allGroupRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppAllGroupRelDTO::getId);
            throw AppAllGroupRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppAllGroupRel appAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, AppAllGroupRel.class);
        this.updateById(appAllGroupRel);
        return BeanUtil.copyProperties(appAllGroupRel, AppAllGroupRelVO.class);
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
