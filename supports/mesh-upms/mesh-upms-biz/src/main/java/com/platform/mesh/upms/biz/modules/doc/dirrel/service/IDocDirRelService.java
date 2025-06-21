package com.platform.mesh.upms.biz.modules.doc.dirrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.dto.DocDirRelDTO;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po.DocDirRel;
import com.platform.mesh.upms.biz.modules.doc.dirrel.domain.vo.DocDirRelVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 文件信息
 * @author 蝉鸣
 */
public interface IDocDirRelService extends IService<DocDirRel> {


    /**
     * 功能描述:
     * 〈获取当前文件信息〉
     * @param dirRelId dirRelId
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    DocDirRelVO getDirRelInfoById(Long dirRelId);

    /**
     * 功能描述:
     * 〈新增文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    DocDirRelVO addDirRel(DocDirRelDTO dirRelDTO);

    /**
     * 功能描述:
     * 〈修改文件〉
     * @param dirRelDTO dirRelDTO
     * @return 正常返回:{@link DocDirRelVO}
     * @author 蝉鸣
     */
    DocDirRelVO editDirRel(DocDirRelDTO dirRelDTO);

    /**
     * 功能描述:
     * 〈删除文件〉
     * @param dirRelId dirRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDirRel(Long dirRelId);
}