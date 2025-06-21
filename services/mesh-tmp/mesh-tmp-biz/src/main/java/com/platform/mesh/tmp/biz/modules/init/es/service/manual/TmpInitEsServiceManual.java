package com.platform.mesh.tmp.biz.modules.init.es.service.manual;

import com.platform.mesh.app.api.modules.init.es.mapper.AppInitEsMapper;
import com.platform.mesh.app.api.modules.init.es.service.manual.AppInitEsServiceManual;
import com.platform.mesh.tmp.biz.modules.init.es.mapper.TmpInitEsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系初始化Es
 * @author 蝉鸣
 */
@Service
public class TmpInitEsServiceManual extends AppInitEsServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TmpInitEsServiceManual.class);

    @Autowired
    private TmpInitEsMapper initEsMapper;

    /**
     * 功能描述:
     * 〈实现Mapper〉
     * @return 正常返回:{@link AppInitEsMapper}
     * @author 蝉鸣
     */
    @Override
    public AppInitEsMapper getInitEsMapper() {
        return initEsMapper;
    }
}