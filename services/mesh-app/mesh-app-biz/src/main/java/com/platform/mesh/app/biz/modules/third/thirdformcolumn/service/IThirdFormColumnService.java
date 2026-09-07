package com.platform.mesh.app.biz.modules.third.thirdformcolumn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.dto.ThirdFormColumnDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.po.ThirdFormColumn;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.vo.ThirdFormColumnVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 第三方字段信息
 * @author 蝉鸣
 */
public interface IThirdFormColumnService extends IService<ThirdFormColumn> {


    /**
     * 功能描述:
     * 〈获取当前第三方字段信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link List<ThirdFormColumnVO>}
     * @author 蝉鸣
     */
    List<ThirdFormColumnVO> getThirdFormColumn(Integer sourceFlag);

    /**
     * 功能描述:
     * 〈新增第三方字段〉
     * @param thirdFormColumnDTOS thirdFormColumnDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addThirdFormColumn(List<ThirdFormColumnDTO> thirdFormColumnDTOS);

    /**
     * 功能描述:
     * 〈新增第三方字段〉
     * @param columnIds columnIds
     * @author 蝉鸣
     */
    void delThirdFormColumn(List<Long> columnIds);

}
