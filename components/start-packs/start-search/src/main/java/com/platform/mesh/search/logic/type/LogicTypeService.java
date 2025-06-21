package com.platform.mesh.search.logic.type;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.es.enums.EsBoolEnum;
import com.platform.mesh.core.application.domain.dto.CondDTO;

import java.util.List;
import java.util.Map;

/**
 * @description 逻辑类型工厂
 * @author 蝉鸣
 */
public interface LogicTypeService {

    /**
     * 功能描述:
     * 〈逻辑运算类型〉
     * @return 正常返回:{@link LogicTypeEnum}
     * @author 蝉鸣
     */
    LogicTypeEnum logicType();

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    <T> QueryWrapper<T> sqlWrapper(QueryWrapper<T> wrapper, List<CondDTO> condDTOS);

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param wrapper wrapper
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    <T> QueryWrapper<T> sqlDataWrapper(QueryWrapper<T> wrapper, List<CondDTO> condDTOS);


    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTOS condDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Map<EsBoolEnum, Query> esBoolQuery(List<CondDTO> condDTOS);
}
