package com.platform.mesh.upms.biz.modules.doc.file.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import com.platform.mesh.upms.biz.modules.doc.dir.service.IDocDirService;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import com.platform.mesh.upms.biz.modules.doc.dirrel.enums.DocRelFlagEnum;
import com.platform.mesh.upms.biz.modules.doc.dirrel.service.IDocDirRelService;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private IDocDirService docDirService;

    @Autowired
    private IDocDirRelService docDirRelService;

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
     * 〈流式下载文件〉
     * @param docFile docFile
     * @return 正常返回:{@link InputStream}
     */
    public InputStream downloadFileStream(DocFile docFile) {
        if (ObjectUtil.isEmpty(docFile)) {
            return InputStream.nullInputStream();
        }
        return ossClient.downloadFileStream(docFile.getFileBucket(), docFile.getFileAddr());
    }

    /**
     * 功能描述:
     * 〈返回VO〉
     * @param docFiles docFiles
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    public List<DocFileVO> packVO(List<DocFile> docFiles) {
        if(CollUtil.isEmpty(docFiles)){
            return CollUtil.newArrayList();
        }
        return docFiles.stream().map(docFile->{
            DocFileVO docFileVO = BeanUtil.copyProperties(docFile, DocFileVO.class);
            String fileURL = ossClient.getFileURL(docFile.getFileBucket(), docFile.getFileAddr(), Duration.ofDays(NumberConst.NUM_7));
            docFileVO.setFileUrl(fileURL);
            return docFileVO;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈保存入开放的文件夹〉
     * @param docFiles docFiles
     * @author 蝉鸣
     */
    public void addByDir(Long dirId,List<DocFile> docFiles) {
        if(CollUtil.isEmpty(docFiles)){
            return;
        }
        //查询一个开放的文件夹
        DocDir docDir = docDirService.getById(dirId);
        List<DocDirRel> dirRels = docFiles.stream().map(docFile -> {
            DocDirRel docDirRel = new DocDirRel();
            docDirRel.setDirId(docDir.getId());
            docDirRel.setDocId(docFile.getId());
            docDirRel.setRelFlag(DocRelFlagEnum.FILE.getValue());
            docDirRel.setScopeUserId(NumberConst.NUM_0.longValue());
            docDirRel.setScopeOrgId(NumberConst.NUM_0.longValue());
            return docDirRel;
        }).toList();
        docDirRelService.saveBatch(dirRels);
    }

    /**
     * 功能描述:
     * 〈查询子文件夹〉
     * @param dirId dirId
     * @author 蝉鸣
     */
    public List<Long> getDirIds(Long dirId) {
        if(ObjectUtil.isEmpty(dirId)){
            return CollUtil.newArrayList();
        }
        //查询当前文件加下的所有文件夹
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        String childrenSql = SqlUtil.getCommonChildrenSql(DocDir.class, dirId);
        //查询子项
        List<DocDir> childList = docDirService.lambdaQuery().apply(childrenSql).list();
        DataScopeHandler.unEnableDataScope();
        if(CollUtil.isEmpty(childList)){
            return CollUtil.newArrayList(dirId);
        }
        List<Long> dirIds = childList.stream().map(DocDir::getId).collect(Collectors.toList());
        dirIds.add(dirId);
        return dirIds;
    }
}
