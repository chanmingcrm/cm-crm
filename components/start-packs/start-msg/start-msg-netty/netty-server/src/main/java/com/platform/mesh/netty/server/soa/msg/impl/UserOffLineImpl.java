package com.platform.mesh.netty.server.soa.msg.impl;

import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
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
public class UserOffLineImpl implements CcMsgTypeService {

    @Autowired
    private CcChannelFactory ccChannelFactory;

    /**
     * 功能描述:
     * 〈用户下线〉
     * @return 正常返回:{@link CcMsgTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public CcMsgTypeEnum ccMsgType() {
        return CcMsgTypeEnum.USER_OFFLINE;
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
        //删除通道信息
        ccChannelFactory.delGroup(ccMsgBO.getGroupHash(),ctx.channel());
        ccChannelFactory.delUser(ccMsgBO.getUserHash(),ctx.channel());
    }

}
