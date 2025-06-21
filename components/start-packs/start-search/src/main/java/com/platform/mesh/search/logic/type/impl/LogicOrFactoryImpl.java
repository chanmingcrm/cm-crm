package com.platform.mesh.search.logic.type.impl;

import cn.hutool.core.collection.CollUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.es.enums.EsBoolEnum;
import com.platform.mesh.search.logic.ref.LogicRefService;
import com.platform.mesh.search.logic.ref.factory.LogicRefFactory;
import com.platform.mesh.search.logic.type.LogicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 逻辑或工厂实现
 * @author 蝉鸣
 */
@Service
public class LogicOrFactoryImpl implements LogicTypeService {

    @Autowired
    private LogicRefFactory logicRefFactory;

    /**
     * 功能描述:
     * 〈逻辑类型〉
     * @return 正常返回:{@link LogicTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicTypeEnum logicType() {
        return LogicTypeEnum.OR;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public <T> QueryWrapper<T> sqlWrapper(QueryWrapper<T> wrapper, List<CondDTO> condDTOS){
        if (CollUtil.isEmpty(condDTOS)) {
            return wrapper;
        }
        wrapper.and(andWrapper->{
            for (CondDTO condDTO : condDTOS) {
                andWrapper.or(orWrapper->{
                    LogicRefEnum condition = condDTO.getCondRef();
                    LogicRefService logicRefService = logicRefFactory.getLogicRefService(condition);
                    logicRefService.sqlWrapper(orWrapper,condDTO);
                });
            }
        });
        return wrapper;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public <T> QueryWrapper<T> sqlDataWrapper(QueryWrapper<T> wrapper, List<CondDTO> condDTOS){
        if (CollUtil.isEmpty(condDTOS)) {
            return wrapper;
        }
        wrapper.and(andWrapper->{
            for (CondDTO condDTO : condDTOS) {
                andWrapper.or(orWrapper->{
                    LogicRefEnum condition = condDTO.getCondRef();
                    LogicRefService logicRefService = logicRefFactory.getLogicRefService(condition);
                    logicRefService.sqlDataWrapper(orWrapper,condDTO);
                });
            }
        });
        return wrapper;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Map<EsBoolEnum, Query> esBoolQuery(List<CondDTO> condDTOS){
        Map<EsBoolEnum, Query> boolMap = new HashMap<>();
        if (CollUtil.isEmpty(condDTOS)) {
            return boolMap;
        }
        for (CondDTO condDTO : condDTOS) {
            LogicRefEnum condition = condDTO.getCondRef();
            LogicRefService logicRefService = logicRefFactory.getLogicRefService(condition);
            Query query = logicRefService.getEsUniQuery(condDTO);
            boolMap.put(EsBoolEnum.SHOULD,query);
        }
        return boolMap;
    }

}
