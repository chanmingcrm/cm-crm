package com.platform.mesh.app.biz.modules.data.importerror.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.bo.ImportErrorBO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorEDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorPDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.po.DataImportError;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorCVO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorSVO;
import com.platform.mesh.core.application.domain.vo.PageVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 导入错误数据数据信息
 * @author 蝉鸣
 */
public interface IDataImportErrorService extends IService<DataImportError> {

    /**
     * 功能描述:
     * 〈查询导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<ErrorSVO>}
     * @author 蝉鸣
     */
    PageVO<ErrorSVO> selectImportErrorBatchPage(ErrorPDTO pageDTO);

    /**
     * 功能描述:
     * 〈查询导入错误信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<ErrorCVO>}
     * @author 蝉鸣
     */
    PageVO<ErrorCVO> selectImportErrorInfoPage(ErrorPDTO pageDTO);

    /**
     * 功能描述:
     * 〈保存导入错误信息〉
     * @param errorBO errorBO
     * @author 蝉鸣
     */
    void saveImportError(ImportErrorBO errorBO);

    /**
     * 功能描述:
     * 〈导出错误行信息信息〉
     * @param errorEDTO errorEDTO
     * @author 蝉鸣
     */
    PageVO<Object> selectImportErrorExportPage(ErrorEDTO errorEDTO);

}
