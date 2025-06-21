package com.platform.mesh.app.biz.modules.map.conf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.dto.MapConfPageDTO;
import com.platform.mesh.app.biz.modules.map.conf.domain.po.MapConf;
import com.platform.mesh.app.biz.modules.map.conf.domain.vo.MapConfVO;
import com.platform.mesh.app.biz.modules.map.conf.exception.MapConfExceptionEnum;
import com.platform.mesh.app.biz.modules.map.conf.mapper.MapConfMapper;
import com.platform.mesh.app.biz.modules.map.conf.service.IMapConfService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 地图配置
 * @author 蝉鸣
 */
@Service
public class MapConfServiceImpl extends ServiceImpl<MapConfMapper, MapConf> implements IMapConfService {


    /***
     * 功能描述:
     * 〈获取表单分页〉
     * @param mapConfPageDTO mapConfPageDTO
     * @return 正常返回:{@link MPage<MapConf>}
     * @author 蝉鸣
     * @since 2024/8/29 17:12
     */
    @Override
    public MPage<MapConf> selectPage(MapConfPageDTO mapConfPageDTO) {
        MPage<MapConf> mapConfMPage = MPageUtil.pageEntityToMPage(mapConfPageDTO, MapConf.class);
        return this.page(mapConfMPage);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param confId confId
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    @Override
    public MapConfVO getMapConfInfoById(Long confId) {
        MapConf mapConf = this.getById(confId);
        return BeanUtil.copyProperties(mapConf, MapConfVO.class);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    @Override
    public MapConfVO addMapConf(MapConfDTO mapConfDTO) {
        MapConf mapConf = BeanUtil.copyProperties(mapConfDTO, MapConf.class);
        this.save(mapConf);
        return BeanUtil.copyProperties(mapConf, MapConfVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param mapConfDTO mapConfDTO
     * @return 正常返回:{@link MapConfVO}
     * @author 蝉鸣
     */
    @Override
    public MapConfVO editMapConf(MapConfDTO mapConfDTO) {
        MapConf mapConf = getById(mapConfDTO.getId());
        if(ObjectUtil.isEmpty(mapConf)){
            throw MapConfExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        BeanUtil.copyProperties(mapConfDTO, mapConf);
        this.updateById(mapConf);
        return BeanUtil.copyProperties(mapConf, MapConfVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param mapConfId mapConfId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMapConf(Long mapConfId) {
        return removeById(mapConfId);
    }
}