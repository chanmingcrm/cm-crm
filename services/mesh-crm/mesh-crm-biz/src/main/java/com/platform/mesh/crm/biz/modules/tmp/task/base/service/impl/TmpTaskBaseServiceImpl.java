package com.platform.mesh.crm.biz.modules.tmp.task.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.dto.TmpTaskBaseDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.po.TmpTaskBase;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.vo.TmpTaskBaseVO;
import com.platform.mesh.crm.biz.modules.tmp.task.base.mapper.TmpTaskBaseMapper;
import com.platform.mesh.crm.biz.modules.tmp.task.base.service.ITmpTaskBaseService;
import com.platform.mesh.crm.biz.modules.tmp.task.base.service.manual.TmpTaskBaseServiceManual;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.domain.po.TmpTaskBaseData;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelPageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务
 * @author 蝉鸣
 */
@Service
public class TmpTaskBaseServiceImpl extends AppServiceAbstract<TmpTaskBaseMapper, TmpTaskBase> implements ITmpTaskBaseService {

    @Autowired
    private TmpTaskBaseServiceManual tmpTaskBaseServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<TmpTaskBaseData> tmpTaskBaseDataList = BeanUtil.copyToList(dataList, TmpTaskBaseData.class);
        //批量保存data表数据
        tmpTaskBaseServiceManual.addDbDataBatch(tmpTaskBaseDataList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(TmpTaskBase::getScopeUserId,scopeUserId)
                .set(TmpTaskBase::getScopeOrgId,scopeOrgId)
                .in(TmpTaskBase::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈新增关联任务〉
     * @param tmpTaskBaseDTO taskBaseDTO
     * @return 正常返回:{@link TmpTaskBase}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public TmpTaskBase addTaskBaseAndRel(TmpTaskBaseDTO tmpTaskBaseDTO) {
        if(ObjectUtil.isNull(tmpTaskBaseDTO.getModuleId())){
            AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleIdBySchema(SqlUtil.getTableName(TmpTaskBase.class, TableName.class));
            tmpTaskBaseDTO.setModuleId(appModuleBaseBO.getId());
        }
        //新增任务
        TmpTaskBase tmpTaskBase = this.addDataSimp(tmpTaskBaseDTO, TmpTaskBase.class, TmpTaskBaseData.class);
        //增加关联关系
        tmpTaskBaseServiceManual.addTaskBaseAndRel(tmpTaskBase, tmpTaskBaseDTO.getRelDTOList());
        return tmpTaskBase;
    }

    /**
     * 功能描述:
     * 〈关联任务分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO< TmpTaskBaseVO >}
     * @author 蝉鸣
     */
    @Override
    public PageVO<TmpTaskBaseVO> taskBaseAndRelPage(TmpTaskBaseRelPageDTO pageDTO) {
        MPage<TmpTaskBase> mPage = MPageUtil.pageEntityToMPage(pageDTO, TmpTaskBase.class);
        MPage<TmpTaskBaseVO> page = this.getBaseMapper().taskBaseAndRelPage(mPage,pageDTO);
        return MPageUtil.convertToVO(page, TmpTaskBaseVO.class);
    }
}