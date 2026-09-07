package com.platform.mesh.upms.biz.modules.conf.sysset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto.ConfSysSetPageDTO;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.po.ConfSysSet;
import com.platform.mesh.upms.biz.modules.conf.sysset.domain.vo.ConfSysSetVO;
import com.platform.mesh.upms.biz.modules.conf.sysset.exception.ConfSysSetExceptionEnum;
import com.platform.mesh.upms.biz.modules.conf.sysset.mapper.ConfSysSetMapper;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.IConfSysSetService;
import com.platform.mesh.upms.biz.modules.conf.sysset.service.manual.ConfSysSetServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置系统
 * @author 蝉鸣
 */
@Service
public class ConfSysSetServiceImpl extends ServiceImpl<ConfSysSetMapper, ConfSysSet> implements IConfSysSetService  {

    private static final String MCP_ENABLED_CONF_MAC = "crm_mcp_enabled";

    @Autowired
    private ConfSysSetServiceManual confSysSetServiceManual;

    @Override
    public PageVO<ConfSysSetVO> selectPage(ConfSysSetPageDTO pageDTO) {
        MPage<ConfSysSet> sysSetMPage = MPageUtil.pageEntityToMPage(pageDTO, ConfSysSet.class);
        MPage<ConfSysSet> page = this
                .lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(pageDTO.getConfSource()),ConfSysSet::getConfSource,pageDTO.getConfSource())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getConfMac()),ConfSysSet::getConfMac,pageDTO.getConfMac())
                .page(sysSetMPage);
        return MPageUtil.convertToVO(page, ConfSysSetVO.class);
    }

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
     * 〈获取当前配置系统信息〉
     * @param confMac confMac
     * @return 正常返回:{@link ConfSysSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfSysSetVO getSysSetInfoByMac(String confMac) {
        ConfSysSet confSysSet = this.lambdaQuery().eq(ConfSysSet::getConfMac,confMac).one();
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
        //校验参数是否存在
        boolean exists = this.lambdaQuery().eq(ConfSysSet::getConfMac, sysSetDTO.getConfMac()).exists();
        if(exists){
            throw ConfSysSetExceptionEnum.ADD_NO_EXIST.getBaseException();
        }
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
