package com.platform.mesh.app.biz.modules.app.formcolumnsorting.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto.AppFormColumnSortingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.po.AppFormColumnSorting;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.vo.AppFormColumnSortingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.mapper.AppFormColumnSortingMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.service.IAppFormColumnSortingService;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段排序
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSortingServiceImpl extends ServiceImpl<AppFormColumnSortingMapper, AppFormColumnSorting> implements IAppFormColumnSortingService {

    /**
     * 功能描述:
     * 〈获取当前单字段排序信息〉
     * @param formId formId
     * @return 正常返回:{@link List<AppFormColumnSortingVO>}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnSortingVO> getFormColumnSortingInfoByFormId(Long formId) {
        return this.getBaseMapper().getFormColumnSortingInfoByFormId(formId);
    }

    /**
     * 功能描述:
     * 〈新增单字段排序〉
     * @param formColumnSortingDTOS formColumnSortingDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addFormColumnSorting(List<AppFormColumnSortingDTO> formColumnSortingDTOS) {
        if(CollUtil.isEmpty(formColumnSortingDTOS)) {
            return Boolean.FALSE;
        }
        AppFormColumnSortingDTO first = CollUtil.getFirst(formColumnSortingDTOS);
        //删除旧数据
        this.lambdaUpdate()
                .eq(AppFormColumnSorting::getModuleId,first.getModuleId())
                .eq(AppFormColumnSorting::getFormId,first.getFormId())
                .eq(AppFormColumnSorting::getParentColumnId,first.getParentColumnId())
                .remove();
        //增加新数据
        List<AppFormColumnSorting> formColumnSorting = BeanUtil.copyToList(formColumnSortingDTOS, AppFormColumnSorting.class);
        this.saveBatch(formColumnSorting);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除单字段排序〉
     * @param formId formId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnSorting(Long formId) {
        //删除旧数据
        this.lambdaUpdate()
                .eq(AppFormColumnSorting::getFormId,formId)
                .remove();
        return Boolean.TRUE;
    }
}