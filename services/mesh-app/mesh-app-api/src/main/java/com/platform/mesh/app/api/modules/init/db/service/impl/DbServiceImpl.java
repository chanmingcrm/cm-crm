package com.platform.mesh.app.api.modules.init.db.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.constant.AppConst;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransSearchBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.app.exception.AppExceptionEnum;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransBO;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.app.api.modules.init.db.service.IDbService;
import com.platform.mesh.app.api.modules.init.db.service.manual.DbServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;
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
        try{
            //根据过滤条件查询检索数据
            while (true){
                //获取对应转化设置
                pageDTO.setPageNum(pageNum);
                PageVO<AppModuleSetTransBO> setTransAutoPage = dbServiceManual.getModuleSetTransAutoPage(pageDTO);
                if(CollUtil.isEmpty(setTransAutoPage.getRecords())){
                    break;
                }
                for (AppModuleSetTransBO setTransAutoBO : setTransAutoPage.getRecords()) {
                    AppModuleSetTransSearchBO searchBO = BeanUtil.copyProperties(setTransAutoBO, AppModuleSetTransSearchBO.class);
                    Integer transPage = NumberConst.NUM_1;
                    while (true){
                        searchBO.setPageNum(transPage);
                        MPage<Long> dataPage = dbServiceManual.getTransDataIdsPage(searchBO);
                        if(CollUtil.isEmpty(dataPage.getRecords())){
                            break;
                        }
                        //缓存数量
                        RedissonUtil.setCacheObject(AppConst.PICK_APP_DATA_COUNT.concat(setTransAutoBO.getTransId().toString()),dataPage.getTotal());
                        DbTransBO dbTransBO = new DbTransBO();
                        dbTransBO.setTransBO(setTransAutoBO);
                        dbTransBO.setDataIds(dataPage.getRecords());
                        dbServiceManual.transData(dbTransBO);
                        transPage++;
                    }
                    //清除当前转化缓存
                    RedissonUtil.deleteObject(AppConst.PICK_APP_DATA_TRANS.concat(setTransAutoBO.getTransId().toString()));
                    RedissonUtil.deleteObject(AppConst.PICK_APP_DATA_COUNT.concat(setTransAutoBO.getTransId().toString()));
                }
                pageNum++;
            }
        }catch (Exception e){
            log.error(">>>数据转化定时任务异常信息！！！");
            log.error(e.getMessage());
            log.error(">>>数据转化定时任务异常信息。。。");
        }

    }

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    @Override
    public DbTransResBO transDbData(DbTransDTO transDTO) {
        AppModuleSetTransBO setTransBO = dbServiceManual.getModuleSetTransById(transDTO.getTransId());
        if(ObjectUtil.isEmpty(setTransBO) || ObjectUtil.isEmpty(setTransBO.getModuleFrom()) || ObjectUtil.isEmpty(setTransBO.getModuleTo())){
            throw AppExceptionEnum.ADD_DATA_TRANS_SET_INVALID.getBaseException();
        }
        List<Long> appModule = UserCacheUtil.getAppModules();
        if(!appModule.contains(setTransBO.getModuleTo().getId())){
            return null;
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
        return dbServiceManual.transData(dbTransBO);
    }


    /**
     * 功能描述:
     * 〈同步人员名称〉
     * @param memberBO memberBO
     * @author 蝉鸣
     */
    @Override
    public void syncUserName(OrgMemberBO memberBO) {
        dbServiceManual.syncUserName(memberBO);
    }

    /**
     * 功能描述:
     * 〈同步组织名称〉
     * @param levelBO levelBO
     * @author 蝉鸣
     */
    @Override
    public void syncOrgName(OrgLevelBO levelBO) {
        dbServiceManual.syncOrgName(levelBO);
    }

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    @Override
    public void transOrgData(OrgMemberTransBO transBO) {
        dbServiceManual.transOrgData(transBO);
    }
}
