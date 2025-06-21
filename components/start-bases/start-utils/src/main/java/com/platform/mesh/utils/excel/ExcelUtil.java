package com.platform.mesh.utils.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.FastExcel;
import cn.idev.excel.write.metadata.WriteSheet;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.utils.excel.bo.DataBO;
import com.platform.mesh.utils.excel.constants.ExcelConst;
import com.platform.mesh.utils.excel.converter.MapConverter;
import com.platform.mesh.utils.excel.dto.HeadDTO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.excel.handler.DataExportCellWriteHandler;
import com.platform.mesh.utils.excel.handler.TempExportCellWriteHandler;
import com.platform.mesh.utils.excel.handler.TempExportRowWriteHandler;
import com.platform.mesh.utils.file.MimeTypeConst;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelUtil {

    protected static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 功能描述:
     * 〈分页数据转换成Excel导出数据〉
     * @return 正常返回:{@link List<List<DataBO>>}
     * @author 蝉鸣
     */
    public static List<List<DataBO>> getDataBOList(List<HeadDTO> headDTOS, List<Object> pageDataList) {
        if(CollUtil.isEmpty(pageDataList)){
            return CollUtil.newArrayList();
        }
        //根据表头顺序组装数据
        return pageDataList.stream().map(record -> {
            JSONObject jsonObject = JSONUtil.parseObj(record);
            List<DataBO> dataBOS = CollUtil.newArrayList();
            headDTOS.forEach(headDTO -> {
                DataBO dataBO = new DataBO();
                dataBO.setKey(headDTO.getHeadName());
                //单元格最大长度32767
                Object value = jsonObject.getOrDefault(headDTO.getHeadMac(), StrUtil.EMPTY);
                //根据组件类型解析对应数据
                value = parseValue(headDTO, value);
                //校验值最大长度：Excel单元最大限制32767
                if(value.toString().length() >= ExcelConst.CELL_MAX_LENGTH){
                    value = ExcelConst.CELL_MAX_LENGTH_NOTICE;
                }
                dataBO.setValue(value);
                dataBOS.add(dataBO);
            });
            return dataBOS;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈Excel导出数据〉
     * @author 蝉鸣
     */
    public static void exportTemp(List<HeadDTO> headDTOS, String fileName, HttpServletResponse response) {
        //获取表头
        AtomicReference<Integer> i = new AtomicReference<>(0);
        List<List<String>> headList = headDTOS.stream().map(head -> {
            i.getAndSet(i.get() + 1);
            List<String> heads = CollUtil.newArrayList();
            //第一行空行用于添加备注
            heads.add(StrUtil.EMPTY);
            //行名称
            heads.add(head.getHeadName());
            return heads;
        }).toList();
        ExcelWriter excelWriter = null;
        try{
            response.setContentType(HttpConst.CONTENT_TYPE_SHEET);
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll(SymbolConst.PATTERN_PLUS, SymbolConst.PATTERN_PLUS_ESCAPE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, HttpConst.FILE_NAME_PREFIX_SHEET + fileName + MimeTypeConst.SUFFIX_EXCEL_X);
            //inMemory(Boolean.TRUE) 富文本 XSSFRichTextString才能生效
            excelWriter = FastExcel.write(response.getOutputStream()).head(headList).inMemory(Boolean.TRUE).registerConverter(new MapConverter()).build();
            WriteSheet sheet = FastExcel.writerSheet()
                    .registerWriteHandler(new TempExportRowWriteHandler())
                    .registerWriteHandler(new TempExportCellWriteHandler())
                    .build();
            excelWriter.write(CollUtil.newArrayList(), sheet);
        }catch (Exception e ){
            logger.error(e.getLocalizedMessage());
        }finally {
            if(ObjectUtil.isNotEmpty(excelWriter)){
                assert excelWriter != null;
                excelWriter.close();
            }
        }
    }

    /**
     * 功能描述:
     * 〈Excel导出数据〉
     * @author 蝉鸣
     */
    public static void exportData(Function<Integer,PageVO<Object>> function, List<HeadDTO> headDTOS, String fileName, HttpServletResponse response) {
        if(CollUtil.isEmpty(headDTOS)){
            return;
        }
        //获取表头
        List<List<String>> headList = headDTOS.stream().map(head -> Collections.singletonList(head.getHeadName())).toList();
        ExcelWriter excelWriter = null;
        try{
            response.setContentType(HttpConst.CONTENT_TYPE_SHEET);
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll(SymbolConst.PATTERN_PLUS, SymbolConst.PATTERN_PLUS_ESCAPE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, HttpConst.FILE_NAME_PREFIX_SHEET + fileName + MimeTypeConst.SUFFIX_EXCEL_X);
            excelWriter = FastExcel.write(response.getOutputStream()).head(headList)
                    .registerWriteHandler(new DataExportCellWriteHandler()).registerConverter(new MapConverter()).build();
            WriteSheet sheet = FastExcel.writerSheet().build();
            Integer pageNum = NumberConst.NUM_1;
            while (true) {
                PageVO<Object> pageVO = function.apply(pageNum);
                if(CollUtil.isEmpty(pageVO.getRecords())){
                    break;
                }
                List<List<DataBO>> dataList = ExcelUtil.getDataBOList(headDTOS, pageVO.getRecords());
                excelWriter.write(dataList, sheet);
                pageNum ++;
            }
        }catch (Exception e ){
            logger.error(e.getMessage());
        }finally {
            if(ObjectUtil.isNotEmpty(excelWriter)){
                assert excelWriter != null;
                excelWriter.close();
            }
        }
    }

    /**
     * 功能描述:
     * 〈Excel导出数据解析处理〉
     * @author 蝉鸣
     */
    public static Object parseValue(HeadDTO headDTO, Object value){
        if(ObjectUtil.isEmpty(value)){
            return value;
        }
        CompTypeEnum enumByDesc = BaseEnum.getEnumByDesc(CompTypeEnum.class, headDTO.getCompMac());
        if(ObjectUtil.isEmpty(enumByDesc)){
            return value;
        }else{
            if(DataTypeEnum.JSON_OBJECT.getValue().equals(enumByDesc.getDataType())){
                JSONObject jsonObject = JSONUtil.parseObj(value);
                return jsonObject.get(enumByDesc.getNameMac());
            } else if (DataTypeEnum.JSON_ARRAY.getValue().equals(enumByDesc.getDataType())) {
                JSONArray jsonArray = JSONUtil.parseArray(value);
                return jsonArray.stream().map(item -> {
                    JSONObject jsonObject = JSONUtil.parseObj(item);
                    return jsonObject.get(enumByDesc.getNameMac());
                }).filter(ObjectUtil::isNotEmpty).map(StrUtil::toString).collect(Collectors.joining());
            }else{
                return value;
            }
        }
    }

}
