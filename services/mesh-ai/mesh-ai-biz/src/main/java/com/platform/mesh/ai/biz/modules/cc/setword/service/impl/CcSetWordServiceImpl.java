package com.platform.mesh.ai.biz.modules.cc.setword.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.dto.CcSetWordDTO;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.po.CcSetWord;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.vo.CcSetWordVO;
import com.platform.mesh.ai.biz.modules.cc.setword.exception.CcSetWordExceptionEnum;
import com.platform.mesh.ai.biz.modules.cc.setword.mapper.CcSetWordMapper;
import com.platform.mesh.ai.biz.modules.cc.setword.service.ICcSetWordService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客服提示语
 * @author 蝉鸣
 */
@Service
public class CcSetWordServiceImpl extends ServiceImpl<CcSetWordMapper, CcSetWord> implements ICcSetWordService {

    
    /**
     * 功能描述: 
     * 〈获取当前客服提示语信息〉
     * @param wordId wordId
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWordVO getCcSetWordById(Long wordId) {
        CcSetWord ccSetWord = this.getById(wordId);
        return BeanUtil.copyProperties(ccSetWord, CcSetWordVO.class);
    }

    /**
     * 功能描述:
     * 〈新增客服提示语〉
     * @param wordDTO wordDTO
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWordVO addCcSetWord(CcSetWordDTO wordDTO) {
        CcSetWord ccSetWord = BeanUtil.copyProperties(wordDTO, CcSetWord.class);
        this.save(ccSetWord);
        return BeanUtil.copyProperties(ccSetWord, CcSetWordVO.class);
    }

    /**
     * 功能描述:
     * 〈修改客服提示语〉
     * @param wordDTO wordDTO
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    @Override
    public CcSetWordVO editCcSetWord(CcSetWordDTO wordDTO) {
        if(ObjectUtil.isEmpty(wordDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(CcSetWordDTO::getId);
            throw CcSetWordExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        CcSetWord ccSetWord = BeanUtil.copyProperties(wordDTO, CcSetWord.class);
        this.updateById(ccSetWord);
        return BeanUtil.copyProperties(ccSetWord, CcSetWordVO.class);
    }

    /**
     * 功能描述:
     * 〈删除客服提示语〉
     * @param wordId wordId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteCcSetWord(Long wordId) {
        return this.removeById(wordId);
    }
}
