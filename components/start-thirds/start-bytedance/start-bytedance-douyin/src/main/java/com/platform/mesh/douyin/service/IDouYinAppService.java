package com.platform.mesh.douyin.service;

import com.platform.mesh.douyin.domain.feiyu.dto.DouYinPageDTO;
import com.platform.mesh.douyin.domain.feiyu.dto.DouyinAppDTO;

import java.util.List;
import java.util.Map;

/**
 * @description 抖音应用接口
 * @author 蝉鸣
 */
public interface IDouYinAppService {

    /**
     * 功能描述:
     * 〈查询飞鱼线索〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    List<Map<String,Object>> getClueList(DouyinAppDTO appDTO, DouYinPageDTO pageDTO);

}
