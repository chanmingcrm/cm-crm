package com.platform.mesh.upms.biz.modules.log.update.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;
import com.platform.mesh.upms.biz.modules.log.update.domain.dto.LogUpdateDTO;
import com.platform.mesh.upms.biz.modules.log.update.domain.po.LogUpdate;
import com.platform.mesh.upms.biz.modules.log.update.exception.LogUpdateExceptionEnum;
import com.platform.mesh.upms.biz.modules.log.update.mapper.LogUpdateMapper;
import com.platform.mesh.upms.biz.modules.log.update.service.ILogUpdateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 更新日志
 * @author 蝉鸣
 */
@Service()
public class LogUpdateServiceImpl extends ServiceImpl<LogUpdateMapper, LogUpdate> implements ILogUpdateService {


    /**
     * 功能描述:
     * 〈获取最新更新信息〉
     * @return 正常返回:{@link LogUpdate}
     * @author 蝉鸣
     */
    @Override
    public LogUpdate getLastOne(Integer logFLag) {
        return this.getBaseMapper().getLastOne(logFLag);
    }

    /**
     * 功能描述:
     * 〈新增更新信息〉
     * @param logDTO logDTO
     * @author 蝉鸣
     */
    @Override
    public void addLog(LogUpdateDTO logDTO) {
        if(ObjectUtil.isEmpty(logDTO.getLogFlag())){
            logDTO.setLogFlag(LoginTypeEnum.APP.getValue());
        }
        LogUpdate lastOne = this.getLastOne(logDTO.getLogFlag());
        if(ObjectUtil.isNotEmpty(lastOne)){
            if(logDTO.getLogCode()<=lastOne.getLogCode()){
                throw LogUpdateExceptionEnum.ADD_CODE_INVALID.getBaseException();
            }
        }
        LogUpdate logUpdate = BeanUtil.copyProperties(logDTO, LogUpdate.class);
        logUpdate.setCreateTime(LocalDateTime.now());
        this.save(logUpdate);
    }

    /**
     * 功能描述:
     * 〈修改更新信息〉
     * @param logDTO logDTO
     * @author 蝉鸣
     */
    @Override
    public void editLog(LogUpdateDTO logDTO) {
        if(ObjectUtil.isEmpty(logDTO.getLogFlag())){
            logDTO.setLogFlag(LoginTypeEnum.APP.getValue());
        }
        LogUpdate lastOne = this.getLastOne(logDTO.getLogFlag());
//        if(ObjectUtil.isNotEmpty(lastOne) && ObjectUtil.isNotEmpty(logDTO.getLogCode())){
//            if(logDTO.getLogCode()<=lastOne.getLogCode()){
//                throw LogUpdateExceptionEnum.ADD_CODE_INVALID.getBaseException();
//            }
//        }
        LogUpdate logUpdate = BeanUtil.copyProperties(logDTO, LogUpdate.class);
        this.updateById(logUpdate);
    }
}

