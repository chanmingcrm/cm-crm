package com.platform.mesh.crm.biz.modules.crm.precontacts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 联系人对象
 * @author 蝉鸣
 */
public interface CrmPreContactsMapper extends BaseMapper<CrmPreContacts> {

    List<CheckVO> checkPreContacts(@Param("checkDTO") CheckDTO checkDTO);
}
