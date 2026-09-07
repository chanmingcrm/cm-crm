package com.platform.mesh.upms.biz.modules.conf.userui.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.dto.ConfUserUiDTO;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.vo.ConfUserUiVO;
import com.platform.mesh.upms.biz.modules.conf.userui.exception.ConfUserUiExceptionEnum;
import com.platform.mesh.upms.biz.modules.conf.userui.mapper.ConfUserUiMapper;
import com.platform.mesh.upms.biz.modules.conf.userui.service.IConfUserUiService;
import com.platform.mesh.upms.biz.modules.conf.userui.service.manual.ConfUserUiServiceManual;
import com.platform.mesh.upms.biz.modules.conf.userui.domain.po.ConfUserUi;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置UI
 * @author 蝉鸣
 */
@Service
public class ConfUserUiServiceImpl extends ServiceImpl<ConfUserUiMapper, ConfUserUi> implements IConfUserUiService  {

    @Autowired
    private ConfUserUiServiceManual confUserUiServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前配置UI信息〉
     * @param userUiId userUiId  
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserUiVO getUserUiInfoById(Long userUiId) {
        ConfUserUi confUserUi = this.getById(userUiId);
        return confUserUiServiceManual.getUserUiInfoById(confUserUi);
    }

    /**
     * 功能描述:
     * 〈新增配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserUiVO addUserUi(ConfUserUiDTO userUiDTO) {
        ConfUserUi confUserUi = BeanUtil.copyProperties(userUiDTO, ConfUserUi.class);
        this.save(confUserUi);
        return BeanUtil.copyProperties(confUserUi, ConfUserUiVO.class);
    }

    /**
     * 功能描述:
     * 〈修改配置UI〉
     * @param userUiDTO userUiDTO
     * @return 正常返回:{@link ConfUserUiVO}
     * @author 蝉鸣
     */
    @Override
    public ConfUserUiVO editUserUi(ConfUserUiDTO userUiDTO) {
        if(ObjectUtil.isEmpty(userUiDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(ConfUserUiDTO::getId);
            throw ConfUserUiExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        ConfUserUi confUserUi = BeanUtil.copyProperties(userUiDTO, ConfUserUi.class);
        this.updateById(confUserUi);
        return BeanUtil.copyProperties(confUserUi, ConfUserUiVO.class);
    }

    /**
     * 功能描述:
     * 〈删除配置UI〉
     * @param userUiId userUiId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteUserUi(Long userUiId) {
        
        return this.removeById(userUiId);
    }
}
