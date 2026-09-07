package com.platform.mesh.app.biz.modules.third.thirdformcolumn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.po.ThirdFormColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
public interface ThirdFormColumnMapper extends BaseMapper<ThirdFormColumn> {


    List<ThirdFormColumn> getThirdFormColumn(@Param("sourceFlag") Integer sourceFlag);

}
