package com.platform.mesh.upms.biz.modules.doc.dir.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.dto.DocDirDTO;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.po.DocDir;
import com.platform.mesh.upms.biz.modules.doc.dir.domain.vo.DocDirVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 文件目录信息
 * @author 蝉鸣
 */
public interface IDocDirService extends IService<DocDir> {


    /**
     * 功能描述:
     * 〈获取当前文件目录信息〉
     * @param dirId dirId
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    DocDirVO getDirInfoById(Long dirId);

    /**
     * 功能描述:
     * 〈新增文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    DocDirVO addDir(DocDirDTO dirDTO);

    /**
     * 功能描述:
     * 〈修改文件目录〉
     * @param dirDTO dirDTO
     * @return 正常返回:{@link DocDirVO}
     * @author 蝉鸣
     */
    DocDirVO editDir(DocDirDTO dirDTO);

    /**
     * 功能描述:
     * 〈删除文件目录〉
     * @param dirId dirId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDir(Long dirId);
}