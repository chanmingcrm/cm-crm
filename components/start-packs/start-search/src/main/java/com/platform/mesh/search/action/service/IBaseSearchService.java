package com.platform.mesh.search.action.service;


import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.search.action.enums.SearchTypeEnum;
import com.platform.mesh.core.application.domain.dto.QueryDTO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分组信息
 * @author 蝉鸣
 */
public interface IBaseSearchService{

    /**
     * 功能描述:
     * 〈搜索执行类型〉
     * @return 正常返回:{@link SearchTypeEnum}
     * @author 蝉鸣
     */
    SearchTypeEnum searchType();

    /**
     * 功能描述:
     * 〈分页查询〉
     * @param classType classType
     * @return 正常返回:{@link E}
     * @author 蝉鸣
     */
    <E,T> PageVO<E> searchPage(Class<E> eType, Class<T> classType, QueryDTO queryDTO);

    /**
     * 功能描述:
     * 〈单条查询〉
     * @param classType classType
     * @return 正常返回:{@link E}
     * @author 蝉鸣
     */
    <E,T> E searchOne(Class<E> eType,Class<T> classType,Long id);

}