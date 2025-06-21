package com.platform.mesh.tmp.biz.modules.init.db.service.manual;

import com.platform.mesh.app.api.modules.init.db.mapper.DbMapper;
import com.platform.mesh.app.api.modules.init.db.service.manual.DbServiceManual;
import com.platform.mesh.tmp.biz.modules.init.db.mapper.TmpDbMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系Db数据
 * @author 蝉鸣
 */
@Service
public class TmpDbServiceManual extends DbServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TmpDbServiceManual.class);

    @Autowired
    private TmpDbMapper dbMapper;

    /**
     * 功能描述:
     * 〈实现Mapper〉
     * @return 正常返回:{@link DbMapper}
     * @author 蝉鸣
     */
    @Override
    public DbMapper getDbMapper() {
        return dbMapper;
    }
}