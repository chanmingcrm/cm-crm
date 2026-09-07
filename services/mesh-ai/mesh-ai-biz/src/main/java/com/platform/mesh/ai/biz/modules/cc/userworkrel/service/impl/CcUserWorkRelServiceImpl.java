package com.platform.mesh.ai.biz.modules.cc.userworkrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import com.platform.mesh.ai.biz.modules.cc.user.domain.po.CcUser;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.user.service.ICcUserService;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.bo.CcUserWorkRelBO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.po.CcUserWorkRel;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.vo.CcUserWorkRelVO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.mapper.CcUserWorkRelMapper;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.service.ICcUserWorkRelService;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.service.manual.CcUserWorkRelServiceManual;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 人员排班
 * @author 蝉鸣
 */
@Service
public class CcUserWorkRelServiceImpl extends ServiceImpl<CcUserWorkRelMapper, CcUserWorkRel> implements ICcUserWorkRelService {


    @Autowired
    private CcUserWorkRelServiceManual ccUserWorkRelServiceManual;

    @Autowired
    private ICcUserService ccUserService;

    /**
     * 功能描述:
     * 〈分页查新排班人员〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<CcUserWorkRel>}
     * @author 蝉鸣
     */
    @Override
    public MPage<CcUserWorkRel> selectPage(CcUserWorkRelPageDTO pageDTO) {
        MPage<CcUserWorkRel> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcUserWorkRel.class);
        return this.lambdaQuery()
                .eq(CcUserWorkRel::getCcWorkId, pageDTO.getCcWorkId())
                .page(mPage);
    }

    /**
     * 功能描述:
     * 〈新增人员排班〉
     * @param relDTO relDTO
     * @return 正常返回:{@link CcUserWorkRelVO}
     * @author 蝉鸣
     */
    @Override
    public CcUserWorkRelVO addCcUserWorkRel(CcUserWorkRelDTO relDTO) {
        CcUserWorkRel ccUserWorkRel = BeanUtil.copyProperties(relDTO, CcUserWorkRel.class);
        fillCcUser(ccUserWorkRel);
        this.save(ccUserWorkRel);
        return BeanUtil.copyProperties(ccUserWorkRel, CcUserWorkRelVO.class);
    }

    /**
     * 功能描述:
     * 〈补全排班关联的客服人员信息〉
     * @param ccUserWorkRel ccUserWorkRel
     * @author Codex
     */
    private void fillCcUser(CcUserWorkRel ccUserWorkRel) {
        CcUser ccUser = null;
        if(ccUserWorkRel.getCcUserId() != null){
            ccUser = ccUserService.getById(ccUserWorkRel.getCcUserId());
        }
        if(ccUser == null && ccUserWorkRel.getUserType() != null && ccUserWorkRel.getUserHash() != null){
            ccUser = ccUserService.lambdaQuery()
                    .eq(CcUser::getUserType, ccUserWorkRel.getUserType())
                    .eq(CcUser::getUserHash, ccUserWorkRel.getUserHash())
                    .last("LIMIT 1")
                    .one();
        }
        if(ccUser == null && UserTypeEnum.AI.getValue().equals(ccUserWorkRel.getUserType()) && ccUserWorkRel.getUserHash() != null){
            try {
                Long agentId = Long.valueOf(ccUserWorkRel.getUserHash());
                ccUser = ccUserService.lambdaQuery()
                        .eq(CcUser::getUserType, UserTypeEnum.AI.getValue())
                        .eq(CcUser::getAgentId, agentId)
                        .last("LIMIT 1")
                        .one();
            } catch (NumberFormatException ignored) {
                // 非历史智能体ID格式，继续使用原始入参。
            }
        }
        if(ccUser != null){
            ccUserWorkRel.setCcUserId(ccUser.getId());
            ccUserWorkRel.setUserHash(ccUser.getUserHash());
            ccUserWorkRel.setUserType(ccUser.getUserType());
        }
    }

    /**
     * 功能描述:
     * 〈删除人员排班〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcUserWorkRel(Long relId) {
        return this.removeById(relId);
    }

    /**
     * 功能描述:
     * 〈获取下一个客服人员〉
     * @param userType userType
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    @Override
    public CcUserVO getNextCcUser(Integer userType) {
        List<CcSetWork> currentWorks = ccUserWorkRelServiceManual.getCurrentWork();
        if(CollUtil.isEmpty(currentWorks)){
            return null;
        }
        List<Long> workIds = currentWorks.stream().map(CcSetWork::getId).toList();
        List<CcUserWorkRelBO> userWorkBOS = this.getBaseMapper().getWorkUserList(workIds, userType);
        if(CollUtil.isEmpty(userWorkBOS)){
            return null;
        }
        //获取上次分配的用户
        Optional<CcUserWorkRelBO> workRelBO = userWorkBOS.stream().filter(rel -> rel.getLast().equals(YesOrNoEnum.YES.getValue())).findFirst();
        if(workRelBO.isEmpty()){
            //返回第一条
            CcUserWorkRelBO first = CollUtil.getFirst(userWorkBOS);
            return BeanUtil.copyProperties(first,CcUserVO.class);
        }
        CcUserWorkRelBO lastRel = workRelBO.get();
        int lastIndex = userWorkBOS.indexOf(lastRel);
        if(lastIndex < 0 || lastIndex + 1 >= userWorkBOS.size()){
            CcUserWorkRelBO first = CollUtil.getFirst(userWorkBOS);
            return BeanUtil.copyProperties(first,CcUserVO.class);
        }
        return BeanUtil.copyProperties(userWorkBOS.get(lastIndex + 1),CcUserVO.class);
    }
}
