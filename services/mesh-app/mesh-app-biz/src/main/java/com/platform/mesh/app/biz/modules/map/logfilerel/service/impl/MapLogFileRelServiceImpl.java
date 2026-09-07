package com.platform.mesh.app.biz.modules.map.logfilerel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.map.logfilerel.domain.po.MapLogFileRel;
import com.platform.mesh.app.biz.modules.map.logfilerel.mapper.MapLogFileRelMapper;
import com.platform.mesh.app.biz.modules.map.logfilerel.service.IMapLogFileRelService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 地图打卡
 * @author 蝉鸣
 */
@Service
public class MapLogFileRelServiceImpl extends ServiceImpl<MapLogFileRelMapper, MapLogFileRel> implements IMapLogFileRelService {

}
