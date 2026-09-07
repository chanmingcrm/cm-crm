package com.platform.mesh.bpm.biz.modules.temp.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessAddDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessEditDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.process.exception.TempProcessExceptionEnum;
import com.platform.mesh.bpm.biz.modules.temp.process.mapper.BpmTempProcessMapper;
import com.platform.mesh.bpm.biz.modules.temp.process.service.IBpmTempProcessService;
import com.platform.mesh.bpm.biz.modules.temp.process.service.manual.BpmTempProcessServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempProcessServiceImpl extends ServiceImpl<BpmTempProcessMapper, BpmTempProcess> implements IBpmTempProcessService {

    protected final Logger log = LoggerFactory.getLogger(BpmTempProcessServiceImpl.class);

    @Autowired
    private BpmTempProcessServiceManual bpmTempProcessServiceManual;

    /**
     * 获取封装方法
     */
    @Override
    public BpmTempProcessServiceManual getServiceManual() {
        return bpmTempProcessServiceManual;
    }

    /**
     * 功能描述:
     * 〈新建流程模板〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<BpmTempProcessVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmTempProcessVO> selectPage(BpmTempProcessPageDTO pageDTO) {
        MPage<BpmTempProcess> processMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmTempProcess.class);
        List<Long> groupList = bpmTempProcessServiceManual.getChildGroupIds(pageDTO.getGroupId());
        pageDTO.setGroupIds(groupList);
        MPage<BpmTempProcessVO> page = this.getBaseMapper().selectMPage(processMPage, pageDTO);
        return MPageUtil.convertToVO(page, BpmTempProcessVO.class);
    }

    /**
     * 功能描述:
     * 〈添加流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BpmTempProcess addProcessTemp(BpmTempProcessAddDTO bpmTempProcessAddDTO) {
        //页面添加流程默认都是顶层主流程
        BpmTempProcess bpmTempProcess = BeanUtil.copyProperties(bpmTempProcessAddDTO, BpmTempProcess.class);
        bpmTempProcess.setTempRootId(NumberConst.NUM_0.longValue());
        bpmTempProcess.setRunFlag(ProcessRunEnum.INIT.getValue());
        if(ObjectUtil.isEmpty(bpmTempProcess.getProcessHash())) {
            bpmTempProcess.setProcessHash(IdUtil.fastSimpleUUID());
        }
        this.save(bpmTempProcess);
        //添加分组关系
        bpmTempProcessServiceManual.addGroupRel(bpmTempProcessAddDTO.getGroupId(),bpmTempProcess.getId());
        return bpmTempProcess;
    }

    /**
     * 功能描述:
     * 〈编辑流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public BpmTempProcess editProcessTemp(BpmTempProcessEditDTO bpmTempProcessEditDTO) {
        BpmTempProcess tempProcess = getById(bpmTempProcessEditDTO.getProcessId());
        if(ObjectUtil.isEmpty(tempProcess)){
            throw TempProcessExceptionEnum.ADD_NO_DATA.getBaseException();
        }
        BeanUtil.copyProperties(bpmTempProcessEditDTO, tempProcess);
        updateById(tempProcess);
        //添加分组关系
        bpmTempProcessServiceManual.editGroupRel(bpmTempProcessEditDTO.getOriginGroupId(),bpmTempProcessEditDTO.getGroupId(),tempProcess.getId());
        return tempProcess;
    }

    /**
     * 功能描述:
     * 〈新建流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Override
    public BpmTempProcess designProcessTemp(BpmTempProcessDesignDTO bpmTempProcessDesignDTO) {
        if(ObjectUtil.isEmpty(bpmTempProcessDesignDTO.getProcessDTO())){
            throw TempProcessExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        if(ObjectUtil.isEmpty(bpmTempProcessDesignDTO.getProcessDTO().getProcessId())){
            throw TempProcessExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        //先保存流程信息
        BpmTempProcess tempProcess;
        //如果是顶层流程校验本流程是否存在
        if (ObjectUtil.isEmpty(bpmTempProcessDesignDTO.getProcessDTO().getParentProcessId())) {
            tempProcess = getById(bpmTempProcessDesignDTO.getProcessDTO().getProcessId());
            if(ObjectUtil.isEmpty(tempProcess)){
                throw TempProcessExceptionEnum.ADD_NO_DATA.getBaseException();
            }
        }else{
            //如果是子流程，校验父流程是否存在
            BpmTempProcess parentTempProcess = getById(bpmTempProcessDesignDTO.getProcessDTO().getParentProcessId());
            if(ObjectUtil.isEmpty(parentTempProcess)){
                throw TempProcessExceptionEnum.ADD_NO_DATA.getBaseException();
            }
            tempProcess = BeanUtil.copyProperties(bpmTempProcessDesignDTO.getProcessDTO(), BpmTempProcess.class);
            tempProcess.setId(bpmTempProcessDesignDTO.getProcessDTO().getProcessId());
            tempProcess.setTempRootId(parentTempProcess.getId());
            tempProcess.setTempRootHash(parentTempProcess.getProcessHash());
            if(ObjectUtil.isEmpty(tempProcess.getProcessHash())){
                tempProcess.setProcessHash(IdUtil.fastSimpleUUID());
            }
            this.saveOrUpdate(tempProcess);
        }
        BpmTempProcessEditDTO bpmTempProcessEditDTO = BeanUtil.copyProperties(tempProcess, BpmTempProcessEditDTO.class);
        bpmTempProcessEditDTO.setProcessId(tempProcess.getId());
        bpmTempProcessDesignDTO.setProcessDTO(bpmTempProcessEditDTO);
        //删除旧流程
        bpmTempProcessServiceManual.delProcessTemp(bpmTempProcessDesignDTO.getProcessDTO().getProcessId());
        //新增流程
        bpmTempProcessServiceManual.designProcessTemp(bpmTempProcessDesignDTO);
        return tempProcess;
    }


    /**
     * 功能描述:
     * 〈获取流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    @Override
    public BpmTempProcessDesignVO getProcessTemp(Long tempProcessId) {
        BpmTempProcessDesignVO processDesignVO = new BpmTempProcessDesignVO();
        //获取流程信息
        BpmTempProcess tempProcess = this.getById(tempProcessId);
        BpmTempProcessVO tempProcessVO = BeanUtil.copyProperties(tempProcess, BpmTempProcessVO.class);
        processDesignVO.setProcessVO(tempProcessVO);
        return bpmTempProcessServiceManual.getProcessTemp(processDesignVO);
    }

    /**
     * 功能描述:
     * 〈发布流程模板〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean pubProcessTemp(Long tempProcessId) {
        BpmTempProcess tempProcess = getById(tempProcessId);
        //校验流程是否有效
        Boolean valid = bpmTempProcessServiceManual.checkTempProcess(tempProcess);
        if(!valid){
            throw TempProcessExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //设置流程状态
        tempProcess.setRunFlag(ProcessRunEnum.RUNNING.getValue());
        updateById(tempProcess);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈作废流程模板〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean cancelProcessTemp(Long tempProcessId) {
        //查询是否有运行的流程实例
        Boolean hasRun = bpmTempProcessServiceManual.checkRunProcessTemp(tempProcessId);
        //如果有则不能删除
        if(hasRun) {
            return Boolean.FALSE;
        }
        BpmTempProcess tempProcess = getById(tempProcessId);
        tempProcess.setRunFlag(ProcessRunEnum.CANCEL.getValue());
        updateById(tempProcess);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除流程模板〉
     * @return 正常返回:{@link Result<Object>}
     * @author 蝉鸣
     */
    @Override
    public Boolean delProcessTemp(Long tempProcessId) {
        //查询是否有运行的流程实例
        Boolean hasRun = bpmTempProcessServiceManual.checkRunProcessTemp(tempProcessId);
        //如果有则不能删除
//        if(hasRun) {
//            throw InstProcessExceptionEnum.DATA_HAS_RUNNING.getBaseException();
//        }
        //如果没有则可以删除
        Boolean aBoolean = bpmTempProcessServiceManual.delProcessTemp(tempProcessId);
        //删除其他信息
        removeById(tempProcessId);
        return aBoolean;
    }

}

