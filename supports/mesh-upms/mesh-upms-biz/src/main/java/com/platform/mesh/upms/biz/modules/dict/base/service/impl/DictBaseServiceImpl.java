package com.platform.mesh.upms.biz.modules.dict.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBaseDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.dto.DictBasePageDTO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import com.platform.mesh.upms.biz.modules.dict.base.domain.vo.DictBaseVO;
import com.platform.mesh.upms.biz.modules.dict.base.exception.DictBaseExceptionEnum;
import com.platform.mesh.upms.biz.modules.dict.base.mapper.DictBaseMapper;
import com.platform.mesh.upms.biz.modules.dict.base.service.IDictBaseService;
import com.platform.mesh.upms.biz.modules.dict.base.service.manual.DictBaseServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 字典基础
 * @author 蝉鸣
 */
@Service
public class DictBaseServiceImpl extends ServiceImpl<DictBaseMapper, DictBase> implements IDictBaseService  {

    @Autowired
    private DictBaseServiceManual dictBaseServiceManual;

    /**
     * 功能描述:
     * 〈获取字典基础列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <MPage<DictBaseVO>>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DictBase> selectPage(DictBasePageDTO pageDTO) {
        List<DictBase> childDict = this.getChildDict(pageDTO.getDictId());
        List<Long> dictIds = dictBaseServiceManual.getChildIds(childDict);
        if(CollUtil.isNotEmpty(pageDTO.getDictIds())){
            dictIds.addAll(pageDTO.getDictIds());
        }
        if(CollUtil.isEmpty(dictIds)){
            return new MPage<>();
        }
        MPage<DictBase> valueMPage = MPageUtil.pageEntityToMPage(pageDTO, DictBase.class);
        return this.lambdaQuery().in(DictBase::getId, dictIds)
                .orderBy(Boolean.TRUE,Boolean.TRUE,DictBase::getId)
                .page(valueMPage);
    }

    /**
     * 功能描述: 
     * 〈获取当前字典基础信息〉
     * @param baseId baseId  
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    @Override
    public DictBaseVO getBaseInfoById(Long baseId) {
        DictBase dictBase = this.getById(baseId);
        return dictBaseServiceManual.getBaseInfoById(dictBase);
    }

    /**
     * 功能描述:
     * 〈新增字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    @Override
    public DictBaseVO addBase(DictBaseDTO baseDTO) {
        DictBase dictBase = BeanUtil.copyProperties(baseDTO, DictBase.class);
        this.save(dictBase);
        return BeanUtil.copyProperties(dictBase, DictBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈修改字典基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link DictBaseVO}
     * @author 蝉鸣
     */
    @Override
    public DictBaseVO editBase(DictBaseDTO baseDTO) {
        if(ObjectUtil.isEmpty(baseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(DictBaseDTO::getId);
            throw DictBaseExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        DictBase dictBase = BeanUtil.copyProperties(baseDTO, DictBase.class);
        this.updateById(dictBase);
        return BeanUtil.copyProperties(dictBase, DictBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈删除字典基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteBase(Long baseId) {
        return this.removeById(baseId);
    }

    /**
     * 功能描述:
     * 〈查询字典子项〉
     * @param dictId dictId
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<DictBase> getChildDict(Long dictId) {
        List<DictBase> dictList = CollUtil.newArrayList();
        if(ObjectUtil.isEmpty(dictId)){
            return dictList;
        }
        String childrenSql = SqlUtil.getCommonChildrenSql(DictBase.class, dictId);
        //查询子项
        List<DictBase> childList = this.lambdaQuery().apply(childrenSql).list();
        if (CollUtil.isNotEmpty(childList)) {
            dictList.addAll(childList);
        }
        return dictList;


//        String parentsSql = SqlUtil.getCommonParentSql(DictBase.class, pageDTO.getDictId());
//        //查询父项demo
//        List<DictBase> parentList = dictBaseService.lambdaQuery().apply(parentsSql).list();
    }

    /**
     * 功能描述:
     * 〈查询字典子项〉
     * @param dictIds dictIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<DictBase> selectDictByIds(List<Long> dictIds) {
        List<DictBase> dictList = CollUtil.newArrayList();
        if(CollUtil.isEmpty(dictIds)){
            return dictList;
        }
        return this.lambdaQuery().in(DictBase::getId, dictIds)
                .orderBy(Boolean.TRUE,Boolean.TRUE,DictBase::getId)
                .list();
    }
}
