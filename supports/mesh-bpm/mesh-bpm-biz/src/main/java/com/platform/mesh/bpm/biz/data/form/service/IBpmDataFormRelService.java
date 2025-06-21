package com.platform.mesh.bpm.biz.data.form.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelAddDTO;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelEditDTO;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelPageDTO;
import com.platform.mesh.bpm.biz.data.form.domain.po.BpmDataFormRel;
import com.platform.mesh.bpm.biz.data.form.domain.vo.BpmDataFormRelVO;
import com.platform.mesh.bpm.biz.data.form.service.manual.BpmDataFormRelServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 数据流程表单
 * @author 蝉鸣
 */
public interface IBpmDataFormRelService extends IService<BpmDataFormRel> {


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmDataFormRelServiceManual}
     * @author 蝉鸣
     */
    BpmDataFormRelServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取分页信息〉
     * @return 正常返回:{@link PageVO<BpmDataFormRelVO>}
     * @author 蝉鸣
     */
    PageVO<BpmDataFormRelVO> selectPage(BpmDataFormRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈根据流程Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    BpmDataFormRel getDataFormRelByProcessId(Long processId);

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link List<BpmDataFormRel>}
     * @author 蝉鸣
     */
    List<BpmDataFormRel> getDataFormRelByFormId(Long formId);

    /**
     * 功能描述:
     * 〈根据应用Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link List<BpmDataFormRel>}
     * @author 蝉鸣
     */
    List<BpmDataFormRel> getDataFormRelByAppId(Long appId);

    /**
     * 功能描述:
     * 〈添加流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    BpmDataFormRel addDataFormRel(BpmDataFormRelAddDTO addDTO);

    /**
     * 功能描述:
     * 〈修改流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    BpmDataFormRel editDataFormRel(BpmDataFormRelEditDTO editDTO);

    /**
     * 功能描述:
     * 〈删除流程与业务表单绑定关系〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDataFormRel(Long relId);

}

