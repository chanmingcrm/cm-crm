package com.platform.mesh.upms.biz.modules.doc.dirrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.dto.DocDirRelDTO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.vo.DocDirRelVO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.exception.DocDirRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.doc.dirrel.mapper.DocDirRelMapper;
import com.platform.mesh.upms.biz.modules.doc.dirrel.service.IDocDirRelService;
import com.platform.mesh.upms.biz.modules.doc.dirrel.service.manual.DocDirRelServiceManual;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 文件
 * @author 蝉鸣
 */
@Service
public class DocDirRelServiceImpl extends ServiceImpl<DocDirRelMapper, DocDirRel> implements IDocDirRelService  {

    @Autowired
    private DocDirRelServiceManual docDirRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前文件信息〉
     * @param dirRelId dirRelId  
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirRelVO getDirRelInfoById(Long dirRelId) {
        DocDirRel docDirRel = this.getById(dirRelId);
        return docDirRelServiceManual.getDirRelInfoById(docDirRel);
    }

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirRelVO addDirRel(DocDirRelDTO dirRelDTO) {
        DocDirRel docDirRel = BeanUtil.copyProperties(dirRelDTO, DocDirRel.class);
        this.save(docDirRel);
        return BeanUtil.copyProperties(docDirRel, DocDirRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirRelVO editDirRel(DocDirRelDTO dirRelDTO) {
        if(ObjectUtil.isEmpty(dirRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(DocDirRelDTO::getId);
            throw DocDirRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        DocDirRel docDirRel = BeanUtil.copyProperties(dirRelDTO, DocDirRel.class);
        this.updateById(docDirRel);
        return BeanUtil.copyProperties(docDirRel, DocDirRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除文件〉
     * @param dirRelId dirRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDirRel(Long dirRelId) {
        
        return this.removeById(dirRelId);
    }
}