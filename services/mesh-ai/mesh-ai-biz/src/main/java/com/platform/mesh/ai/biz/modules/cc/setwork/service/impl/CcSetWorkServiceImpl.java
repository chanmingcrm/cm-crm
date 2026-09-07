package com.platform.mesh.ai.biz.modules.cc.setwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.dto.CcSetWorkDTO;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.vo.CcSetWorkVO;
import com.platform.mesh.ai.biz.modules.cc.setwork.exception.CcSetWorkExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.setwork.mapper.CcSetWorkMapper;
import com.platform.mesh.ai.biz.modules.cc.setwork.service.ICcSetWorkService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 排班
 * @author 蝉鸣
 */
@Service
public class CcSetWorkServiceImpl extends ServiceImpl<CcSetWorkMapper, CcSetWork> implements ICcSetWorkService {

    
    /**
     * 功能描述: 
     * 〈获取当前排班信息〉
     * @param workId workId
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWorkVO getCcSetWorkById(Long workId) {
        CcSetWork ccSetWork = this.getById(workId);
        return BeanUtil.copyProperties(ccSetWork, CcSetWorkVO.class);
    }

    /**
     * 功能描述:
     * 〈新增排班〉
     * @param workDTO workDTO
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWorkVO addCcSetWork(CcSetWorkDTO workDTO) {
        CcSetWork ccSetWork = BeanUtil.copyProperties(workDTO, CcSetWork.class);
        this.save(ccSetWork);
        return BeanUtil.copyProperties(ccSetWork, CcSetWorkVO.class);
    }

    /**
     * 功能描述:
     * 〈修改排班〉
     * @param workDTO workDTO
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWorkVO editCcSetWork(CcSetWorkDTO workDTO) {
        if(ObjectUtil.isEmpty(workDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcSetWorkDTO::getId);
            throw CcSetWorkExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CcSetWork ccSetWork = BeanUtil.copyProperties(workDTO, CcSetWork.class);
        this.updateById(ccSetWork);
        return BeanUtil.copyProperties(ccSetWork, CcSetWorkVO.class);
    }

    /**
     * 功能描述:
     * 〈删除排班〉
     * @param workId workId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcSetWork(Long workId) {
        return this.removeById(workId);
    }

    /**
     * 功能描述:
     * 〈获取当前时间的排班〉
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    @Override
    public List<CcSetWork> getCurrentSetWork(LocalTime localTime) {
        return this.getBaseMapper().getCurrentSetWork(localTime);
    }
}
