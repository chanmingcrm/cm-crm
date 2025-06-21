package com.platform.mesh.upms.biz.modules.doc.file.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 文件
 * @author 蝉鸣
 */
@Service
public class DocFileServiceManual{

    /**
     * 服务对象
     */
    @Autowired
    private BaseOssClient ossClient;
    
    /**
     * 功能描述: 
     * 〈获取当前文件信息〉
     * @param docFile docFile 
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    public DocFileVO getFileInfoById(DocFile docFile) {
        DocFileVO docFileVO = new DocFileVO();
        if(ObjectUtil.isEmpty(docFile)){
            return docFileVO;
        }
        //转换VO
        BeanUtil.copyProperties(docFile, docFileVO);
        String fileURL = ossClient.getFileURL(docFile.getFileBucket(), docFile.getFileAddr(), Duration.ofDays(NumberConst.NUM_7));
        docFileVO.setFileUrl(fileURL);
        return docFileVO;
    }

    /**
     * 功能描述:
     * 〈上传文件〉
     * @param files files
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    public List<DocFileBO> uploadFile(MultipartFile[] files) {
        //前端分片直接上传,前端调用合并接口
        return FutureHandleUtil.runWithResult(Arrays.stream(files).toList(), ossClient::uploadFileMultiPart);
    }

    /**
     * 功能描述:
     * 〈下载文件〉
     * @param docFile docFile
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    public byte[] downloadFile(DocFile docFile) {
        return ossClient.downloadFile(docFile.getFileBucket(), docFile.getFileAddr());
//        //获取输出流
//        if(filePath.startsWith(HttpConst.HTTP) || filePath.startsWith(HttpConst.HTTPS)) {
//            return OssFileUtil.getRemoteFile(filePath);
//        }else {
//            return OssFileUtil.getLocalFile(filePath);
//        }
    }
}