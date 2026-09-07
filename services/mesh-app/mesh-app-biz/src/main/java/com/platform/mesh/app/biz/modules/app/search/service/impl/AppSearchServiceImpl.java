package com.platform.mesh.app.biz.modules.app.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.dto.AppSearchPageDTO;
import com.platform.mesh.app.biz.modules.app.search.domain.po.AppSearch;
import com.platform.mesh.app.biz.modules.app.search.domain.vo.AppSearchVO;
import com.platform.mesh.app.biz.modules.app.search.enums.AppSearchEnum;
import com.platform.mesh.app.biz.modules.app.search.exception.AppSearchExceptionEnum;
import com.platform.mesh.app.biz.modules.app.search.mapper.AppSearchMapper;
import com.platform.mesh.app.biz.modules.app.search.service.IAppSearchService;
import com.platform.mesh.app.biz.modules.app.search.service.manual.AppSearchServiceManual;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 查询
 * @author 蝉鸣
 */
@Service
public class AppSearchServiceImpl extends ServiceImpl<AppSearchMapper, AppSearch> implements IAppSearchService {

    @Autowired
    private AppSearchServiceManual appSearchServiceManual;

    /**
     * 功能描述:
     * 〈获取分页查询信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AppSearch>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AppSearchVO> selectPage(AppSearchPageDTO pageDTO) {
        MPage<AppSearch> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, AppSearch.class);
        pageDTO.setScopeUserId(UserCacheUtil.getUserId());
        return this.getBaseMapper().selectMPage(baseMPage,pageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param searchId searchId
     * @return 正常返回:{@link AppSearchVO}
     * @author 蝉鸣
     */
    @Override
    public AppSearchVO getSearchInfoById(Long searchId) {
        AppSearch appSearch = this.getBaseMapper().getById(searchId);
        return appSearchServiceManual.getSearchInfoById(appSearch);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppSearchVO}
     * @author 蝉鸣
     */
    @Override
    public AppSearch addSearch(AppSearchDTO baseDTO) {
        AppSearch appSearch = BeanUtil.copyProperties(baseDTO, AppSearch.class);
        appSearch.setSearchData(JSONUtil.toJsonStr(baseDTO.getCondDTO()));
        appSearch.setSearchFlag(AppSearchEnum.INIT.getValue());
        appSearch.setInitFlag(YesOrNoEnum.NO.getValue());
        appSearch.setAddFlag(YesOrNoEnum.NO.getValue());
        appSearch.setHideFlag(YesOrNoEnum.NO.getValue());
        appSearch.setDelFlag(YesOrNoEnum.YES.getValue());
        this.save(appSearch);
        return appSearch;
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppSearchVO}
     * @author 蝉鸣
     */
    @Override
    public AppSearch editSearch(AppSearchDTO baseDTO) {
        if(ObjectUtil.isEmpty(baseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppSearchDTO::getId);
            throw AppSearchExceptionEnum.ADD_NO_INVALID.getBaseException(fieldName);
        }
        AppSearch check = this.getBaseMapper().getById(baseDTO.getId());
        if(check.getModuleId().equals(NumberConst.NUM_0.longValue())){
            throw AppSearchExceptionEnum.SYS_NO_EDIT.getBaseException();
        }
        AppSearch appSearch = BeanUtil.copyProperties(baseDTO, AppSearch.class);
        appSearch.setSearchData(JSONUtil.toJsonStr(baseDTO.getCondDTO()));
        this.updateById(appSearch);
        return appSearch;
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteSearch(Long baseId) {
        AppSearch appSearch = this.getById(baseId);
        if(ObjectUtil.isEmpty(appSearch)){
            return false;
        }
        //删除当前查询
        appSearch.setDelFlag(YesOrNoEnum.NO.getValue());
        this.updateById(appSearch);
        return true;
    }


}
