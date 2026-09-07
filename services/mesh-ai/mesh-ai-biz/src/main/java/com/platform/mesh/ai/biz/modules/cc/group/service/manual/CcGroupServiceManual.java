package com.platform.mesh.ai.biz.modules.cc.group.service.manual;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.ai.biz.modules.cc.group.domain.dto.CcGroupInitDTO;
import com.platform.mesh.ai.biz.modules.cc.group.domain.po.CcGroup;
import com.platform.mesh.ai.biz.modules.cc.group.domain.vo.CcGroupInitVO;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po.CcGroupUserRel;
import com.platform.mesh.ai.biz.modules.cc.groupuserrel.service.ICcGroupUserRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服会话群
 * @author 蝉鸣
 */
@Service
public class CcGroupServiceManual {

    private static final Logger log = LoggerFactory.getLogger(CcGroupServiceManual.class);

    @Autowired
    private ICcGroupUserRelService ccGroupUserRelService;

    /**
     * 功能描述:
     * 〈初始化会话群人员关系〉
     * @param initDTO initDTO
     * @return 正常返回:{@link CcGroupInitVO}
     * @author 蝉鸣
     */
    public CcGroupInitVO initCcGroupUserRel(CcGroup ccGroup, CcGroupInitDTO initDTO) {
        CcGroupUserRel ccGroupUserRel = BeanUtil.copyProperties(initDTO, CcGroupUserRel.class);
        ccGroupUserRel.setGroupId(ccGroup.getId());
        ccGroupUserRelService.save(ccGroupUserRel);
        return BeanUtil.copyProperties(ccGroupUserRel, CcGroupInitVO.class);
    }
}