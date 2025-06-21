package com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.vo.AppFormColumnMappingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.po.AppFormColumnMapping;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetTransMappingServiceManual {

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appFormColumnMapping appFormColumnMapping 
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    public AppFormColumnMappingVO getFormColumnMappingInfoById(AppFormColumnMapping appFormColumnMapping) {
        AppFormColumnMappingVO appFormColumnMappingVO = new AppFormColumnMappingVO();
        if(ObjectUtil.isEmpty(appFormColumnMappingVO)){
            return appFormColumnMappingVO;
        }
        //转换VO
        BeanUtil.copyProperties(appFormColumnMapping, appFormColumnMappingVO);
        return appFormColumnMappingVO;
    }

}