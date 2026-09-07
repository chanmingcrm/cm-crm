package com.platform.mesh.crm.biz.modules.crm.predrainage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 客户关系活动引流
 * @author 蝉鸣
 */
public interface CrmPreDrainageMapper extends BaseMapper<CrmPreDrainage> {

    List<CheckVO> checkPreDrainage(@Param("checkDTO") CheckDTO checkDTO);

    CrmPreDrainage getExistByPhone(@Param("moduleId") Long moduleId, @Param("phone") String phone);
}
