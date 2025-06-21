package com.platform.mesh.upms.biz.modules.doc.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocFileDTO;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.mapper.DocFileMapper;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import com.platform.mesh.upms.biz.modules.doc.file.service.manual.DocFileServiceManual;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 文件
 * @author 蝉鸣
 */
@Service
public class DocFileServiceImpl extends ServiceImpl<DocFileMapper, DocFile> implements IDocFileService  {

    @Autowired
    private DocFileServiceManual docFileServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前文件信息〉
     * @param fileId fileId  
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    @Override
    public DocFileVO getFileInfoById(Long fileId) {
        DocFile docFile = this.getById(fileId);
        return docFileServiceManual.getFileInfoById(docFile);
    }

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param fileIds fileIds
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
    @Override
    public List<DocFileVO> getFileInfoById(List<Long> fileIds) {
        if(CollUtil.isEmpty(fileIds)){
            return CollUtil.newArrayList();
        }
        List<DocFile> docFiles = this.listByIds(fileIds);
        return BeanUtil.copyToList(docFiles, DocFileVO.class);
    }

    @Override
    public List<DocFileVO> getByFileFlagNoAuth(Integer fileFlag) {
        return this.getBaseMapper().selectListNoAuth(fileFlag);
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param files files
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocFileVO> uploadFile(MultipartFile[] files) {
        List<DocFileBO> docFileBOS = docFileServiceManual.uploadFile(files);
        List<DocFile> docFiles = BeanUtil.copyToList(docFileBOS, DocFile.class);
        this.saveBatch(docFiles);
        return BeanUtil.copyToList(docFiles, DocFileVO.class);
    }

    /**
     * 功能描述:
     * 〈下载文件〉
     * @param fileId fileId
     * @return 正常返回:{@link byte[]}
     * @author 蝉鸣
     */
    @Override
    public byte[] downloadFile(Long fileId){
        //获取文件信息
        DocFile docFile = getById(fileId);
        //获取文件字节
        return docFileServiceManual.downloadFile(docFile);
    }

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param fileDTO fileDTO
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    @Override
    public DocFileVO addFile(DocFileDTO fileDTO) {
        DocFile docFile = BeanUtil.copyProperties(fileDTO, DocFile.class);
        this.saveOrUpdate(docFile);
        return BeanUtil.copyProperties(docFile, DocFileVO.class);
    }

    /**
     * 功能描述:
     * 〈删除文件〉
     * @param fileId fileId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFile(Long fileId) {
        
        return this.removeById(fileId);
    }
}