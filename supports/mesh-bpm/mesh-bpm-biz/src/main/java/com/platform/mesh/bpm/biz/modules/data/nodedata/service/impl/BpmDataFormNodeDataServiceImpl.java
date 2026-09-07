package com.platform.mesh.bpm.biz.modules.data.nodedata.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.dto.BpmDataFormNodeDataDTO;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.po.BpmDataFormNodeData;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.vo.BpmDataFormNodeDataVO;
import com.platform.mesh.bpm.biz.modules.data.nodedata.exception.BpmDataFormNodeDataExceptionEnum;
import com.platform.mesh.bpm.biz.modules.data.nodedata.mapper.BpmDataFormNodeDataMapper;
import com.platform.mesh.bpm.biz.modules.data.nodedata.service.IBpmDataFormNodeDataService;
import com.platform.mesh.bpm.biz.modules.data.nodedata.service.manual.BpmDataFormNodeDataServiceManual;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
@Service
public class BpmDataFormNodeDataServiceImpl extends ServiceImpl<BpmDataFormNodeDataMapper, BpmDataFormNodeData> implements IBpmDataFormNodeDataService {

    @Autowired
    private BpmDataFormNodeDataServiceManual bpmDataFormNodeDataServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前业务数据实例流程节点表单数据信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeDataVO getDataFormNodeDataInfoByInstNodeId(Long instNodeId) {
        BpmDataFormNodeData bpmDataFormNodeData = this.lambdaQuery().eq(BpmDataFormNodeData::getInstNodeId,instNodeId).one();
        return bpmDataFormNodeDataServiceManual.getDataFormNodeDataInfoById(bpmDataFormNodeData);
    }

    /**
     * 功能描述:
     * 〈新增业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeDataVO addDataFormNodeData(BpmDataFormNodeDataDTO dataFormNodeDataDTO) {
        BpmDataFormNodeData bpmDataFormNodeData = BeanUtil.copyProperties(dataFormNodeDataDTO, BpmDataFormNodeData.class);
        //删除旧数据
        this.lambdaUpdate().eq(BpmDataFormNodeData::getInstNodeId,bpmDataFormNodeData.getInstNodeId()).remove();
        //新增数据
        this.save(bpmDataFormNodeData);
        //发送审批回调消息
        bpmDataFormNodeDataServiceManual.sendBpmMsg(bpmDataFormNodeData);
        return BeanUtil.copyProperties(bpmDataFormNodeData, BpmDataFormNodeDataVO.class);
    }

    /**
     * 功能描述:
     * 〈修改业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormNodeDataVO editDataFormNodeData(BpmDataFormNodeDataDTO dataFormNodeDataDTO) {
        if(ObjectUtil.isEmpty(dataFormNodeDataDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(BpmDataFormNodeDataDTO::getId);
            throw BpmDataFormNodeDataExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        BpmDataFormNodeData bpmDataFormNodeData = BeanUtil.copyProperties(dataFormNodeDataDTO, BpmDataFormNodeData.class);
        this.updateById(bpmDataFormNodeData);
        return BeanUtil.copyProperties(bpmDataFormNodeData, BpmDataFormNodeDataVO.class);
    }

    /**
     * 功能描述:
     * 〈删除业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataId dataFormNodeDataId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDataFormNodeData(Long dataFormNodeDataId) {
        
        return this.removeById(dataFormNodeDataId);
    }

    /**
     * 功能描述:
     * 〈获取阶段流程名称〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    @Override
    public String getProcessStageName(Long instProcessId) {
        BpmDataFormNodeData nodeData = this.lambdaQuery().eq(BpmDataFormNodeData::getInstProcessId, instProcessId)
                .orderByDesc(BpmDataFormNodeData::getCreateTime)
                .last(MybatisPlusConst.LIMIT_1)
                .one();
        return bpmDataFormNodeDataServiceManual.getProcessStageName(nodeData);
    }
}
