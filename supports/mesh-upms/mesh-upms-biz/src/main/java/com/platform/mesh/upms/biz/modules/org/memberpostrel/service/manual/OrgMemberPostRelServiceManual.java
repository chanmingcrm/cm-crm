package com.platform.mesh.upms.biz.modules.org.memberpostrel.service.manual;

import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.postdatascope.service.IOrgPostDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class OrgMemberPostRelServiceManual {


    @Autowired
    private IOrgLevelPostRelService orgLevelPostRelService;

    /**
     * 功能描述:
     * 〈获取层级岗位关系〉
     * @param postId postId
     * @author 蝉鸣
     */
    public OrgLevelPostRel getLevelPostRel(Long postId) {
       return orgLevelPostRelService.lambdaQuery().eq(OrgLevelPostRel::getPostId, postId).one();
    }

    /**
     * 功能描述:
     * 〈获取层级岗位关系〉
     * @param postIds postIds
     * @author 蝉鸣
     */
    public List<OrgLevelPostRel> getLevelPostRelList(List<Long> postIds) {
       return orgLevelPostRelService.lambdaQuery().in(OrgLevelPostRel::getPostId, postIds).list();
    }
}

