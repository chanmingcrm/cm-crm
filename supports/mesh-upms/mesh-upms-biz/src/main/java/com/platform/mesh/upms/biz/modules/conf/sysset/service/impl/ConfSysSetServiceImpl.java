package com.platform.mesh.upms.biz.modules.conf.sysset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.vo.ConfSysSetVO;
import com.platform.mesh.upms.biz.modules.conf.sysset.exception.ConfSysSetExceptionEnum;
import com.platform.mesh.upms.biz.modules.conf.sysset.mapper.ConfSysSetMapper;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.IConfSysSetService;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.manual.ConfSysSetServiceManual;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置系统
 * @author 蝉鸣
 */
@Service
public class ConfSysSetServiceImpl extends ServiceImpl<ConfSysSetMapper, ConfSysSet> implements IConfSysSetService  {

    @Autowired
    private ConfSysSetServiceManual confSysSetServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前配置系统信息〉
     * @param sysSetId sysSetId  
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSetVO getSysSetInfoById(Long sysSetId) {
        ConfSysSet confSysSet = this.getById(sysSetId);
        return confSysSetServiceManual.getSysSetInfoById(confSysSet);
    }

    /**
     * 功能描述:
     * 〈新增配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSetVO addSysSet(ConfSysSetDTO sysSetDTO) {
        ConfSysSet confSysSet = BeanUtil.copyProperties(sysSetDTO, ConfSysSet.class);
        this.save(confSysSet);
        return BeanUtil.copyProperties(confSysSet, ConfSysSetVO.class);
    }

    /**
     * 功能描述:
     * 〈修改配置系统〉
     * @param sysSetDTO sysSetDTO
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSetVO editSysSet(ConfSysSetDTO sysSetDTO) {
        if(ObjectUtil.isEmpty(sysSetDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(ConfSysSetDTO::getId);
            throw ConfSysSetExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        ConfSysSet confSysSet = BeanUtil.copyProperties(sysSetDTO, ConfSysSet.class);
        this.updateById(confSysSet);
        return BeanUtil.copyProperties(confSysSet, ConfSysSetVO.class);
    }

    /**
     * 功能描述:
     * 〈删除配置系统〉
     * @param sysSetId sysSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteSysSet(Long sysSetId) {
        
        return this.removeById(sysSetId);
    }
}