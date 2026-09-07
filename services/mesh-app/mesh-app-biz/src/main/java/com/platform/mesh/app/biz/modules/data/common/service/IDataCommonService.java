package com.platform.mesh.app.biz.modules.data.common.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.app.biz.modules.data.common.domain.po.DataCommon;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.es.domain.dto.EsDocUGetDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 通用数据信息
 * @author 蝉鸣
 */
public interface IDataCommonService extends IAppService<DataCommon> {

    /**
     * 功能描述:
     * 〈添加团队成员〉
     * @param baseDTO baseDTO
     * @author 蝉鸣
     */
    Boolean addTeamMember(TeamBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @author 蝉鸣
     */
    Boolean deleteTeamMember(TeamBaseDelDTO delDTO);

    /**
     * 功能描述:
     * 〈多索引联合过滤查询〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    PageVO<Object> selectUniPage(EsDocUGetDTO pageDTO);

}