package com.platform.mesh.app.biz.modules.app.compbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBaseDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBasePageDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.po.AppCompBase;
import com.platform.mesh.app.biz.modules.app.compbase.domain.vo.AppCompBaseVO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 页面组件信息
 * @author 蝉鸣
 */
public interface IAppCompBaseService extends IService<AppCompBase> {

    /**
     * 功能描述:
     * 〈获取页面组件列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AppCompBase>}
     * @author 蝉鸣
     */
    MPage<AppCompBase> selectPage(AppCompBasePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前页面组件信息〉
     * @param compBaseId compBaseId
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    AppCompBaseVO getCompBaseInfoById(Long compBaseId);

    /**
     * 功能描述:
     * 〈新增页面组件〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    AppCompBaseVO addCompBase(AppCompBaseDTO compBaseDTO);

    /**
     * 功能描述:
     * 〈修改页面组件〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link AppCompBaseVO}
     * @author 蝉鸣
     */
    AppCompBaseVO editCompBase(AppCompBaseDTO compBaseDTO);

    /**
     * 功能描述:
     * 〈删除页面组件〉
     * @param compBaseId compBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCompBase(Long compBaseId);

}
