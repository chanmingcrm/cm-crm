package com.platform.mesh.wxwork.contants;

/**
 * @description 企微机器人常量
 * @author 蝉鸣
 */
public interface WxWorkConst {

//-- --------------------------------------------------------
//        -- 配置信息
//-- --------------------------------------------------------
    //获取token接口
    String WX_WORK_INNER_APP_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    //获取客户列表接口
    String WX_WORK_INNER_APP_EXTERNAL_CONTACT_BATCH_URL = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/batch/get_by_user";
    //获取配置了客户联系功能的成员列表
    String WX_WORK_INNER_APP_FOLLOW_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get_follow_user_list";
    //获取配置了js请求签名
    String WX_WORK_JSAPI_TICKET_COM = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
    String WX_WORK_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/ticket/get";
    //机器人上传附件接口
    String ROBOT_UPLOAD_FILE_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media";
    //机器人发送消息接口
    String ROBOT_SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send";
//-- --------------------------------------------------------
//        -- 关键参数
//-- --------------------------------------------------------
    String KEY = "key";

    String TYPE = "type";

//-- --------------------------------------------------------
//        -- 缓存参数
//-- --------------------------------------------------------
    String WX_WORK_CACHE_KEY = "WX_WORK_CACHE_KEY";

//-- --------------------------------------------------------
//        -- 消息参数
//-- --------------------------------------------------------
    String ARTICLES = "articles";

    String BASE64 = "base64";

    String CONTENT = "content";

    String DESCRIPTION = "description";

    String FILE = "file";

    String FILENAME = "filename";

    String MD5 = "md5";

    String MEDIA_ID = "media_id";

    String MENTIONED_LIST = "mentioned_list";

    String MENTIONED_MOBILE_LIST = "mentioned_mobile_list";

    String MSG_TYPE = "msgtype";

    String PIC_URL = "picurl";

    String TITLE = "title";

    String URL = "url";


}
