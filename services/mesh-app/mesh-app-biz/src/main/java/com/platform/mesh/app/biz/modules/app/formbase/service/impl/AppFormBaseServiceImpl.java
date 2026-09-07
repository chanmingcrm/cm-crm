package com.platform.mesh.app.biz.modules.app.formbase.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBaseDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBasePageDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.app.biz.modules.app.formbase.exception.AppFormBaseExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formbase.mapper.AppFormBaseMapper;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
import com.platform.mesh.app.biz.modules.app.formbase.service.manual.AppFormBaseServiceManual;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单
 * @author 蝉鸣
 */
@Service
public class AppFormBaseServiceImpl extends ServiceImpl<AppFormBaseMapper, AppFormBase> implements IAppFormBaseService  {

    @Autowired
    private AppFormBaseServiceManual appFormBaseServiceManual;


    /***
     * 功能描述:
     * 〈获取表单分页〉
     * @param appFormBasePageDTO appFormBasePageDTO
     * @return 正常返回:{@link MPage<AppFormBase>}
     * @author 蝉鸣
     * @since 2024/8/29 17:12
     */
    @Override
    public MPage<AppFormBase> selectPage(AppFormBasePageDTO appFormBasePageDTO) {
        MPage<AppFormBase> appFormBaseMPage = MPageUtil.pageEntityToMPage(appFormBasePageDTO,AppFormBase.class);
        return this.getBaseMapper().selectMPage(appFormBaseMPage,appFormBasePageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param formBaseId formBaseId  
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormBaseVO getFormBaseInfoById(Long formBaseId) {
        AppFormBase appFormBase = this.getById(formBaseId);
        return appFormBaseServiceManual.getFormBaseInfoById(appFormBase);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormBaseVO addFormBase(AppFormBaseDTO formBaseDTO) {
        AppFormBase appFormBase = BeanUtil.copyProperties(formBaseDTO, AppFormBase.class);
        //校验默认数据
        if(YesOrNoEnum.YES.getValue().equals(formBaseDTO.getDefaultFlag())){
            //设置相同类型其他数据关闭默认
            this.lambdaUpdate().set(AppFormBase::getDefaultFlag,YesOrNoEnum.NO.getValue())
                    .eq(AppFormBase::getModuleId,formBaseDTO.getModuleId())
                    .eq(AppFormBase::getFormType,formBaseDTO.getFormType())
                    .update();
        }else{
            //查询数据库是否存在相同类型
            boolean exists = this.lambdaQuery()
                    .eq(AppFormBase::getModuleId, formBaseDTO.getModuleId())
                    .eq(AppFormBase::getFormType, formBaseDTO.getFormType())
                    .eq(AppFormBase::getDefaultFlag, YesOrNoEnum.YES.getValue())
                    .exists();
            //如果不存在相同类型则设置当前数据为默认值
            if(!exists){
                appFormBase.setDefaultFlag(YesOrNoEnum.YES.getValue());
            }
        }
        this.save(appFormBase);
        return BeanUtil.copyProperties(appFormBase, AppFormBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormBaseVO editFormBase(AppFormBaseDTO formBaseDTO) {
        if(ObjectUtil.isEmpty(formBaseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppFormBaseDTO::getId);
            throw AppFormBaseExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppFormBase appFormBase = BeanUtil.copyProperties(formBaseDTO, AppFormBase.class);
        //校验默认数据
        if(YesOrNoEnum.YES.getValue().equals(formBaseDTO.getDefaultFlag())){
            //设置相同类型其他数据关闭默认
            this.lambdaUpdate().set(AppFormBase::getDefaultFlag,YesOrNoEnum.NO.getValue())
                    .eq(AppFormBase::getModuleId,formBaseDTO.getModuleId())
                    .eq(AppFormBase::getFormType,formBaseDTO.getFormType())
                    .update();
        }else{
            //查询数据库是否存在相同类型
            List<AppFormBase> list = this.lambdaQuery()
                    .eq(AppFormBase::getModuleId, formBaseDTO.getModuleId())
                    .eq(AppFormBase::getFormType, formBaseDTO.getFormType())
                    .orderByAsc(AppFormBase::getCreateTime)
                    .list();
            //如果不存在相同类型则设置当前数据为默认值
            List<AppFormBase> defaultList = list.stream().filter(base -> base.getDefaultFlag().equals(YesOrNoEnum.YES.getValue())).toList();
            if(CollUtil.isEmpty(defaultList)){
                //设置第一条数据为默认值
                AppFormBase first = CollUtil.getFirst(list);
                first.setDefaultFlag(YesOrNoEnum.YES.getValue());
                this.updateById(first);
            }
        }
        this.updateById(appFormBase);
        return BeanUtil.copyProperties(appFormBase, AppFormBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formBaseId formBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormBase(Long formBaseId) {
        AppFormBase appFormBase = getById(formBaseId);
        this.removeById(appFormBase);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈复制表单〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, Long> copyFormBase(Long sourceModuleId, Long targetModuleId) {
        Map<Long, Long> copyMap = new HashMap<>();
        //查询源模块下的表单
        List<AppFormBase> sourceForms = this.lambdaQuery().eq(AppFormBase::getModuleId, sourceModuleId).list();
        if(CollUtil.isEmpty(sourceForms)){
            return copyMap;
        }
        List<AppFormBase> targetForms = sourceForms.stream().map(sourceForm -> {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormBase targetForm = new AppFormBase();
            BeanUtil.copyProperties(sourceForm, targetForm, ObjFieldUtil.ignoreDefault());
            targetForm.setId(id);
            targetForm.setModuleId(targetModuleId);
            copyMap.put(sourceForm.getId(), id);
            return targetForm;
        }).toList();
        //批量保存
        this.saveBatch(targetForms);
        return copyMap;
    }

    /**
     * 功能描述:
     * 〈获取当前默认类型表单信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormBaseVO getFormBaseDefaultByModuleId(Long moduleId, Integer formType) {
        AppFormBaseVO appFormBaseVO = new AppFormBaseVO();
        AppFormBase one = this.lambdaQuery()
                .eq(AppFormBase::getModuleId, moduleId)
                .eq(AppFormBase::getFormType, formType)
                .eq(AppFormBase::getDefaultFlag, YesOrNoEnum.YES.getValue()).one();
        if(ObjectUtil.isNotEmpty(one)){
            appFormBaseVO = BeanUtil.copyProperties(one, AppFormBaseVO.class);
        }else{
            //如果没有默认类型查询第一条数据
            List<AppFormBase> formBases = this.lambdaQuery()
                    .eq(AppFormBase::getModuleId, moduleId)
                    .eq(AppFormBase::getFormType, formType)
                    .orderByAsc(AppFormBase::getCreateTime)
                    .list();
            if(CollUtil.isEmpty(formBases)){
                return appFormBaseVO;
            }
            AppFormBase formBase = CollUtil.getFirst(formBases);
            appFormBaseVO = BeanUtil.copyProperties(formBase, AppFormBaseVO.class);
        }
        return appFormBaseVO;
    }

    /**
     * 功能描述:
     * 〈获取当前默认类型表单信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormBase}
     * @author 蝉鸣
     */
    @Override
    public AppFormBase getAppFormBaseByFormType(Long moduleId, Integer formType) {
        
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        List<AppFormBase> appFormBases = this.lambdaQuery()
                .eq(AppFormBase::getModuleId, moduleId)
                .eq(AppFormBase::getFormType, formType)
                .orderByAsc(AppFormBase::getDefaultFlag)
                .list();
        
        DataScopeHandler.unEnableDataScope();
        if(CollUtil.isEmpty(appFormBases)){
            return null;
        }
        return CollUtil.getFirst(appFormBases);
    }
}
