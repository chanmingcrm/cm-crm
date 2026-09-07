package com.platform.mesh.upms.biz.modules.dict.value.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValueDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValuePageDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.po.DictValue;
import com.platform.mesh.upms.biz.modules.dict.value.domain.vo.DictValueVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 字典值信息
 * @author 蝉鸣
 */
public interface IDictValueService extends IService<DictValue> {


    /**
     * 功能描述:
     * 〈分页查询字典值〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DictValueVO>}
     * @author 蝉鸣
     */
    PageVO<DictValueVO> selectPage(DictValuePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前字典值信息〉
     * @param valueId valueId
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    DictValueVO getValueInfoById(Long valueId);

    /**
     * 功能描述:
     * 〈新增字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    DictValueVO addValue(DictValueDTO valueDTO);

    /**
     * 功能描述:
     * 〈修改字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    DictValueVO editValue(DictValueDTO valueDTO);

    /**
     * 功能描述:
     * 〈删除字典值〉
     * @param valueId valueId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteValue(Long valueId);

    /**
     * 功能描述:
     * 〈查询字典值〉
     * @param dictMac dictMac
     * @param dictValue dictValue
     * @return 正常返回:{@link DictValue}
     * @author 蝉鸣
     */
    DictValue getFistSysDictByMac(String dictMac, Integer dictValue);
}