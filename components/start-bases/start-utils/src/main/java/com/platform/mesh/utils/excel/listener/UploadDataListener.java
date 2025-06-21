package com.platform.mesh.utils.excel.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.platform.mesh.core.constants.StrConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class UploadDataListener extends AnalysisEventListener<Map<Integer,Object>> {

    private final static Logger log = LoggerFactory.getLogger(UploadDataListener.class);

    /**
     * 默认批量处理阈值
     */
    public static int BATCH_COUNT = 10;

    /**
     * 批量处理数据缓存
     */
    private List<Map<String,Object>> cachedDataList;

    /**
     * 函数
     */
    private final Consumer<List<Map<String,Object>>> consumer;

    /**
     * 批量处理阈值
     */
    private final int batchCount;

    /**
     * Excel原始列头信息
     */
    private Map<String, String> columnMap;
    /**
     * Excel转义后列头信息
     */
    private final Map<String, String> headTransMap = new HashMap<>();

    public UploadDataListener(Consumer<List<Map<String,Object>>> consumer, Map<String, String> headMap) {
        this(consumer, BATCH_COUNT);
        this.columnMap = headMap;
    }

    public UploadDataListener(Consumer<List<Map<String,Object>>> consumer, int batchCount) {
        this.cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        this.consumer = consumer;
        this.batchCount = batchCount;
    }

    /**
     * 捕获并处理解析过程中抛出的异常，方便处理错误数据。
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败: {}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException ex = (ExcelDataConvertException) exception;
            log.error("第 {} 行, 第 {} 列解析异常, 数据: {}", ex.getRowIndex(), ex.getColumnIndex(), ex.getCellData());
        }
    }

    /**
     * 获取 Excel 表头数据，常用于动态表头处理。
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到表头: {}", JSON.toJSONString(columnMap));
        headMap.forEach((key, value) -> {
            if(columnMap.containsKey(value.getStringValue())){
                String columnMac = columnMap.get(value.getStringValue());
                headTransMap.put(key.toString(),StrUtil.toUnderlineCase(columnMac));
            }
        });
        log.info("解析到表头: {}", JSON.toJSONString(headTransMap));
    }

    /**
     * 当读取到一行数据时触发，data 为解析后的当前行数据，context 为读取上下文。
     */
    @Override
    public void invoke(Map<Integer,Object> data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        HashMap<String, Object> dataMap = new HashMap<>();
        //添加ID
        dataMap.put(StrConst.ID, IdUtil.getSnowflake().nextId());
        //添加其他数据
        data.forEach((key, value) -> {
            if(headTransMap.containsKey(StrUtil.toString(key))){
                dataMap.put(headTransMap.get(StrUtil.toString(key)),value);
            }
        });
        this.cachedDataList.add(dataMap);
        if (this.cachedDataList.size() >= this.batchCount) {
            this.consumer.accept(this.cachedDataList);
            this.cachedDataList = ListUtils.newArrayListWithExpectedSize(this.batchCount);
        }
    }

    /**
     * 在所有数据解析完成后调用，用于资源清理或批量操作后处理。
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollUtil.isNotEmpty(this.cachedDataList)) {
            this.consumer.accept(this.cachedDataList);
        }
    }
}
