package com.platform.mesh.upms.biz.modules.dict.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBaseDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBasePageDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import com.platform.mesh.upms.biz.modules.dict.base.domain.vo.DictBaseVO;
import com.platform.mesh.utils.result.Result;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 字典基础信息
 * @author 蝉鸣
 */
public interface IDictBaseService extends IService<DictBase> {


    /**
     * 功能描述:
     * 〈获取字典基础列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <MPage<DictBase>>}
     * @author 蝉鸣
     */
    MPage<DictBase> selectPage(DictBasePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前字典基础信息〉
     * @param baseId baseId
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    DictBaseVO getBaseInfoById(Long baseId);

    /**
     * 功能描述:
     * 〈新增字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    DictBaseVO addBase(DictBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈修改字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    DictBaseVO editBase(DictBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈删除字典基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteBase(Long baseId);

    /**
     * 功能描述:
     * 〈查询字典子项〉
     * @param dictId dictId
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<DictBase> getChildDict(Long dictId);

    /**
     * 功能描述:
     * 〈查询字典子项〉
     * @param dictIds dictIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<DictBase> selectDictByIds(List<Long> dictIds);

}