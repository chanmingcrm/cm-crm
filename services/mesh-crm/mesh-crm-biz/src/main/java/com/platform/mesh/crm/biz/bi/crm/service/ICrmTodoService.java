package com.platform.mesh.crm.biz.bi.crm.service;

import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.crm.biz.bi.crm.domain.dto.TodoPDTO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系分组信息
 * @author 蝉鸣
 */
public interface ICrmTodoService {

    /**
     * 功能描述:
     * 〈获取待办数量角标〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    Object todoNum(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈今日需联系客户〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoRelCustomerToday(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈今日需联系商机〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoRelBusinessToday(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈待审核客户〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoAuditCustomer(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈待审核商机〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoAuditBusiness(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈待审核合同〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoAuditContract(TodoPDTO pageDTO);

    /**
     * 功能描述:
     * 〈待审核订单〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    /**
     * 功能描述:
     * 〈待审核报价单〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> todoAuditProposal(TodoPDTO pageDTO);

}
