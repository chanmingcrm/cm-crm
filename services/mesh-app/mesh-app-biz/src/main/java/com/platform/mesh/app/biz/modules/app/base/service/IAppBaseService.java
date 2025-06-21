package com.platform.mesh.app.biz.modules.app.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.po.AppBase;
import com.platform.mesh.app.biz.modules.app.base.domain.vo.AppBaseVO;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 应用信息
 * @author 蝉鸣
 */
public interface IAppBaseService extends IService<AppBase> {

    /**
     * 功能描述:
     * 〈获取应用分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    MPage<AppBase> selectPage(PageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前应用信息〉
     * @param baseId baseId
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    AppBaseVO getBaseInfoById(Long baseId);

    /**
     * 功能描述:
     * 〈新增应用〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    AppBaseVO addBase(AppBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈修改应用〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    AppBaseVO editBase(AppBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈删除应用〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteBase(Long baseId);

    /**
     * 功能描述:
     * 〈清除应用〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean clearBase(Long baseId);

    /**
     * 功能描述:
     * 〈拷贝应用〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean copyAppBase(AppBaseCopyDTO copyDTO);

}