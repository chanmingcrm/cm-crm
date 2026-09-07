package com.platform.mesh.douyin.service;

import com.platform.mesh.douyin.domain.feiyu.dto.FeiYuDTO;

import java.util.List;
import java.util.Map;

/**
 * @description 飞鱼接口
 * @author 蝉鸣
 */
public interface IFeiYuService {

    /**
     * 功能描述:
     * 〈查询飞鱼线索〉
     * @param feiYuDTO feiYuDTO
     * @author 蝉鸣
     */
    List<Map<String,Object>> getClueList(FeiYuDTO feiYuDTO);

}
