package com.platform.mesh.ai.biz.modules.cc.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.user.domain.dto.CcUserDTO;
import com.platform.mesh.ai.biz.modules.cc.user.domain.po.CcUser;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.user.exception.CcUserExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.user.mapper.CcUserMapper;
import com.platform.mesh.ai.biz.modules.cc.user.service.ICcUserService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服人员
 * @author 蝉鸣
 */
@Service
public class CcUserServiceImpl extends ServiceImpl<CcUserMapper, CcUser> implements ICcUserService {

    private static final String AI_USER_HASH_PREFIX = "ai:";
    private static final int DEFAULT_MAX_RECEPTION = 5;

    /**
     * 功能描述: 
     * 〈获取当前客服人员信息〉
     * @param userId userId
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    @Override
    public CcUserVO getCcUserById(Long userId) {
        CcUser ccSession = this.getById(userId);
        return BeanUtil.copyProperties(ccSession, CcUserVO.class);
    }

    /**
     * 功能描述:
     * 〈新增客服人员〉
     * @param userDTO userDTO
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    @Override
    public CcUserVO addCcUser(CcUserDTO userDTO) {
        normalizeCapacity(userDTO);
        normalizeAiUser(userDTO);
        CcUser ccSession = BeanUtil.copyProperties(userDTO, CcUser.class);
        this.save(ccSession);
        return BeanUtil.copyProperties(ccSession, CcUserVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客服人员〉
     * @param userDTO userDTO
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    @Override
    public CcUserVO editCcUser(CcUserDTO userDTO) {
        if(ObjectUtil.isEmpty(userDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcUserDTO::getId);
            throw CcUserExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        normalizeCapacity(userDTO);
        normalizeAiUser(userDTO);
        CcUser ccSession = BeanUtil.copyProperties(userDTO, CcUser.class);
        this.updateById(ccSession);
        return BeanUtil.copyProperties(ccSession, CcUserVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客服人员〉
     * @param userId userId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcUser(Long userId) {
        return this.removeById(userId);
    }

    /**
     * 功能描述:
     * 〈规范化AI客服身份信息〉
     * @param userDTO userDTO
     * @author Codex
     */
    private void normalizeAiUser(CcUserDTO userDTO) {
        if(!UserTypeEnum.AI.getValue().equals(userDTO.getUserType())){
            userDTO.setAgentId(null);
            return;
        }
        if(ObjectUtil.isEmpty(userDTO.getAgentId()) && StrUtil.isNotBlank(userDTO.getUserHash())){
            String userHash = StrUtil.removePrefix(userDTO.getUserHash(), AI_USER_HASH_PREFIX);
            try {
                userDTO.setAgentId(Long.valueOf(userHash));
            } catch (NumberFormatException ignored) {
                // 已经是在线身份或自定义身份时，保持原值。
            }
        }
        if(ObjectUtil.isNotEmpty(userDTO.getAgentId())){
            userDTO.setUserHash(AI_USER_HASH_PREFIX + userDTO.getAgentId());
        }
    }

    /**
     * 功能描述:
     * 〈规范化客服接待容量信息〉
     * @param userDTO userDTO
     * @author Codex
     */
    private void normalizeCapacity(CcUserDTO userDTO) {
        if(ObjectUtil.isEmpty(userDTO.getUserFlag())){
            userDTO.setUserFlag(1);
        }
        if(ObjectUtil.isEmpty(userDTO.getMaxReception()) || userDTO.getMaxReception() <= 0){
            userDTO.setMaxReception(DEFAULT_MAX_RECEPTION);
        }
    }

}
