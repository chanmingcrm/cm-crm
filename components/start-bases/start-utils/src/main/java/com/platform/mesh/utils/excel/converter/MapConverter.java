package com.platform.mesh.utils.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.platform.mesh.utils.excel.bo.DataBO;
import org.apache.fesod.sheet.converters.Converter;
import org.apache.fesod.sheet.converters.ReadConverterContext;
import org.apache.fesod.sheet.converters.WriteConverterContext;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description FesodSheet 数据处理转换器
 * @author 蝉鸣
 */
public class MapConverter implements Converter<DataBO> {

    private final static Logger log = LoggerFactory.getLogger(MapConverter.class);

    /**
     * 支持数据类型
     */
    @Override
    public Class<DataBO> supportJavaTypeKey() {
        return DataBO.class;
    }

    /**
     * 支持数据类型
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 导入数据处理
     */
    @Override
    public DataBO convertToJavaData(ReadConverterContext<?> context){
        log.info(context.toString());
        return new DataBO();
    }

    /**
     * 导出数据处理
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<DataBO> context){
        log.info(context.toString());
        Object value = context.getValue().getValue();
        return new WriteCellData<>(StrUtil.toString(value));
    }
}
