package com.platform.mesh.search.logic.ref.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.ScriptSource;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.constants.NumberConst;
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
public class LogicNxFactoryImpl implements LogicRefService {

    private final static Logger log = LoggerFactory.getLogger(LogicNxFactoryImpl.class);

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicRefEnum refType() {
        return LogicRefEnum.NOT_EXIST;
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
        String field = condDTO.getColumnMac();
        if(isJson(condDTO.getCompMac())){
            field = StrUtil.subBefore(condDTO.getColumnMac(), SymbolConst.PERIOD, Boolean.TRUE);
        }
        // script 参数
        Map<String, JsonData> params = new HashMap<>();
        params.put("field", JsonData.of(field));
        // 构建 script 查询
        ScriptSource.Builder builder = new ScriptSource.Builder();
        builder.scriptString("""
                    // 检查字段是否存在
                    if (!doc.containsKey(params.field)) {
                        return true;
                    }
                    def fieldValue = doc[params.field];
                    // 检查数组/列表类型
                    if (fieldValue instanceof List) {
                        return fieldValue.size() == 0;
                    }
                    // 获取字段值
                    def value = fieldValue.value;
                    // 检查字符串类型
                    if (value instanceof String) {
                        return value == null || value.trim().isEmpty();
                    }
                    // 检查数值类型
                    if (value == null) {
                        return true;
                    }
                    return false;
            """);
        ScriptSource build = builder.build();
        Script script = new Script.Builder()
                .source(build).params(params)
                .lang("painless")
                .build();

        String finalField = field;
        return BoolQuery.of(b -> b
                .should(s -> s
                        .bool(bb -> bb
                                .mustNot(mn -> mn
                                        .exists(e -> e.field(finalField))
                                )
                        )
                )
                .should(s -> s
                        .bool(bb -> bb
                                .must(m -> m.exists(e -> e.field(finalField)))
                                .must(m -> m.script(sc -> sc.script(script)))
                        )
                )
                .minimumShouldMatch(NumberConst.NUM_1.toString())
        )._toQuery();
    }
}
