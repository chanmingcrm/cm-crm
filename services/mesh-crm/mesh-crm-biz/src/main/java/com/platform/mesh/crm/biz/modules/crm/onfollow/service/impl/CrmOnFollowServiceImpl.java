package com.platform.mesh.crm.biz.modules.crm.onfollow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.crm.biz.modules.crm.onfollow.domain.po.CrmOnFollow;
import com.platform.mesh.crm.biz.modules.crm.onfollow.mapper.CrmOnFollowMapper;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.ICrmOnFollowService;
import com.platform.mesh.crm.biz.modules.crm.onfollow.service.manual.CrmOnFollowServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系跟进拜访
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowServiceImpl extends AppServiceAbstract<CrmOnFollowMapper, CrmOnFollow> implements ICrmOnFollowService  {

    @Autowired
    private CrmOnFollowServiceManual crmOnFollowServiceManual;


    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataPO dataPO
     * @param columnBOS columnBOS
     * @param docData docData
     * @author 蝉鸣
     */
    @Override
    public void parsePO(CrmOnFollow dataPO, List<AppFormColumnBO> columnBOS, Map<String, Object> docData){
        if(ObjectUtil.isEmpty(dataPO) || CollUtil.isEmpty(columnBOS) || CollUtil.isEmpty(docData)){
            return;
        }
        //获取关联类型的字段
        List<AppFormColumnBO> list = columnBOS.stream().filter(column -> column.getCompMac().equals(CompTypeEnum.RELEVANCE.getDesc())).toList();
        if(CollUtil.isEmpty(list)){
            return;
        }
        //获取关联配置信息
        AppFormColumnBO first = CollUtil.getFirst(list);
        Object setDataValue = first.getSetDataValue();
        if(ObjectUtil.isEmpty(setDataValue)){
            return;
        }
        //获取模块分类ID
        List<Long> moduleIds = Arrays.stream(StrUtil.toString(setDataValue).split(SymbolConst.COMMA)).map(StrUtil::trim).map(Long::parseLong).toList();
        Long moduleId = CollUtil.getLast(moduleIds);
        dataPO.setRelModuleId(moduleId);
        //获取参数值
        Object object = docData.get(first.getColumnMac());
        if(ObjectUtil.isEmpty(object)){
            return;
        }
        //解析参数值
        String jsonStr = JSONUtil.toJsonStr(object);
        JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
        List<Long> ids = jsonArray.stream().map(obj -> {
            JSONObject entries = JSONUtil.parseObj(obj.toString());
            Object value = entries.get(CompTypeEnum.RELEVANCE.getIdMac());
            if (ObjectUtil.isEmpty(value) || !NumberUtil.isNumber(value.toString())) {
                return NumberConst.NUM_0.longValue();
            } else {
                return Long.valueOf(value.toString());
            }
        }).distinct().toList();
        dataPO.setRelDataId(CollUtil.getFirst(ids));
    }

    /**
     * 功能描述:
     * 〈新增客户关系跟进拜访〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnFollowData> crmOnFollowDataList = BeanUtil.copyToList(dataList, CrmOnFollowData.class);
        //批量保存data表数据
        crmOnFollowServiceManual.addDbDataBatch(crmOnFollowDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系跟进拜访〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmOnFollowServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
    public  void transDbDataBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmOnFollow::getScopeUserId,scopeUserId)
                .set(CrmOnFollow::getScopeOrgId,scopeOrgId)
                .in(CrmOnFollow::getId,dataIds)
                .update();
        //修改DB Data
        crmOnFollowServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}