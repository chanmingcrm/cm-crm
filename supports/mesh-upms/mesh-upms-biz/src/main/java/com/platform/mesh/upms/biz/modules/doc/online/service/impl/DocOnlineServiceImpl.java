package com.platform.mesh.upms.biz.modules.doc.online.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.doc.domain.dto.DocOnlineSaveDTO;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.dir.enums.DocFlagEnum;
import com.platform.mesh.upms.biz.modules.doc.dirrel.enums.DocRelFlagEnum;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineLastDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlinePageDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.po.DocOnline;
import com.platform.mesh.upms.biz.modules.doc.online.domain.vo.DocOnlineVO;
import com.platform.mesh.upms.biz.modules.doc.online.exception.DocOnlineExceptionEnum;
import com.platform.mesh.upms.biz.modules.doc.online.mapper.DocOnlineMapper;
import com.platform.mesh.upms.biz.modules.doc.online.service.IDocOnlineService;
import com.platform.mesh.upms.biz.modules.doc.online.service.manual.DocOnlineServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 在线文档
 * @author 蝉鸣
 */
@Service
public class DocOnlineServiceImpl extends ServiceImpl<DocOnlineMapper, DocOnline> implements IDocOnlineService {

    @Autowired
    private DocOnlineServiceManual docOnlineServiceManual;

    /**
     * 功能描述:
     * 〈获取官网在线文档分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocOnline>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DocOnline> selectHomePage(DocOnlinePageDTO pageDTO) {
        MPage<DocOnline> docMPage = MPageUtil.pageEntityToMPage(pageDTO, DocOnline.class);
        pageDTO.setDocFlag(DocFlagEnum.HOME.getValue());
        return this.getBaseMapper().selectPage(docMPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈获取官网在线文档分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocOnline>}
     * @author 蝉鸣
     */
    @Override
    public MPage<DocOnline> selectPage(DocOnlinePageDTO pageDTO) {
        MPage<DocOnline> docMPage = MPageUtil.pageEntityToMPage(pageDTO, DocOnline.class);
        return this.getBaseMapper().selectPage(docMPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈获取官网文件信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    @Override
    public DocOnlineVO getOnlineHomeInfoById(Long onlineId) {
        DocOnline docOnline = this.getBaseMapper().getOnlineHomeInfoById(onlineId,DocFlagEnum.HOME.getValue());
        return BeanUtil.copyProperties(docOnline, DocOnlineVO.class);
    }

    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    @Override
    public DocOnlineVO getOnlineInfoById(Long onlineId) {
        DocOnline docOnline = getById(onlineId);
        return BeanUtil.copyProperties(docOnline, DocOnlineVO.class);
    }

    /**
     * 功能描述:
     * 〈添加文档〉
     * @param docOnlineDTO docOnlineDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean addOnLine(DocOnlineDTO docOnlineDTO) {
        //校验文档大小
        int length = docOnlineDTO.getDocContext().length();
        if(length > NumberConst.NUM_50000){
            throw DocOnlineExceptionEnum.ADD_LIMIT_INVALID.getBaseException();
        }
        DocOnline docOnline = BeanUtil.copyProperties(docOnlineDTO, DocOnline.class);
        if(ObjectUtil.isEmpty(docOnline.getDocFlag())){
            docOnline.setDocFlag(DocFlagEnum.ADMIN.getValue());
        }
        saveOrUpdate(docOnline);
        //新增文档与目录关系
        docOnlineServiceManual.saveDocRel(docOnlineDTO.getDirId(), DocRelFlagEnum.ONLINE.getValue(), docOnline);
        if(DocFlagEnum.HOME.getValue().equals(docOnlineDTO.getDocFlag())){
            //发布到百度搜索
            DocOnline online = getById(docOnline.getId());
            Boolean pubToBaidu = docOnlineServiceManual.pubToBaidu(docOnline.getId());
            if((!YesOrNoEnum.YES.getValue().equals(online.getPubFlag())) && pubToBaidu){
                online.setPubFlag(YesOrNoEnum.YES.getValue());
            }
            updateById(online);
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除文档〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteOnLine(Long onlineId) {
        return removeById(onlineId);
    }

    /**
     * 功能描述:
     * 〈查询上/下一条数据〉
     * @param lastDTO lastDTO
     * @return 正常返回:{@link DocOnlineVO}
     * @author 蝉鸣
     */
    @Override
    public DocOnline selectLastOne(DocOnlineLastDTO lastDTO) {
        DocOnline online = this.getBaseMapper().getLastOne(lastDTO);
        if(ObjectUtil.isEmpty(online)){
            return this.getBaseMapper().getOnlineHomeInfoById(lastDTO.getId(),lastDTO.getDocFlag());
        }
        return online;
    }

    /**
     * 功能描述:
     * 〈AI生成文章〉
     * @param saveDTO saveDTO
     * @author 蝉鸣
     */
    @Override
    public void addOnLineByAi(DocOnlineSaveDTO saveDTO) {
        int length = StrUtil.length(saveDTO.getContentPc());
        if (length > NumberConst.NUM_50000) {
            throw DocOnlineExceptionEnum.ADD_LIMIT_INVALID.getBaseException();
        }
        {
            DocOnline docOnline = new DocOnline();
            docOnline.setDocFlag(DocFlagEnum.HOME.getValue());
            docOnline.setDocTitle(saveDTO.getTitle());
            docOnline.setDocKeyword(saveDTO.getKeyword());
            docOnline.setDocDesc(saveDTO.getDesc());
            docOnline.setDocContext(HtmlUtil.unescape(saveDTO.getContentPc()));
            docOnline.setDocAppContext(HtmlUtil.unescape(saveDTO.getContentApp()));
            this.save(docOnline);
            //新增文档与目录关系
            docOnlineServiceManual.saveDocRel(saveDTO.getDirId(), DocRelFlagEnum.ONLINE.getValue(), docOnline);
            //发布到百度搜索
            Boolean pubToBaidu = docOnlineServiceManual.pubToBaidu(docOnline.getId());
            if(pubToBaidu){
                docOnline.setPubFlag(YesOrNoEnum.YES.getValue());
            }else{
                docOnline.setPubFlag(YesOrNoEnum.NO.getValue());
            }
            updateById(docOnline);
        }
    }

    /**
     * 功能描述:
     * 〈发布文章〉
     * @param onlineId onlineId
     * @author 蝉鸣
     */
    @Override
    public Boolean pubOne(Long onlineId) {
        DocOnline docOnline = this.getById(onlineId);
        //发布到百度搜索
        Boolean pubToBaidu = docOnlineServiceManual.pubToBaidu(docOnline.getId());
        if(pubToBaidu){
            docOnline.setPubFlag(YesOrNoEnum.YES.getValue());
        }else{
            docOnline.setPubFlag(YesOrNoEnum.NO.getValue());
        }
        //修改发布标识
        updateById(docOnline);
        return pubToBaidu;
    }
}
