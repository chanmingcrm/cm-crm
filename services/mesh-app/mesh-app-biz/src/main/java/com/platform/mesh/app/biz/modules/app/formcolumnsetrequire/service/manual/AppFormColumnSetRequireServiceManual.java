package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段请求
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetRequireServiceManual {


    /**
     * 功能描述:
     * 〈封装请求信息〉
     * @param requireMap requireMap
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    public List<AppFormColumnSetRequireVO> getFormColumnSetRequireInfo(Map<String, List<AppFormColumnSetRequireVO>> requireMap) {
        List<AppFormColumnSetRequireVO> requireVOList = CollUtil.newArrayList();
        requireMap.forEach((key,value)->{
            AppFormColumnSetRequireVO appFormColumnSetRequireVO = BeanUtil.copyProperties(CollUtil.getFirst(value), AppFormColumnSetRequireVO.class);
            requireVOList.add(appFormColumnSetRequireVO);
        });
        return requireVOList;
    }
}