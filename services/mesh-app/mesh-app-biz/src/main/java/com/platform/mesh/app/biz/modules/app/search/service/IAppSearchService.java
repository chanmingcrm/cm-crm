package com.platform.mesh.app.biz.modules.app.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchPageDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.po.AppSearch;
import com.platform.mesh.app.biz.modules.app.search.domain.vo.AppSearchVO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 查询信息
 * @author 蝉鸣
 */
public interface IAppSearchService extends IService<AppSearch> {

    /**
     * 功能描述:
     * 〈获取查询分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AppSearch>}
     * @author 蝉鸣
     */
    MPage<AppSearchVO> selectPage(AppSearchPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前查询信息〉
     * @param searchId searchId
     * @return 正常返回:{@link AppSearch}
     * @author 蝉鸣
     */
    AppSearchVO getSearchInfoById(Long searchId);

    /**
     * 功能描述:
     * 〈新增查询〉
     * @param searchDTO searchDTO
     * @return 正常返回:{@link AppSearch}
     * @author 蝉鸣
     */
    AppSearch addSearch(AppSearchDTO searchDTO);

    /**
     * 功能描述:
     * 〈修改查询〉
     * @param searchDTO searchDTO
     * @return 正常返回:{@link AppSearch}
     * @author 蝉鸣
     */
    AppSearch editSearch(AppSearchDTO searchDTO);

    /**
     * 功能描述:
     * 〈删除查询〉
     * @param searchId searchId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteSearch(Long searchId);

}
