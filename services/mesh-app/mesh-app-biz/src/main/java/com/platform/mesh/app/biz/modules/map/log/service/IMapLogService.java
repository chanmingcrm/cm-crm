package com.platform.mesh.app.biz.modules.map.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogPageDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.po.MapLog;
import com.platform.mesh.app.biz.modules.map.log.domain.vo.MapLogVO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 地图打卡信息
 * @author 蝉鸣
 */
public interface IMapLogService extends IService<MapLog> {

    /***
     * 功能描述:
     * 〈页面地图打卡查询〉
     * @param appMapLogPageDTO appMapLogPageDTO
     * @return 正常返回:{@link MPage<MapLog>}
     * @author 蝉鸣
     * @since 2024/8/29 17:08
     */
    MPage<MapLogVO> selectPage(MapLogPageDTO appMapLogPageDTO);

    /**
     * 功能描述:
     * 〈获取当前地图打卡信息〉
     * @param mapLogId mapLogId
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    MapLogVO getMapLogInfoById(Long mapLogId);

    /**
     * 功能描述:
     * 〈新增地图打卡〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    MapLogVO addMapLog(MapLogDTO mapLogDTO);

    /**
     * 功能描述:
     * 〈修改地图打卡〉
     * @param mapLogDTO mapLogDTO
     * @return 正常返回:{@link MapLogVO}
     * @author 蝉鸣
     */
    MapLogVO editMapLog(MapLogDTO mapLogDTO);

    /**
     * 功能描述:
     * 〈删除地图打卡〉
     * @param mapLogId mapLogId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMapLog(Long mapLogId);

}