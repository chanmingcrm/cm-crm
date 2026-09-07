package com.platform.mesh.wxwork.msg.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.wxwork.contants.WxWorkConst;
import com.platform.mesh.wxwork.msg.domain.NewsMsgBO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * @description 企微机器人消息构建服务
 * @author 蝉鸣
 */
@Service
public class MsgTempServiceManual {

    /**
     * 功能描述:
     * 〈机器人消息-文本消息〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packTextMsg(String content){
        JSONObject entries = JSONUtil.createObj();
        entries.putOpt(WxWorkConst.CONTENT,content);
        return entries;
    }

    /**
     * 功能描述:
     * 〈机器人消息-文本消息附带人员〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packTextMsgWithUser(String content, List<String> mobiles){
        JSONObject entries = JSONUtil.createObj();
        entries.putOpt(WxWorkConst.CONTENT,content);
        JSONArray array = JSONUtil.createArray();
        if(CollUtil.isNotEmpty(mobiles)){
            array.addAll(mobiles);
        }
        entries.putOpt(WxWorkConst.MENTIONED_LIST,array);
        entries.putOpt(WxWorkConst.MENTIONED_MOBILE_LIST,array);
        return entries;
    }

    /**
     * 功能描述:
     * 〈机器人消息-MarkDown消息〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packMarkDown(String content){
        return packTextMsg(content);
    }

    /**
     * 功能描述:
     * 〈机器人消息-图片消息〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packImageMsg(String path){
        JSONObject entries = JSONUtil.createObj();
        String base64 = StrUtil.EMPTY;
        String md5 = StrUtil.EMPTY;
        // 获取Base64编码
//        String path = "C:\\Users\\admin\\Desktop\\测试图片.png";
        try(FileInputStream inputStream = new FileInputStream(path)) {
            byte[] bs = new byte[inputStream.available()];
            inputStream.read(bs);
            base64 = Base64.getEncoder().encodeToString(bs);
        } catch (IOException e) {
//            logger.error("WxWork::Robot::error",  e);
        }

        // 获取md5值
        try(FileInputStream inputStream = new FileInputStream(path)) {
            byte[] buf = new byte[inputStream.available()];
            inputStream.read(buf);
            md5 = DigestUtils.md5Hex(buf);
        } catch (IOException e) {
//            logger.error("WxWork::Robot::error",  e);
        }
        entries.putOpt(WxWorkConst.BASE64,base64);
        entries.putOpt(WxWorkConst.MD5,md5);
        return entries;
    }

    /**
     * 功能描述:
     * 〈机器人消息-媒体消息〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packNewsMsg(List<NewsMsgBO> newsMsgBOS){
        JSONObject entries = JSONUtil.createObj();
        if(CollUtil.isEmpty(newsMsgBOS)){
            return entries;
        }
        JSONArray array = JSONUtil.createArray();
        for (NewsMsgBO newsMsgBO : newsMsgBOS) {
            JSONObject subEntries = JSONUtil.createObj();
            subEntries.putOpt(WxWorkConst.TITLE,newsMsgBO.getTitle());
            subEntries.putOpt(WxWorkConst.DESCRIPTION,newsMsgBO.getDescription());
            subEntries.putOpt(WxWorkConst.URL,newsMsgBO.getUrl());
            subEntries.putOpt(WxWorkConst.PIC_URL,newsMsgBO.getPicUrl());
            array.add(subEntries);
        }
        entries.putOpt(WxWorkConst.ARTICLES,array);
        return entries;
    }

    /**
     * 功能描述:
     * 〈机器人消息-文件消息〉
     * @return 正常返回:{@link JSONObject}
     * @author 蝉鸣
     */
    public JSONObject packFileMsg(String mediaId){
        JSONObject entries = JSONUtil.createObj();
        entries.putOpt(WxWorkConst.MEDIA_ID,mediaId);
        return entries;
    }

}
