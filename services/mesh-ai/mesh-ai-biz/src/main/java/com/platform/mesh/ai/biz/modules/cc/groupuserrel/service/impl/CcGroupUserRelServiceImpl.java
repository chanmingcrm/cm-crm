package com.platform.mesh.ai.biz.modules.cc.groupuserrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto.CcGroupUserRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.vo.CcGroupUserRelVO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.mapper.CcGroupUserRelMapper;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.service.ICcGroupUserRelService;
import com.platform.mesh.core.application.domain.bo.SimpBO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服会话群人员关系
 * @author 蝉鸣
 */
@Service
public class CcGroupUserRelServiceImpl extends ServiceImpl<CcGroupUserRelMapper, CcGroupUserRel> implements ICcGroupUserRelService {


    /**
     * 功能描述:
     * 〈分页查询〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<CcGroupUserRel>}
     * @author 蝉鸣
     */
    @Override
    public MPage<CcGroupUserRel> selectPage(CcGroupUserRelPageDTO pageDTO) {
        MPage<CcGroupUserRel> mPage = MPageUtil.pageEntityToMPage(pageDTO, CcGroupUserRel.class);
        return this.getBaseMapper().selectMPage(mPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增客服会话群人员关系〉
     * @param relDTO relDTO
     * @return 正常返回:{@link CcGroupUserRelVO}
     * @author 蝉鸣
     */
    @Override
    public CcGroupUserRelVO addCcGroupUserRel(CcGroupUserRelDTO relDTO) {
        CcGroupUserRel ccGroupUserRel = BeanUtil.copyProperties(relDTO, CcGroupUserRel.class);
        this.save(ccGroupUserRel);
        return BeanUtil.copyProperties(ccGroupUserRel, CcGroupUserRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客服会话群人员关系〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcGroupUserRel(Long relId) {
        return this.removeById(relId);
    }

    /**
     * 功能描述:
     * 〈根据分组Hash和人员类型获取〉
     * @param groupHash groupHash
     * @param userType userType
     * @return 正常返回:{@link CcGroupUserRel}
     * @author 蝉鸣
     */
    @Override
    public CcGroupUserRel getByGroupHashAndType(String groupHash, Integer userType) {
        return this.getBaseMapper().getByGroupHashAndType(groupHash, userType);
    }

    /**
     * 功能描述:
     * 〈查询当前未读消息〉
     * @param userHash userHash
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    public Map<Long, Integer> getUserUnReadNum(String userHash) {
        List<SimpBO> simpBOS = this.getBaseMapper().getUserUnReadNum(userHash);
        if(CollUtil.isEmpty(simpBOS)){
            return new HashMap<>();
        }
        return simpBOS.stream()
                .filter(item-> ObjectUtil.isNotEmpty(item.getId()) && ObjectUtil.isNotEmpty(item.getValue()))
                .collect(Collectors.toMap(SimpBO::getId,value->Integer.parseInt(value.getValue().toString())));
    }
}
