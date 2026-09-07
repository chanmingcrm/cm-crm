package com.platform.mesh.crm.biz.modules.crm.precustomer.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.service.ICrmPreCustomerDataService;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
@Service
public class CrmPreCustomerServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreCustomerServiceManual.class);


    @Autowired
    private ICrmPreCustomerDataService crmPreCustomerDataService;

    /**
     * 功能描述:
     * 〈解析返回数据〉
     * @param pageVO pageVO
     * @author 蝉鸣
     */
    public PageVO<Object> parseVO(PageVO<Object> pageVO) {
        //处理统计金额,为保证客户金额 = 已回款 + 未回款,进行计算处理
        Map<String, Object> voAggregations = pageVO.getAggregations();
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal receivedMoney = BigDecimal.ZERO;
        //合同金额
        if(voAggregations.containsKey(ObjFieldUtil.getColumnName(CrmPreCustomer::getTotalMoney))){
            Object object = voAggregations.get(ObjFieldUtil.getColumnName(CrmPreCustomer::getTotalMoney));
            totalMoney = new BigDecimal(object.toString());
        }
        //回款金额
        if(voAggregations.containsKey(ObjFieldUtil.getColumnName(CrmPreCustomer::getReceivedMoney))){
            Object object = voAggregations.get(ObjFieldUtil.getColumnName(CrmPreCustomer::getReceivedMoney));
            receivedMoney = new BigDecimal(object.toString());
        }
        //未回款金额
        BigDecimal unreceivedMoney = totalMoney.subtract(receivedMoney);
        pageVO.getAggregations().put(ObjFieldUtil.getColumnName(CrmPreCustomer::getUnreceivedMoney),unreceivedMoney);
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preCustomerDataList preCustomerDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreCustomerData> preCustomerDataList) {
        if(CollUtil.isEmpty(preCustomerDataList)){
            return;
        }
        CrmPreCustomerData data = CollUtil.getFirst(preCustomerDataList);
        //删除旧数据
        crmPreCustomerDataService.lambdaUpdate().eq(CrmPreCustomerData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreCustomerDataService.saveBatch(preCustomerDataList);
    }

    /**
     * 功能描述:
     * 〈修改Data数据〉
     * @param id id
     * @param columnMac columnMac
     * @param value value
     * @author 蝉鸣
     */
    public void updateData(Long id, String columnMac, Object value) {
        CrmPreCustomerData one = crmPreCustomerDataService.lambdaQuery()
                .eq(CrmPreCustomerData::getDataId, id)
                .eq(CrmPreCustomerData::getColumnMac, columnMac)
                .one();
        if(ObjectUtil.isEmpty(one)){
            //新增数据

            return;
        }
        DataTypeEnum enumByValue = BaseEnum.getEnumByValue(DataTypeEnum.class, one.getDataType());
        Object defaultValue = enumByValue.getDefaultValue(value);
        one.setDataValue(defaultValue);
        crmPreCustomerDataService.updateById(one);
    }


}