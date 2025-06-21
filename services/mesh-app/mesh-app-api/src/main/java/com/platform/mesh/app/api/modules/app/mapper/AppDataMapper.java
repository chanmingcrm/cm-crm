package com.platform.mesh.app.api.modules.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 应用
 * @author 蝉鸣
 */
@Mapper
public interface AppDataMapper extends BaseMapper<AppDataPO> {

}