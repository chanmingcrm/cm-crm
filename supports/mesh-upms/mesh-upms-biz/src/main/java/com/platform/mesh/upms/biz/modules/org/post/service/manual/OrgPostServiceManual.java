package com.platform.mesh.upms.biz.modules.org.post.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelAddDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostAddDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostEditDTO;
import com.platform.mesh.upms.biz.modules.org.post.exception.PostExceptionEnum;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto.OrgPostDataScopeDTO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po.OrgPostDataScope;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.vo.OrgPostDataScopeVO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.service.IOrgPostDataScopeService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class OrgPostServiceManual {

    private static final Logger log = LoggerFactory.getLogger(OrgPostServiceManual.class);

    @Autowired
    private IOrgLevelService orgLevelService;

    @Autowired
    private IOrgLevelPostRelService orgLevelPostRelService;

    @Autowired
    private IOrgPostDataScopeService orgPostDataScopeService;

    /**
     * 功能描述:
     * 〈添加岗位〉
     * @param postDTO postDTO
     * @author 蝉鸣
     */
    public void addPost(Long postId, OrgPostAddDTO postDTO) {
        if(ObjectUtil.isEmpty(postDTO.getLevelId())) {
            return;
        }
        //验证层级是否有效
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        OrgLevel orgLevel = orgLevelService.getById(postDTO.getLevelId());
        if(ObjectUtil.isEmpty(orgLevel)) {
            throw PostExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        //保存岗位层级关系信息
        OrgLevelPostRelAddDTO levelPostRel = new OrgLevelPostRelAddDTO();
        levelPostRel.setRootId(orgLevel.getRootId());
        levelPostRel.setLevelId(postDTO.getLevelId());
        levelPostRel.setPostIds(CollUtil.newArrayList(postId));
        orgLevelPostRelService.addLevelPost(levelPostRel);
        //保存岗位数据权限信息
        if(CollUtil.isEmpty(postDTO.getPostDataScopes())){
            return;
        }
        List<OrgPostDataScopeDTO> postDataScopes = postDTO.getPostDataScopes();
        postDataScopes.forEach(orgPostDataScope -> {
            orgPostDataScope.setPostId(postId);
            if(ObjectUtil.isEmpty(orgPostDataScope.getDataFlag())){
                if(DataScopeEnum.LEVEL.getValue().equals(orgPostDataScope.getDataScope())
                || DataScopeEnum.SELF.getValue().equals(orgPostDataScope.getDataScope())
                || DataScopeEnum.CUSTOM.getValue().equals(orgPostDataScope.getDataScope())
                ){
                    orgPostDataScope.setDataFlag(DataFlagEnum.USER.getValue());
                }else{
                    orgPostDataScope.setDataFlag(DataFlagEnum.ORG.getValue());
                }
            }
        });
        orgPostDataScopeService.editPostDataScope(postDataScopes);
    }

    /**
     * 功能描述:
     * 〈添加岗位〉
     * @param postDTO postDTO
     * @author 蝉鸣
     */
    public void editPost(Long postId, OrgPostEditDTO postDTO) {

        //保存岗位层级关系信息
        OrgLevelPostRel levelPostRel = orgLevelPostRelService.lambdaQuery()
                .eq(OrgLevelPostRel::getLevelId,postDTO.getLevelId())
                .eq(OrgLevelPostRel::getPostId,postDTO.getPostId())
                .list().getFirst();
        if(ObjectUtil.isEmpty(levelPostRel)) {
            String fieldName = ObjFieldUtil.getFieldName(OrgPostEditDTO::getPostId);
            throw PostExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        //验证层级是否有效
        IOrgLevelService orgLevelService = SpringContextHolderUtil.getBean(IOrgLevelService.class);
        OrgLevel orgLevel = orgLevelService.getById(postDTO.getLevelId());
        if(ObjectUtil.isEmpty(orgLevel)) {
            throw PostExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        levelPostRel.setLevelRootId(orgLevel.getRootId());
        levelPostRel.setLevelId(postDTO.getLevelId());
        levelPostRel.setPostId(postId);
        orgLevelPostRelService.updateById(levelPostRel);
        //保存岗位数据权限信息
        List<OrgPostDataScopeDTO> postDataScopes = postDTO.getPostDataScopes();
        postDataScopes.forEach(orgPostDataScope -> {
            orgPostDataScope.setPostId(postId);
        });
        orgPostDataScopeService.editPostDataScope(postDataScopes);
    }

    /**
     * 功能描述:
     * 〈获取岗位数据权限〉
     * @param postId postId
     * @author 蝉鸣
     */
    public List<OrgPostDataScopeVO> getPostDataScope(Long postId) {
        List<OrgPostDataScope> dataScopes = orgPostDataScopeService.lambdaQuery().eq(OrgPostDataScope::getPostId, postId).list();
        if(CollUtil.isEmpty(dataScopes)){
            return CollUtil.newArrayList();
        }
        return BeanUtil.copyToList(dataScopes, OrgPostDataScopeVO.class);
    }

    /**
     * 功能描述:
     * 〈删除岗位其他信息〉
     * @param postId postId
     * @author 蝉鸣
     */
    public void delPostRel(Long postId) {
        //删除层级关系
        IOrgLevelPostRelService levelPostRelService = SpringContextHolderUtil.getBean(IOrgLevelPostRelService.class);
        levelPostRelService.lambdaUpdate().eq(OrgLevelPostRel::getPostId, postId).remove();
        //删除成员关系
        IOrgMemberPostRelService orgMemberPostRelService = SpringContextHolderUtil.getBean(IOrgMemberPostRelService.class);
        orgMemberPostRelService.lambdaUpdate().eq(OrgMemberPostRel::getPostId, postId).remove();
        //删除数据权限
        IOrgPostDataScopeService orgPostDataScopeService = SpringContextHolderUtil.getBean(IOrgPostDataScopeService.class);
        orgPostDataScopeService.lambdaUpdate().eq(OrgPostDataScope::getPostId, postId).remove();
    }

    /**
     * 功能描述:
     * 〈获取层级与岗位关联信息〉
     * @param postId postId
     * @author 蝉鸣
     */
    public OrgLevelPostRelVO getLevelPostRel(Long postId) {
        return orgLevelPostRelService.getLevelPostRelByPostId(postId);
    }

    /**
     * 功能描述:
     * 〈获取层级子Id〉
     * @param levelIds levelIds
     * @author 蝉鸣
     */
    public List<Long> getChileLevelIds(List<Long> levelIds) {
        return orgLevelService.getLevelChildByIds(levelIds);
    }
}

