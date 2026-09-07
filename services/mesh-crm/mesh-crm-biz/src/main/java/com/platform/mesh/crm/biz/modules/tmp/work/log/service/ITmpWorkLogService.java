package com.platform.mesh.crm.biz.modules.tmp.work.log.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.dto.TmpWorkLogDTO;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.po.TmpWorkLog;
import com.platform.mesh.crm.biz.modules.tmp.work.log.domain.vo.TmpWorkLogVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 工作日志跟进信息
 * @author 蝉鸣
 */
public interface ITmpWorkLogService extends IAppService<TmpWorkLog> {

    /**
     * 功能描述:
     * 〈一键生成周报/月报〉
     * @param tmpWorkLogDTO workLogDTO
     * @author 蝉鸣
     */
    TmpWorkLogVO getOneKeyLog(TmpWorkLogDTO tmpWorkLogDTO);
}