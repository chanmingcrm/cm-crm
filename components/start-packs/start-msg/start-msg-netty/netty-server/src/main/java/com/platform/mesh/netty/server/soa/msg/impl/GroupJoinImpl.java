package com.platform.mesh.netty.server.soa.msg.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.soa.msg.CcMsgExecuteService;
import com.platform.mesh.netty.server.soa.msg.CcMsgTypeService;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import com.platform.mesh.netty.server.soa.msg.factory.CcChannelFactory;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
@Service
public class GroupJoinImpl implements CcMsgTypeService {

    @Autowired
    private CcChannelFactory ccChannelFactory;

    private final CcMsgExecuteService ccMsgExecuteService;

    @Autowired
    public GroupJoinImpl(@Autowired(required = false) CcMsgExecuteService ccMsgExecuteService) {
        this.ccMsgExecuteService = ccMsgExecuteService;
    }

    /**
     * 功能描述:
     * 〈创建房间〉
     * @return 正常返回:{@link CcMsgTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public CcMsgTypeEnum ccMsgType() {
        return CcMsgTypeEnum.GROUP_JOIN;
    }

    /**
     * 功能描述:
     * 〈消息处理〉
     * @param ctx ctx
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void handle(ChannelHandlerContext ctx, CcMsgBO ccMsgBO) {
        String groupHash = ccMsgBO.getGroupHash();
        String userHash = ccMsgBO.getUserHash();
        if (ObjectUtil.isEmpty(groupHash) || ObjectUtil.isEmpty(userHash)) {
            return;
        }
        //创建通道组
        ccChannelFactory.addGroup(ccMsgBO.getGroupHash(), ctx.channel());
        //持久化信息
        if(ObjectUtil.isEmpty(ccMsgExecuteService)){
            return;
        }
        ccMsgExecuteService.joinGroup(ccMsgBO);
    }

}
