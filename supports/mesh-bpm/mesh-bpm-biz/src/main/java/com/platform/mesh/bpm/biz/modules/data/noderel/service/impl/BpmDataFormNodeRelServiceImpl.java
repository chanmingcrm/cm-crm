package com.platform.mesh.bpm.biz.modules.data.noderel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.dto.BpmDataFormNodeRelDTO;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.po.BpmDataFormNodeRel;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.vo.BpmDataFormNodeRelVO;
import com.platform.mesh.bpm.biz.modules.data.noderel.exception.BpmDataFormNodeRelExceptionEnum;
import com.platform.mesh.bpm.biz.modules.data.noderel.mapper.BpmDataFormNodeRelMapper;
import com.platform.mesh.bpm.biz.modules.data.noderel.service.IBpmDataFormNodeRelService;
import com.platform.mesh.bpm.biz.modules.data.noderel.service.manual.BpmDataFormNodeRelServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 业务数据模板流程节点表单关系
 * @author 蝉鸣
 */
@Service
public class BpmDataFormNodeRelServiceImpl extends ServiceImpl<BpmDataFormNodeRelMapper, BpmDataFormNodeRel> implements IBpmDataFormNodeRelService {

    @Autowired
    private BpmDataFormNodeRelServiceManual bpmDataFormNodeRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前业务数据模板流程节点表单关系信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeRelVO getDataFormNodeRelInfoByTempNodeId(Long tempNodeId) {
        BpmDataFormNodeRel bpmDataFormNodeRel = this.lambdaQuery().eq(BpmDataFormNodeRel::getTempNodeId,tempNodeId).one();
        return bpmDataFormNodeRelServiceManual.getDataFormNodeRelInfoById(bpmDataFormNodeRel);
    }

    /**
     * 功能描述:
     * 〈获取当前业务数据模板流程节点表单关系信息〉
     * @param nodeIds nodeIds
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    public List<BpmDataFormNodeRelVO> getDataFormNodeRelInfoByNodeId(List<Long> nodeIds){
        if(CollUtil.isEmpty(nodeIds)){
            return CollUtil.newArrayList();
        }
        List<BpmDataFormNodeRel> nodeRelList = this.lambdaQuery().in(BpmDataFormNodeRel::getTempNodeId, nodeIds).list();
        return BeanUtil.copyToList(nodeRelList,BpmDataFormNodeRelVO.class);
    }

    /**
     * 功能描述:
     * 〈新增业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeRelVO addDataFormNodeRel(BpmDataFormNodeRelDTO dataFormNodeRelDTO) {
        BpmDataFormNodeRel bpmDataFormNodeRel = BeanUtil.copyProperties(dataFormNodeRelDTO, BpmDataFormNodeRel.class);
        this.save(bpmDataFormNodeRel);
        return BeanUtil.copyProperties(bpmDataFormNodeRel, BpmDataFormNodeRelVO.class);
    }

    /**
     * 功能描述:
     * 〈批量新增业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTOS dataFormNodeRelDTOS
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddDataFormNodeRel(List<BpmDataFormNodeRelDTO> dataFormNodeRelDTOS) {
        CopyOptions copyOptions = ObjFieldUtil.ignoreDefault();
        List<BpmDataFormNodeRel> bpmDataFormNodeRels = BeanUtil.copyToList(dataFormNodeRelDTOS, BpmDataFormNodeRel.class,copyOptions);
        this.saveBatch(bpmDataFormNodeRels);
    }

    /**
     * 功能描述:
     * 〈修改业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeRelVO editDataFormNodeRel(BpmDataFormNodeRelDTO dataFormNodeRelDTO) {
        if(ObjectUtil.isEmpty(dataFormNodeRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(BpmDataFormNodeRelDTO::getId);
            throw BpmDataFormNodeRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        BpmDataFormNodeRel bpmDataFormNodeRel = BeanUtil.copyProperties(dataFormNodeRelDTO, BpmDataFormNodeRel.class);
        this.updateById(bpmDataFormNodeRel);
        return BeanUtil.copyProperties(bpmDataFormNodeRel, BpmDataFormNodeRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelId dataFormNodeRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDataFormNodeRel(Long dataFormNodeRelId) {
        
        return this.removeById(dataFormNodeRelId);
    }
}
