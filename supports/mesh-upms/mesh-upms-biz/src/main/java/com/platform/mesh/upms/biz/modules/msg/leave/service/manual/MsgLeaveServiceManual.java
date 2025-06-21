package com.platform.mesh.upms.biz.modules.msg.leave.service.manual;

import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 留言消息
 * @author 蝉鸣
 */
@Service
public class MsgLeaveServiceManual {


    /**
     * 功能描述:
     * 〈获取留言消息缓存信息〉
     * @param key key
     * @author 蝉鸣
     */
    public Boolean getRedisKey(String key) {
        String concatKey = CacheConstants.SYS_MSG_LEAVE.concat(SymbolConst.COLON).concat(key);
        long toLive = RedissonUtil.getTimeToLive(concatKey);
        if(toLive > NumberConst.NUM_0){
            return Boolean.FALSE;
        }
        //默认时间1天
        Duration duration = Duration.ofDays(NumberConst.NUM_1);
        RedissonUtil.expire(concatKey, duration);
        return Boolean.TRUE;
    }
}