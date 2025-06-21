package com.platform.mesh.upms.biz.modules.doc.dir.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.vo.DocDirVO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 文件目录
 * @author 蝉鸣
 */
@Service
public class DocDirServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前文件目录信息〉
     * @param docDir docDir 
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    public DocDirVO getDirInfoById(DocDir docDir) {
        DocDirVO docDirVO = new DocDirVO();
        if(ObjectUtil.isEmpty(docDirVO)){
            return docDirVO;
        }
        //转换VO
        BeanUtil.copyProperties(docDir, docDirVO);
        return docDirVO;
    }

}