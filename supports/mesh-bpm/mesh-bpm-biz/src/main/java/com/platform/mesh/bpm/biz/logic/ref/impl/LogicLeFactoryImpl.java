package com.platform.mesh.bpm.biz.logic.ref.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.logic.ref.LogicRefService;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 逻辑关系工厂实现
 * @author 蝉鸣
 */
@Service
public class LogicLeFactoryImpl implements LogicRefService {

    private final static Logger log = LoggerFactory.getLogger(LogicLeFactoryImpl.class);

    /**
     * 功能描述:
     * 〈逻辑关系类型〉
     * @return 正常返回:{@link LogicRefEnum}
     * @author 蝉鸣
     */
    @Override
    public LogicRefEnum refType() {
        return LogicRefEnum.LE;
    }

    /**
     * 功能描述:
     * 〈逻辑运算〉
     * @param paramOne paramOne
     * @param paramTwo paramTwo
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean compare(List<String> paramOne,List<String> paramTwo) {
        String one = CollUtil.getFirst(paramOne);
        String two = CollUtil.getFirst(paramTwo);
        boolean equal = ObjectUtil.equal(one, two);
        return !CollUtil.containsAll(paramOne, paramTwo);
    }


}
