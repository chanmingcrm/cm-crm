package com.platform.mesh.upms.biz.modules.conf.userset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.dto.ConfUserSetDTO;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.vo.ConfUserSetVO;
import com.platform.mesh.upms.biz.modules.conf.userset.exception.ConfUserSetExceptionEnum;
import com.platform.mesh.upms.biz.modules.conf.userset.mapper.ConfUserSetMapper;
import com.platform.mesh.upms.biz.modules.conf.userset.service.IConfUserSetService;
import com.platform.mesh.upms.biz.modules.conf.userset.service.manual.ConfUserSetServiceManual;
import com.platform.mesh.upms.biz.modules.conf.userset.domain.po.ConfUserSet;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置用户
 * @author 蝉鸣
 */
@Service
public class ConfUserSetServiceImpl extends ServiceImpl<ConfUserSetMapper, ConfUserSet> implements IConfUserSetService  {

    @Autowired
    private ConfUserSetServiceManual confUserSetServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前配置用户信息〉
     * @param userSetId userSetId  
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserSetVO getUserSetInfoById(Long userSetId) {
        ConfUserSet confUserSet = this.getById(userSetId);
        return confUserSetServiceManual.getUserSetInfoById(confUserSet);
    }

    /**
     * 功能描述:
     * 〈新增配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserSetVO addUserSet(ConfUserSetDTO userSetDTO) {
        ConfUserSet confUserSet = BeanUtil.copyProperties(userSetDTO, ConfUserSet.class);
        this.save(confUserSet);
        return BeanUtil.copyProperties(confUserSet, ConfUserSetVO.class);
    }

    /**
     * 功能描述:
     * 〈修改配置用户〉
     * @param userSetDTO userSetDTO
     * @return 正常返回:{@link ConfUserSetVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserSetVO editUserSet(ConfUserSetDTO userSetDTO) {
        if(ObjectUtil.isEmpty(userSetDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(ConfUserSetDTO::getId);
            throw ConfUserSetExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        ConfUserSet confUserSet = BeanUtil.copyProperties(userSetDTO, ConfUserSet.class);
        this.updateById(confUserSet);
        return BeanUtil.copyProperties(confUserSet, ConfUserSetVO.class);
    }

    /**
     * 功能描述:
     * 〈删除配置用户〉
     * @param userSetId userSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteUserSet(Long userSetId) {
        
        return this.removeById(userSetId);
    }
}