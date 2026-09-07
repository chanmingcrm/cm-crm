package com.platform.mesh.crm.biz.modules.tmp.task.base.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.dto.TmpTaskBaseDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.po.TmpTaskBase;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.vo.TmpTaskBaseVO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelPageDTO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务跟进信息
 * @author 蝉鸣
 */
public interface ITmpTaskBaseService extends IAppService<TmpTaskBase> {

    /**
     * 功能描述:
     * 〈新增关联任务〉
     * @param tmpTaskBaseDTO taskBaseDTO
     * @return 正常返回:{@link TmpTaskBase}
     * @author 蝉鸣
     */
    TmpTaskBase addTaskBaseAndRel(TmpTaskBaseDTO tmpTaskBaseDTO);

    /**
     * 功能描述:
     * 〈关联任务分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    PageVO<TmpTaskBaseVO> taskBaseAndRelPage(TmpTaskBaseRelPageDTO pageDTO);
}