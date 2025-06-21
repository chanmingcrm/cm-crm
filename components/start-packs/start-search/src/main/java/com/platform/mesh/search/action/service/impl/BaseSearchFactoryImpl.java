package com.platform.mesh.search.action.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.search.action.enums.SearchTypeEnum;
import com.platform.mesh.search.action.service.IBaseSearchService;
import com.platform.mesh.core.application.domain.dto.QueryDTO;
import com.platform.mesh.search.utils.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 逻辑关系工厂实现
 * @author 蝉鸣
 */
@Service
public class BaseSearchFactoryImpl implements IBaseSearchService {

    private final static Logger log = LoggerFactory.getLogger(BaseSearchFactoryImpl.class);

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link SearchTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public SearchTypeEnum searchType() {
        return SearchTypeEnum.SQL_DB;
    }

    @Override
    public <E, T> PageVO<E> searchPage(Class<E> eType, Class<T> tType, QueryDTO queryDTO) {
        IService<T> service = new ServiceImpl<>() {};
        QueryWrapper<T> sqlQueryWrapper = SearchUtil.getSqlQueryWrapper(tType, queryDTO);
        MPage<T> userMPage = MPageUtil.pageEntityToMPage(queryDTO, tType);
        MPage<T> page = service.page(userMPage, sqlQueryWrapper);
        return MPageUtil.convertToVO(page,eType);
    }

    @Override
    public <E, T> E searchOne(Class<E> eType,Class<T> classType,Long id) {
        return null;
    }

}
