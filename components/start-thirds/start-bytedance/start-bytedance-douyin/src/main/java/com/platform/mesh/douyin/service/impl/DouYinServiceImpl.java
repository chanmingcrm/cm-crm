package com.platform.mesh.douyin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.douyin.openapi.client.Client;
import com.douyin.openapi.client.models.ClueQueryRequest;
import com.douyin.openapi.client.models.ClueQueryResponse;
import com.douyin.openapi.client.models.ClueQueryResponseDataClueDataItem;
import com.douyin.openapi.credential.models.Config;
import com.platform.mesh.douyin.domain.feiyu.dto.DouYinPageDTO;
import com.platform.mesh.douyin.domain.feiyu.dto.DouyinAppDTO;
import com.platform.mesh.douyin.service.IDouYinAppService;
import com.platform.mesh.douyin.service.manual.SignServiceManual;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 飞鱼接口
 * @author 蝉鸣
 */
@Service
public class DouYinServiceImpl implements IDouYinAppService {

    private final static Logger log = LoggerFactory.getLogger(DouYinServiceImpl.class);

    @Autowired
    private SignServiceManual signServiceManual;

    /**
     * 功能描述:
     * 〈查询飞鱼线索〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    @Override
    public List<Map<String,Object>> getClueList(DouyinAppDTO appDTO, DouYinPageDTO pageDTO) {
        try {
            Config config = new Config().setClientKey(appDTO.getClientKey()).setClientSecret(appDTO.getClientSecret());
            Client client = new Client(config);
            ClueQueryRequest sdkRequest = new ClueQueryRequest();
            sdkRequest.setAccessToken(pageDTO.getAccessToken());
            sdkRequest.setAccountId(appDTO.getAccountId());
            sdkRequest.setStartTime(DateTimeUtil.localDateTimeToStr(pageDTO.getStartTime()));
            sdkRequest.setEndTime(DateTimeUtil.localDateTimeToStr(pageDTO.getEndTime()));
            sdkRequest.setPage(pageDTO.getPageNum());
            sdkRequest.setPageSize(pageDTO.getPageSize());
            ClueQueryResponse sdkResponse = client.ClueQuery(sdkRequest);
            List<ClueQueryResponseDataClueDataItem> clueData = sdkResponse.getData().getClueData();

            List<Map<String,Object>> objects = CollUtil.newArrayList();
            if(CollUtil.isEmpty(clueData)){
                return objects;
            }
            //解析手机号码
            for (ClueQueryResponseDataClueDataItem clueDatum : clueData) {
                Map<String, Object> map = clueDatum.toMap();
                String telephoneDecry = signServiceManual.decryptAES(appDTO.getClientSecret(), clueDatum.getTelephone());
                map.put(ObjFieldUtil.getColumnName(ClueQueryResponseDataClueDataItem::getTelephone),telephoneDecry);
                objects.add(map);
            }
            return objects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
