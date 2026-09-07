package com.platform.mesh.utils.excel.handler;

import cn.hutool.core.util.BooleanUtil;
import com.platform.mesh.core.constants.NumberConst;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.write.handler.RowWriteHandler;
import org.apache.fesod.sheet.write.handler.context.RowWriteHandlerContext;
import org.apache.fesod.sheet.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempExportRowWriteHandler implements RowWriteHandler {

    private final static Logger log = LoggerFactory.getLogger(TempExportRowWriteHandler.class);

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        Sheet sheet = context.getWriteSheetHolder().getSheet();
        sheet.setDefaultRowHeight(NumberConst.NUM_480.shortValue());
        Row row = context.getRow();
        //设置行高
        setRowHeadHeight(context);
        if(NumberConst.NUM_0 == row.getRowNum()){
            //设置备注
            setRowHeadRemark(context);
        }
        if(NumberConst.NUM_1 == row.getRowNum()){
            //设置批注
            //setRowHeadComment(context);
        }
    }

    /**
     * 设置表头行高
     */
    private void setRowHeadHeight(RowWriteHandlerContext context) {
        Row row = context.getRow();
        if (Boolean.TRUE.equals(context.getHead())) {
            if(NumberConst.NUM_0 == row.getRowNum()){
                row.setHeight(NumberConst.NUM_2000.shortValue());
            }else{
                row.setHeight(NumberConst.NUM_600.shortValue());
            }
        }else{
            row.setHeight(NumberConst.NUM_480.shortValue());
        }
    }

    /**
     * 设置表头富文本
     */
    private void setRowHeadRemark(RowWriteHandlerContext context) {
        Row row = context.getRow();
        if(NumberConst.NUM_0 == row.getRowNum()){
            short lastCellNum = row.getFirstCellNum();
            Cell cell = row.getCell(lastCellNum);
            //创建样式
            WriteCellStyle style = new WriteCellStyle();
            //设置文字顶对齐
            style.setVerticalAlignment(VerticalAlignment.TOP);
            //设置文字左对齐
            style.setHorizontalAlignment(HorizontalAlignment.LEFT);
            //转换样式
            CellStyle cellStyle = context.getWriteWorkbookHolder().createCellStyle(style, cell.getCellStyle(), CellDataTypeEnum.RICH_TEXT_STRING);
            //重置样式
            cell.setCellStyle(cellStyle);
            //设置富文本信息
            cell.setCellValue(remarkRickString());
        }
    }

    /**
     * 备注富文本拼接
     */
    private XSSFRichTextString remarkRickString() {
        XSSFRichTextString richTextString = new XSSFRichTextString();
        XSSFFont red = new XSSFFont();
        red.setColor(IndexedColors.RED.getIndex());
        XSSFFont black = new XSSFFont();
        black.setColor(IndexedColors.BLACK.getIndex());
        richTextString.append("注意说明:\r\n",red);
        richTextString.append("1:必填项：表头标",black);
        richTextString.append(" * ",red);
        richTextString.append("的红色字体为必填项\r\n",black);
        richTextString.append("2:日期时间：格式样例 2020-01-01 00:00:00\r\n",black);
//        builder.append("3:手机号：支持6-15位数字（包含国外手机号格式）\r\n");
//        builder.append("4:邮箱：只支持邮箱格式\r\n");
//        builder.append("4:多行文本：字数限制为800字\r\n");
        return richTextString;
    }

    /**
     * 设置批注：案例
     */
    private void setRowHeadComment(RowWriteHandlerContext context) {
        if (BooleanUtil.isTrue(context.getHead())) {
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            //创建批注
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            //创建批注
            Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 1, 0, 0, (short) 1, 1, (short) 2, 2));
            comment.setString(new XSSFRichTextString("这是一个批注"));
            sheet.getRow(1).getCell(1).setCellComment(comment);
        }
    }
}
