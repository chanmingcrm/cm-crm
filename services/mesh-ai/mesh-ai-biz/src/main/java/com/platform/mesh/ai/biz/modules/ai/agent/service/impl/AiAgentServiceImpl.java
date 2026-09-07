package com.platform.mesh.ai.biz.modules.ai.agent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.dto.AiAgentDTO;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.vo.AiAgentVO;
import com.platform.mesh.ai.biz.modules.ai.agent.exception.AiAgentExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.agent.mapper.AiAgentMapper;
import com.platform.mesh.ai.biz.modules.ai.agent.service.IAiAgentService;
import com.platform.mesh.ai.biz.modules.ai.agent.service.manual.AiAgentServiceManual;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AiAgent
 * @author 蝉鸣
 */
@Service
public class AiAgentServiceImpl extends ServiceImpl<AiAgentMapper, AiAgent> implements IAiAgentService {

    @Autowired
    private AiAgentServiceManual aiAgentServiceManual;
    
    /**
     * 功能描述: 
     * 〈获取当前AIAgent信息〉
     * @param agentId agentId
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    @Override
    public AiAgentVO getAiAgentById(Long agentId) {
        AiAgent aiMcp = this.getById(agentId);
        return BeanUtil.copyProperties(aiMcp, AiAgentVO.class);
    }

    /**
     * 功能描述:
     * 〈获取当前AIAgent信息〉
     * @param agentId agentId
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    @Override
    public AiAgent getAiAgentBOById(Long agentId) {
        return this.getBaseMapper().getAiAgentBOById(agentId);
    }

    /**
     * 功能描述:
     * 〈新增AIAgent〉
     * @param agentDTO agentDTO
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    @Override
    public AiAgentVO addAiAgent(AiAgentDTO agentDTO) {
        AiAgent aiAgent = BeanUtil.copyProperties(agentDTO, AiAgent.class);
        //密钥绑定创建
        if(ObjectUtil.isEmpty(aiAgent.getAgentKey())){
            //获取默认智能体密钥以及空间
            AiAgent defaultAgent = this.getBaseMapper().getDefaultAgent(aiAgent.getAgentFlag());
            if(ObjectUtil.isEmpty(defaultAgent)){
                throw AiAgentExceptionEnum.ADD_NO_INIT.getBaseException();
            }
        }else{
            if(ObjectUtil.isEmpty(aiAgent.getAgentKey())){
                //获取默认智能体密钥以及空间
                AiAgent defaultAgent = this.getBaseMapper().getDefaultAgent(aiAgent.getAgentFlag());
                if(ObjectUtil.isEmpty(defaultAgent)){
                    throw AiAgentExceptionEnum.ADD_NO_INIT.getBaseException();
                }
                aiAgent.setAgentSpace(defaultAgent.getAgentSpace());
                aiAgent.setAgentSecret(defaultAgent.getAgentSecret());
                //Prompt动态创建
                aiAgentServiceManual.addAiAgent(aiAgent);
            }
        }
        aiAgent.setDelFlag(YesOrNoEnum.YES.getValue());
        this.save(aiAgent);
        //保存支持库
        aiAgentServiceManual.saveKlRel(aiAgent,agentDTO.getKlIds());
        return BeanUtil.copyProperties(aiAgent, AiAgentVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AIAgent〉
     * @param agentDTO agentDTO
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    @Override
    public AiAgentVO editAiAgent(AiAgentDTO agentDTO) {
        if(ObjectUtil.isEmpty(agentDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiAgentDTO::getId);
            throw AiAgentExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiAgent aiAgent = getById(agentDTO.getId());
        if(ObjectUtil.isEmpty(aiAgent)){
            throw AiAgentExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //密钥绑定创建
        if(ObjectUtil.isEmpty(agentDTO.getAgentKey())){
            //获取默认智能体密钥以及空间
            AiAgent defaultAgent = this.getBaseMapper().getDefaultAgent(aiAgent.getAgentFlag());
            if(ObjectUtil.isEmpty(defaultAgent)){
                throw AiAgentExceptionEnum.ADD_NO_INIT.getBaseException();
            }
            aiAgent.setAgentSpace(defaultAgent.getAgentSpace());
            aiAgent.setAgentSecret(defaultAgent.getAgentSecret());
            //更新信息
            aiAgent.setAgentName(agentDTO.getAgentName());
            aiAgent.setAgentLogo(agentDTO.getAgentLogo());
            aiAgent.setAgentDesc(agentDTO.getAgentDesc());
            aiAgent.setAgentNick(agentDTO.getAgentNick());
            aiAgent.setAgentPrompt(agentDTO.getAgentPrompt());
            //Prompt动态创建
            aiAgentServiceManual.addAiAgent(aiAgent);
        }
        this.updateById(aiAgent);
        return BeanUtil.copyProperties(aiAgent, AiAgentVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AIAgent〉
     * @param agentId agentId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAiAgent(Long agentId) {
        return this.removeById(agentId);
    }

    /**
     * 功能描述:
     * 〈根据组Hash 获取智能体客服〉
     * @param groupHash groupHash
     * @return 正常返回:{@link AiAgent}
     * @author 蝉鸣
     */
    @Override
    public AiAgent getAiAgentBOByGroupHash(String groupHash) {
        //
        return this.getBaseMapper().getAiAgentBOByGroupHash(groupHash);
    }
}
