package com.platform.mesh.app.biz.modules.data.importerror.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.data.importerror.domain.dto.ErrorPDTO;
import com.platform.mesh.app.biz.modules.data.importerror.domain.po.DataImportError;
import com.platform.mesh.app.biz.modules.data.importerror.domain.vo.ErrorSVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 导入错误数据数据
 * @author 蝉鸣
 */
public interface DataImportErrorMapper extends BaseMapper<DataImportError> {

    MPage<ErrorSVO> selectImportErrorBatchPage(MPage<DataImportError> mPage,@Param("pageDTO") ErrorPDTO pageDTO);
}