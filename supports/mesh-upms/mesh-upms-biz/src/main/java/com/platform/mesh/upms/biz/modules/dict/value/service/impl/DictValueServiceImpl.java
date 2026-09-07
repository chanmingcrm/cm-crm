package com.platform.mesh.upms.biz.modules.dict.value.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.biz.modules.dict.base.exception.DictBaseExceptionEnum;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValueDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValuePageDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.po.DictValue;
import com.platform.mesh.upms.biz.modules.dict.value.domain.vo.DictValueVO;
import com.platform.mesh.upms.biz.modules.dict.value.exception.DictValueExceptionEnum;
import com.platform.mesh.upms.biz.modules.dict.value.mapper.DictValueMapper;
import com.platform.mesh.upms.biz.modules.dict.value.service.IDictValueService;
import com.platform.mesh.upms.biz.modules.dict.value.service.manual.DictValueServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 字典值
 * @author 蝉鸣
 */
@Service
public class DictValueServiceImpl extends ServiceImpl<DictValueMapper, DictValue> implements IDictValueService  {

    @Autowired
    private DictValueServiceManual dictValueServiceManual;

    /**
     * 功能描述:
     * 〈分页查询字典值〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DictValueVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<DictValueVO> selectPage(DictValuePageDTO pageDTO) {
        List<Long> dictIds = dictValueServiceManual.getDictIds(pageDTO);
        if (CollUtil.isEmpty(dictIds)) {
            return new PageVO<>();
        }
        MPage<DictValue> valueMPage = MPageUtil.pageEntityToMPage(pageDTO, DictValue.class);
        MPage<DictValue> page = this.lambdaQuery().in(DictValue::getDictId, dictIds).page(valueMPage);
        return MPageUtil.convertToVO(page, DictValueVO.class);

    }

    /**
     * 功能描述: 
     * 〈获取当前字典值信息〉
     * @param valueId valueId  
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    @Override
    public DictValueVO getValueInfoById(Long valueId) {
        DictValue dictValue = this.getById(valueId);
        return dictValueServiceManual.getValueInfoById(dictValue);
    }

    /**
     * 功能描述:
     * 〈新增字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    @Override
    public DictValueVO addValue(DictValueDTO valueDTO) {
        DictValue dictValue = BeanUtil.copyProperties(valueDTO, DictValue.class);
        this.save(dictValue);
        return BeanUtil.copyProperties(dictValue, DictValueVO.class);
    }

    /**
     * 功能描述:
     * 〈修改字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link DictValueVO}
     * @author 蝉鸣
     */
    @Override
    public DictValueVO editValue(DictValueDTO valueDTO) {
        if(ObjectUtil.isEmpty(valueDTO.getId())){
            throw DictValueExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        DictValue dictValue = BeanUtil.copyProperties(valueDTO, DictValue.class);
        this.updateById(dictValue);
        return BeanUtil.copyProperties(dictValue, DictValueVO.class);
    }

    /**
     * 功能描述:
     * 〈删除字典值〉
     * @param valueId valueId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteValue(Long valueId) {
        return this.removeById(valueId);
    }

    /**
     * 功能描述:
     * 〈查询字典值〉
     * @param dictMac dictMac
     * @param dictValue dictValue
     * @return 正常返回:{@link DictValue}
     * @author 蝉鸣
     */
    @Override
    public DictValue getFistSysDictByMac(String dictMac, Integer dictValue) {
        return this.getBaseMapper().getFistSysDictByMac(dictMac,dictValue);
    }
}
