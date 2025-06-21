package com.platform.mesh.app.biz.modules.map.conf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfPageDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.po.MapConf;
import com.platform.mesh.app.biz.modules.map.conf.domain.vo.MapConfVO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 地图配置信息
 * @author 蝉鸣
 */
public interface IMapConfService extends IService<MapConf> {

    /***
     * 功能描述:
     * 〈页面地图配置查询〉
     * @param appMapConfPageDTO appMapConfPageDTO
     * @return 正常返回:{@link MPage<MapConf>}
     * @author 蝉鸣
     * @since 2024/8/29 17:08
     */
    MPage<MapConf> selectPage(MapConfPageDTO appMapConfPageDTO);

    /**
     * 功能描述:
     * 〈获取当前地图配置信息〉
     * @param confId confId
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    MapConfVO getMapConfInfoById(Long confId);

    /**
     * 功能描述:
     * 〈新增地图配置〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    MapConfVO addMapConf(MapConfDTO mapConfDTO);

    /**
     * 功能描述:
     * 〈修改地图配置〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    MapConfVO editMapConf(MapConfDTO mapConfDTO);

    /**
     * 功能描述:
     * 〈删除地图配置〉
     * @param confId confId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMapConf(Long confId);

}