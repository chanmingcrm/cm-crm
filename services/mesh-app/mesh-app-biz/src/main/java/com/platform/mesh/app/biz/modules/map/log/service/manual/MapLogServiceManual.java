package com.platform.mesh.app.biz.modules.map.log.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.map.log.domain.dto.MapLogDTO;
import com.platform.mesh.app.biz.modules.map.log.domain.vo.MapLogVO;
import com.platform.mesh.app.biz.modules.map.logfilerel.domain.po.MapLogFileRel;
import com.platform.mesh.app.biz.modules.map.logfilerel.service.IMapLogFileRelService;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.api.modules.doc.feign.RemoteDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单
 * @author 蝉鸣
 */
@Service
public class MapLogServiceManual {

    @Autowired
    private IMapLogFileRelService mapLogFileRelService;

    @Autowired
    private RemoteDocService remoteDocService;

    /**
     * 功能描述:
     * 〈关联地图打卡图片信息〉
     * @param mapLogDTO mapLogDTO
     * @author 蝉鸣
     */
    public void addRel(Long id, MapLogDTO mapLogDTO) {
        if(ObjectUtil.isEmpty(id) ||  CollUtil.isEmpty(mapLogDTO.getFileIds())){
            return;
        }
        List<MapLogFileRel> fileRels = mapLogDTO.getFileIds().stream().map(fileId -> {
            MapLogFileRel mapLogFileRel = BeanUtil.copyProperties(mapLogDTO, MapLogFileRel.class);
            mapLogFileRel.setMapId(id);
            mapLogFileRel.setFileId(fileId);
            return mapLogFileRel;
        }).toList();
        //删除旧信息
        mapLogFileRelService.lambdaUpdate()
                .eq(MapLogFileRel::getModuleId,mapLogDTO.getModuleId())
                .eq(MapLogFileRel::getDataId,mapLogDTO.getDataId())
                .eq(MapLogFileRel::getMapId,id)
                .remove();
        //新增信息
        mapLogFileRelService.saveBatch(fileRels);
    }

    /**
     * 功能描述:
     * 〈封装VO信息〉
     * @param records records
     * @author 蝉鸣
     */
    public void packVO(List<MapLogVO> records) {
        if(CollUtil.isEmpty(records)){
            return;
        }
        List<Long> mapIds = records.stream().map(MapLogVO::getId).toList();
        //获取所有图片关联信息
        List<MapLogFileRel> fileRels = mapLogFileRelService.lambdaQuery().in(MapLogFileRel::getMapId, mapIds).list();
        if(CollUtil.isEmpty(fileRels)){
            return;
        }
        List<Long> fileIds = fileRels.stream().map(MapLogFileRel::getFileId).toList();
        List<DocFileVO> fileVOS = remoteDocService.getDocFiles(fileIds).getData();
        if(CollUtil.isEmpty(fileVOS)){
            return;
        }
        Map<Long, List<Long>> dataMap = fileRels.stream()
                .collect(Collectors.groupingBy(
                        MapLogFileRel::getMapId,
                        Collectors.mapping(
                                MapLogFileRel::getFileId,
                                Collectors.toList()
                        )
                ));
        for (MapLogVO logVO : records) {
            if(dataMap.containsKey(logVO.getId())){
                List<Long> fileIdList = dataMap.get(logVO.getId());
                List<DocFileVO> files = fileVOS.stream().filter(file -> fileIdList.contains(file.getId())).toList();
                logVO.setFileVOS(files);
            }
        }

    }
}