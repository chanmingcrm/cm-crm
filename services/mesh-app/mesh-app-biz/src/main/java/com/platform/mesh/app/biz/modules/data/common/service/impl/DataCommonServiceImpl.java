package com.platform.mesh.app.biz.modules.data.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.biz.modules.data.common.domain.po.DataCommon;
import com.platform.mesh.app.biz.modules.data.common.mapper.DataCommonMapper;
import com.platform.mesh.app.biz.modules.data.common.service.IDataCommonService;
import com.platform.mesh.app.biz.modules.data.common.service.manual.DataCommonServiceManual;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import com.platform.mesh.app.biz.modules.data.commonrel.domain.po.DataCommonRel;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.es.domain.dto.EsDocUGetDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段数据
 * @author 蝉鸣
 */
@Service
public class DataCommonServiceImpl extends AppServiceAbstract<DataCommonMapper, DataCommon> implements IDataCommonService {


    @Autowired
    private DataCommonServiceManual dataCommonServiceManual;

    /**
     * 功能描述:
     * 〈新增通用数据〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<DataCommonData> commonDatas = BeanUtil.copyToList(dataList,DataCommonData.class);
        //批量保存data表数据
        dataCommonServiceManual.addDbDataBatch(commonDatas);
    }

    /**
     * 功能描述:
     * 〈新增Rel通用数据〉
     * @param relList relList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppRelPO> void addDbRelBatch(List<D> relList) {
        List<DataCommonRel> commonRelList = BeanUtil.copyToList(relList,DataCommonRel.class);
        //批量保存rel表数据
        dataCommonServiceManual.addDbRelBatch(commonRelList);
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
                .set(DataCommon::getScopeUserId,scopeUserId)
                .set(DataCommon::getScopeOrgId,scopeOrgId)
                .in(DataCommon::getId,dataIds)
                .update();
    }

    /**
     * 功能描述:
     * 〈添加团队成员〉
     * @param baseDTO baseDTO
     * @author 蝉鸣
     */
    @Override
    public Boolean addTeamMember(TeamBaseDTO baseDTO) {
        dataCommonServiceManual.addTeamMember(baseDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteTeamMember(TeamBaseDelDTO delDTO) {
        dataCommonServiceManual.deleteTeamMember(delDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈多索引联合过滤查询〉
     * @param pageDTO pageDTO
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectUniPage(EsDocUGetDTO pageDTO) {
        return dataCommonServiceManual.selectUniPage(pageDTO);
    }
}