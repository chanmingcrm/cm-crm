package com.platform.mesh.crm.biz.modules.tmp.work.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.dto.TmpWorkLogDTO;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.po.TmpWorkLog;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.vo.TmpWorkLogVO;
import com.platform.mesh.crm.biz.modules.tmp.work.log.mapper.TmpWorkLogMapper;
import com.platform.mesh.crm.biz.modules.tmp.work.log.service.ITmpWorkLogService;
import com.platform.mesh.crm.biz.modules.tmp.work.log.service.manual.TmpWorkLogServiceManual;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.domain.po.TmpWorkLogData;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 工作日志
 * @author 蝉鸣
 */
@Service
public class TmpTmpWorkLogServiceImpl extends AppServiceAbstract<TmpWorkLogMapper, TmpWorkLog> implements ITmpWorkLogService {

    @Autowired
    private TmpWorkLogServiceManual tmpWorkLogServiceManual;


    /**
     * 功能描述:
     * 〈新增工作日志〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<TmpWorkLogData> tmpWorkLogDataData = BeanUtil.copyToList(dataList, TmpWorkLogData.class);
        //批量保存data表数据
        tmpWorkLogServiceManual.addDbDataBatch(tmpWorkLogDataData);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(TmpWorkLog::getScopeUserId,scopeUserId)
                .set(TmpWorkLog::getScopeOrgId,scopeOrgId)
                .in(TmpWorkLog::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈一键生成/日报/周报/月报〉
     * @param tmpWorkLogDTO workLogDTO
     * @author 蝉鸣
     */
    @Override
    public TmpWorkLogVO getOneKeyLog(TmpWorkLogDTO tmpWorkLogDTO) {
        TmpWorkLogVO tmpWorkLogVO = new TmpWorkLogVO();
        LocalDateTime startTime = tmpWorkLogServiceManual.getStartTime(tmpWorkLogDTO.getTimeUnit(), LocalDateTime.now());
        LocalDateTime endTime = tmpWorkLogServiceManual.getEndTime(tmpWorkLogDTO.getTimeUnit(), LocalDateTime.now());
        //获取期间所有日报信息
        List<TmpWorkLog> logList = this.lambdaQuery()
                .between(TmpWorkLog::getCreateTime, startTime, endTime)
                .eq(TmpWorkLog::getCreateUserId, UserCacheUtil.getUserId())
                .list();
        if(CollUtil.isEmpty(logList)){
            return tmpWorkLogVO;
        }
        StringBuilder builder = StrUtil.builder();
        logList.forEach(log->{
            builder.append(DateTimeUtil.localDateToStr2(log.getCreateTime().toLocalDate())).append(SymbolConst.RETURN_NEW_LINE);
            builder.append(log.getDataName()).append(SymbolConst.SEMICOLON).append(SymbolConst.RETURN_NEW_LINE);
        });
        tmpWorkLogVO.setDataName(builder.toString());
        return tmpWorkLogVO;
    }


}