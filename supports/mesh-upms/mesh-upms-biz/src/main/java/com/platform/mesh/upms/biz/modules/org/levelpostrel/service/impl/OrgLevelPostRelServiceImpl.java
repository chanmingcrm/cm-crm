package com.platform.mesh.upms.biz.modules.org.levelpostrel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelAddDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.exception.LevelPostRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.mapper.OrgLevelPostRelMapper;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.post.exception.PostExceptionEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 组织信息
 * @author 蝉鸣
 */
@Service()
public class OrgLevelPostRelServiceImpl extends ServiceImpl<OrgLevelPostRelMapper, OrgLevelPostRel> implements IOrgLevelPostRelService {


    /**
     * 功能描述:
     * 〈添加层级岗位关系〉
     * @param levelPostRelDTO levelPostRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addLevelPost(OrgLevelPostRelAddDTO levelPostRelDTO) {
        if(CollUtil.isEmpty(levelPostRelDTO.getPostIds())){
            return Boolean.FALSE;
        }
        //验证当前岗位是否已经绑定层级
        boolean exists = this.lambdaQuery()
                .eq(OrgLevelPostRel::getLevelId, levelPostRelDTO.getLevelId())
                .in(OrgLevelPostRel::getPostId, levelPostRelDTO.getPostIds()).exists();
        if(exists){
            //当前岗位存在绑定关系
            throw  LevelPostRelExceptionEnum.ADD_EXISTS_REL_DATA.getBaseException();
        }
        List<OrgLevelPostRel> list = CollUtil.newArrayList();
        for (Long postId : levelPostRelDTO.getPostIds()) {
            OrgLevelPostRel orgLevelPostRel = new OrgLevelPostRel();
            orgLevelPostRel.setLevelRootId(levelPostRelDTO.getRootId());
            orgLevelPostRel.setLevelId(levelPostRelDTO.getLevelId());
            orgLevelPostRel.setPostId(postId);
            list.add(orgLevelPostRel);
        }
        //不存在批量新增
        saveBatch(list);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈获取层级岗位关系〉
     * @param postId postId
     * @return 正常返回:{@link OrgLevelPostRelVO}
     * @author 蝉鸣
     */
    @Override
    public OrgLevelPostRelVO getLevelPostRelByPostId(Long postId) {
        return this.getBaseMapper().getLevelPostRelByPostId(postId);
    }
}

