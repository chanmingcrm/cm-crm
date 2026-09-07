package com.platform.mesh.utils.excel.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.utils.excel.dto.HeadDTO;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.metadata.data.CellData;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.write.handler.CellWriteHandler;
import org.apache.fesod.sheet.write.handler.context.CellWriteHandlerContext;
import org.apache.fesod.sheet.write.metadata.holder.WriteSheetHolder;
import org.apache.fesod.sheet.write.metadata.style.WriteCellStyle;
import org.apache.fesod.sheet.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempExportCellWriteHandler implements CellWriteHandler {

    private final static Logger log = LoggerFactory.getLogger(TempExportCellWriteHandler.class);

    private final Map<Integer, HeadDTO> headMap = new HashMap<>();

    public TempExportCellWriteHandler(List<HeadDTO> headDTOS) {
        // 初始化列索引到列名的映射
        // 使用流式API和Collectors.toMap创建不可变映射
        for (int i = 0; i < headDTOS.size(); i++) {
            headMap.put(i,headDTOS.get(i));
        }
    }

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
            // 设置表头必填
            emptyRickString(context);
//            log.info("已设置样式: 行 {}, 列 {}", context.getRowIndex(), context.getColumnIndex());
        }else{
            //设置单元格格式
            setColumnFormat(context);
            //设置数据校验
            setColumnDataValidation(context);
        }
    }

    /**
     * 设置表头颜色
     * @param context context
     */
    private void setColumnHeadColor(CellWriteHandlerContext context) {
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle style = cellData.getOrCreateStyle();
        // 设置背景颜色
        if(context.getRow().getRowNum() == NumberConst.NUM_0){
            style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        }else{
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        }
        style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
    }

    /**
     * 设置表头字体
     * @param context context
     */
    private void setColumnHeadFont(CellWriteHandlerContext context) {
        Row row = context.getRow();
        if(NumberConst.NUM_0 == row.getRowNum()){
//            setRowHeadRemark(context);
        }else{
            WriteCellData<?> cellData = context.getFirstCellData();
            WriteCellStyle style = cellData.getOrCreateStyle();
            // 设置字体为黄色
            WriteFont font = new WriteFont();
            font.setColor(IndexedColors.YELLOW.getIndex());
            style.setWriteFont(font);
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
        if (columnWidth > NumberConst.NUM_0) {
            // 设置列宽，单位是1/256个字符宽度
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * NumberConst.NUM_2 * NumberConst.NUM_256);
        }
    }

    /**
     * 设置列宽
     * @param context context
     */
    private void setColumnFormat(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        int columnIndex = cell.getColumnIndex();
        if(!headMap.containsKey(columnIndex)){
            return;
        }
        HeadDTO headDTO = headMap.get(columnIndex);
        if(ObjectUtil.isEmpty(headDTO) || !headDTO.getHeadMac().endsWith(StrConst.TIME_SUFFIX)){
            return;
        }
        // 创建并设置样式
        Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.createDataFormat().getFormat(SymbolConst.AT));
        context.getWriteSheetHolder().getSheet().setDefaultColumnStyle(columnIndex, cellStyle);

    }

    /**
     * 设置数据校验
     * @param context context
     */
    private void setColumnDataValidation(CellWriteHandlerContext context) {
        Integer columnIndex = context.getColumnIndex();
        if(!headMap.containsKey(columnIndex)){
            return;
        }
        HeadDTO headDTO = headMap.get(columnIndex);
        if(ObjectUtil.isEmpty(headDTO) || CollUtil.isEmpty(headDTO.getHeadValid())){
            return;
        }
        String[] array = headDTO.getHeadValid().toArray(String[]::new);
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(context.getRow().getRowNum(),context.getRow().getRowNum(),context.getColumnIndex(),context.getColumnIndex());
        DataValidationHelper validationHelper = context.getWriteSheetHolder().getSheet().getDataValidationHelper();
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(array);
        DataValidation dataValidation = validationHelper.createValidation(constraint, cellRangeAddressList);
        context.getWriteSheetHolder().getSheet().addValidationData(dataValidation);
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


    /**
     * 必填富文本拼接
     */
    private void emptyRickString(CellWriteHandlerContext context) {
        //设置必填样式
        Cell cell = context.getCell();
        int columnIndex = cell.getColumnIndex();
        if(!headMap.containsKey(columnIndex)){
            return;
        }
        HeadDTO headDTO = headMap.get(columnIndex);
        if(ObjectUtil.isEmpty(headDTO) || !headDTO.getEmptyFlag().equals(YesOrNoEnum.YES.getValue())){
            return;
        }
        XSSFRichTextString richTextString = new XSSFRichTextString();
        XSSFFont red = new XSSFFont();
        red.setColor(IndexedColors.RED.getIndex());
        richTextString.append(SymbolConst.STAR.concat(SymbolConst.SPACE),red);
        XSSFFont yellow = new XSSFFont();
        yellow.setColor(IndexedColors.YELLOW.getIndex());
        StringBuilder builder = StrUtil.builder();
        builder.append(headDTO.getHeadName());
        richTextString.append(builder.toString(),yellow);
        //设置富文本信息
        cell.setCellValue(richTextString);
    }

}
