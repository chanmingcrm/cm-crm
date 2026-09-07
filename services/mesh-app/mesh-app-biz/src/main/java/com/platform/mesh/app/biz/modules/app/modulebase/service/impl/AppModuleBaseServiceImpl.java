package com.platform.mesh.app.biz.modules.app.modulebase.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.constant.AppConst;
import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.modulebase.constant.ModuleConst;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBasePageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleFastPageVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelDictVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelVO;
import com.platform.mesh.app.biz.modules.app.modulebase.enums.CopyTypeEnum;
import com.platform.mesh.app.biz.modules.app.modulebase.exception.AppModuleBaseExceptionEnum;
import com.platform.mesh.app.biz.modules.app.modulebase.mapper.AppModuleBaseMapper;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.app.biz.modules.app.modulebase.service.manual.AppModuleBaseServiceManual;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块
 * @author 蝉鸣
 */
@Service
public class AppModuleBaseServiceImpl extends ServiceImpl<AppModuleBaseMapper, AppModuleBase> implements IAppModuleBaseService  {

    @Autowired
    private AppModuleBaseServiceManual appModuleBaseServiceManual;


    /***
     * 功能描述:
     * 〈列表页查询〉
     * @param appModuleBasePageDTO appModuleBasePageReqVO
     * @return 正常返回:{@link MPage<AppModuleBase>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AppModuleBase> selectPage(AppModuleBasePageDTO appModuleBasePageDTO) {
        MPage<AppModuleBase> moduleBaseMPage = MPageUtil.pageEntityToMPage(appModuleBasePageDTO, AppModuleBase.class);
        appModuleBasePageDTO.setDelFlag(YesOrNoEnum.YES.getValue());
        return this.getBaseMapper().selectMPage(moduleBaseMPage,appModuleBasePageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param moduleBaseId moduleBaseId  
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppModuleBaseVO getModuleBaseInfoById(Long moduleBaseId) {
        AppModuleBase appModuleBase = this.getBaseMapper().getModuleBaseInfoById(moduleBaseId);
        return BeanUtil.copyProperties(appModuleBase, AppModuleBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseIds moduleBaseIds
     * @return 正常返回:{@link List<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleBaseVO> getModuleBaseInfoByIds(List<Long> moduleBaseIds) {
        List<AppModuleBase> moduleBases = this.listByIds(moduleBaseIds);
        return BeanUtil.copyToList(moduleBases, AppModuleBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈根据表单名称获取模块信息〉
     * @param appTables appTables
     * @return 正常返回:{@link List<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleBase> getModuleBaseInfoBySchema(List<String> appTables) {
        if(CollUtil.isEmpty(appTables)){
            return CollUtil.newArrayList();
        }
        //开启取消数据隔离设定
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        //根据表名获取模块信息
        List<AppModuleBase> appModuleBases = this.lambdaQuery()
                .eq(AppModuleBase::getModuleType,ModuleTypeEnum.CATEGORY.getValue())
                .in(AppModuleBase::getModuleSchema,appTables).list();
        //关闭据隔离设定
        DataScopeHandler.unEnableDataScope();
        return appModuleBases;
    }

    /**
     * 功能描述:
     * 〈获取当前模块所有子信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link AppModuleBase}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleBase> getModuleBaseChildById(Long moduleBaseId) {
        if(ObjectUtil.isEmpty(moduleBaseId)){
            return CollUtil.newArrayList();
        }
        String childrenSql = SqlUtil.getCommonChildrenSql(AppModuleBase.class, moduleBaseId);
        //查询子项demo
        return this.lambdaQuery().eq(AppModuleBase::getModuleType,ModuleTypeEnum.CATEGORY.getValue()).apply(childrenSql).list();
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppModuleBaseVO addModuleBase(AppModuleBaseDTO moduleBaseDTO) {
        AppModuleBase appModuleBase = BeanUtil.copyProperties(moduleBaseDTO, AppModuleBase.class);
        appModuleBase.setDelFlag(YesOrNoEnum.YES.getValue());
        this.save(appModuleBase);
        appModuleBaseServiceManual.addOrEditMenu(appModuleBase, OperateTypeEnum.INSERT);
        //初始化表单模板
        Map<Long, Long> formMap = appModuleBaseServiceManual.addInitForm(appModuleBase);
        //初始化对接模板
        Map<Long, AppFormColumnSetRequire> copyRequire = appModuleBaseServiceManual.addInitColumnSetRequire(appModuleBase);
        //初始化字段模板
        Map<AppFormColumn, AppFormColumn> columnMap = appModuleBaseServiceManual.addInitColumn(appModuleBase, formMap,copyRequire);
        Map<Long, AppFormColumn> columnLongMap = appModuleBaseServiceManual.getColumnLongMap(columnMap);
        //初始化动作
        appModuleBaseServiceManual.addInitColumnSetAction(appModuleBase, columnLongMap);
        //初始化事件
        appModuleBaseServiceManual.addInitColumnSetEvent(appModuleBase, columnMap, copyRequire);
        return BeanUtil.copyProperties(appModuleBase, AppModuleBaseVO.class);
    }


    /**
     * 功能描述:
     * 〈修改〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppModuleBaseVO editModuleBase(AppModuleBaseDTO moduleBaseDTO) {
        AppModuleBase appModuleBase = getById(moduleBaseDTO.getId());
        if(ObjectUtil.isEmpty(appModuleBase)){
            throw AppModuleBaseExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        BeanUtil.copyProperties(moduleBaseDTO, appModuleBase);
        // 其他情况都执行更新操作
        this.updateById(appModuleBase);
        appModuleBaseServiceManual.addOrEditMenu(appModuleBase, OperateTypeEnum.UPDATE);
        return BeanUtil.copyProperties(appModuleBase, AppModuleBaseVO.class);
    }


    /**
     * 功能描述:
     * 〈删除〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteModuleBase(Long moduleBaseId) {
         AppModuleBase appModuleBase = this.getById(moduleBaseId);
         if(ObjectUtil.isEmpty(appModuleBase)){
             return Boolean.FALSE;
         }
        List<Long> allModuleIds = CollUtil.newArrayList(moduleBaseId);
        //查询子项
        String childrenSql = SqlUtil.getCommonChildrenSql(AppModuleBase.class, moduleBaseId);
        List<AppModuleBase> childList = this.lambdaQuery().apply(childrenSql).list();
        if(CollUtil.isNotEmpty(childList)){
            //删除当前模块
            List<Long> moduleIds = childList.stream().map(AppModuleBase::getId).distinct().toList();
            allModuleIds.addAll(moduleIds);
        }
        //删除其他模块信息
        appModuleBaseServiceManual.deleteModuleBase(allModuleIds);
        //删除模块信息
        this.lambdaUpdate().set(AppModuleBase::getDelFlag,YesOrNoEnum.NO.getValue()).in(AppModuleBase::getId,allModuleIds).update();
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean clearModuleBase(Long moduleBaseId) {
         AppModuleBase appModuleBase = this.getById(moduleBaseId);
         if(ObjectUtil.isEmpty(appModuleBase)){
             return Boolean.FALSE;
         }
        List<Long> allModuleIds = CollUtil.newArrayList(moduleBaseId);
        //查询子项
        String childrenSql = SqlUtil.getCommonChildrenSql(AppModuleBase.class, moduleBaseId);
        List<AppModuleBase> childList = this.lambdaQuery().apply(childrenSql).list();
        if(CollUtil.isNotEmpty(childList)){
            //删除当前模块
            List<Long> moduleIds = childList.stream().map(AppModuleBase::getId).distinct().toList();
            allModuleIds.addAll(moduleIds);
        }
        //删除其他关联信息
        appModuleBaseServiceManual.clearModuleBase(allModuleIds);
        //删除模块信息
        this.removeBatchByIds(allModuleIds);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈发布模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean publicModuleBase(Long moduleBaseId) {
        AppModuleBase moduleBase = this.getById(moduleBaseId);
        if(ObjectUtil.isEmpty(moduleBase)){
            return false;
        }
        //校验存储是否存在
        //设置模块版本号
        moduleBase.setModuleVersion("1");
        updateById(moduleBase);
        return appModuleBaseServiceManual.initModuleBaseToES(moduleBase.getModuleIndex(),CollUtil.newArrayList(moduleBase.getParentId()));
    }

    /**
     * 功能描述:
     * 〈初始化模块ES〉
     * @param tableSchema tableSchema
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleBase> initModuleBaseEs(String tableSchema) {
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        List<AppModuleBase> esModuleBases = this.lambdaQuery()
                .isNotNull(AppModuleBase::getModuleIndex)
                .eq(AppModuleBase::getModuleSchema,tableSchema)
                .eq(AppModuleBase::getModuleType,ModuleTypeEnum.CATEGORY.getValue())
                .eq(AppModuleBase::getInitEsFlag,YesOrNoEnum.YES.getValue())
                .list();
        //取消数据权限隔离
        DataScopeHandler.unEnableDataScope();
        if(CollUtil.isEmpty(esModuleBases)){
            return CollUtil.newArrayList();
        }
        //初始化索引信息
        return appModuleBaseServiceManual.initEs(esModuleBases);
    }

    /**
     * 功能描述:
     * 〈拷贝模块〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean copyModuleBase(AppModuleBaseCopyDTO copyDTO) {
        RLock rLock = RedissonUtil.getLock(AppConst.COPY_APP_MODULE_LOCK.concat(copyDTO.getSourceId().toString()));
        try {
        //加锁
        rLock.lock();
        //获取当前模块信息
        AppModuleBase source = getById(copyDTO.getSourceId());
        if(ObjectUtil.isEmpty(source)){
            return Boolean.FALSE;
        }
        AppModuleBase target;
        //如果是父ID进行复制则需要新建一个子模块
        if(CopyTypeEnum.SELF.getValue().equals(copyDTO.getCopyType())){
            target = getById(copyDTO.getTargetId());
            //是否强制覆盖:如果强制覆盖则先清除旧数据信息
            if(YesOrNoEnum.YES.getValue().equals(copyDTO.getForceOver())){
                this.clearModuleBase(target.getId());
            }
            target.setAppId(copyDTO.getAppId());
            target.setModuleDesc(source.getModuleDesc().concat(ModuleConst.COPY_CN_SUFFIX));
        }else {
            target = new AppModuleBase();
            target.setId(IdUtil.getSnowflake().nextId());
            target.setParentId(copyDTO.getTargetId());
            target.setModuleType(source.getModuleType());
            target.setAppId(copyDTO.getAppId());
            target.setModuleMac(source.getModuleMac().concat(ModuleConst.COPY_EN_SUFFIX));
            target.setModuleName(source.getModuleName().concat(ModuleConst.COPY_CN_SUFFIX));
            target.setModuleDesc(source.getModuleDesc().concat(ModuleConst.COPY_CN_SUFFIX));
            copyDTO.setCopyType(CopyTypeEnum.SELF.getValue());
            target.setDelFlag(YesOrNoEnum.YES.getValue());
        }
        if(ObjectUtil.isEmpty(target)){
            return Boolean.FALSE;
        }
        this.saveOrUpdate(target);
        //复制其他逻辑
        appModuleBaseServiceManual.copyModuleBase(source.getId(), target);
        //查询子项
        List<AppModuleBase> sourceChildList = this.lambdaQuery().eq(AppModuleBase::getParentId,copyDTO.getSourceId()).list();
        if(CollUtil.isEmpty(sourceChildList)){
            return Boolean.TRUE;
        }
        //进行子模块复制
        for (AppModuleBase childModule : sourceChildList) {
            AppModuleBase targetChild = new AppModuleBase();
            BeanUtil.copyProperties(childModule, targetChild, ObjFieldUtil.ignoreDefault());
            targetChild.setAppId(copyDTO.getAppId());
            targetChild.setParentId(target.getId());
            this.save(targetChild);
            //添加菜单
            appModuleBaseServiceManual.addOrEditMenu(targetChild, OperateTypeEnum.INSERT);
            //递归执行
            copyDTO.setSourceId(childModule.getId());
            copyDTO.setTargetId(targetChild.getId());
            //递归执行
            this.copyModuleBase(copyDTO);
        }

        }catch (Exception ignored){

        }finally {
            //释放锁
            rLock.unlock();
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈获取模块默认列表配置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleFastPageVO}
     * @author 蝉鸣
     */
    @Override
    public AppModuleFastPageVO fastPageFormBase(Long moduleId) {
        AppModuleFastPageVO fastPageVO = new AppModuleFastPageVO();
        //获取模块信息
        AppModuleBase moduleBase = this.getById(moduleId);
        if(ObjectUtil.isEmpty(moduleBase)){
            return fastPageVO;
        }
//        List<AppModuleBase> moduleBases = this.lambdaQuery().eq(AppModuleBase::getParentId, moduleBase.getId()).list();
//        if(CollUtil.isEmpty(moduleBases)){
//            return fastPageVO;
//        }
//        AppModuleBase appModuleBase = CollUtil.getFirst(moduleBases);
        fastPageVO.setModuleBaseVO(BeanUtil.copyProperties(moduleBase, AppModuleBaseVO.class));
        //获取页面组件
        List<AppFormColumnVO> pageForm = appModuleBaseServiceManual.getFastPageFormColumn(moduleBase.getId());
        fastPageVO.setPageForm(pageForm);
        //获取关联组件
        Map<Long,List<AppFormColumnVO>> relForm = appModuleBaseServiceManual.getFastPageRelFormColumn(moduleBase.getId(),pageForm);
        fastPageVO.setRelForm(relForm);
        return fastPageVO;
    }

    /**
     * 功能描述:
     * 〈获取模块关联字典分页〉
     * @param appModuleRelPageDTO appModuleRelPageDTO
     * @return 正常返回:{@link MPage<DictBaseBO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AppModuleRelDictVO> selectRelDictPage(AppModuleRelPageDTO appModuleRelPageDTO) {
        //分页查询关联字典
        MPage<AppModuleBase> moduleBaseMPage = MPageUtil.pageEntityToMPage(appModuleRelPageDTO, AppModuleBase.class);
        List<String> dictComps = CollUtil.newArrayList(CompTypeEnum.SELECT.getDesc(), CompTypeEnum.RADIO.getDesc(), CompTypeEnum.CHECKBOX.getDesc());
        MPage<AppModuleRelDictVO> dictPage = this.getBaseMapper().selectRelDictPage(moduleBaseMPage,appModuleRelPageDTO,dictComps);
        if(CollUtil.isEmpty(dictPage.getRecords())){
            return new MPage<>();
        }
        List<Long> dictIds = dictPage.getRecords().stream().filter(ObjectUtil::isNotEmpty)
                .map(AppModuleRelDictVO::getDictId)
                .toList();
        List<DictBaseBO> dictBaseBOList = appModuleBaseServiceManual.getRelDictPage(dictIds);
        Map<Long, String> dictMap;
        if(CollUtil.isEmpty(dictBaseBOList)){
            dictMap = new HashMap<>();
        }else{
            dictMap = dictBaseBOList.stream().filter(ObjectUtil::isNotEmpty).collect(Collectors.toMap(DictBaseBO::getId,DictBaseBO::getDictName));
        }
        Map<Long, String> finalDictMap = dictMap;
        List<AppModuleRelDictVO> list = dictPage.getRecords().stream().peek(dictVO -> {
            Long dictId = dictVO.getDictId();
            dictVO.setDictId(dictId);
            if (ObjectUtil.isNotEmpty(finalDictMap) && finalDictMap.containsKey(dictId)) {
                dictVO.setDictName(finalDictMap.get(dictId));
            }
        }).toList();
        MPage<AppModuleRelDictVO> mPage = MPageUtil.convertToPage(dictPage);
        mPage.setRecords(list);
        return mPage;
    }

    /**
     * 功能描述:
     * 〈查询当前模块关联的模块信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link List<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleRelVO> selectRelModuleList(AppModuleRelPageDTO pageDTO) {
        return this.getBaseMapper().selectRelModuleList(pageDTO);
    }
}
