package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;

import java.util.List;

/**
 * @description 单字段请求
 * @author 蝉鸣
 */
public interface AppFormColumnSetRequireMapper extends BaseMapper<AppFormColumnSetRequire> {

    List<AppFormColumnSetRequireVO> selectAddRequire();
}