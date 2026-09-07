package com.platform.mesh.app.biz.modules.app.search.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.biz.modules.app.base.domain.vo.AppBaseVO;
import com.platform.mesh.app.biz.modules.app.search.domain.po.AppSearch;
import com.platform.mesh.app.biz.modules.app.search.domain.vo.AppSearchVO;
import com.platform.mesh.app.biz.modules.app.search.enums.AppSearchEnum;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 查询
 * @author 蝉鸣
 */
@Service
public class AppSearchServiceManual {

    private static final Logger log = LoggerFactory.getLogger(AppSearchServiceManual.class);

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appSearch appSearch
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    public AppSearchVO getSearchInfoById(AppSearch appSearch) {
        AppSearchVO appSearchVO = new AppSearchVO();
        if(ObjectUtil.isEmpty(appSearchVO)){
            return appSearchVO;
        }
        //转换VO
        BeanUtil.copyProperties(appSearch, appSearchVO);
        if(AppSearchEnum.SELF.getValue().equals(appSearch.getSearchFlag())){
            List<CondDTO> self = getSelf();
            appSearchVO.setCondDTO(self);
        } else if (AppSearchEnum.SUB.getValue().equals(appSearch.getSearchFlag())) {
            List<CondDTO> sub = getSub();
            appSearchVO.setCondDTO(sub);
        }else {
            List<CondDTO> custom = getCustom(appSearch.getSearchData());
            appSearchVO.setCondDTO(custom);
        }
        return appSearchVO;
    }

    /**
     * 功能描述:
     * 〈查询我的条件〉
     * @return 正常返回:{@link List<CondDTO>}
     * @author 蝉鸣
     */
    public List<CondDTO> getSelf(){
        CondDTO condDTO = new CondDTO();
        condDTO.setColumnMac(DataScopeConst.DEFAULT_SCOPE_USER_ID);
        condDTO.setCondRef(LogicRefEnum.EQ);
        condDTO.setSearchValues(CollUtil.newArrayList(UserCacheUtil.getUserId().toString()));
        return CollUtil.newArrayList(condDTO);
    }

    /**
     * 功能描述:
     * 〈查询下属条件〉
     * @return 正常返回:{@link List<CondDTO>}
     * @author 蝉鸣
     */
    public List<CondDTO> getSub(){
        CondDTO condDTO = new CondDTO();
        condDTO.setColumnMac(DataScopeConst.DEFAULT_SCOPE_USER_ID);
        condDTO.setCondRef(LogicRefEnum.NE);
        condDTO.setSearchValues(CollUtil.newArrayList(UserCacheUtil.getUserId().toString()));
        return CollUtil.newArrayList(condDTO);
    }

    /**
     * 功能描述:
     * 〈查询自定义条件〉
     * @return 正常返回:{@link List<CondDTO>}
     * @author 蝉鸣
     */
    public List<CondDTO> getCustom(String searchData){
        if(ObjectUtil.isEmpty(searchData)){
            return CollUtil.newArrayList();
        }
        return JSONUtil.toList(searchData, CondDTO.class);
    }

}