package com.platform.mesh.upms.biz.modules.doc.dir.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.dto.DocDirDTO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.vo.DocDirVO;
import com.platform.mesh.upms.biz.modules.doc.dir.exception.DocDirExceptionEnum;
import com.platform.mesh.upms.biz.modules.doc.dir.mapper.DocDirMapper;
import com.platform.mesh.upms.biz.modules.doc.dir.service.IDocDirService;
import com.platform.mesh.upms.biz.modules.doc.dir.service.manual.DocDirServiceManual;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 文件目录
 * @author 蝉鸣
 */
@Service
public class DocDirServiceImpl extends ServiceImpl<DocDirMapper, DocDir> implements IDocDirService  {

    @Autowired
    private DocDirServiceManual docDirServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前文件目录信息〉
     * @param dirId dirId  
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirVO getDirInfoById(Long dirId) {
        DocDir docDir = this.getById(dirId);
        return docDirServiceManual.getDirInfoById(docDir);
    }

    /**
     * 功能描述:
     * 〈新增文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirVO addDir(DocDirDTO dirDTO) {
        DocDir docDir = BeanUtil.copyProperties(dirDTO, DocDir.class);
        this.save(docDir);
        return BeanUtil.copyProperties(docDir, DocDirVO.class);
    }

    /**
     * 功能描述:
     * 〈修改文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    @Override
    public DocDirVO editDir(DocDirDTO dirDTO) {
        if(ObjectUtil.isEmpty(dirDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(DocDirDTO::getId);
            throw DocDirExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        DocDir docDir = BeanUtil.copyProperties(dirDTO, DocDir.class);
        this.updateById(docDir);
        return BeanUtil.copyProperties(docDir, DocDirVO.class);
    }

    /**
     * 功能描述:
     * 〈删除文件目录〉
     * @param dirId dirId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDir(Long dirId) {
        
        return this.removeById(dirId);
    }
}