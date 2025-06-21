package com.platform.mesh.upms.biz.modules.doc.dirrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.vo.DocDirRelVO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 文件
 * @author 蝉鸣
 */
@Service
public class DocDirRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前文件信息〉
     * @param docDirRel docDirRel 
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    public DocDirRelVO getDirRelInfoById(DocDirRel docDirRel) {
        DocDirRelVO docDirRelVO = new DocDirRelVO();
        if(ObjectUtil.isEmpty(docDirRelVO)){
            return docDirRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(docDirRel, docDirRelVO);
        return docDirRelVO;
    }

}