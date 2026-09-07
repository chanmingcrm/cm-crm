package com.platform.mesh.utils.excel.handler;

import cn.hutool.core.util.BooleanUtil;
import com.platform.mesh.core.constants.NumberConst;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.metadata.data.CellData;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.write.handler.CellWriteHandler;
import org.apache.fesod.sheet.write.handler.context.CellWriteHandlerContext;
import org.apache.fesod.sheet.write.metadata.holder.WriteSheetHolder;
import org.apache.fesod.sheet.write.metadata.style.WriteCellStyle;
import org.apache.fesod.sheet.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataExportCellWriteHandler implements CellWriteHandler {

    private final static Logger log = LoggerFactory.getLogger(DataExportCellWriteHandler.class);

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        // 确保只操作表头
        if (BooleanUtil.isTrue(context.getHead())) {
            // 设置表头颜色
            setColumnHeadColor(context);
            // 设置表头字体
            setColumnHeadFont(context);
            // 设置表头列宽
            setColumnHeadWidth(context);
        }
        // 设置行高
        setRowHeight(context);
    }

    /**
     * 设置表头颜色
     * @param context context
     */
    private void setColumnHeadColor(CellWriteHandlerContext context) {
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle style = cellData.getOrCreateStyle();
        // 设置背景颜色为绿色
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
    }

    /**
     * 设置表头字体
     * @param context context
     */
    private void setColumnHeadFont(CellWriteHandlerContext context) {
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle style = cellData.getOrCreateStyle();
        // 设置字体为黄色
        WriteFont font = new WriteFont();
        font.setColor(IndexedColors.YELLOW.getIndex());
        style.setWriteFont(font);

    }

    /**
     * 设置表头行高
     */
    private void setRowHeight(CellWriteHandlerContext context) {
        Row row = context.getRow();
        if (Boolean.TRUE.equals(context.getHead())) {
            row.setHeight(NumberConst.NUM_600.shortValue());
        }else{
            row.setHeight(NumberConst.NUM_480.shortValue());
        }
    }

    /**
     * 设置列宽
     * @param context context
     */
    private void setColumnHeadWidth(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        WriteSheetHolder writeSheetHolder = context.getWriteSheetHolder();
        // 根据业务逻辑计算列宽
        int columnWidth = calculateColumnWidth(context.getCellDataList(), context.getCell(), context.getHead());
        // 设置默认列宽
        if (columnWidth <= NumberConst.NUM_0) {
            return;
        }
        if(columnWidth > NumberConst.NUM_16){
            columnWidth = NumberConst.NUM_16;
        }
        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * NumberConst.NUM_2 * NumberConst.NUM_256);
    }

    /**
     * 自定义列宽计算方法
     * @param cellDataList cellDataList
     * @param cell cell
     * @param isHead isHead
     * @return Integer
     */
    private Integer calculateColumnWidth(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData<?> cellData = cellDataList.getFirst();
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return NumberConst.NUM__1;
            } else {
                return switch (type) {
                    case STRING -> {
                        // 换行符（数据需要提前解析好）
                        int index = cellData.getStringValue().indexOf("\n");
                        yield index != NumberConst.NUM__1 ?
                                cellData.getStringValue().substring(NumberConst.NUM_0, index).getBytes().length + NumberConst.NUM_1 : cellData.getStringValue().getBytes().length + NumberConst.NUM_1;
                    }
                    case BOOLEAN -> cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER -> cellData.getNumberValue().toString().getBytes().length;
                    default -> NumberConst.NUM__1;
                };
            }
        }
    }
}
