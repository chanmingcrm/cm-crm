package com.platform.mesh.gen.biz.modules.code.build.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildInitDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildPreviewDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.po.CodeBuild;
import com.platform.mesh.gen.biz.modules.code.build.mapper.CodeBuildMapper;
import com.platform.mesh.gen.biz.modules.code.build.service.ICodeBuildService;
import com.platform.mesh.gen.biz.modules.code.build.service.manual.CodeBuildServiceManual;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 构建信息
 * @author 蝉鸣
 */
@Service
public class CodeBuildServiceImpl extends ServiceImpl<CodeBuildMapper, CodeBuild> implements ICodeBuildService {

    @Autowired
    private CodeBuildServiceManual codeBuildServiceManual;


    /**
     * 功能描述:
     * 〈初始化代码生成〉
     * @param buildInitDTO buildInitDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean buildInit(CodeBuildInitDTO buildInitDTO) {
        codeBuildServiceManual.buildInit(buildInitDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈预览代码〉
     * @param previewDTO previewDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String previewCode(CodeBuildPreviewDTO previewDTO) {
        // 数据模型
        Map<String, Object> dataModel = codeBuildServiceManual.getDataModel(previewDTO.getBuildId(),previewDTO.getTableId());
        CodeTemplate codeTemplate = codeBuildServiceManual.getTempById(previewDTO.getTempId());
        return codeBuildServiceManual.renderStr(codeTemplate.getTemplateCode(), dataModel);
    }

    /**
     * 功能描述:
     * 〈构建代码〉
     *step:
     * 0：根据buildId查询CodeBuild表中的信息->codeBuild
     * 1: 查询codeTable中的所有表,并提取出Id -> {要生成哪些表}
     * 2：根据配置比表的tableId进行遍历生成
     * 3：生成模板对象-使用0中的buildId构建数据对象
     *      * 3.1:根据codeBuildId查询codeBuildDataConf->并将结构处理成map<confCode,confValue>
     *      * 3.2:根据step1中的tableId 查询 codeTable(业务表)信息
     *      * 3.3：将codeTable 实际的包路径，包模块名称等存入到 map模板对象中
     *      * 3.4：step3.1中的模板对象value的${param}进行渲染，重新保存
     * 4：根据分组类型（这里默认传模板类型）和分组id查询 gen_all_group_rel 中的 dataId
     * 5：根据dataId作为主键id查询code_template中的id
     * 6：根据模板代码和模板路径生成相对于的文件（这里将step3中的数据作为入参进行传入结合生成）
     * @param codeBuildDTO codeBuildDTO
     * @return 正常返回:{@link byte}
     * @author 蝉鸣
     */
    @Override
    public byte[] buildCode(CodeBuildDTO codeBuildDTO) {
        CodeBuild codeBuild = getById(codeBuildDTO.getId());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //模拟查询所有表
        List<Long> tableIds = codeBuildServiceManual.selectAllTableList();
        codeBuildDTO.setTableIds(tableIds);
        for (Long tableId : codeBuildDTO.getTableIds()) {
            codeBuildServiceManual.buildZip(codeBuild,codeBuildDTO.getGroupIds(),tableId, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}
