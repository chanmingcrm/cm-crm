package com.platform.mesh.search.logic.ref;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;

/**
 * @description 逻辑关系工厂
 * @author 蝉鸣
 */
public interface LogicRefService {

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    LogicRefEnum refType();

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    <T> QueryWrapper<T> sqlWrapper(QueryWrapper<T> wrapper, CondDTO condDTO);

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    <T> QueryWrapper<T> sqlDataWrapper(QueryWrapper<T> wrapper,CondDTO condDTO);


    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param condDTO condDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Query esQuery(CondDTO condDTO);

    /**
     * 功能描述:
     * 〈获取es查询字段名称〉
     * @param condDTO condDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    default String getEsColumnMac(CondDTO condDTO){
        String columnMac = condDTO.getColumnMac();
        if(!isJson(condDTO.getCompMac())){
            return columnMac;
        }else{
            if (columnMac.contains(SymbolConst.AT)){
                return columnMac.replace(SymbolConst.AT,SymbolConst.PERIOD);
            }else{
                return columnMac.concat(SymbolConst.PERIOD).concat(StrConst.ID);
            }
        }
    }

    /**
     * 功能描述:
     * 〈符合es查询条件〉
     * @param condDTO condDTO
     * @return 正常返回:{@link Query}
     * @author 蝉鸣
     */
    default Query getEsUniQuery(CondDTO condDTO){
        //解析字段名称
        String columnMac = getEsColumnMac(condDTO);
        //重置字段名称
        condDTO.setColumnMac(columnMac);
        //封装查询条件
        return esQuery(condDTO);
    }

    /**
     * 功能描述:
     * 〈符合es嵌套查询〉
     * @param compMac compMac
     * @return 正常返回:{@link Query}
     * @author 蝉鸣
     */
    default Boolean isJson(String compMac){
        CompTypeEnum enumByDesc = BaseEnum.getEnumByDesc(CompTypeEnum.class, compMac);
        if(ObjectUtil.isEmpty(enumByDesc)){
            return Boolean.FALSE;
        }else{
            return Boolean.TRUE;
        }
    }
}
