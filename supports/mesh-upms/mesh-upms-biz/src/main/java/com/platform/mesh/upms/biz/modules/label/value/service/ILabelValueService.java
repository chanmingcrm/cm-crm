package com.platform.mesh.upms.biz.modules.label.value.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.label.value.domain.dto.LabelValueDTO;
import com.platform.mesh.upms.biz.modules.label.value.domain.po.LabelValue;
import com.platform.mesh.upms.biz.modules.label.value.domain.vo.LabelValueVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 标签值信息
 * @author 蝉鸣
 */
public interface ILabelValueService extends IService<LabelValue> {


    /**
     * 功能描述:
     * 〈获取当前标签值信息〉
     * @param valueId valueId
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    LabelValueVO getValueInfoById(Long valueId);

    /**
     * 功能描述:
     * 〈新增标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    LabelValueVO addValue(LabelValueDTO valueDTO);

    /**
     * 功能描述:
     * 〈修改标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link LabelValueVO}
     * @author 蝉鸣
     */
    LabelValueVO editValue(LabelValueDTO valueDTO);

    /**
     * 功能描述:
     * 〈删除标签值〉
     * @param valueId valueId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteValue(Long valueId);
}