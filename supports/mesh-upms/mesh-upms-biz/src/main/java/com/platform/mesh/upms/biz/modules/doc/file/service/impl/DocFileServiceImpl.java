package com.platform.mesh.upms.biz.modules.doc.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.file.oss.base.common.model.bo.DocFileBO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocFileDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.dto.DocPageDTO;
import com.platform.mesh.upms.biz.modules.doc.file.domain.po.DocFile;
import com.platform.mesh.upms.biz.modules.doc.file.mapper.DocFileMapper;
import com.platform.mesh.upms.biz.modules.doc.file.service.IDocFileService;
import com.platform.mesh.upms.biz.modules.doc.file.service.manual.DocFileServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
     * 〈分页查询文档〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocFile>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DocFile> selectPage(DocPageDTO pageDTO) {
        MPage<DocFile> fileMPage = MPageUtil.pageEntityToMPage(pageDTO, DocFile.class);
        List<Long> dirIds = docFileServiceManual.getDirIds(pageDTO.getDirId());
        if (CollUtil.isEmpty(dirIds)) {
            return this.lambdaQuery()
                    .eq(ObjectUtil.isNotEmpty(pageDTO.getModuleId()), DocFile::getModuleId, pageDTO.getModuleId())
                    .eq(ObjectUtil.isNotEmpty(pageDTO.getDataId()), DocFile::getDataId, pageDTO.getDataId())
                    .orderByDesc(DocFile::getCreateTime)
                    .page(fileMPage);
        }
        return this.getBaseMapper().selectPageWithDir(fileMPage,dirIds,pageDTO);
    }
    
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
        return docFileServiceManual.packVO(docFiles);
    }

    /**
     * 功能描述:
     * 〈主要用于获取验证码等未登录状态下的文件，需要限制数量〉
     * @param fileFlag fileFlag
     * @return 正常返回:{@link List<DocFileVO>}
     * @author 蝉鸣
     */
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
    public List<DocFileVO> uploadFile(Long moduleId,Long dataId,MultipartFile[] files) {
        List<DocFileBO> docFileBOS = docFileServiceManual.uploadFile(files);
        List<DocFile> docFiles = BeanUtil.copyToList(docFileBOS, DocFile.class);
        if(ObjectUtil.isNotEmpty(moduleId) && ObjectUtil.isNotEmpty(dataId)){
            docFiles.forEach(docFile -> {
                docFile.setModuleId(moduleId);
                docFile.setDataId(dataId);
                docFile.setOpenFlag(YesOrNoEnum.NO.getValue());
            });
        }
        this.saveBatch(docFiles);
        return docFileServiceManual.packVO(docFiles);
    }

    /**
     * 功能描述:
     * 〈流式下载文件〉
     * @param fileId fileId
     * @return 正常返回:{@link InputStream}
     * @author 蝉鸣
     */
    @Override
    public InputStream downloadFileStream(Long fileId) {
        DocFile docFile = this.getById(fileId);
        return docFileServiceManual.downloadFileStream(docFile);
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


    /**
     * 功能描述:
     * 〈分页查询文档〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocFile>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<DocFileVO> selectOpenPage(DocPageDTO pageDTO) {
        MPage<DocFile> fileMPage = MPageUtil.pageEntityToMPage(pageDTO, DocFile.class);
        List<Long> dirIds = docFileServiceManual.getDirIds(pageDTO.getDirId());
        MPage<DocFile> mPage = this.getBaseMapper().selectOpenPage(fileMPage, dirIds, YesOrNoEnum.YES.getValue());
        PageVO<DocFileVO> voPage = MPageUtil.convertToVO(mPage, DocFileVO.class);
        if(CollUtil.isEmpty(mPage.getRecords())){
            return voPage;
        }
        for (DocFileVO record : voPage.getRecords()) {
            //设置访问URL
            String fileEndpoint = record.getFileEndpoint();
            int lastIndexOf = fileEndpoint.lastIndexOf(SymbolConst.HTTP_CONCAT);
            String prefix = fileEndpoint.substring(NumberConst.NUM_0, lastIndexOf + NumberConst.NUM_3);
            String region = fileEndpoint.substring(lastIndexOf+NumberConst.NUM_3);
            String url = prefix.concat(record.getFileBucket()).concat(SymbolConst.PERIOD)
                    .concat(region).concat(record.getFileAddr());
            record.setFileUrl(url);
        }
        return voPage;
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param files files
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DocFileVO> uploadFileByDir(Long dirId, MultipartFile[] files) {
        List<DocFileBO> docFileBOS = docFileServiceManual.uploadFile(files);
        List<DocFile> docFiles = BeanUtil.copyToList(docFileBOS, DocFile.class);
        docFiles.forEach(docFile -> {
            docFile.setOpenFlag(YesOrNoEnum.YES.getValue());
            docFile.setScopeUserId(NumberConst.NUM_0.longValue());
            docFile.setScopeOrgId(NumberConst.NUM_0.longValue());
        });
        this.saveBatch(docFiles);
        //保存入开放的文件夹
        docFileServiceManual.addByDir(dirId,docFiles);
        List<DocFileVO> fileVOS = CollUtil.newArrayList();
        for (DocFile docFile : docFiles) {
            DocFileVO fileVO = docFileServiceManual.getFileInfoById(docFile);
            fileVOS.add(fileVO);
        }
        return fileVOS;
    }

}
