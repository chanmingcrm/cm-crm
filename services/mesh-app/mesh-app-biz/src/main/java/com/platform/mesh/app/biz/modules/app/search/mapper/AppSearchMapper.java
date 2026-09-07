package com.platform.mesh.app.biz.modules.app.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchPageDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.po.AppSearch;
import com.platform.mesh.app.biz.modules.app.search.domain.vo.AppSearchVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 查询
 * @author 蝉鸣
 */
public interface AppSearchMapper extends BaseMapper<AppSearch> {

    MPage<AppSearchVO> selectMPage(MPage<AppSearch> baseMPage, @Param("pageDTO") AppSearchPageDTO pageDTO);

    AppSearch getById(@Param("id") Long id);
}
