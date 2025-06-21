package com.platform.mesh.upms.biz.modules.org.level.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.query.LambdaQueryWrapperX;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.dto.OrgLevelPageDTO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.domain.vo.OrgLevelVO;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.exception.LevelExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.level.mapper.OrgLevelMapper;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.level.service.manual.OrgLevelServiceManual;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgLevelServiceImpl extends ServiceImpl<OrgLevelMapper, OrgLevel> implements IOrgLevelService {


    @Autowired
    private OrgLevelServiceManual orgLevelServiceManual;


    /**
     * 功能描述:
     * 〈层级分页〉
     * @param orgLevelPageDTO orgLevelPageDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public MPage<OrgLevel> selectPage(OrgLevelPageDTO orgLevelPageDTO) {
        MPage<OrgLevel> mPage = MPageUtil.pageEntityToMPage(orgLevelPageDTO,OrgLevel.class);
        // 构建 LambdaQueryWrapper
        LambdaQueryWrapperX<OrgLevel> queryWrapperX =  new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(OrgLevel::getId,orgLevelPageDTO.getId());
//        queryWrapperX.eqIfPresent(OrgLevel::getLevelFlag,orgLevelPageDTO.getLevelFlag());
//        queryWrapperX.eqIfPresent(OrgLevel::getName,orgLevelPageDTO.getName());
//        queryWrapperX.eqIfPresent(OrgLevel::getParentId,orgLevelPageDTO.getParentId());
        return page(mPage,queryWrapperX);
    }

    /**
     * 功能描述:
     * 〈获取组织树结构〉
     * @param levelId levelId
     * @return 正常返回:{@link SysOrgInfoBO}
     * @author 蝉鸣
     */
    @Override
    public SysOrgInfoBO getOrgInfoByLevelId(Long levelId) {
        if(UserCacheUtil.getExistsCache(CacheConstants.SYS_ORG_DETAILS,levelId)){
            return UserCacheUtil.getSysOrgInfoCache(levelId);
        }
        OrgLevel orgLevel = getById(levelId);
        if(ObjectUtil.isEmpty(orgLevel)){
            return null;
        }
        SysOrgInfoBO orgInfoBO = new SysOrgInfoBO();
        orgInfoBO.setLevelId(levelId);
        orgInfoBO.setLevelName(orgLevel.getLevelName());
        //设置同级
        List<OrgLevel> sameList = this.lambdaQuery().eq(OrgLevel::getParentId,orgLevel.getParentId()).list();
        if(CollUtil.isNotEmpty(sameList)){
            //设置同级
            List<Long> sameIds = sameList.stream().map(OrgLevel::getId).toList();
            orgInfoBO.setSameLevelIds(sameIds);
        }
        String childrenSql = SqlUtil.getCommonChildrenSql(OrgLevel.class, levelId);
        //查询子项
        List<OrgLevel> childList = this.lambdaQuery().apply(childrenSql).list();
        if(CollUtil.isNotEmpty(childList)){
            //设置直接下级
            List<Long> subIds = childList.stream().filter(item -> item.getParentId().equals(levelId)).map(OrgLevel::getId).toList();
            orgInfoBO.setSubLevelIds(subIds);
            //设置所有子级
            List<Long> allIds = childList.stream().map(OrgLevel::getId).toList();
            orgInfoBO.setAllChildrenIds(allIds);
        }
        //增加缓存
        UserCacheUtil.setSysOrgInfoCache(Result.success(orgInfoBO));
        return orgInfoBO;
    }

    /**
     * 功能描述:
     * 〈通过账户ID查询用户角色信息〉
     * @param accountId accountId
     * @return 正常返回:{@link List<OrgLevel>}
     * @author 蝉鸣
     */
    @Override
    public List<SysOrgBO> getOrgInfoByAccountId(Long accountId) {
        //根据账户ID获取用户ID
        SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(accountId);
        if(ObjectUtil.isEmpty(accountBO) || ObjectUtil.isEmpty(accountBO.getUserId())) {
            return CollUtil.newArrayList();
        }
        //查询当前用户下关联的组织
        List<SysOrgBO> sysOrgBOS = orgLevelServiceManual.getOrgInfoByUserId(accountBO.getUserId());
        if(CollUtil.isEmpty(sysOrgBOS)){
            return sysOrgBOS;
        }
        sysOrgBOS.stream().filter(ObjectUtil::isNotEmpty).forEach(sysOrgBO -> {
            //设置数据权限:自身
            if(ObjectUtil.isEmpty(sysOrgBO.getDataScope())) {
                sysOrgBO.setDataScope(DataScopeEnum.SELF.getValue());
            }
            //如果是自定义类型权限
            if(DataScopeEnum.CUSTOM.getValue().equals(sysOrgBO.getDataScope())) {
                //如果是自定义关联类型为空
                if(ObjectUtil.isEmpty(sysOrgBO.getDataFlag())){
                    //设置默认值
                    sysOrgBO.setDataFlag(DataFlagEnum.USER.getValue());
                    sysOrgBO.setDataId(accountBO.getUserId());
                }
            }
            DataScopeHandler.setEnableDataScope(Boolean.FALSE);
            //设置所有子部门
            String childrenSql = SqlUtil.getCommonChildrenSql(OrgLevel.class, sysOrgBO.getLevelId());
            //查询子项
            List<OrgLevel> childList = this.lambdaQuery().apply(childrenSql).list();
            //设置所有子级
            List<Long> allIds = childList.stream().map(OrgLevel::getId).toList();
            sysOrgBO.setLevelIds(allIds);
            DataScopeHandler.unEnableDataScope();
        });
        return sysOrgBOS;
    }

    /**
     * 功能描述:
     * 〈获取组织详情〉
     * @param levelId levelId
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    @Override
    public OrgLevelVO getLevelInfoById(Long levelId) {
        OrgLevel orgLevel = this.getById(levelId);
        return orgLevelServiceManual.getLevelInfoById(orgLevel);
    }

    /**
     * 功能描述:
     * 〈获取组织树结构〉
     * @param levelId levelId
     * @return 正常返回:{@link List<OrgLevelVO>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgLevelVO> getLevelTree(Long levelId) {
        if(ObjectUtil.isEmpty(levelId)){
            levelId = NumberConst.NUM_0.longValue();
        }
        String childrenSql = SqlUtil.getCommonChildrenSql(OrgLevel.class, levelId);
        //查询子项demo
        List<OrgLevel> childList = this.lambdaQuery().apply(childrenSql).list();

        String parentsSql = SqlUtil.getCommonParentSql(OrgLevel.class, levelId);
        //查询父项demo
        List<OrgLevel> parentList = this.lambdaQuery().apply(parentsSql).list();
        List<OrgLevel> orgLevel = this.list();
        return orgLevelServiceManual.getLevelTree(orgLevel);
    }

    /**
     * 功能描述:
     * 〈新增层级〉
     * @param levelDTO levelDTO
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    @Override
    public OrgLevelVO addLevel(OrgLevelDTO levelDTO) {
        OrgLevel orgLevel = BeanUtil.copyProperties(levelDTO, OrgLevel.class);
        //如果RootId为空则查询最近上级为LevelFlagEnum.COMPANY类型层级为rootId
        if(ObjectUtil.isEmpty(orgLevel.getRootId())){
            String parentsSql = SqlUtil.getCommonParentSql(OrgLevel.class, orgLevel.getParentId());
            //查询父项demo
            List<OrgLevel> parentList = this.lambdaQuery().apply(parentsSql).list();
            if(CollUtil.isNotEmpty(parentList)){
                OrgLevel rootLevel = orgLevelServiceManual.getLastRootLevel(parentList,orgLevel);
                if(ObjectUtil.isNotEmpty(rootLevel)){
                    orgLevel.setRootId(rootLevel.getId());
                }else{
                    orgLevel.setRootId(NumberConst.NUM_0.longValue());
                }
            }else{
                orgLevel.setRootId(NumberConst.NUM_0.longValue());
            }
        }
        this.save(orgLevel);
        return BeanUtil.copyProperties(orgLevel, OrgLevelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改层级〉
     * @param menuDTO menuDTO
     * @return 正常返回:{@link OrgLevelVO}
     * @author 蝉鸣
     */
    @Override
    public OrgLevelVO editLevel(OrgLevelDTO menuDTO) {
        if(ObjectUtil.isEmpty(menuDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(OrgLevelDTO::getId);
            throw LevelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        OrgLevel sysMenu = BeanUtil.copyProperties(menuDTO, OrgLevel.class);
        this.updateById(sysMenu);
        return BeanUtil.copyProperties(sysMenu, OrgLevelVO.class);
    }

    /**
     * 功能描述:
     * 〈获取所有子级包含自己〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public List<Long> getLevelChildByIds(List<Long> levelIds) {
        List<Long> levelIdList = CollUtil.newArrayList();
        if(CollUtil.isEmpty(levelIds)){
            return levelIdList;
        }
        for (Long levelId : levelIds) {
            // 删除层级
            String childrenSql = SqlUtil.getCommonChildrenSql(OrgLevel.class, levelId);
            //查询子项demo
            List<OrgLevel> childList = this.lambdaQuery().apply(childrenSql).list();
            if(CollUtil.isNotEmpty(childList)){
                List<Long> ids = childList.stream().map(OrgLevel::getId).toList();
                levelIdList.addAll(ids);
            }
        }
        levelIdList.addAll(levelIds);
        return levelIdList.stream().distinct().toList();
    }

    /**
     * 功能描述:
     * 〈删除层级〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteLevel(List<Long> levelIds) {
        //获取所有子级包含自己
        List<Long> levelIdList = getLevelChildByIds(levelIds);
        if(CollUtil.isEmpty(levelIds)){
            return Boolean.FALSE;
        }
        //删除对象
        return this.removeByIds(levelIdList);
    }

}

