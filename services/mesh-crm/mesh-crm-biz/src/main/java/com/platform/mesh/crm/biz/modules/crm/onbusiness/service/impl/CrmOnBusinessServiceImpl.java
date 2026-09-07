package com.platform.mesh.crm.biz.modules.crm.onbusiness.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.mapper.CrmOnBusinessMapper;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.ICrmOnBusinessService;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.manual.CrmOnBusinessServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.domain.po.CrmOnBusinessData;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系商机跟进
 * @author 蝉鸣
 */
@Service
public class CrmOnBusinessServiceImpl extends AppServiceAbstract<CrmOnBusinessMapper, CrmOnBusiness> implements ICrmOnBusinessService  {

    @Autowired
    private CrmOnBusinessServiceManual crmOnBusinessServiceManual;

    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO <Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        
        //添加分析字段
        List<String> aggregations = CollUtil.newArrayList();
        aggregations.add(ObjFieldUtil.getColumnName(CrmOnBusiness::getRealMoney));
        pageDTO.setAggregations(aggregations);
        return this.getAppServiceManual().selectEsPage(pageDTO);
    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnBusinessData> crmOnBusinessData = BeanUtil.copyToList(dataList, CrmOnBusinessData.class);
        //批量保存data表数据
        crmOnBusinessServiceManual.addDbDataBatch(crmOnBusinessData);
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
                .set(CrmOnBusiness::getScopeUserId,scopeUserId)
                .set(CrmOnBusiness::getScopeOrgId,scopeOrgId)
                .in(CrmOnBusiness::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈自动初始化财务管理应收数据〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(CrmOnBusiness dataPO, DataAddSimpDTO dataAddDTO) {
        Map<String, Object> docData = dataAddDTO.getDocData();
        //获取客户关联数据
        Long customerId = AppUtil.getSingleColumnIdValue(CrmConst.CUSTOMER, docData);
        dataPO.setCustomerId(customerId);
        //更新信息
        this.updateById(dataPO);
        //处理子表数据
        crmOnBusinessServiceManual.saveSubProductList(dataPO,dataAddDTO);
    }

    /**
     * 功能描述:
     * 〈获取订单下的产品列表〉
     * @param dataVO dataVO
     * @author 蝉鸣
     */
    @Override
    public <E extends AppVO> E getOtherAction(E dataVO){
        //填充订单产品列表数据
        List<Object> dataList = crmOnBusinessServiceManual.getSubProductList(dataVO.getId());
        dataVO.getEsData().put(CrmConst.PRODUCT_LIST,dataList);
        return dataVO;
    }


}