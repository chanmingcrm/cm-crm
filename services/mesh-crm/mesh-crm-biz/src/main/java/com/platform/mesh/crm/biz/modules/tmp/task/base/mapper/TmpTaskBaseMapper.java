package com.platform.mesh.crm.biz.modules.tmp.task.base.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.po.TmpTaskBase;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.vo.TmpTaskBaseVO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelPageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 任务
 * @author 蝉鸣
 */
public interface TmpTaskBaseMapper extends BaseMapper<TmpTaskBase> {
    MPage<TmpTaskBaseVO> taskBaseAndRelPage(MPage<TmpTaskBase> mPage, @Param("pageDTO") TmpTaskBaseRelPageDTO pageDTO);
}