package com.platform.mesh.upms.biz.modules.msg.userrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.MsgUserRelVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 消息接收
 * @author 蝉鸣
 */
@Service
public class MsgUserRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前消息接收信息〉
     * @param msgUserRel msgUserRel 
     * @return 正常返回:{@link MsgUserRelVO}
     * @author 蝉鸣
     */
    public MsgUserRelVO getUserRelInfoById(MsgUserRel msgUserRel) {
        MsgUserRelVO msgUserRelVO = new MsgUserRelVO();
        if(ObjectUtil.isEmpty(msgUserRelVO)){
            return msgUserRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(msgUserRel, msgUserRelVO);
        return msgUserRelVO;
    }

}