package com.platform.mesh.upms.biz.modules.msg.userrel.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.message.jpush.domain.bo.JPushBO;
import com.platform.mesh.message.jpush.service.JPushService;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadUserVO;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 消息接收
 * @author 蝉鸣
 */
@Service
public class MsgUserRelServiceManual{

    @Autowired(required = false)
    private JPushService jPushService;

    /**
     * 功能描述:
     * 〈极光推送〉
     * @author 蝉鸣
     */
    public void jPush(MsgBase msgBase, List<UnReadUserVO> unReadUserVOS) {
        if(ObjectUtil.isEmpty(msgBase) || CollUtil.isEmpty(unReadUserVOS)){
            return;
        }
        if(ObjectUtil.isEmpty(jPushService)){
            return;
        }
        for (UnReadUserVO readUserVO : unReadUserVOS) {
            JPushBO jPushBO = getJPushBO(msgBase,readUserVO);
            jPushService.send(jPushBO);
        }
    }

    /**
     * 功能描述:
     * 〈极光推送〉
     * @author 蝉鸣
     */
    public JPushBO getJPushBO(MsgBase msgBase,UnReadUserVO readUserVO) {
        JPushBO jPushBO = new JPushBO();
        //标题
        jPushBO.setTitle(msgBase.getMsgTitle());
        //内容
        jPushBO.setContent(msgBase.getMsgBody());
        //目标人群
        jPushBO.setAliasList(CollUtil.newArrayList(readUserVO.getUserId().toString()));
        //设置角标数量
        jPushBO.setBadge(StrUtil.toString(readUserVO.getUnReadCount()));
        //扩展信息
        Map<String, Object> extendMap = new HashMap<>();
        extendMap.put(ObjFieldUtil.getFieldName(MsgBase::getMsgFlag),msgBase.getMsgFlag());
        jPushBO.setExtendMap(extendMap);
        return jPushBO;
    }
}