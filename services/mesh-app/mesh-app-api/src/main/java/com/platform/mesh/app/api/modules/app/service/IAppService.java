package com.platform.mesh.app.api.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IAppService<T extends AppPO> extends IService<T> {

    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO <Object>}
     * @author 蝉鸣
     */
    PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前数据对象信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    <E extends AppVO> E getDataInfoById(EsDocSGetDTO esDocSGetDTO, Class<E> voClass);

    /**
     * 功能描述:
     * 〈新增数据对象〉
     * @param dataAddSimpDTO dataAddDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    <D extends AppDataPO> T addDataSimp(DataAddSimpDTO dataAddSimpDTO, Class<T> poClass, Class<D> dataPoClass);

    /**
     * 功能描述:
     * 〈新增数据对象〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    <D extends AppDataPO> T addDataComp(DataAddCompDTO dataAddCompDTO, Class<T> poClass, Class<D> dataPoClass);

    /**
     * 功能描述:
     * 〈修改数据对象〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    T editData(DataEditSimpDTO dataEditDTO, Class<T> poClass);

    /**
     * 功能描述:
     * 〈删除数据对象〉
     * @param preCustomerId preCustomerId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteData(Long preCustomerId);

    /**
     * 功能描述:
     * 〈删除数据对象〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteData(DataDelDTO delDTO);

    /**
     * 功能描述:
     * 〈转移数据对象〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean transData(TransScopeDTO transScopeDTO);

    /**
     * 功能描述:
     * 〈导入数据对象〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    <D extends AppDataPO>Boolean importData(Long moduleId, Long formId, MultipartFile file, Class<T> poClass ,Class<D> dataPoClass);
}
