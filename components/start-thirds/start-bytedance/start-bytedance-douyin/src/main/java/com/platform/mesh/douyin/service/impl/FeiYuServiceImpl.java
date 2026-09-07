package com.platform.mesh.douyin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.bytedance.ads.ApiClient;
import com.bytedance.ads.api.ToolsClueGetV2Api;
import com.bytedance.ads.model.ToolsClueGetV2Response;
import com.bytedance.ads.model.ToolsClueGetV2ResponseDataListInner;
import com.platform.mesh.douyin.domain.feiyu.dto.FeiYuDTO;
import com.platform.mesh.douyin.service.IFeiYuService;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 飞鱼接口
 * @author 蝉鸣
 */
@Service
public class FeiYuServiceImpl implements IFeiYuService {

    private final static Logger log = LoggerFactory.getLogger(FeiYuServiceImpl.class);

    /**
     * 功能描述:
     * 〈查询飞鱼线索〉
     * @param feiYuDTO feiYuDTO
     * @author 蝉鸣
     */
    @Override
    public List<Map<String,Object>> getClueList(FeiYuDTO feiYuDTO) {
        List<Long> advertiserIds = feiYuDTO.getAdvertiserIds();
        String startTime = DateTimeUtil.localDateToStr2(feiYuDTO.getStartTime().toLocalDate());
        String endTime = DateTimeUtil.localDateToStr2(feiYuDTO.getEndTime().toLocalDate());;
        Integer page = feiYuDTO.getPageNum();
        Integer pageSize = feiYuDTO.getPageSize();
        List<Long> clueIds = null;
        Boolean encryptSensitiveFields = Boolean.TRUE;
        ToolsClueGetV2Api api = new ToolsClueGetV2Api();
        ApiClient apiClient = api.getApiClient();
        apiClient.addDefaultHeader("Access-Token", feiYuDTO.getAccessToken());
        apiClient.setDebugging(true);
        api.setApiClient(apiClient);
        try {
            ToolsClueGetV2Response response = api.openApi2ToolsClueGetGet(advertiserIds, startTime, endTime, page, pageSize, clueIds, encryptSensitiveFields);
            List<Map<String,Object>> objects = CollUtil.newArrayList();
            assert response.getData() != null;
            List<ToolsClueGetV2ResponseDataListInner> list = response.getData().getList();
            assert list != null;
            for (ToolsClueGetV2ResponseDataListInner listInner : list) {
                Map<String, Object> map = JSONUtil.parseObj(listInner);
                objects.add(map);
            }
            return objects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
