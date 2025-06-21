package com.platform.mesh.upms.biz.modules.org.postdatascope.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.org.postdatascope.mapper.OrgPostDataScopeMapper;
import com.platform.mesh.upms.biz.modules.org.postdatascope.service.IOrgPostDataScopeService;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto.OrgPostDataScopeDTO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po.OrgPostDataScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgPostDataScopeServiceImpl extends ServiceImpl<OrgPostDataScopeMapper, OrgPostDataScope> implements IOrgPostDataScopeService {

    /**
     * 功能描述:
     * 〈通过岗位Id查询岗位权限〉
     * @param postId postId
     * @return 正常返回:{@link List<OrgPostDataScope>}
     * @author 蝉鸣
     */
    @Override
    public List<OrgPostDataScope> selectByPostId(Long postId) {
        return this.lambdaQuery().eq(OrgPostDataScope::getPostId, postId).list();
    }

    /**
     * 功能描述:
     * 〈修改岗位-权限〉
     * @param orgPostDataScopeDTO orgPostDataScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editPostDataScope(List<OrgPostDataScopeDTO> orgPostDataScopeDTO) {
        if (CollUtil.isEmpty(orgPostDataScopeDTO)) {
            return Boolean.FALSE;
        }
        List<Long> postIds = orgPostDataScopeDTO.stream().map(OrgPostDataScopeDTO::getPostId).toList();
        if (CollUtil.isEmpty(postIds)) {
            return Boolean.FALSE;
        }
        //删除原有岗位权限信息
        LambdaUpdateWrapper<OrgPostDataScope> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(OrgPostDataScope::getPostId, postIds);
        this.remove(updateWrapper);
        //新增岗位权限信息
        List<OrgPostDataScope> orgPostDataScope = BeanUtil.copyToList(orgPostDataScopeDTO, OrgPostDataScope.class);
        saveBatch(orgPostDataScope);
        return Boolean.TRUE;
    }
}

