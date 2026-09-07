package com.platform.mesh.crm.biz.modules.tmp.work.log.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.domain.po.TmpWorkLogData;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.service.ITmpWorkLogDataService;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.format.TimeUnitEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 工作日志
 * @author 蝉鸣
 */
@Service
public class TmpWorkLogServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TmpWorkLogServiceManual.class);

    @Autowired
    private ITmpWorkLogDataService workLogDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param tmpWorkLogDataList workLogDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<TmpWorkLogData> tmpWorkLogDataList) {
        if(CollUtil.isEmpty(tmpWorkLogDataList)){
            return;
        }
        TmpWorkLogData data = CollUtil.getFirst(tmpWorkLogDataList);
        //删除旧数据
        workLogDataService.lambdaUpdate().eq(TmpWorkLogData::getDataId,data.getDataId()).remove();
        //批量新增信息
        workLogDataService.saveBatch(tmpWorkLogDataList);
    }

    /**
     * 功能描述:
     * 〈获取日志开始时间〉
     * @param timeUnit timeUnit
     * @param now now
     * @author 蝉鸣
     */
    public LocalDateTime getStartTime(Integer timeUnit, LocalDateTime now) {
        TimeUnitEnum enumByValue = BaseEnum.getEnumByValue(TimeUnitEnum.class, timeUnit,TimeUnitEnum.DAY);
        return switch (enumByValue) {
            case WEEK -> DateTimeUtil.getDayStartDateTime(DateTimeUtil.getWeekStartDate(now.toLocalDate()));
            case MONTH -> DateTimeUtil.getDayStartDateTime(DateTimeUtil.getMonthStartDate(now.toLocalDate()));
            default -> DateTimeUtil.getDayStartDateTime(now.toLocalDate());
        };
    }

    /**
     * 功能描述:
     * 〈获取日志结束时间〉
     * @param timeUnit timeUnit
     * @param now now
     * @author 蝉鸣
     */
    public LocalDateTime getEndTime(Integer timeUnit, LocalDateTime now) {
        TimeUnitEnum enumByValue = BaseEnum.getEnumByValue(TimeUnitEnum.class, timeUnit,TimeUnitEnum.DAY);
        return switch (enumByValue) {
            case WEEK -> DateTimeUtil.getDayEndDateTime(DateTimeUtil.getWeekStartDate(now.toLocalDate()));
            case MONTH -> DateTimeUtil.getDayEndDateTime(DateTimeUtil.getMonthStartDate(now.toLocalDate()));
            default -> DateTimeUtil.getDayEndDateTime(now.toLocalDate());
        };
    }
}