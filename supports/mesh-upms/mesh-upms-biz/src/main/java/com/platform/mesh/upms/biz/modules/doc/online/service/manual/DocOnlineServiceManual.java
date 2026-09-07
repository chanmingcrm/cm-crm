package com.platform.mesh.upms.biz.modules.doc.online.service.manual;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import com.platform.mesh.upms.biz.modules.doc.dirrel.service.IDocDirRelService;
import com.platform.mesh.upms.biz.modules.doc.online.domain.po.DocOnline;
import com.platform.mesh.upms.biz.modules.doc.pub.domain.po.DocPub;
import com.platform.mesh.upms.biz.modules.doc.pub.service.IDocPubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 在线文档
 * @author 蝉鸣
 */
@Service
public class DocOnlineServiceManual {

    private static final Logger log = LoggerFactory.getLogger(DocOnlineServiceManual.class);

    @Autowired
    private IDocDirRelService docDirRelService;

    @Autowired
    private IDocPubService docPubService;

    /**
     * 功能描述: 
     * 〈获取当前文件信息〉
     * @param dirId dirId
     * @param relFlag relFlag
     * @param doc doc
     * @author 蝉鸣
     */
    public void saveDocRel(Long dirId, Integer relFlag, DocOnline doc) {
        DocDirRel docDirRel = new DocDirRel();
        docDirRel.setDirId(dirId);
        docDirRel.setRelFlag(relFlag);
        docDirRel.setDocId(doc.getId());
        docDirRelService.save(docDirRel);
    }

    /**
     * 功能描述:
     * 〈发布文章到百度站点〉
     * @param docId docId
     * @author 蝉鸣
     */
    public Boolean pubToBaidu(Long docId) {
        DocPub docPub = docPubService.lambdaQuery().last("LIMIT 1").one();
        if (docPub == null) {
            return Boolean.FALSE;
        }
        String token = docPub.getDocToken();
        String urlSite = docPub.getDocUrlSite();
        String urlPrefix = docPub.getDocUrlPrefix();

        // 字符串拼接 URLs（换行分隔）
        String url = urlPrefix.concat(docId.toString());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://data.zz.baidu.com/urls?site=" + urlSite + "&token=" + token))
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            log.info("提交百度站点",body);
            if(!JSONUtil.isTypeJSON(body)){
                return Boolean.FALSE;
            }
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if(!jsonObject.containsKey("success")){
                return Boolean.FALSE;
            }
            Object object = jsonObject.get("success");
            int parsed = Integer.parseInt(object.toString());
            if(parsed > NumberConst.NUM_0){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
}
