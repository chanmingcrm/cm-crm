package com.platform.mesh.tmp.biz.modules.appr.approval.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.AppDataAddBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.serial.SerialUtil;
import com.platform.mesh.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.po.OaApproval;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.vo.OaApprovalVO;
import com.platform.mesh.tmp.biz.modules.appr.approval.exception.OaApprovalExceptionEnum;
import com.platform.mesh.tmp.biz.modules.appr.approval.mapper.OaApprovalMapper;
import com.platform.mesh.tmp.biz.modules.appr.approval.service.IOaApprovalService;
import com.platform.mesh.tmp.biz.modules.appr.approval.service.manual.OaApprovalServiceManual;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.listener.UploadDataListener;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description OA办公审批
 * @author 蝉鸣
 */
@Service
public class OaApprovalServiceImpl extends ServiceImpl<OaApprovalMapper, OaApproval> implements IOaApprovalService {

    @Autowired
    private OaApprovalServiceManual oaApprovalServiceManual;


    /**
     * 功能描述:
     * 〈获取数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        return oaApprovalServiceManual.selectEsPage(pageDTO);
    }
    
    /**
     * 功能描述: 
     * 〈获取当前OA办公审批信息〉
     * @param esDocSGetDTO esDocSGetDTO 
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    @Override
    public OaApprovalVO getOaApprovalInfoById(EsDocSGetDTO esDocSGetDTO) {
        OaApproval oaApproval = this.getById(esDocSGetDTO.getDataId());
        if(ObjectUtil.isEmpty(oaApproval)){
            return new OaApprovalVO();
        }
        Object esData = oaApprovalServiceManual.getOaApprovalInfoById(esDocSGetDTO);
        OaApprovalVO oaApprovalVO = BeanUtil.copyProperties(oaApproval, OaApprovalVO.class);
        oaApprovalVO.setEsData(BeanUtil.beanToMap(esData));
        return oaApprovalVO;
    }

    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OaApprovalVO addOaApprovalSimp(DataAddSimpDTO dataAddSimpDTO) {
        //获取字段信息
        List<AppFormColumnBO> columnBOS = oaApprovalServiceManual.getFormColumnInfo(dataAddSimpDTO.getModuleId(),dataAddSimpDTO.getFormId());
        OaApproval oaApproval = SerialUtil.genDataSerial(OaApproval.class,BeanUtil.copyToList(columnBOS, ColumnCompBO.class),dataAddSimpDTO.getDocData());
        oaApproval.setModuleId(dataAddSimpDTO.getModuleId());
        //保存数据库
        this.save(oaApproval);
        //获取模块信息
        AppModuleBaseBO moduleInfo = oaApprovalServiceManual.getModuleInfo(dataAddSimpDTO.getModuleId());
        //保存data数据
        Map<String, Object> docMap = oaApprovalServiceManual.addDbDataSimp(moduleInfo, dataAddSimpDTO.getFormId(), oaApproval, columnBOS, dataAddSimpDTO.getDocData());
        //保存ES数据
        oaApprovalServiceManual.addEsData(moduleInfo.getModuleIndex(),oaApproval,docMap);
        //返回VO对象
        return BeanUtil.copyProperties(oaApproval, OaApprovalVO.class);
    }
    
    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OaApprovalVO addOaApprovalComp(DataAddCompDTO dataAddCompDTO) {
        List<AppFormColumnAddDTO> docDataList = dataAddCompDTO.getDocDataList();
        if(CollUtil.isEmpty(docDataList)){
            return new OaApprovalVO();
        }
        //获取存储ID
        Long dataId = IdUtil.getSnowflake().nextId();
        //保存data数据
        Map<String, Object> dataMap = oaApprovalServiceManual.addDbDataComp(dataId, dataAddCompDTO);
        //保存数据库
        OaApproval oaApproval = BeanUtil.copyProperties(dataMap, OaApproval.class);
        oaApproval.setModuleId(dataAddCompDTO.getModuleId());
        oaApproval.setId(dataId);
        this.save(oaApproval);
        //保存ES数据
        oaApprovalServiceManual.addEsData(dataAddCompDTO.getModuleIndex(),oaApproval, dataMap);
        //返回VO对象
        return BeanUtil.copyProperties(oaApproval, OaApprovalVO.class);
    }

    /**
     * 功能描述:
     * 〈修改OA办公审批〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OaApprovalVO editOaApproval(DataEditSimpDTO dataEditDTO) {
        if(ObjectUtil.isEmpty(dataEditDTO.getDataId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(OaApproval::getId);
            throw OaApprovalExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        OaApproval oaApproval = BeanUtil.copyProperties(dataEditDTO.getDocData(), OaApproval.class);
        oaApproval.setId(dataEditDTO.getDataId());
        this.updateById(oaApproval);
        //获取模块信息
        AppModuleBaseBO moduleInfo = oaApprovalServiceManual.getModuleInfo(dataEditDTO.getModuleId());
        //修改data数据
        oaApprovalServiceManual.editDbData(oaApproval.getId(),dataEditDTO);
        //修改ES数据
        oaApprovalServiceManual.editEsData(moduleInfo.getModuleIndex(),oaApproval.getId(),dataEditDTO.getDocData());
        return BeanUtil.copyProperties(oaApproval, OaApprovalVO.class);
    }

    /**
     * 功能描述:
     * 〈删除OA办公审批〉
     * @param onBusinessId onBusinessId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOaApproval(Long onBusinessId) {
        OaApproval oaApproval = getById(onBusinessId);
        if(ObjectUtil.isEmpty(oaApproval)){
            return Boolean.FALSE;
        }
        //获取模块信息
        AppModuleBaseBO moduleInfo = oaApprovalServiceManual.getModuleInfo(oaApproval.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //删除ES数据
        oaApprovalServiceManual.deleteEsData(moduleInfo.getModuleIndex(),CollUtil.newArrayList(onBusinessId));
        //删除数据库数据
        this.removeById(onBusinessId);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量删除OA办公审批〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOaApproval(DataDelDTO delDTO) {
        //获取模块信息
        AppModuleBaseBO moduleInfo = oaApprovalServiceManual.getModuleInfo(delDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //删除ES数据
        oaApprovalServiceManual.deleteEsData(moduleInfo.getModuleIndex(),delDTO.getDataIds());
        //删除数据库数据
        this.removeBatchByIds(delDTO.getDataIds());
        return Boolean.TRUE;
    }
    
    /**
     * 功能描述:
     * 〈转移OA办公审批〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean transOaApproval(TransScopeDTO transScopeDTO) {
        if(CollUtil.isEmpty(transScopeDTO.getIds())){
            return Boolean.FALSE;
        }
        //修改DB
        this.lambdaUpdate()
                .set(OaApproval::getScopeUserId,transScopeDTO.getScopeUserId())
                .set(OaApproval::getScopeOrgId,transScopeDTO.getScopeOrgId())
                .in(OaApproval::getId,transScopeDTO.getIds())
                .update();
        //修改ES
        oaApprovalServiceManual.transEsData(transScopeDTO);
        return Boolean.TRUE;
    }
    
    
    /**
     * 功能描述:
     * 〈导入OA办公审批〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importOaApproval(Long moduleId, Long formId, MultipartFile file) {
        //获取模块信息
        AppModuleBaseBO moduleInfo = oaApprovalServiceManual.getModuleInfo(moduleId);
        //获取模块对应的字段信息
        List<AppFormColumnBO> formColumnInfo = oaApprovalServiceManual.getFormColumnInfo(moduleId, formId);
        Map<String, String> columnMap = formColumnInfo.stream().collect(Collectors.toMap(AppFormColumnBO::getColumnName, AppFormColumnBO::getColumnMac));
        List<String> transMacs = Arrays.stream(CompTypeEnum.values()).map(CompTypeEnum::getDesc).toList();
        List<String> objFieldNames = ObjFieldUtil.getObjFieldNames(OaApproval.class);
        //transMacs需要解析的字段,objFieldNames固定表字段则不解析
        Map<String, AppFormColumnBO> compMap = formColumnInfo.stream()
                .filter(comp->transMacs.contains(comp.getCompMac()))
                .filter(comp->!objFieldNames.contains(StrUtil.toCamelCase(comp.getColumnMac())))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity()));
        //分页读取信息,默认100
        UploadDataListener uploadDataListener = new UploadDataListener(dataList -> {
            // dataList分页批量处理
            //保存db对象
            log.debug(dataList.toString());
            List<OaApproval> oaApprovals = CollUtil.newArrayList();
            List<AppDataAddBO> appDataAddBOS = CollUtil.newArrayList();
            for (Map<String, Object> dataMap : dataList) {
                //解析数据
                if(ObjectUtil.isNotEmpty(compMap)){
                    compMap.forEach((key,value)->{
                        Object parseValue = SearchUtil.parseImportData(value.getCompMac(),value.getRelDataValue(),key,dataMap.get(key));
                        dataMap.put(key,parseValue);
                    });
                }
                //对象实体
//                OaApproval oaApproval = SerialUtil.genDataSerial(OaApproval.class,BeanUtil.copyToList(formColumnInfo, ColumnCompBO.class),dataMap);
                OaApproval oaApproval = BeanUtil.copyProperties(dataMap, OaApproval.class);
                oaApproval.setModuleId(moduleId);
                oaApprovals.add(oaApproval);
                //data保存数据
                AppDataAddBO addBO = new AppDataAddBO();
                addBO.setModuleBaseBO(moduleInfo);
                addBO.setFormId(formId);
                addBO.setInstObj(oaApproval);
                addBO.setColumnBOS(formColumnInfo);
                addBO.setDataDoc(dataMap);
                appDataAddBOS.add(addBO);
            }
            //保存实体
            this.saveBatch(oaApprovals);
            //保存data
            FutureHandleUtil.runWithResult(appDataAddBOS,oaApprovalServiceManual::importData);
            //保存ES数据
            FutureHandleUtil.runWithResult(appDataAddBOS,oaApprovalServiceManual::importEs);
        },columnMap);
        try{
            FastExcel.read(file.getInputStream(), uploadDataListener)
                    .sheet()
                    .headRowNumber(NumberConst.NUM_2)
                    .doRead();
        }catch(Exception ignore){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}