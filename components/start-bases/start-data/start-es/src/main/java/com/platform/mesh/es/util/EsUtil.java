package com.platform.mesh.es.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.JsonData;
import com.platform.mesh.core.application.domain.dto.SortDTO;
import com.platform.mesh.core.constants.DateConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.enums.EsBoolEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;

import java.util.List;
import java.util.Map;

/**
 * @description Es处理工具类
 * @author 蝉鸣
 */
public class EsUtil {

    /**
     * 功能描述:
     * 〈创建搜索builder〉
     * @param docGetDTO docGetDTO
     * @return 正常返回:{@link EsDocGetBO}
     * @author 蝉鸣
     */
    public static EsDocGetBO esPageDtoToBO(EsDocPGetDTO docGetDTO){
        //对象转化
        EsDocGetBO esDocGetBO = BeanUtil.copyProperties(docGetDTO, EsDocGetBO.class
                , ObjFieldUtil.getFieldName(EsDocGetBO::getSorts)
                , ObjFieldUtil.getFieldName(EsDocGetBO::getSearchAfter));
        //设置排序字段
        esDocGetBO.setSortOptions(getSortOptionsByKind(docGetDTO.getSorts()));
        //设置搜索后置
        esDocGetBO.setSearchAfter(getFieldValueByKind(docGetDTO.getSearchAfter()));
        return esDocGetBO;
    }

    /**
     * 功能描述:
     * 〈创建搜索builder〉
     * @param docGetDTO docGetDTO
     * @param esBoolQuery esBoolQuery
     * @return 正常返回:{@link EsDocGetBO}
     * @author 蝉鸣
     */
    public static EsDocGetBO esPageDtoToBO(EsDocPGetDTO docGetDTO, BoolQuery.Builder esBoolQuery){
        //对象转化
        EsDocGetBO esDocGetBO = esPageDtoToBO(docGetDTO);
        //设置条件
        esDocGetBO.setBoolBuilder(esBoolQuery);
        return esDocGetBO;
    }

    /**
     * 功能描述:
     * 〈创建搜索builder〉
     * @param indexName indexName
     * @return 正常返回:{@link SearchRequest.Builder}
     * @author 蝉鸣
     */
    public static SearchRequest.Builder getSearchBuilder(String indexName){
        //bool复杂条件查询
        SearchRequest.Builder searchBuilder = new SearchRequest.Builder();
        //设置索引
        searchBuilder.index(indexName);
        return searchBuilder;
    }

    /**
     * 功能描述:
     * 〈设置search分页〉
     * @param searchBuilder searchBuilder
     * @param esDocGetBO esDocGetBO
     * @author 蝉鸣
     */
    public static void setSearchBuilderPage(SearchRequest.Builder searchBuilder, EsDocGetBO esDocGetBO){
        Integer pageNum = esDocGetBO.getPageNum();
        Integer pageSize = esDocGetBO.getPageSize();
        //设置分页
        if (ObjectUtil.isNotEmpty(pageNum) && ObjectUtil.equals(pageSize, NumberConst.NUM__1)) {
            return;
        }
        //小于10000 使用 from size 进行分页查询
        if((pageNum * pageSize) > NumberConst.NUM_10000){
            //大于10000 使用 searchAfter 进行分页查询
            String pit = esDocGetBO.getPit();
            List<FieldValue> searchAfter = esDocGetBO.getSearchAfter();
            //如果为空则重置首页
            if(ObjectUtil.isEmpty(pit) || ObjectUtil.isEmpty(searchAfter)){
                pageNum = NumberConst.NUM_1;
            }else{
                //使用SearchAfter from 必须是0 或者 -1
                searchBuilder.from(NumberConst.NUM_0);
                searchBuilder.size(pageSize);
                searchBuilder.pit(builder -> builder.id(pit));
                searchBuilder.searchAfter(searchAfter);
                return;
            }
        }
        //Hu_tool page 页面起始值是0
        PageUtil.setOneAsFirstPageNo();
        searchBuilder.from(PageUtil.getStart(pageNum, pageSize));
        searchBuilder.size(pageSize);
    }

    /**
     * 功能描述:
     * 〈获取Bool查询实例〉
     * @return 正常返回:{@link BoolQuery.Builder}
     * @author 蝉鸣
     */
    public static BoolQuery.Builder getSearchBuilderBool(){
        //设置筛选条件
        BoolQuery.Builder builder = new BoolQuery.Builder();
        builder.must(CollUtil.newArrayList());
        builder.mustNot(CollUtil.newArrayList());
        builder.should(CollUtil.newArrayList());
        return builder;
    }

    /**
     * 功能描述:
     * 〈设置Bool查询条件〉
     * @param boolBuilder boolBuilder
     * @param boolEnum    boolEnum
     * @param queries     queries
     * @author 蝉鸣
     */
    public static void setSearchBuilderBool(BoolQuery.Builder boolBuilder, EsBoolEnum boolEnum, List<Query> queries){
        //设置筛选条件
        if(EsBoolEnum.MUST.equals(boolEnum)){
            boolBuilder.must(queries);
        }
        if (EsBoolEnum.MUST_NOT.equals(boolEnum)) {
            boolBuilder.mustNot(queries);
        }
        if(EsBoolEnum.SHOULD.equals(boolEnum)){
            boolBuilder.should(queries);
        }
        if(EsBoolEnum.FILTER.equals(boolEnum)){
            boolBuilder.filter(queries);
        }
    }

    /**
     * 功能描述:
     * 〈设置Bool查询条件〉
     * @param boolBuilder boolBuilder
     * @param queryMap queryMap
     * @return 正常返回:{@link BoolQuery.Builder}
     * @author 蝉鸣
     */
    public static BoolQuery.Builder setSearchBuilderBool(BoolQuery.Builder boolBuilder, Map<EsBoolEnum,List<Query>> queryMap){
        //设置筛选条件
        queryMap.forEach((key,value)->{
            setSearchBuilderBool(boolBuilder,key,value);
        });
        return boolBuilder;
    }

    /**
     * 功能描述:
     * 〈设置查询实例〉
     * @param searchBuilder searchBuilder
     * @param boolBuilder boolBuilder
     * @author 蝉鸣
     */
    public static void setSearchBuilderQuery(SearchRequest.Builder searchBuilder,BoolQuery.Builder boolBuilder){
        //设置筛选条件
        searchBuilder.query(query -> query.bool(boolBuilder.build()));
    }

    /**
     * 功能描述:
     * 〈设置查询实例〉
     * @param searchBuilder searchBuilder
     * @param boolBuilder   boolBuilder
     * @param queryMap      queryMap
     * @author 蝉鸣
     */
    public static void setSearchBuilderQuery(SearchRequest.Builder searchBuilder
            , BoolQuery.Builder boolBuilder, Map<EsBoolEnum,List<Query>> queryMap){
        BoolQuery.Builder builder = setSearchBuilderBool(boolBuilder, queryMap);
        //设置筛选条件
        setSearchBuilderQuery(searchBuilder,builder);
    }

    /**
     * 功能描述:
     * 〈设置查询排序〉
     * @param searchBuilder searchBuilder
     * @author 蝉鸣
     */
    public static void setSearchBuilderSort(SearchRequest.Builder searchBuilder
            , List<SortOptions> sortOptions){
        if(CollUtil.isNotEmpty(sortOptions)){
            searchBuilder.sort(sortOptions);
        }
    }

    /**
     * 功能描述:
     * 〈根据类型生成对应Property〉
     * @param kindStr kindStr
     * @return 正常返回:{@link Property}
     * @author 蝉鸣
     */
    public static Property getPropertyByKind(String kindStr){
        Property.Kind kind;
        if(ObjectUtil.isEmpty(kindStr)){
            kind = Property.Kind.Keyword;
        }else{
            kind = Property.Kind.valueOf(StrUtil.upperFirst(kindStr));
        }
        return switch (kind) {
            case AggregateMetricDouble -> AggregateMetricDoubleProperty.of(builder -> builder)._toProperty();
            case Alias -> FieldAliasProperty.of(builder -> builder)._toProperty();
            case Binary -> BinaryProperty.of(builder -> builder)._toProperty();
            case Byte -> ByteNumberProperty.of(builder -> builder)._toProperty();
            case Completion -> CompletionProperty.of(builder -> builder)._toProperty();
            case ConstantKeyword -> ConstantKeywordProperty.of(builder -> builder)._toProperty();
            case DateNanos -> DateNanosProperty.of(builder -> builder)._toProperty();
            case Date -> DateProperty.of(builder -> {
                builder.format(DateConst.ES_YYYY_MM__DD__HH_MM_SS);
                return builder;
            })._toProperty();
            case DateRange -> DateRangeProperty.of(builder -> {
                builder.format(DateConst.ES_YYYY_MM__DD__HH_MM_SS);
                return builder;
            })._toProperty();
            case DynamicType -> DynamicProperty.of(builder -> builder)._toProperty();
            case Flattened -> FlattenedProperty.of(builder -> builder)._toProperty();
            case Float -> FloatNumberProperty.of(builder -> builder)._toProperty();
            case FloatRange -> FloatRangeProperty.of(builder -> builder)._toProperty();
            case Keyword -> KeywordProperty.of(builder -> {
                builder.ignoreAbove(NumberConst.NUM_256);
                builder.normalizer(EsConst.NORMALIZER_LOWERCASE);
                builder.fields(EsConst.SORT, s -> {
                    s._custom(EsConst.SORT,JSONUtil.createObj().putOpt(EsConst.MAPPING_TYPE, EsConst.ICU_COLLATION_KEYWORD).putOpt(EsConst.LANGUAGE, EsConst.CN).putOpt(EsConst.COUNTRY, EsConst.CN));
                    return s;
                });
                return builder;
            })._toProperty();
            case GeoPoint -> GeoPointProperty.of(builder -> builder)._toProperty();
            case GeoShape -> GeoShapeProperty.of(builder -> builder)._toProperty();
            case HalfFloat -> HalfFloatNumberProperty.of(builder -> builder)._toProperty();
            case Histogram -> HistogramProperty.of(builder -> builder)._toProperty();
            case Integer -> IntegerNumberProperty.of(builder -> builder)._toProperty();
            case IntegerRange -> IntegerRangeProperty.of(builder -> builder)._toProperty();
            case Ip -> IpProperty.of(builder -> builder)._toProperty();
            case IpRange -> IpRangeProperty.of(builder -> builder)._toProperty();
            case Join -> JoinProperty.of(builder -> builder)._toProperty();
            case Long -> LongNumberProperty.of(builder -> builder)._toProperty();
            case LongRange -> LongRangeProperty.of(builder -> builder)._toProperty();
            case MatchOnlyText -> MatchOnlyTextProperty.of(builder -> builder)._toProperty();
            case Murmur3 -> Murmur3HashProperty.of(builder -> builder)._toProperty();
            case Nested -> NestedProperty.of(builder -> builder)._toProperty();
            case Object -> ObjectProperty.of(builder -> builder)._toProperty();
            case Percolator -> PercolatorProperty.of(builder -> builder)._toProperty();
            case Point -> PointProperty.of(builder -> builder)._toProperty();
            case RankFeature -> RankFeatureProperty.of(builder -> builder)._toProperty();
            case RankFeatures -> RankFeaturesProperty.of(builder -> builder)._toProperty();
            case ScaledFloat -> ScaledFloatNumberProperty.of(builder -> builder)._toProperty();
            case SearchAsYouType -> SearchAsYouTypeProperty.of(builder -> builder)._toProperty();
            case Shape -> ShapeProperty.of(builder -> builder)._toProperty();
            case Short -> ShortNumberProperty.of(builder -> builder)._toProperty();
            case SparseVector -> SparseVectorProperty.of(builder -> builder)._toProperty();
            case TokenCount -> TokenCountProperty.of(builder -> builder)._toProperty();
            case UnsignedLong -> UnsignedLongNumberProperty.of(builder -> builder)._toProperty();
            case Version -> VersionProperty.of(builder -> builder)._toProperty();
            case Wildcard -> WildcardProperty.of(builder -> builder)._toProperty();
            default -> TextProperty.of(builder -> builder
                    .analyzer(EsConst.ANALYZER_ICU)
                    .fields(EsConst.KEYWORD, s -> {
                        s._custom(EsConst.KEYWORD,JSONUtil.createObj().putOpt(EsConst.MAPPING_TYPE, EsConst.KEYWORD).putOpt(EsConst.IGNORE_ABOVE, NumberConst.NUM_256));
                        return s;
                    })
            )._toProperty();
        };
    }

    /**
     * 功能描述:
     * 〈根据类型生成对应SortOptions〉
     * @param kindStr kindStr
     * @param sortObj sortObj
     * @param isAsc isAsc
     * @return 正常返回:{@link SortOptions}
     * @author 蝉鸣
     */
    public static SortOptions getSortOptionsByKind(String kindStr,Object sortObj,Boolean isAsc){
        if(ObjectUtil.isEmpty(sortObj)){
            return null;
        }
        if(StrUtil.isBlank(kindStr)){
            kindStr = SortOptions.Kind.Field.name();
        }
        SortOptions.Kind kind = SortOptions.Kind.valueOf(kindStr);
        SortOrder sortOrder = isAsc ? SortOrder.Asc : SortOrder.Desc;
        return switch (kind) {
            case Score -> SortOptions.of(builder -> builder.score(score->score.order(sortOrder)));
            case Doc -> SortOptions.of(builder -> builder.doc(doc->doc.order(sortOrder)));
            case GeoDistance -> SortOptions.of(builder -> builder.geoDistance(geo->geo.field(sortObj.toString()).order(sortOrder)));
            case Script -> SortOptions.of(builder -> builder.script(script->script.order(sortOrder)));
            default -> SortOptions.of(builder -> builder.field(field->field.field(sortObj.toString()).order(sortOrder)));
        };
    }

    /**
     * 功能描述:
     * 〈根据类型生成对应SortOptions〉
     * @param sortDTOS sortDTOS
     * @return 正常返回:{@link SortOptions}
     * @author 蝉鸣
     */
    public static List<SortOptions> getSortOptionsByKind(List<SortDTO> sortDTOS){
        if(CollUtil.isEmpty(sortDTOS)){
            //如果是空则执行默认排序，ID+修改时间
            List<String> sortColumns = CollUtil.newArrayList(StrConst.ID, StrConst.UPDATE_TIME);
            return sortColumns.stream().map(column -> getSortOptionsByKind(SortOptions.Kind.Field.name(), column, Boolean.FALSE)).toList();
        }
        return sortDTOS.stream().map(sortDTO -> getSortOptionsByKind(sortDTO.getKind(), sortDTO.getSortColumn(), sortDTO.getIsAsc())).toList();
    }

    /**
     * 功能描述:
     * 〈根据类型生成对应FieldValue〉
     * @param kindStr kindStr
     * @param valueObj valueObj
     * @return 正常返回:{@link FieldValue}
     * @author 蝉鸣
     */
    public static FieldValue getFieldValueByKind(String kindStr,Object valueObj){
        if(ObjectUtil.isEmpty(valueObj)){
            return null;
        }
        if(StrUtil.isBlank(kindStr)){
            kindStr = FieldValue.Kind.Any.toString();
        }
        FieldValue.Kind kind = FieldValue.Kind.valueOf(kindStr);
        return switch (kind) {
            case Double -> FieldValue.of(builder -> builder.doubleValue((Double) valueObj));
            case Long -> FieldValue.of(builder -> builder.longValue((Long) valueObj));
            case Boolean -> FieldValue.of(builder -> builder.booleanValue((Boolean) valueObj));
            case String -> FieldValue.of(builder -> builder.stringValue(valueObj.toString()));
            case Null -> FieldValue.of(FieldValue.Builder::nullValue);
            default ->  FieldValue.of(builder -> builder.anyValue(JsonData.of(valueObj)));
        };
    }

    /**
     * 功能描述:
     * 〈根据类型生成对应FieldValue〉
     * @param fieldValueMaps fieldValueMaps：key kind {@link FieldValue.Kind},value object
     * @return 正常返回:{@link FieldValue}
     * @author 蝉鸣
     */
    public static List<FieldValue> getFieldValueByKind(List<Map<String,Object>> fieldValueMaps){
        if(CollUtil.isEmpty(fieldValueMaps)){
            return CollUtil.newArrayList();
        }
        List<FieldValue> fieldValues = CollUtil.newArrayList();
        fieldValueMaps.forEach(fieldValue->{
            fieldValue.forEach((kindStr,valueObj)->{
                FieldValue fieldValueByKind = getFieldValueByKind(kindStr, valueObj);
                fieldValues.add(fieldValueByKind);
            });
        });
        return fieldValues;
    }


}
