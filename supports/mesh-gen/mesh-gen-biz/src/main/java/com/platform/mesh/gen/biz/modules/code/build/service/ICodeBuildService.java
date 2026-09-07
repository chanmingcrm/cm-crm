package com.platform.mesh.gen.biz.modules.code.build.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildInitDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildPreviewDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.po.CodeBuild;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 构造信息
 * @author 蝉鸣
 */
public interface ICodeBuildService extends IService<CodeBuild> {


    /**
     * 功能描述:
     * 〈初始化代码生成〉
     * @param buildInitDTO buildInitDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean buildInit(CodeBuildInitDTO buildInitDTO);

    /**
     * 功能描述:
     * 〈预览代码〉
     * @param previewDTO previewDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String previewCode(CodeBuildPreviewDTO previewDTO);

    /**
     * 功能描述:
     * 〈构建代码〉
     * @param codeBuildDTO codeBuildDTO
     * @return 正常返回:{@link byte}
     * @author 蝉鸣
     */
    byte[] buildCode(CodeBuildDTO codeBuildDTO);

}
