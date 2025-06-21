package com.platform.mesh.search.logic.ref.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.constants.SearchColumnConst;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.search.logic.ref.LogicRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 逻辑关系工厂实现
 * @author 蝉鸣
 */
@Service
public class LogicNeFactoryImpl implements LogicRefService {

    private final static Logger log = LoggerFactory.getLogger(LogicNeFactoryImpl.class);

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicRefEnum refType() {
        return LogicRefEnum.NE;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public <T> QueryWrapper<T> sqlWrapper(QueryWrapper<T> wrapper, CondDTO condDTO){

        if (ObjectUtil.isEmpty(wrapper) || ObjectUtil.isEmpty(condDTO.getColumnMac())) {
            return wrapper;
        }
        if (condDTO.getIgnoreCase() && CollUtil.isEmpty(CollUtil.removeEmpty(condDTO.getSearchValues()))) {
            return wrapper;
        }
        return wrapper.ne(condDTO.getColumnMac(), condDTO.getSearchValues());
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public <T> QueryWrapper<T> sqlDataWrapper(QueryWrapper<T> wrapper, CondDTO condDTO){

        if (ObjectUtil.isEmpty(wrapper) || ObjectUtil.isEmpty(condDTO.getColumnMac())) {
            return wrapper;
        }
        wrapper.eq(SearchColumnConst.DATA_COLUMN_MAC, condDTO.getColumnMac());
        if (condDTO.getIgnoreCase() && CollUtil.isEmpty(CollUtil.removeEmpty(condDTO.getSearchValues()))) {
            return wrapper;
        }
        wrapper.ne(SearchColumnConst.DATA_COLUMN_VALUE, condDTO.getSearchValues());
        return wrapper;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Query esQuery(CondDTO condDTO){
        if (ObjectUtil.isEmpty(condDTO.getColumnMac())) {
            return null;
        }
        return QueryBuilders.term(ne->{
            ne.field(condDTO.getColumnMac());
            ne.value(CollUtil.getFirst(condDTO.getSearchValues()));
            return ne;
        });
    }
}
