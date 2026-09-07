package com.platform.mesh.ai.biz.bi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.utils.result.Result;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 会话群信息
 * @author 蝉鸣
 */
public interface ICcBiService extends IService<CcSessionMsg> {


    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biAllTotal(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biUserTotal(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈满意度〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    SimpVO biStar(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈接待排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> biNumRank(BiDTO biDTO);

    /**
     * 功能描述:
     * 〈满意度排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    List<SimpVO> biStarRank(BiDTO biDTO);
}
