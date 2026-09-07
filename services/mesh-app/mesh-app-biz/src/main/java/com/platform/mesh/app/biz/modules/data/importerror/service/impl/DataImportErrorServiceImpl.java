package com.platform.mesh.app.biz.modules.data.importerror.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.ImportErrorBO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorEDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorPDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.po.DataImportError;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorCVO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorSVO;
import com.platform.mesh.app.biz.modules.data.importerror.mapper.DataImportErrorMapper;
import com.platform.mesh.app.biz.modules.data.importerror.service.IDataImportErrorService;
import com.platform.mesh.app.biz.modules.data.importerror.service.manual.DataImportErrorServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 导入错误数据数据
 * @author 蝉鸣
 */
@Service
public class DataImportErrorServiceImpl extends ServiceImpl<DataImportErrorMapper, DataImportError> implements IDataImportErrorService {

    @Autowired
    private DataImportErrorServiceManual dataImportErrorServiceManual;

    /**
     * 功能描述:
     * 〈查询导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<ErrorSVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<ErrorSVO> selectImportErrorBatchPage(ErrorPDTO pageDTO) {
        MPage<DataImportError> mPage = MPageUtil.pageEntityToMPage(pageDTO, DataImportError.class);
        MPage<ErrorSVO> page = this.getBaseMapper().selectImportErrorBatchPage(mPage,pageDTO);
        return MPageUtil.convertToVO(page,ErrorSVO.class);
    }

    /**
     * 功能描述:
     * 〈查询导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<ErrorCVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<ErrorCVO> selectImportErrorInfoPage(ErrorPDTO pageDTO) {
        MPage<DataImportError> mPage = MPageUtil.pageEntityToMPage(pageDTO, DataImportError.class);
        MPage<DataImportError> page = this.lambdaQuery()
                .eq(DataImportError::getBatchId,pageDTO.getBatchId())
                .orderByAsc(DataImportError::getRowNum,DataImportError::getCreateTime)
                .page(mPage);
        return MPageUtil.convertToVO(page,ErrorCVO.class);
    }

    /**
     * 功能描述:
     * 〈保存导入错误信息〉
     * @param importErrorBO importErrorBO
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void saveImportError(ImportErrorBO importErrorBO) {
        Map<Integer, List<String>> errorRecordMap = importErrorBO.getErrorRecordMap();
        Map<Integer, String> rowDataMap = importErrorBO.getRowDataMap();
        if(CollUtil.isEmpty(errorRecordMap)){
            return;
        }
        List<DataImportError> recordList = CollUtil.newArrayList();
        //根据数量进行分页处理，保证单行数据不会太大
        errorRecordMap.forEach((rowNum,value)->{
            String error = String.join(SymbolConst.COMMA, value);
            DataImportError errorPO = BeanUtil.copyProperties(importErrorBO,DataImportError.class);
            errorPO.setRowNum(rowNum);
            errorPO.setCreateUserId(importErrorBO.getUserId());
            errorPO.setCreateTime(LocalDateTime.now());
            errorPO.setRowData(rowDataMap.get(rowNum));
            errorPO.setErrorRecord(error);
            recordList.add(errorPO);
        });
        if (CollUtil.isNotEmpty(recordList)) {
            this.saveBatch(recordList);
        }
    }

    /**
     * 功能描述:
     * 〈导出错误行信息信息〉
     * @param errorEDTO errorEDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectImportErrorExportPage(ErrorEDTO errorEDTO) {
        MPage<DataImportError> mPage = MPageUtil.pageEntityToMPage(errorEDTO, DataImportError.class);
        MPage<DataImportError> page = this.lambdaQuery()
                .eq(DataImportError::getBatchId,errorEDTO.getBatchId())
                .isNotNull(DataImportError::getRowData)
                .orderByAsc(DataImportError::getRowNum,DataImportError::getCreateTime)
                .page(mPage);
        //组装信息
        return dataImportErrorServiceManual.packPageVO(page);
    }
}
