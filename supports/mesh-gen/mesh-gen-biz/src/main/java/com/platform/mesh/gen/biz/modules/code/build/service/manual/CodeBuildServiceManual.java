package com.platform.mesh.gen.biz.modules.code.build.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.gen.biz.modules.code.build.constants.BuildConst;
import com.platform.mesh.gen.biz.modules.code.build.domain.dto.CodeBuildInitDTO;
import com.platform.mesh.gen.biz.modules.code.build.domain.po.CodeBuild;
import com.platform.mesh.gen.biz.modules.code.build.exception.BuildExceptionEnum;
import com.platform.mesh.gen.biz.modules.code.buildconf.constants.BuildConfConst;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.service.ICodeBuildConfDataService;
import com.platform.mesh.gen.biz.modules.code.ds.service.ICodeDataSourceService;
import com.platform.mesh.gen.biz.modules.code.field.domain.po.CodeFieldMapping;
import com.platform.mesh.gen.biz.modules.code.field.service.ICodeFieldService;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.bo.DbTableColumnBO;
import com.platform.mesh.gen.biz.modules.code.table.domain.dto.TableQueryDTO;
import com.platform.mesh.gen.biz.modules.code.table.domain.po.CodeTable;
import com.platform.mesh.gen.biz.modules.code.table.service.ICodeTableService;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.dto.TableColumnQueryDTO;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.domain.po.CodeTableColumn;
import com.platform.mesh.gen.biz.modules.code.tablecolunm.service.ICodeTableColumnService;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;
import com.platform.mesh.gen.biz.modules.code.temp.service.ICodeTemplateService;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.enums.GenGroupRelEnum;
import com.platform.mesh.gen.biz.modules.gen.grouprel.service.IGenGroupRelService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class CodeBuildServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CodeBuildServiceManual.class);

    @Autowired
    private ICodeBuildConfDataService codeBuildConfDataService;

    @Autowired
    private ICodeTableService codeTableService;

    @Autowired
    private ICodeFieldService codeFieldService;

    @Autowired
    private ICodeTableColumnService codeTableColumnService;

    @Autowired
    private ICodeTemplateService codeTemplateService;

    @Autowired
    private IGenGroupRelService genGroupRelService;

    @Autowired
    private ICodeTemplateService codeTempService;

    @Autowired
    private ICodeDataSourceService codeDataSourceService;
    /**
     * 功能描述:
     * 〈初始化代码生成〉
     * @param buildInitDTO buildInitDTO
     * @author 蝉鸣
     */
    public void buildInit(CodeBuildInitDTO buildInitDTO) {
        //清除旧数据
        List<CodeTable> tables = codeTableService.lambdaQuery().eq(CodeTable::getDsId,buildInitDTO.getDsId()).eq(CodeTable::getTableSchema, buildInitDTO.getTableSchema()).list();
        if(CollUtil.isNotEmpty(tables)){
            List<Long> tableIds = tables.stream().map(CodeTable::getId).toList();
            //删除表单
            codeTableService.lambdaUpdate().in(CodeTable::getId,tableIds).remove();
            //删除字段
            codeTableColumnService.lambdaUpdate().in(CodeTableColumn::getTableId,tableIds).remove();
            //删除组关系
            genGroupRelService.lambdaUpdate().eq(GenGroupRel::getGroupId,buildInitDTO.getGroupId())
                    .eq(GenGroupRel::getGroupType,GenGroupRelEnum.CODE_TEMP.getValue()).remove();
            genGroupRelService.lambdaUpdate().eq(GenGroupRel::getGroupId,buildInitDTO.getGroupId())
                    .eq(GenGroupRel::getGroupType,GenGroupRelEnum.CODE_TABLE.getValue()).remove();
        }
        //选择数据库，获取所有的表结构
        TableQueryDTO tableQueryDTO = BeanUtil.copyProperties(buildInitDTO, TableQueryDTO.class);
        List<DbTableBO> dbTableBOS = this.getDbTableList(tableQueryDTO);
        if(CollUtil.isEmpty(dbTableBOS)){
            return;
        }
        //设置配置
        List<CodeTable> codeTables = this.initCodeTable(buildInitDTO,dbTableBOS);
        //设置模板
        this.saveCodeTempGroupRel(buildInitDTO.getGroupId());
        //设置分组
        this.initCodeTableGroupRel(buildInitDTO.getGroupId(),codeTables);
        //设置字段
        TableColumnQueryDTO columnQueryDTO = BeanUtil.copyProperties(buildInitDTO, TableColumnQueryDTO.class);
        this.initCodeTableColumn(codeTables,columnQueryDTO);
    }


    /**
     * 保存生成table
     * @param tableQueryDTO tableQueryDTO
     */
    private List<DbTableBO> getDbTableList(TableQueryDTO tableQueryDTO){
        List<DbTableBO> dbTableBOs = CollUtil.newArrayList();
        Integer pageNum = NumberConst.NUM_1;
        tableQueryDTO.setPageSize(NumberConst.NUM_100);
        while(true){
            tableQueryDTO.setPageNum(pageNum);
            MPage<DbTableBO> tableBOMPage = codeDataSourceService.getDBTable(tableQueryDTO);
            if(CollUtil.isEmpty(tableBOMPage.getRecords())){
                break;
            }
            dbTableBOs.addAll(tableBOMPage.getRecords());
            pageNum++;
        }
        return dbTableBOs;
    }


    /**
     * 保存生成table
     * @param dbTableBOS dbTableBOS
     */
    private List<CodeTable> initCodeTable(CodeBuildInitDTO buildInitDTO,List<DbTableBO> dbTableBOS){
        List<CodeTable> codeTables = dbTableBOS.stream().map(item -> {
            CodeTable codeTable = BeanUtil.copyProperties(item, CodeTable.class, ObjFieldUtil.getFieldName(CodeTable::getId));
            codeTable.setDsId(buildInitDTO.getDsId());
            codeTable.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(item.getTableName())));
            String rootModuleName = item.getTableName().substring(NumberConst.NUM_0,item.getTableName().indexOf(SymbolConst.UNDERLINE));
            codeTable.setRootModuleName(rootModuleName);
            codeTable.setPackageName(buildInitDTO.getPackageName());
            String moduleName = item.getTableName().substring(item.getTableName().indexOf(SymbolConst.UNDERLINE) + NumberConst.NUM_1);
            codeTable.setModuleName(StrUtil.toCamelCase(moduleName));
            codeTable.setModuleDesc(codeTable.getTableComment().replace("表",StrUtil.EMPTY));
            return codeTable;
        }).toList();
        codeTableService.saveBatch(codeTables);
        return codeTables;
    }

    /**
     * 保存生成模板 分组关系
     */
    private void saveCodeTempGroupRel(Long groupId){
        //获取所有的表ID
        List<CodeTemplate> templateList = codeTempService.selectAllTempList();
        //设置分组
        List<GenGroupRel> groupRels = templateList.stream().map(item -> {
            GenGroupRel genGroupRel = new GenGroupRel();
            genGroupRel.setGroupType(GenGroupRelEnum.CODE_TEMP.getValue());
            genGroupRel.setGroupId(groupId);
            genGroupRel.setDataId(item.getId());
            return genGroupRel;
        }).toList();
        genGroupRelService.saveBatch(groupRels);
    }

    /**
     * 保存生成table 分组关系
     * @param codeTables codeTables
     */
    private void initCodeTableGroupRel(Long groupId,List<CodeTable> codeTables){
        //获取所有的表ID
        List<Long> tableIds = codeTables.stream().map(CodeTable::getId).toList();
        //设置分组
        List<GenGroupRel> groupRels = tableIds.stream().map(item -> {
            GenGroupRel genGroupRel = new GenGroupRel();
            genGroupRel.setGroupType(GenGroupRelEnum.CODE_TABLE.getValue());
            genGroupRel.setGroupId(groupId);
            genGroupRel.setDataId(item);
            return genGroupRel;
        }).toList();
        genGroupRelService.saveBatch(groupRels);
    }

    /**
     * 保存生成table 字段
     * @param codeTables codeTables
     */
    private void initCodeTableColumn(List<CodeTable> codeTables,TableColumnQueryDTO columnQueryDTO){
        List<String> tableNameList = codeTables.stream().map(CodeTable::getTableName).toList();
        if(CollUtil.isEmpty(tableNameList)){
            return;
        }
        columnQueryDTO.setTableNames(tableNameList);
        Map<String, CodeTable> codeTableMap = codeTables.stream().collect(Collectors.toMap(CodeTable::getTableName, Function.identity(), (v1, v2) -> v2));
        //获取字段映射
        List<CodeFieldMapping> fieldMappings = codeFieldService.getAllFieldMappingList();
        Map<String, CodeFieldMapping> mappingMap = fieldMappings.stream().collect(Collectors.toMap(CodeFieldMapping::getColumnFlag, Function.identity(), (v1, v2) -> v2));
        Integer pageNum = NumberConst.NUM_1;
        while (true){
            columnQueryDTO.setPageNum(pageNum);
            columnQueryDTO.setPageSize(NumberConst.NUM_100);
            //选择表结构，获取所有的表字段
            MPage<DbTableColumnBO> dbTableColumnBOS = codeDataSourceService.getDBTableColumn(columnQueryDTO);
            if(CollUtil.isEmpty(dbTableColumnBOS.getRecords())){
                break;
            }
            List<CodeTableColumn> codeTableColumns = dbTableColumnBOS.getRecords().stream().map(item -> {
                CodeTableColumn codeTableColumn = BeanUtil.copyProperties(item, CodeTableColumn.class, ObjFieldUtil.getFieldName(CodeTableColumn::getId));
                CodeTable codeTable = codeTableMap.get(item.getTableName());
                codeTableColumn.setTableId(codeTable.getId());
                codeTableColumn.setColumnFlag(item.getDataType());
                CodeFieldMapping codeFieldMapping = mappingMap.get(item.getDataType());
                if(ObjectUtil.isEmpty(codeFieldMapping)){
                    log.info(String.valueOf(codeFieldMapping));
                }
                codeTableColumn.setFieldFlag(codeFieldMapping.getFieldFlag());
                codeTableColumn.setFieldName(StrUtil.toCamelCase(item.getColumnName()));
                return codeTableColumn;
            }).toList();
            codeTableColumnService.saveBatch(codeTableColumns);
            pageNum++;
        }
    }

    /**
     * 获取模板对象
     */
    public CodeTemplate getTempById(Long tempId){
        //获取模板对象
        return codeTempService.getById(tempId);
    }

    /**
     * 获取数据模型方法
     * step:
     * 1:根据codeBuildId查询codeBuildDataConf->并将结构处理成map<confCode,confValue>
     * 2:根据tableId 查询 codeTable(业务表)信息
     * 3：将codeTable 实际的包路径，包模块名称等存入到 map模板对象中
     * 4：step1中的模板对象value的${param}进行渲染，重新保存
     * @param tableId 表格 ID
     * @return 数据模型 Map 对象
     */
    public Map<String, Object> getDataModel(Long buildId, Long tableId) {
        // 获取构造信息
        Map<String, Object> dataModel = codeBuildConfDataService.getConfDataMapByBuildId(buildId);
        // 获取表格信息
        CodeTable codeTable = codeTableService.getById(tableId);
        if(ObjectUtil.isEmpty(codeTable)){
            return new HashMap<>();
        }
        // 创建数据模型对象
        Map<String, Object> tabelMap = BeanUtil.beanToMap(codeTable);
        dataModel.putAll(tabelMap);
        // 获取字段信息
        List<CodeTableColumn> columns = codeTableColumnService.getListByTableId(tableId);
        // 获取字段映射信息
        List<CodeFieldMapping> fieldMappings = codeFieldService.getAllFieldMappingList();
        // 转换Map
        Map<String, CodeFieldMapping> mappingMap = fieldMappings.stream().filter(item->ObjectUtil.isNotEmpty(item.getPackageName()))
                .collect(Collectors.toMap(CodeFieldMapping::getFieldFlag, Function.identity(), (v1, v2) -> v2));
        // 获取字段需要额外导入的包
        List<String> importList = columns.stream().filter(item->mappingMap.containsKey(item.getFieldFlag()))
                .map(item -> mappingMap.get(item.getFieldFlag()).getPackageName()).distinct().toList();
        // 填充数据模型：模板里面尽量使用变量，宏，方法等可能因版本问题报错
        dataModel.put(BuildConfConst.PACKAGE_PATH, codeTable.getPackageName().replace(SymbolConst.PERIOD, File.separator));
        dataModel.put(BuildConfConst.PACK_MODULE_NAME, codeTable.getModuleName().toLowerCase());
        dataModel.put(BuildConfConst.MAPPING_PATH, codeTable.getTableName().replace(SymbolConst.UNDERLINE, SymbolConst.FORWARD_SLASH));
        dataModel.put(BuildConfConst.IMPORT_LIST, importList);
        dataModel.put(BuildConfConst.COLUMNS, columns);
        dataModel.put(BuildConfConst.UPPER_MODULE_NAME, StrUtil.upperFirst(codeTable.getModuleName()));
        dataModel.put(BuildConfConst.LOW_MODULE_NAME, StrUtil.lowerFirst(codeTable.getModuleName()));
        dataModel.put(BuildConfConst.UPPER_CLASS_NAME, StrUtil.upperFirst(codeTable.getClassName()));
        dataModel.put(BuildConfConst.LOW_CLASS_NAME, StrUtil.lowerFirst(codeTable.getClassName()));
        // 转换数据
        Map<String, Object> newMap = new HashMap<>();
        Pattern pattern = Pattern.compile(SymbolConst.PATTERN_EL);
        dataModel.forEach((key,value)->{
            if(ObjectUtil.isNotEmpty(value)){
                Matcher matcher = pattern.matcher(value.toString());
                if (matcher.find()) {
                    newMap.put(key,this.renderStr(value.toString(), dataModel));
                }
            }
        });
        //将替换后的值放入原map
        dataModel.putAll(newMap);
        return dataModel;
    }

    /**
     * 功能描述:
     * 〈查询表信息并生成代码〉
     * @param codeBuild codeBuild
     * @param groupIds groupIds
     * @param tableId tableId
     * @param zip zip
     * @author 蝉鸣
     */
    public void buildZip(CodeBuild codeBuild, List<Long> groupIds, Long tableId, ZipOutputStream zip) {
        // 数据模型
        Map<String, Object> dataModel = this.getDataModel(codeBuild.getId(),tableId);

        // 获取模板列表
        List<CodeTemplate> templateList = codeTemplateService.getTemplateListByGroupId(groupIds);
        for (CodeTemplate template : templateList) {

            String templateCode = template.getTemplateCode();
            String buildPath = template.getBuildPath();

            String content = this.renderStr(templateCode, dataModel);
            String path = this.renderStr(buildPath, dataModel);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(path));
                IoUtil.writeUtf8(zip, false, content);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e) {
                log.error(BuildExceptionEnum.BUILD_CODE_ERROR.getDesc(), path, e);
            }
        }
    }

    /**
     * 渲染文本
     * @param str 模板
     * @param dataModel 模板数据
     * @return 渲染后的模板
     */
    public String renderStr(String str, Map<String, Object> dataModel) {
        // 设置velocity资源加载器
        Velocity.init();
        StringWriter stringWriter = new StringWriter();
        VelocityContext context = new VelocityContext(dataModel);
        // 函数库
        Velocity.evaluate(context, stringWriter, BuildConst.Velocity_LOG_TAG, str);
        return stringWriter.toString();
    }

    public List<Long> selectAllTableList() {
        List<CodeTable> codeTables = codeTableService.list();
        return codeTables.stream().map(CodeTable::getId).toList();
    }

}

