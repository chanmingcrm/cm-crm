package com.platform.mesh.bpm.biz.pipe.base.executor;

import com.platform.mesh.bpm.biz.pipe.base.pipe.BasePipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 基础管道执行
 * @author 蝉鸣
 */
@Service
public class BaseExecutor {

    private final static Logger log = LoggerFactory.getLogger(BaseExecutor.class);

    /**
     * 功能描述:
     * 〈基础管道执行〉
     * @param basePipe basePipe
     * @param param param
     * @author 蝉鸣
     */
    public <R,C extends String> void  baseExecutor(BasePipe<R,C> basePipe, Long param){
        basePipe.executor();
    }
}
