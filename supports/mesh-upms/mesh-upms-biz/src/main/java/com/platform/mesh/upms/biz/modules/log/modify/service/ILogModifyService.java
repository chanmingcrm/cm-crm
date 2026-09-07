package com.platform.mesh.upms.biz.modules.log.modify.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.dto.LogModifyPageDTO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.po.LogModify;
import com.platform.mesh.upms.biz.modules.log.modify.domain.vo.LogModifyVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 修改日志信息
 * @author 蝉鸣
 */
public interface ILogModifyService extends IService<LogModify> {


    /**
     * 功能描述:
     * 〈获取变更日志分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<LogModifyVO>}
     * @author 蝉鸣
     */
    PageVO<LogModifyVO> selectPage(LogModifyPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增修改日志〉
     * @param modifyBO modifyBO
     * @author 蝉鸣
     */
    void addModifyLog(LogModifyBO modifyBO);

}

