package com.platform.mesh.app.biz.modules.map.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogPageDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.po.MapLog;
import com.platform.mesh.app.biz.modules.map.log.domain.vo.MapLogVO;
import com.platform.mesh.app.biz.modules.map.log.exception.MapLogExceptionEnum;
import com.platform.mesh.app.biz.modules.map.log.mapper.MapLogMapper;
import com.platform.mesh.app.biz.modules.map.log.service.IMapLogService;
import com.platform.mesh.app.biz.modules.map.log.service.manual.MapLogServiceManual;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 地图打卡
 * @author 蝉鸣
 */
@Service
public class MapLogServiceImpl extends ServiceImpl<MapLogMapper, MapLog> implements IMapLogService {

    @Autowired
    private MapLogServiceManual mapLogServiceManual;


    /***
     * 功能描述:
     * 〈获取表单分页〉
     * @param mapLogPageDTO mapLogPageDTO
     * @return 正常返回:{@link MPage<MapLog>}
     * @author 蝉鸣
     * @since 2024/8/29 17:12
     */
    @Override
    public MPage<MapLogVO> selectPage(MapLogPageDTO mapLogPageDTO) {
        MPage<MapLog> mapLogMPage = MPageUtil.pageEntityToMPage(mapLogPageDTO,MapLog.class);
        MPage<MapLog> logMPage = this.lambdaQuery()
                .eq(MapLog::getDataId,mapLogPageDTO.getDataId())
                .eq(MapLog::getDelFlag,YesOrNoEnum.YES.getValue())
                .gt(ObjectUtil.isNotEmpty(mapLogPageDTO.getStartTime()),MapLog::getCreateTime,mapLogPageDTO.getStartTime())
                .le(ObjectUtil.isNotEmpty(mapLogPageDTO.getEndTime()),MapLog::getCreateTime,mapLogPageDTO.getEndTime())
                .orderByDesc(MapLog::getCreateTime).page(mapLogMPage);
        if(CollUtil.isEmpty(logMPage.getRecords())){
            return new MPage<>();
        }
        MPage<MapLogVO> voPage = MPageUtil.convertToPage(logMPage, MapLogVO.class);
        mapLogServiceManual.packVO(voPage.getRecords());
        return voPage;
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param logId logId
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    @Override
    public MapLogVO getMapLogInfoById(Long logId) {
        MapLog mapLog = this.getById(logId);
        return BeanUtil.copyProperties(mapLog, MapLogVO.class);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    @Override
    public MapLogVO addMapLog(MapLogDTO mapLogDTO) {
        MapLog mapLog = BeanUtil.copyProperties(mapLogDTO, MapLog.class);
        if(ObjectUtil.isNull(mapLog.getBatchId())){
            mapLog.setBatchId(IdUtil.getSnowflakeNextId());
        }
        mapLog.setDelFlag(YesOrNoEnum.YES.getValue());
        this.save(mapLog);
        //保存关联信息
        mapLogServiceManual.addRel(mapLog.getId(),mapLogDTO);
        return BeanUtil.copyProperties(mapLog, MapLogVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    @Override
    public MapLogVO editMapLog(MapLogDTO mapLogDTO) {
        MapLog mapLog = getById(mapLogDTO.getId());
        if(ObjectUtil.isEmpty(mapLog)){
            throw MapLogExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        BeanUtil.copyProperties(mapLogDTO, mapLog);
        this.updateById(mapLog);
        //保存关联信息
        mapLogServiceManual.addRel(mapLog.getId(),mapLogDTO);
        return BeanUtil.copyProperties(mapLog, MapLogVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param mapLogId mapLogId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMapLog(Long mapLogId) {
        this.lambdaUpdate().set(MapLog::getDelFlag,YesOrNoEnum.NO.getValue()).eq(MapLog::getId, mapLogId).update();
        return Boolean.TRUE;
    }
}
