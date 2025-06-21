package com.platform.mesh.upms.biz.modules.msg.base.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.IMsgUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 消息
 * @author 蝉鸣
 */
@Service
public class MsgBaseServiceManual{

    @Autowired
    private IMsgUserRelService msgUserRelService;
    
    /**
     * 功能描述: 
     * 〈获取当前消息信息〉
     * @param msgBase msgBase 
     * @return 正常返回:{@link MsgBaseVO}
     * @author 蝉鸣
     */
    public MsgBaseVO getBaseInfoById(MsgBase msgBase) {
        MsgBaseVO msgBaseVO = new MsgBaseVO();
        if(ObjectUtil.isEmpty(msgBaseVO)){
            return msgBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(msgBase, msgBaseVO);
        return msgBaseVO;
    }

    /**
     * 功能描述:
     * 〈保存消息接收人信息〉
     * @param msgId msgId
     * @param msgUserIds msgUserIds
     * @author 蝉鸣
     */
    public void saveMsgUser(Long msgId, List<Long> msgUserIds) {
        if(ObjectUtil.isEmpty(msgId) || CollUtil.isEmpty(msgUserIds)){
            return;
        }
        List<MsgUserRel> msgUserRels = msgUserIds.stream().map(msgUserId -> {
            MsgUserRel userRel = new MsgUserRel();
            userRel.setMsgId(msgId);
            userRel.setUserId(msgUserId);
            userRel.setReadFlag(YesOrNoEnum.YES.getValue());
            userRel.setDelFlag(YesOrNoEnum.YES.getValue());
            return userRel;
        }).toList();
        msgUserRelService.saveBatch(msgUserRels);
    }
}