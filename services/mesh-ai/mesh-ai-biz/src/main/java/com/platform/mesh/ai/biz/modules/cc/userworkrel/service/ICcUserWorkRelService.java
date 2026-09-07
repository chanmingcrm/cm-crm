package com.platform.mesh.ai.biz.modules.cc.userworkrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.user.domain.vo.CcUserVO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto.CcUserWorkRelPageDTO;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.po.CcUserWorkRel;
import com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.vo.CcUserWorkRelVO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 人员排班信息
 * @author 蝉鸣
 */
public interface ICcUserWorkRelService extends IService<CcUserWorkRel> {

    /**
     * 功能描述:
     * 〈分页查新排班人员〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<CcUserWorkRel>}
     * @author 蝉鸣
     */
    MPage<CcUserWorkRel> selectPage(CcUserWorkRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增人员排班〉
     * @param relDTO relDTO
     * @return 正常返回:{@link CcUserWorkRelVO}
     * @author 蝉鸣
     */
    CcUserWorkRelVO addCcUserWorkRel(CcUserWorkRelDTO relDTO);

    /**
     * 功能描述:
     * 〈删除人员排班〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcUserWorkRel(Long relId);

    /**
     * 功能描述:
     * 〈获取下一个客服人员〉
     * @param userType userType
     * @return 正常返回:{@link CcUserVO}
     * @author 蝉鸣
     */
    CcUserVO getNextCcUser(Integer userType);

}
