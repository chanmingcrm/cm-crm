package com.platform.mesh.bpm.biz.soa.pub.upms;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.bpm.biz.modules.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.upms.api.pub.upms.UpmsFeedbackService;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class BpmServiceImpl implements UpmsFeedbackService {


    @Autowired
    private IBpmDataInstRelService bpmDataInstRelService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return ServiceNameConst.BPM_SERVICE;
    }

    /**
     * 功能描述:
     * 〈转移组织数据回调处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void delBpmDataRel(MsgUpmsBO msgUpmsBO){
        Map<String, Object> extendJson = msgUpmsBO.getExtendJson();
        if(!extendJson.containsKey(StrConst.VALUE)){
            return;
        }
        Object value = extendJson.get(StrConst.VALUE);
        JSONArray objects = JSONUtil.parseArray(value);
        if(CollUtil.isEmpty(objects)){
            return;
        }
        List<Long> dataIs = objects.toList(Long.class);
        Object moduleStr = extendJson.get(StrConst.MODULE_ID);
        Long moduleId = Long.parseLong(moduleStr.toString());
        bpmDataInstRelService.delBpmDataRel(moduleId, dataIs);
    }

}
