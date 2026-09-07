package com.platform.mesh.upms.biz.modules.msg.notice.factory;

import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeLoopEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 序列号类型工厂
 * @author 蝉鸣
 */
@Service
public class NoticeLoopFactory implements InitializingBean {

    @Autowired
    private List<NoticeLoopService> noticeLoopServiceList;

    private final Map<NoticeLoopEnum, NoticeLoopService> noticeLoopMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的提醒实现〉
     * @param noticeLoop noticeLoop
     * @return 正常返回:{@link NoticeLoopService}
     * @author 蝉鸣
     */
    public NoticeLoopService getNoticeLoopService(NoticeLoopEnum noticeLoop){
        return noticeLoopMaps.get(noticeLoop);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NoticeLoopService service : noticeLoopServiceList){
            noticeLoopMaps.put(service.noticeLoop(), service);
        }
    }
}
