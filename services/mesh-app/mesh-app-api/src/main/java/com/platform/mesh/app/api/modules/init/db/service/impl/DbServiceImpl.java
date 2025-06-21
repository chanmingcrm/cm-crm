package com.platform.mesh.app.api.modules.init.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransSearchBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransBO;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.app.api.modules.init.db.service.IDbService;
import com.platform.mesh.app.api.modules.init.db.service.manual.DbServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description Db服务
 * @author 蝉鸣
 */
public abstract class DbServiceImpl implements IDbService {

    private static final Logger log = LoggerFactory.getLogger(DbServiceImpl.class);

    @Autowired
    private DbServiceManual dbServiceManual;



    /**
     * 功能描述:
     * 〈获取自定义模块数据库表名称〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    public List<String> initEsDbTables(){
        return dbServiceManual.selectAppTables();
    }

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    @Override
    public void transDbData() {

        log.info(">>>数据转化定时任务开始执行！！！");
        log.info(">>>数据转化定时任务开始执行！！！");
        log.info(">>>数据转化定时任务开始执行！！！");
        log.info(">>>数据转化定时任务开始执行！！！");
        log.info(">>>数据转化定时任务开始执行！！！");

        //获取当前数据库满足条件的数据表名
        List<String> tableNames = initEsDbTables();
        if(CollUtil.isEmpty(tableNames)){
            return;
        }
        ModulePageDTO pageDTO = new ModulePageDTO();
        pageDTO.setModuleSchemas(tableNames);
        Integer pageNum = NumberConst.NUM_1;
        //根据过滤条件查询检索数据
        while (true){
            //获取对应转化设置
            pageDTO.setPageNum(pageNum);
            PageVO<AppModuleSetTransBO> setTransPage = dbServiceManual.getModuleSetTransPage(pageDTO);
            if(CollUtil.isEmpty(setTransPage.getRecords())){
                break;
            }
            for (AppModuleSetTransBO setTransBO : setTransPage.getRecords()) {
                AppModuleSetTransSearchBO searchBO = BeanUtil.copyProperties(setTransBO, AppModuleSetTransSearchBO.class);
                Integer transPage = NumberConst.NUM_1;
                while (true){
                    searchBO.setPageNum(transPage);
                    PageVO<Object> dataList = dbServiceManual.searchSearchData(searchBO);
                    List<Long> dataIds = dbServiceManual.getDataIds(dataList.getRecords(), searchBO.getModuleSearchRelColumn());
                    if(CollUtil.isEmpty(dataIds)){
                        break;
                    }
                    DbTransBO dbTransBO = new DbTransBO();
                    dbTransBO.setTransBO(setTransBO);
                    dbTransBO.setDataIds(dataIds);
                    dbServiceManual.transData(dbTransBO);
                    transPage++;
                }
            }
            pageNum++;
        }

    }

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    @Override
    public Boolean transDbData(DbTransDTO transDTO) {

        AppModuleSetTransBO setTransBO = dbServiceManual.getModuleSetTransById(transDTO.getTransId());
        if(ObjectUtil.isEmpty(setTransBO) || ObjectUtil.isEmpty(setTransBO.getModuleFrom()) || ObjectUtil.isEmpty(setTransBO.getModuleTo())){
            return Boolean.FALSE;
        }
        DbTransBO dbTransBO = new DbTransBO();
        dbTransBO.setTransBO(setTransBO);
        dbTransBO.setDataIds(transDTO.getDataIds());
        if(ObjectUtil.isNotEmpty(transDTO.getMemberId())){
            //获取当前成员默认数据权限信息
            OrgMemberRelBO memberRelBO = dbServiceManual.getDataScopeByMemberId(transDTO.getMemberId());
            dbTransBO.setUserRelBO(memberRelBO);
        }
        //转化数据
        dbServiceManual.transData(dbTransBO);
        return Boolean.TRUE;
    }
}