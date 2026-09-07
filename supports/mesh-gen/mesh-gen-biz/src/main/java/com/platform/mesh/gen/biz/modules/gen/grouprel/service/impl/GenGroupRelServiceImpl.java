package com.platform.mesh.gen.biz.modules.gen.grouprel.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelPageDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.vo.GenGroupRelVO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.mapper.GenGroupRelMapper;
import com.platform.mesh.gen.biz.modules.gen.grouprel.service.IGenGroupRelService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 构建分组模板关系信息
 * @author 蝉鸣
 */
@Service
public class GenGroupRelServiceImpl extends ServiceImpl<GenGroupRelMapper, GenGroupRel> implements IGenGroupRelService {


    @Override
    public MPage<GenGroupRelVO> selectPage(GenGroupRelPageDTO pageDTO) {
        MPage<GenGroupRel> mPage = MPageUtil.pageEntityToMPage(pageDTO, GenGroupRel.class);
        return this.baseMapper.selectAllPage(mPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param groupRelDTO groupRelDTO
     * @return 正常返回:{@link GenGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAllGroupRel(GenGroupRelDTO groupRelDTO) {
        if(ObjectUtil.isEmpty(groupRelDTO.getGroupId()) || CollUtil.isEmpty(groupRelDTO.getDataIds())){
            return Boolean.FALSE;
        }
        //删除旧关系
        this.lambdaUpdate().eq(GenGroupRel::getGroupId,groupRelDTO.getGroupId()).remove();
        //新增新关系
        List<GenGroupRel> genGroupRels = groupRelDTO.getDataIds().stream().map(dataId -> {
            GenGroupRel genGroupRel = new GenGroupRel();
            genGroupRel.setGroupId(groupRelDTO.getGroupId());
            genGroupRel.setGroupType(groupRelDTO.getGroupType());
            genGroupRel.setDataId(dataId);
            return genGroupRel;
        }).toList();
        this.saveBatch(genGroupRels);
        return Boolean.TRUE;
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
