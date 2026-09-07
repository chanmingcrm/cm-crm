package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireQueryDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.manual.AppFormColumnSetRequireServiceManual;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;
import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段请求信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSetRequireService extends IService<AppFormColumnSetRequire> {


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link AppFormColumnSetRequireServiceManual}
     * @author 蝉鸣
     */
    AppFormColumnSetRequireServiceManual getServiceManual();


    /**
     * 功能描述:
     * 〈获取分页〉
     * @return 正常返回:{@link MPage<AppFormColumnSetRequire>}
     * @author 蝉鸣
     */
    MPage<AppFormColumnSetRequire> getFormColumnSetRequirePage(AppFormColumnSetRequireQueryDTO pageDTO);


    /**
     * 功能描述:
     * 〈获取当前单字段请求信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    List<AppFormColumnSetRequireVO> getFormColumnSetRequireInfo(AppFormColumnSetRequireQueryDTO queryDTO);

    /**
     * 功能描述:
     * 〈新增单字段请求〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    AppFormColumnSetRequireVO addFormColumnSetRequire(AppFormColumnSetRequireDTO formColumnSetRequireDTO);

    /**
     * 功能描述:
     * 〈修改单字段请求〉
     * @param formColumnSetRequireDTO formColumnSetRequireDTO
     * @return 正常返回:{@link AppFormColumnSetRequireVO}
     * @author 蝉鸣
     */
    AppFormColumnSetRequireVO editFormColumnSetRequire(AppFormColumnSetRequireDTO formColumnSetRequireDTO);

    /**
     * 功能描述:
     * 〈删除单字段请求〉
     * @param formColumnSetRequireId formColumnSetRequireId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnSetRequire(Long formColumnSetRequireId);

    /**
     * 功能描述:
     * 〈复制字段请求〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @author 蝉鸣
     */
    Map<Long, AppFormColumnSetRequire> copyFormColumnSetRequire(Long sourceModuleId, Long targetModuleId);

    /**
     * 功能描述:
     * 〈查询用于Ai创建的添加接口〉
     * @return 正常返回:{@link List<AppFormColumnSetEventVO>}
     * @author 蝉鸣
     */
    List<AppFormColumnSetRequireVO> selectAddRequire();
}
