package com.platform.mesh.bpm.biz.logic.type.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import com.platform.mesh.bpm.biz.logic.ref.factory.LogicRefFactory;
import com.platform.mesh.bpm.biz.logic.type.LogicTypeService;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 逻辑与工厂实现
 * @author 蝉鸣
 */
@Service
public class LogicAndFactoryImpl implements LogicTypeService {

    @Autowired
    private LogicRefFactory logicRefFactory;

    /**
     * 功能描述:
     * 〈逻辑类型〉
     * @return 正常返回:{@link LogicTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicTypeEnum logicType() {
        return LogicTypeEnum.AND;
    }

    /**
     * 功能描述:
     * 〈逻辑与对比〉
     * @param params params
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean compare(Boolean ...params) {
        if(ArrayUtil.isEmpty(params)){
            return Boolean.FALSE;
        }
        return BooleanUtil.andOfWrap(params);
    }
}
