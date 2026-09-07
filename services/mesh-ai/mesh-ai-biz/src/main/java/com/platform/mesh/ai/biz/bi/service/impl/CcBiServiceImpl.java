package com.platform.mesh.ai.biz.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.bi.domain.dto.BiDTO;
import com.platform.mesh.ai.biz.bi.mapper.CcBiMapper;
import com.platform.mesh.ai.biz.bi.service.ICcBiService;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 会话群信息
 * @author 蝉鸣
 */
@Service
public class CcBiServiceImpl extends ServiceImpl<CcBiMapper, CcSessionMsg> implements ICcBiService {

    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO biAllTotal(BiDTO biDTO) {
        return this.getBaseMapper().biAllTotal(biDTO);
    }

    /**
     * 功能描述:
     * 〈总接待数量〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO biUserTotal(BiDTO biDTO) {
        return this.getBaseMapper().biAllTotal(biDTO);
    }

    /**
     * 功能描述:
     * 〈满意度〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO biStar(BiDTO biDTO) {
        return this.getBaseMapper().biStar(biDTO, UserCacheUtil.getUserId());
    }

    /**
     * 功能描述:
     * 〈接待排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> biNumRank(BiDTO biDTO) {
        return this.getBaseMapper().biNumRank(biDTO);
    }

    /**
     * 功能描述:
     * 〈满意度排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> biStarRank(BiDTO biDTO) {
        return this.getBaseMapper().biStarRank(biDTO);
    }
}
