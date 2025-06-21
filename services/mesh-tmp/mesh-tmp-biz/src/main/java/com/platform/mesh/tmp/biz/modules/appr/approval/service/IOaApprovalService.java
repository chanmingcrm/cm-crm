package com.platform.mesh.tmp.biz.modules.appr.approval.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.po.OaApproval;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.vo.OaApprovalVO;
import org.springframework.web.multipart.MultipartFile;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description OA办公审批信息
 * @author 蝉鸣
 */
public interface IOaApprovalService extends IService<OaApproval> {

    /**
     * 功能描述:
     * 〈获取数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前OA办公审批信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    OaApprovalVO getOaApprovalInfoById(EsDocSGetDTO esDocSGetDTO);

    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddSimpDTO dataAddSimpDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    OaApprovalVO addOaApprovalSimp(DataAddSimpDTO dataAddSimpDTO);
    
    /**
     * 功能描述:
     * 〈新增OA办公审批〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    OaApprovalVO addOaApprovalComp(DataAddCompDTO dataAddCompDTO);

    /**
     * 功能描述:
     * 〈修改OA办公审批〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link OaApprovalVO}
     * @author 蝉鸣
     */
    OaApprovalVO editOaApproval(DataEditSimpDTO dataEditDTO);

    /**
     * 功能描述:
     * 〈删除OA办公审批〉
     * @param onBusinessId onBusinessId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteOaApproval(Long onBusinessId);

    /**
     * 功能描述:
     * 〈批量删除OA办公审批〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteOaApproval(DataDelDTO delDTO);
    
    /**
     * 功能描述:
     * 〈转移OA办公审批〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean transOaApproval(TransScopeDTO transScopeDTO);
    
    /**
     * 功能描述:
     * 〈导入OA办公审批〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean importOaApproval(Long moduleId, Long formId, MultipartFile file);
}