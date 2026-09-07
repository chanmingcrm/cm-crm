package com.platform.mesh.app.api.modules.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.IAppDataService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public abstract class AppDataServiceAbstract<M extends BaseMapper<D>, D extends AppDataPO> extends CrudRepository<M, D> implements IAppDataService<D> {

    private static final Logger log = LoggerFactory.getLogger(AppDataServiceAbstract.class);

    @Autowired
    private IEsDocService esDocService;

    /**
     * 功能描述:
     * 〈同步名称〉
     * @param msgAppBO msgAppBO
     * @author 蝉鸣
     */
    @Transactional(rollbackFor = BaseException.class)
    public void syncName(MsgAppBO msgAppBO){
        Map<String, Object> extendJson = msgAppBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.APP_DATA_COLUMN)){
            return;
        }
        if(!extendJson.containsKey(StrConst.NAME)){
            return;
        }
        Object object = extendJson.get(StrConst.APP_DATA_COLUMN);
        List<String> columnMacs = JSONUtil.toList(object.toString(), String.class);
        Object name = extendJson.get(StrConst.NAME);
        //修改Data中的信息
        MPage<D> mPage = new MPage<>();
        int pageNum = NumberConst.NUM_1;
        while (true) {
            try {
                mPage.setCurrent(pageNum);
                
                DataScopeHandler.setEnableDataScope(Boolean.FALSE);
                //分页查询命中数据。需要文档ID更新ES最方便
                MPage<D> dataPage = this.lambdaQuery()
                        .eq(D::getModuleId,msgAppBO.getModuleId())
                        .in(D::getColumnMac,columnMacs)
                        .apply("JSON_SEARCH(data_value, 'one', {0}, null, '$[*].id') IS NOT NULL",
                                StrUtil.toString(msgAppBO.getDataId()))
                        .page(mPage);
                if(CollUtil.isEmpty(dataPage.getRecords())){
                    break;
                }
                //获取数据
                List<EsDocPutBO> docs = AppUtil.getBatchEsDocPut(dataPage, msgAppBO.getModuleIndex(), msgAppBO.getDataId(), name);
                if(CollUtil.isEmpty(docs)){
                    break;
                }
                //修改DATA数据
                this.updateBatchById(dataPage.getRecords());
                DataScopeHandler.unEnableDataScope();
                
                //修改ES数据
                esDocService.updateBatchDocument(docs);
                pageNum ++;
            }catch (Exception e){
                log.error(e.getMessage());
                break;
            }
        }
    }
}
