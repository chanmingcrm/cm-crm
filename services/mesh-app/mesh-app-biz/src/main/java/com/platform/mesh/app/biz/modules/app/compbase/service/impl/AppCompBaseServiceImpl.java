package com.platform.mesh.app.biz.modules.app.compbase.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBaseDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBasePageDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.vo.AppCompBaseVO;
import com.platform.mesh.app.biz.modules.app.compbase.exception.AppCompBaseExceptionEnum;
import com.platform.mesh.app.biz.modules.app.compbase.mapper.AppCompBaseMapper;
import com.platform.mesh.app.biz.modules.app.compbase.service.IAppCompBaseService;
import com.platform.mesh.app.biz.modules.app.compbase.service.manual.AppCompBaseServiceManual;
import com.platform.mesh.app.biz.modules.app.compbase.domain.po.AppCompBase;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.query.LambdaQueryWrapperX;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 页面组件
 * @author 蝉鸣
 */
@Service
public class AppCompBaseServiceImpl extends ServiceImpl<AppCompBaseMapper, AppCompBase> implements IAppCompBaseService  {

    @Autowired
    private AppCompBaseServiceManual appCompBaseServiceManual;


    /**
     * 功能描述:
     * 〈获取页面组件列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AppCompBase>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AppCompBase> selectPage(AppCompBasePageDTO pageDTO) {
        MPage<AppCompBase> compBaseMPage = MPageUtil.pageEntityToMPage(pageDTO, AppCompBase.class);
        LambdaQueryWrapperX<AppCompBase> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(AppCompBase::getColumnFlag,pageDTO.getColumnFlag());
        return this.page(compBaseMPage,queryWrapperX);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param compBaseId compBaseId  
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppCompBaseVO getCompBaseInfoById(Long compBaseId) {
        AppCompBase appCompBase = this.getById(compBaseId);
        return appCompBaseServiceManual.getCompBaseInfoById(appCompBase);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppCompBaseVO addCompBase(AppCompBaseDTO compBaseDTO) {
        AppCompBase appCompBase = BeanUtil.copyProperties(compBaseDTO, AppCompBase.class);
        if(ObjectUtil.isNotEmpty(appCompBase.getEsInit()) && YesOrNoEnum.YES.getValue().equals(appCompBase.getEsInit())){
            if(ObjectUtil.isEmpty(appCompBase.getEsKind())){
                appCompBase.setEsKind(Property.Kind.Keyword.name());
            }
        }
        //转换下划线
        appCompBase.setCompMac(StrUtil.toUnderlineCase(appCompBase.getCompMac()));
        this.save(appCompBase);
        return BeanUtil.copyProperties(appCompBase, AppCompBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppCompBaseVO editCompBase(AppCompBaseDTO compBaseDTO) {
        if(ObjectUtil.isEmpty(compBaseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppCompBaseDTO::getId);
            throw AppCompBaseExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppCompBase appCompBase = BeanUtil.copyProperties(compBaseDTO, AppCompBase.class);
        //转换下划线
        appCompBase.setCompMac(StrUtil.toUnderlineCase(appCompBase.getCompMac()));
        this.updateById(appCompBase);
        return BeanUtil.copyProperties(appCompBase, AppCompBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param compBaseId compBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCompBase(Long compBaseId) {
        
        return this.removeById(compBaseId);
    }
}
