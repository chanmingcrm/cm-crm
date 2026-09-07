package com.platform.mesh.search.logic.ref.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.ScriptSource;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.constants.SearchColumnConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.search.logic.ref.LogicRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 逻辑关系工厂实现
 * @author 蝉鸣
 */
@Service
public class LogicExFactoryImpl implements LogicRefService {

    private final static Logger log = LoggerFactory.getLogger(LogicExFactoryImpl.class);

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicRefEnum refType() {
        return LogicRefEnum.EXIST;
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
        return wrapper.notLike(condDTO.getColumnMac(), CollUtil.getFirst(condDTO.getSearchValues()));
    };

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
        wrapper.notLike(SearchColumnConst.DATA_COLUMN_VALUE, CollUtil.getFirst(condDTO.getSearchValues()));
        return wrapper;
    };

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
        // script 参数
        Map<String, JsonData> params = new HashMap<>();
        params.put("field", JsonData.of(condDTO.getColumnMac()));
        // 构建 script 查询
        ScriptSource.Builder builder = new ScriptSource.Builder();
        builder.scriptString("""
                    doc[params.field].value == null
            """);
        ScriptSource build = builder.build();
        Script script = new Script.Builder()
                .source(build).params(params)
                .lang("painless")
                .build();
        return BoolQuery.of(b -> b
                .must(m -> m.exists(e -> e.field(condDTO.getColumnMac())))
                .mustNot(mn -> mn
                        .term(t -> t
                                .field(condDTO.getColumnMac())
                                .value(SymbolConst.BLANK)
                        )
                )
                .mustNot(mn -> mn.script(s -> s.script(script)))
        )._toQuery();
    }
}
