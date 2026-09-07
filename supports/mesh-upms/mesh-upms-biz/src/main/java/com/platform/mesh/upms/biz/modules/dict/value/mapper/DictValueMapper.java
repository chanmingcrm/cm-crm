package com.platform.mesh.upms.biz.modules.dict.value.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.dict.value.domain.po.DictValue;
import org.apache.ibatis.annotations.Param;

/**
 * @description 字典值
 * @author 蝉鸣
 */
public interface DictValueMapper extends BaseMapper<DictValue> {

    @InterceptorIgnore(tenantLine = "true")
    DictValue getFistSysDictByMac(@Param("dictMac") String dictMac,@Param("dictValue") Integer dictValue);
}