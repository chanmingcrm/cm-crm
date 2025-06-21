package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireQueryDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.mapper.AppFormColumnSetRequireMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.IAppFormColumnSetRequireService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.manual.AppFormColumnSetRequireServiceManual;
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
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段请求
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetRequireServiceImpl extends ServiceImpl<AppFormColumnSetRequireMapper, AppFormColumnSetRequire> implements IAppFormColumnSetRequireService {

    @Autowired
    private AppFormColumnSetRequireServiceManual appFormColumnSetRequireServiceManual;


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link AppFormColumnSetRequireServiceManual}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetRequireServiceManual getServiceManual() {
        return appFormColumnSetRequireServiceManual;
    }


    /**
     * 功能描述:
     * 〈获取分页〉
     * @return 正常返回:{@link MPage<AppFormColumnSetRequire>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AppFormColumnSetRequire> getFormColumnSetRequirePage(AppFormColumnSetRequireQueryDTO pageDTO) {
        MPage<AppFormColumnSetRequire> formColumnSetRequireMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnSetRequire.class);
        LambdaQueryWrapper<AppFormColumnSetRequire> requireLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requireLambdaQueryWrapper.eq(ObjectUtil.isNotEmpty(pageDTO.getModuleId()),AppFormColumnSetRequire::getModuleId, pageDTO.getModuleId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getFormId()),AppFormColumnSetRequire::getFormId, pageDTO.getFormId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getColumnId()),AppFormColumnSetRequire::getColumnId, pageDTO.getColumnId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getActionId()),AppFormColumnSetRequire::getActionId, pageDTO.getActionId())
                .eq(ObjectUtil.isNotEmpty(pageDTO.getEventId()),AppFormColumnSetRequire::getEventId, pageDTO.getEventId());
        return this.page(formColumnSetRequireMPage, requireLambdaQueryWrapper);
    }


    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnSetRequireVO> getFormColumnSetRequireInfo(AppFormColumnSetRequireQueryDTO queryDTO) {
        if(ObjectUtil.isEmpty(queryDTO.getModuleId())
                && ObjectUtil.isEmpty(queryDTO.getFormId())
                && ObjectUtil.isEmpty(queryDTO.getColumnId())
                && ObjectUtil.isEmpty(queryDTO.getActionId())
                && ObjectUtil.isEmpty(queryDTO.getEventId())
        ){
            return CollUtil.newArrayList();
        }
        List<AppFormColumnSetRequire> requireList = this.lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(queryDTO.getModuleId()),AppFormColumnSetRequire::getModuleId, queryDTO.getModuleId())
                .eq(ObjectUtil.isNotEmpty(queryDTO.getFormId()),AppFormColumnSetRequire::getFormId, queryDTO.getFormId())
                .eq(ObjectUtil.isNotEmpty(queryDTO.getColumnId()),AppFormColumnSetRequire::getColumnId, queryDTO.getColumnId())
                .eq(ObjectUtil.isNotEmpty(queryDTO.getActionId()),AppFormColumnSetRequire::getActionId, queryDTO.getActionId())
                .eq(ObjectUtil.isNotEmpty(queryDTO.getEventId()),AppFormColumnSetRequire::getEventId, queryDTO.getEventId())
                .list();
        if(CollUtil.isEmpty(requireList)){
            return CollUtil.newArrayList();
        }
        Map<String, List<AppFormColumnSetRequireVO>> requireMap = requireList.stream()
                .map(item -> BeanUtil.copyProperties(item, AppFormColumnSetRequireVO.class))
                .collect(Collectors.groupingBy(AppFormColumnSetRequireVO::getRequireHash));
        return appFormColumnSetRequireServiceManual.getFormColumnSetRequireInfo(requireMap);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppFormColumnSetRequireVO addFormColumnSetRequire(AppFormColumnSetRequireDTO formColumnSetRequireDTO) {
        if(ObjectUtil.isEmpty(formColumnSetRequireDTO)) {
            return new AppFormColumnSetRequireVO();
        }
        //增加请求
        AppFormColumnSetRequire columnSetRequire = BeanUtil.copyProperties(formColumnSetRequireDTO, AppFormColumnSetRequire.class);
        this.save(columnSetRequire);
        return BeanUtil.copyProperties(columnSetRequire, AppFormColumnSetRequireVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppFormColumnSetRequireVO editFormColumnSetRequire(AppFormColumnSetRequireDTO formColumnSetRequireDTO) {
        AppFormColumnSetRequire columnSetRequire = BeanUtil.copyProperties(formColumnSetRequireDTO
                , AppFormColumnSetRequire.class);
        this.updateById(columnSetRequire);
        return BeanUtil.copyProperties(columnSetRequire, AppFormColumnSetRequireVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formColumnSetRequireId formColumnSetRequireId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnSetRequire(Long formColumnSetRequireId) {

        return this.removeById(formColumnSetRequireId);
    }

    /**
     * 功能描述:
     * 〈复制字段请求〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyColumn copyColumn
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyFormColumnSetRequire(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumn> copyColumn) {
        if(CollUtil.isEmpty(copyColumn)){
            return;
        }
        List<AppFormColumnSetRequire> sourceSetRequires = this.lambdaQuery().eq(AppFormColumnSetRequire::getModuleId, sourceModuleId).list();
        if(CollUtil.isEmpty(sourceSetRequires)){
            return;
        }
        List<AppFormColumnSetRequire> targetRequires = sourceSetRequires.stream()
                .map(sourceRequire -> {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumnSetRequire targetRequire = new AppFormColumnSetRequire();
            BeanUtil.copyProperties(sourceRequire, targetRequire, ObjFieldUtil.ignoreDefault());
            targetRequire.setId(id);
            targetRequire.setModuleId(targetModuleId);
            AppFormColumn appFormColumn = copyColumn.get(sourceRequire.getColumnId());
            if(ObjectUtil.isNotEmpty(appFormColumn)){
                targetRequire.setFormId(appFormColumn.getFormId());
                targetRequire.setColumnId(appFormColumn.getId());
                targetRequire.setColumnMac(appFormColumn.getColumnMac());
                targetRequire.setColumnName(appFormColumn.getColumnName());
                targetRequire.setEventId(appFormColumn.getId());
            }
            return targetRequire;
        }).toList();
        //批量保存
        this.saveBatch(targetRequires);
    }
}