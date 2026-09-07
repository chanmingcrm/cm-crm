package com.platform.mesh.ai.biz.modules.cc.group.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupInitDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupPageDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupInitVO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupVO;
import com.platform.mesh.ai.biz.modules.cc.group.enums.CcGroupStatusEnum;
import com.platform.mesh.ai.biz.modules.cc.group.exception.CcGroupExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.group.mapper.CcGroupMapper;
import com.platform.mesh.ai.biz.modules.cc.group.service.ICcGroupService;
import com.platform.mesh.ai.biz.modules.cc.group.service.manual.CcGroupServiceManual;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 会话群信息
 * @author 蝉鸣
 */
@Service
public class CcGroupServiceImpl extends ServiceImpl<CcGroupMapper, CcGroup> implements ICcGroupService {

    @Autowired
    private CcGroupServiceManual ccGroupServiceManual;

    /**
     * 功能描述:
     * 〈获取聊天群信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<CcGroupVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<CcGroupVO> selectPage(CcGroupPageDTO pageDTO) {
        MPage<CcGroup> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcGroup.class);
        pageDTO.setUserHash(UserCacheUtil.getUserId().toString());
        MPage<CcGroupVO> page = this.getBaseMapper().selectMPage(mPage, pageDTO);
        return MPageUtil.convertToVO(page,CcGroupVO.class);
    }

    /**
     * 功能描述: 
     * 〈获取当前会话群信息信息〉
     * @param groupId groupId
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CcGroupVO getCcGroupById(Long groupId) {
        CcGroup ccSession = this.getById(groupId);
        return BeanUtil.copyProperties(ccSession, CcGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈获取当前会话群信息信息〉
     * @param groupHash groupHash
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CcGroup getCcGroupByHash(String groupHash) {
        return this.getBaseMapper().getCcGroupByHash(groupHash);
    }

    /**
     * 功能描述:
     * 〈新增会话群信息〉
     * @param groupDTO groupDTO
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CcGroupVO addCcGroup(CcGroupDTO groupDTO) {
        CcGroup ccSession = BeanUtil.copyProperties(groupDTO, CcGroup.class);
        this.save(ccSession);
        return BeanUtil.copyProperties(ccSession, CcGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改会话群信息〉
     * @param groupDTO groupDTO
     * @return 正常返回:{@link CcGroupVO}
     * @author 蝉鸣
     */
    @Override
    public CcGroupVO editCcGroup(CcGroupDTO groupDTO) {
        if(ObjectUtil.isEmpty(groupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcGroupDTO::getId);
            throw CcGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CcGroup ccSession = BeanUtil.copyProperties(groupDTO, CcGroup.class);
        if(CcGroupStatusEnum.ENDED.getValue().equals(groupDTO.getStatus())){
            ccSession.setClosedAt(LocalDateTime.now());
        }
        this.updateById(ccSession);
        if(UserTypeEnum.AI.getValue().equals(groupDTO.getReplyType())){
            this.lambdaUpdate()
                    .set(CcGroup::getAssigneeUserHash, null)
                    .eq(CcGroup::getId, groupDTO.getId())
                    .update();
        }
        if(ObjectUtil.isNotEmpty(groupDTO.getStatus()) && !CcGroupStatusEnum.ENDED.getValue().equals(groupDTO.getStatus())){
            this.lambdaUpdate()
                    .set(CcGroup::getClosedAt, null)
                    .eq(CcGroup::getId, groupDTO.getId())
                    .update();
        }
        return BeanUtil.copyProperties(ccSession, CcGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈删除会话群信息〉
     * @param groupId groupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcGroup(Long groupId) {
        return this.removeById(groupId);
    }

    /**
     * 功能描述:
     * 〈初始化会话群〉
     * @param initDTO initDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public CcGroupInitVO initCcGroup(CcGroupInitDTO initDTO) {
        CcGroup ccGroup = BeanUtil.copyProperties(initDTO, CcGroup.class);
        ccGroup.setStatus(CcGroupStatusEnum.CHATTING.getValue());
        this.save(ccGroup);
        return ccGroupServiceManual.initCcGroupUserRel(ccGroup,initDTO);
    }

    /**
     * 功能描述:
     * 〈设置群回复类型〉
     * @param replyType replyType
     * @param groupHash groupHash
     * @author 蝉鸣
     */
    @Override
    public void setReplyType(Integer replyType, String groupHash) {
        this.getBaseMapper().setReplyType(replyType,groupHash);
    }

    /**
     * 功能描述:
     * 〈设置会话状态〉
     * @param status status
     * @param groupHash groupHash
     * @param tenantId tenantId
     * @author Codex
     */
    @Override
    public void setStatus(Integer status, String groupHash) {
        this.getBaseMapper().setStatus(status, groupHash);
    }

}
