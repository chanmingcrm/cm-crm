package com.platform.mesh.crm.biz.bi.plm.service.impl;

import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.crm.biz.bi.plm.mapper.PlmProductBiMapper;
import com.platform.mesh.crm.biz.bi.plm.service.IPlmProductBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有统计在Mapper中执行
 * @description 产品看板BI统计
 * @author 蝉鸣
 */
@Service
public class PlmProductBiServiceImpl implements IPlmProductBiService {

    @Autowired
    private PlmProductBiMapper plmProductBiMapper;

    /**
     * 功能描述:
     * 【获取产品汇总统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> productSummary(BiDTO biDTO) {
        biDTO = AppUtil.parseBiDTO(biDTO);
        return plmProductBiMapper.productSummary(biDTO);
    }

    /**
     * 功能描述:
     * 【获取生命周期结构统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> productLifecycle(BiDTO biDTO) {
        biDTO = AppUtil.parseBiDTO(biDTO);
        return plmProductBiMapper.productLifecycle(biDTO);
    }

    /**
     * 功能描述:
     * 【获取产品健康度统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> productHealth(BiDTO biDTO) {
        biDTO = AppUtil.parseBiDTO(biDTO);
        return plmProductBiMapper.productHealth(biDTO);
    }

    /**
     * 功能描述:
     * 【获取产品运营指标统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> productOperation(BiDTO biDTO) {
        biDTO = AppUtil.parseBiDTO(biDTO);
        return plmProductBiMapper.productOperation(biDTO);
    }

    /**
     * 功能描述:
     * 【获取产品预警统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> productWarningItems(BiDTO biDTO) {
        biDTO = AppUtil.parseBiDTO(biDTO);
        return plmProductBiMapper.productWarningItems(biDTO);
    }

}
