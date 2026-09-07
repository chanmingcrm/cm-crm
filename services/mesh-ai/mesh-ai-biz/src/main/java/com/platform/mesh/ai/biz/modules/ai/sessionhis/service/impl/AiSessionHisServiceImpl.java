package com.platform.mesh.ai.biz.modules.ai.sessionhis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.dto.AiSessionHisDTO;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.po.AiSessionHis;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.mapper.AiSessionHisMapper;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.service.IAiSessionHisService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI会话历史
 * @author 蝉鸣
 */
@Service
public class AiSessionHisServiceImpl extends ServiceImpl<AiSessionHisMapper, AiSessionHis> implements IAiSessionHisService {


    /**
     * 功能描述:
     * 〈获取当前AI会话历史分页信息〉
     * @param pageDTO sessionId
     * @return 正常返回:{@link MPage<AiSessionHis>}
     * @author 蝉鸣
     */
    @Override
    public MPage<AiSessionHis> selectPage(AiSessionHisDTO pageDTO) {
        MPage<AiSessionHis> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiSessionHis.class);
        return this.lambdaQuery()
                .eq(AiSessionHis::getSessionId, pageDTO.getSessionId())
                .orderByDesc(AiSessionHis::getCreateTime)
                .page(mPage);
    }

}
