package com.platform.mesh.gen.biz.modules.code.buildconfdata.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.po.CodeBuildConf;
import com.platform.mesh.gen.biz.modules.code.buildconf.service.ICodeBuildConfService;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.dto.CodeBuildConfDataPageDTO;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.po.CodeBuildConfData;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.mapper.CodeBuildConfDataMapper;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.service.ICodeBuildConfDataService;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.service.manual.CodeBuildConfDataServiceManual;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 构建配置项信息
 * @author 蝉鸣
 */
@Service
public class CodeBuildConfDataServiceImpl extends ServiceImpl<CodeBuildConfDataMapper, CodeBuildConfData> implements ICodeBuildConfDataService {

    @Autowired
    private ICodeBuildConfService codeBuildConfService;

    @Autowired
    private CodeBuildConfDataServiceManual codeBuildConfDataServiceManual;



    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @return 正常返回:{@link MPage <CodeBuildConfData>}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MPage<CodeBuildConfData> buildConfDataPage(CodeBuildConfDataPageDTO pageEntity) {
        boolean exists = lambdaQuery().eq(CodeBuildConfData::getBuildId, pageEntity.getBuildId()).exists();
        if(!exists){
            //查询所有默认配置信息
            List<CodeBuildConf> list = codeBuildConfService.list();
            if(CollUtil.isNotEmpty(list)){
                List<CodeBuildConfData> confDataList = list.stream().map(item -> {
                    CodeBuildConfData confData = BeanUtil.copyProperties(item, CodeBuildConfData.class, ObjFieldUtil.getFieldName(CodeBuildConfData::getId));
                    confData.setBuildId(pageEntity.getBuildId());
                    confData.setConfValue(StrUtil.EMPTY);
                    return confData;
                }).collect(Collectors.toList());
                //初始化信息
                saveBatch(confDataList);
            }
        }
        //返回分页数据
        MPage<CodeBuildConfData> confDataMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeBuildConfData.class);
        return this.lambdaQuery().eq(CodeBuildConfData::getBuildId,pageEntity.getBuildId()).page(confDataMPage);
    }

    /**
     * 功能描述:
     * 〈根据构建ID获取配置参数〉
     * @param buildId buildId
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    public Map<String, Object> getConfDataMapByBuildId(Long buildId) {
        List<CodeBuildConfData> codeBuildConfData = lambdaQuery().eq(CodeBuildConfData::getBuildId, buildId).list();
        return codeBuildConfDataServiceManual.getConfDataMap(codeBuildConfData);
    }
}
