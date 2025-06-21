package com.platform.mesh.app.biz.modules.app.formcolumnsorting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.po.AppFormColumnSorting;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.vo.AppFormColumnSortingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 单字段排序
 * @author 蝉鸣
 */
public interface AppFormColumnSortingMapper extends BaseMapper<AppFormColumnSorting> {

    /**
     * 功能描述:
     * 〈获取当前单字段排序信息〉
     * @param formId formId
     * @return 正常返回:{@link List<AppFormColumnSortingVO>}
     * @author 蝉鸣
     */
    List<AppFormColumnSortingVO> getFormColumnSortingInfoByFormId(@Param("formId") Long formId);
}