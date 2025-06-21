package com.platform.mesh.upms.biz.modules.label.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.label.base.domain.dto.LabelBaseDTO;
import com.platform.mesh.upms.biz.modules.label.base.domain.po.LabelBase;
import com.platform.mesh.upms.biz.modules.label.base.domain.vo.LabelBaseVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 标签基础信息
 * @author 蝉鸣
 */
public interface ILabelBaseService extends IService<LabelBase> {


    /**
     * 功能描述:
     * 〈获取当前标签基础信息〉
     * @param baseId baseId
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    LabelBaseVO getBaseInfoById(Long baseId);

    /**
     * 功能描述:
     * 〈新增标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    LabelBaseVO addBase(LabelBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈修改标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link LabelBaseVO}
     * @author 蝉鸣
     */
    LabelBaseVO editBase(LabelBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈删除标签基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteBase(Long baseId);
}