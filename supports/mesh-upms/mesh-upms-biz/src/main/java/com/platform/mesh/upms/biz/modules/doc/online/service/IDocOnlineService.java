package com.platform.mesh.upms.biz.modules.doc.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.doc.domain.dto.DocOnlineSaveDTO;
import com.platform.mesh.upms.api.modules.doc.domain.vo.DocFileVO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlineLastDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.dto.DocOnlinePageDTO;
import com.platform.mesh.upms.biz.modules.doc.online.domain.po.DocOnline;
import com.platform.mesh.upms.biz.modules.doc.online.domain.vo.DocOnlineVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 在线文档信息
 * @author 蝉鸣
 */
public interface IDocOnlineService extends IService<DocOnline> {

    /**
     * 功能描述:
     * 〈获取官网在线文档分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocOnline>}
     * @author 蝉鸣
     */
    MPage<DocOnline> selectHomePage(DocOnlinePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取官网在线文档分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<DocOnline>}
     * @author 蝉鸣
     */
    MPage<DocOnline> selectPage(DocOnlinePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取官网在线文档信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    DocOnlineVO getOnlineHomeInfoById(Long onlineId);

    /**
     * 功能描述:
     * 〈获取当前在线文档信息〉
     * @param onlineId onlineId
     * @return 正常返回:{@link DocFileVO}
     * @author 蝉鸣
     */
    DocOnlineVO getOnlineInfoById(Long onlineId);

    /**
     * 功能描述:
     * 〈添加文档〉
     * @param docOnlineDTO docOnlineDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addOnLine(DocOnlineDTO docOnlineDTO);

    /**
     * 功能描述:
     * 〈删除文档〉
     * @param onlineId onlineId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteOnLine(Long onlineId);

    /**
     * 功能描述:
     * 〈查询上/下一条数据〉
     * @param lastDTO lastDTO
     * @return 正常返回:{@link DocOnlineVO}
     * @author 蝉鸣
     */
    DocOnline selectLastOne(DocOnlineLastDTO lastDTO);

    /**
     * 功能描述:
     * 〈AI生成文章〉
     * @param saveDTO saveDTO
     * @author 蝉鸣
     */
    void addOnLineByAi(DocOnlineSaveDTO saveDTO);

    /**
     * 功能描述:
     * 〈发布文章〉
     * @param onlineId onlineId
     * @author 蝉鸣
     */
    Boolean pubOne(Long onlineId);

}
