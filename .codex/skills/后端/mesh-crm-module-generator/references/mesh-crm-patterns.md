# mesh-crm-biz 动态业务模块生成规范

本参考用于在 `services/mesh-crm/mesh-crm-biz` 中生成类似 `oncontract` 的动态业务模块，目标是让新业务从数据库表、MyBatis Mapper、App 动态服务、ES 查询到 Controller 接口都能执行。

## 根路径

- Biz Java 根目录：`services/mesh-crm/mesh-crm-biz/src/main/java`
- Biz 包根路径：`com.platform.mesh.crm.biz`
- 业务模块包根路径：`com.platform.mesh.crm.biz.modules`
- API 模块根路径：`services/mesh-crm/mesh-crm-api/src/main/java/com/platform/mesh/crm/api/modules`

用户可能会口头描述为 `com.platform.mesh.crm.modules`；本仓库真实 biz 包名必须包含 `.biz`：`com.platform.mesh.crm.biz.modules`。

## 参考案例

主参考案例使用 `crm/oncontract` 与 `crm/oncontractdata`：

- 主表：`crm_on_contract`
- Data 表：`crm_on_contract_data`
- 主包：`crm/oncontract`
- Data 包：`crm/oncontractdata`
- SQL 文件目录：`crm/oncontract/sql`
- 主 PO：`crm/oncontract/domain/po/CrmOnContract.java`，继承 `AppPO`
- 主 VO：`crm/oncontract/domain/vo/CrmOnContractVO.java`，继承 `AppVO`
- Data PO：`crm/oncontractdata/domain/po/CrmOnContractData.java`，继承 `AppDataPO`
- Controller：`crm/oncontract/controller/CrmOnContractController.java`
- 主 Service：`crm/oncontract/service/ICrmOnContractService.java`
- 主 ServiceImpl：`crm/oncontract/service/impl/CrmOnContractServiceImpl.java`
- Manual：`crm/oncontract/service/manual/CrmOnContractServiceManual.java`
- 主 Mapper/XML：`crm/oncontract/mapper/CrmOnContractMapper.java` 与 `mapper/xml/CrmOnContractMapper.xml`
- Data Service：`crm/oncontractdata/service/ICrmOnContractDataService.java`
- Data ServiceImpl：`crm/oncontractdata/service/impl/CrmOnContractDataServiceImpl.java`
- Data Mapper/XML：`crm/oncontractdata/mapper/CrmOnContractDataMapper.java` 与 `mapper/xml/CrmOnContractDataMapper.xml`

## 成对包结构

每个新动态业务默认生成两套 Java 包，并在主包中生成 SQL 目录。以 `<business>`、`<entityFolder>`、`<ClassName>`、`<tableName>` 为变量：

```text
modules/<business>/<entityFolder>/
  controller/<ClassName>Controller.java
  sql/<tableName>.sql
  domain/po/<ClassName>.java
  domain/vo/<ClassName>VO.java
  exception/<ClassName>ExceptionEnum.java
  mapper/<ClassName>Mapper.java
  mapper/xml/<ClassName>Mapper.xml
  service/I<ClassName>Service.java
  service/impl/<ClassName>ServiceImpl.java
  service/manual/<ClassName>ServiceManual.java

modules/<business>/<entityFolder>data/
  domain/po/<ClassName>Data.java
  exception/<ClassName>DataExceptionEnum.java
  mapper/<ClassName>DataMapper.java
  mapper/xml/<ClassName>DataMapper.xml
  service/I<ClassName>DataService.java
  service/impl/<ClassName>DataServiceImpl.java
```

`sql` 文件夹与 `controller` 文件夹平级，放在主业务包下。`sql/<tableName>.sql` 必须同时包含主 PO 对应主表和 Data PO 对应 Data 表的建表 SQL，例如 `crm/oncontract/sql/crm_on_contract.sql` 中包含 `crm_on_contract` 和 `crm_on_contract_data` 两张表。

DTO 通常使用 App 通用 DTO：`DataAddSimpDTO`、`DataAddCompDTO`、`DataEditSimpDTO`、`DataDelDTO`、`DataImportDTO`、`TransScopeDTO`、`EsDocPGetDTO`、`EsDocSGetDTO`、`EsDocEGetDTO`。只有 PRD 明确要求专用 DTO 时才新增 `domain/dto`。

## 命名规则

- `<business>` 使用小写，如 `crm`、`edu`、`fms`。
- `<entityFolder>` 使用压缩小写，如 `oncontract`。
- Data 包名为 `<entityFolder>data`，如 `oncontractdata`。
- Java 类名为业务前缀大驼峰加实体名，如 `CrmOnContract`。
- Data 类名为 `<ClassName>Data`，如 `CrmOnContractData`。
- 主表通常为 `<business>_<snake_entity>`，如 `crm_on_contract`。
- Data 表通常为 `<tableName>_data`，如 `crm_on_contract_data`。
- SQL 文件通常为 `<tableName>.sql`，如 `crm_on_contract.sql`。
- Controller 路由通常为业务、实体、动作分段，如 `/crm/on/contract/page`、`/crm/on/contract/add/simp`、`/crm/on/contract/edit`。
- ExceptionEnum 的 module 字符串优先使用表名，如 `crm_on_contract`；若附近模块使用 kebab-case，则跟随附近模块。

## 主 PO

主 PO 继承 `AppPO`，写业务专有字段：

```java
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "<tableName>", autoResultMap = true)
public class <ClassName> extends AppPO {
    // 业务专有字段写在这里
}
```

字段生成规则：

- PRD 中的固定业务字段放在主 PO，例如合同的 `customerId`、`businessId`、`totalMoney`。
- 金额字段使用 `BigDecimal`，如有默认值需求可初始化为 `BigDecimal.ZERO`。
- 时间字段优先使用 `LocalDateTime`。
- 动态表单字段、导入 Excel 的扩展字段不要写入主 PO，除非需要在 DB 主表固定持久化或被业务逻辑频繁使用。
- 主 PO 字段必须能在主表 SQL 中找到对应 snake_case 字段。

## 主 VO

主 VO 继承 `AppVO`，字段与接口展示需求一致：

```java
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "<moduleDesc>VO")
public class <ClassName>VO extends AppVO {
    // 对外展示的主表业务字段
}
```

## Data PO

Data PO 继承 `AppDataPO`，对应 `<tableName>_data`：

```java
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "<tableName>_data", autoResultMap = true)
public class <ClassName>Data extends AppDataPO {
}
```

默认不要在 Data PO 中重复写主表业务字段。Data 表承载 App 动态字段数据，通常只需要继承 `AppDataPO`。

## SQL 文件

每个动态业务必须生成建表 SQL，路径为：

`modules/<business>/<entityFolder>/sql/<tableName>.sql`

SQL 文件包含两张表：

- 主 PO 对应主表：`<tableName>`
- Data PO 对应 Data 表：`<tableName>_data`

主表字段要求：

- 必须包含 `AppPO` 所需的通用字段：`id`、`module_id`、`data_type`、`data_mac`、`data_name`、`data_desc`、`data_serial`、`data_period`、`del_flag`、`create_user_id`、`create_time`、`update_user_id`、`update_time`、`scope_user_id`、`scope_org_id`、`tenant_id`。
- 必须包含主 PO 中声明的业务专有字段，例如 `customer_id`、`business_id`、`total_money`。
- 主键使用 `PRIMARY KEY (id) USING BTREE`。
- 建议给 `create_time` 建索引：`KEY CREATE_TIME (create_time) USING BTREE COMMENT '创建时间'`。
- 表注释使用 PRD 中的中文业务名称，主表建议以“表”结尾。

Data 表字段要求：

- 必须包含 `AppDataPO` 所需字段：`id`、`parent_module_id`、`module_id`、`add_form_id`、`edit_form_id`、`data_id`、`column_id`、`column_mac`、`column_name`、`data_value`、`data_type`、`del_flag`、`create_user_id`、`create_time`、`update_user_id`、`update_time`、`scope_user_id`、`scope_org_id`、`tenant_id`。
- 表名固定为主表名追加 `_data`。
- 表注释使用“<业务名称>数据表”。

`oncontract` SQL 样例：

```sql
CREATE TABLE `crm_on_contract` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint DEFAULT NULL COMMENT '客户ID',
  `business_id` bigint DEFAULT NULL COMMENT '商机ID',
  `data_type` int NOT NULL DEFAULT '1' COMMENT '合同类型ContractTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '合同标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同描述',
  `data_serial` int DEFAULT NULL COMMENT '客户序列号',
  `data_period` int DEFAULT NULL COMMENT '合同期数',
  `total_money` decimal(10,2) DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10,2) DEFAULT NULL COMMENT '实际金额',
  `cost_money` decimal(10,2) DEFAULT NULL COMMENT '成本金额',
  `received_money` decimal(10,2) DEFAULT NULL COMMENT '已收金额',
  `unreceived_money` decimal(10,2) DEFAULT NULL COMMENT '未收金额',
  `invoice_money` decimal(10,2) DEFAULT NULL COMMENT '开票金额',
  `profit_money` decimal(10,2) DEFAULT NULL COMMENT '利润金额',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `inst_process_id` bigint DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int DEFAULT NULL COMMENT '通过状态',
  `del_flag` int DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint DEFAULT NULL COMMENT '组织ID',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `CREATE_TIME` (`create_time`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='客户关系合同签订表';

CREATE TABLE `crm_on_contract_data` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '数据值',
  `data_type` int DEFAULT '1' COMMENT '数据类型DataTypeEnum',
  `del_flag` int DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint DEFAULT NULL COMMENT '组织ID',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='客户关系合同签订数据表';
```

生成新业务 SQL 时，按 PRD 替换表名、字段和注释，不要直接复用合同字段。

## Controller

Controller 继承 `BaseController`，只注入当前主 Service。动态业务默认提供以下接口：

- `POST /<mappingPath>/page`：使用 `EsDocPGetDTO` 调用 `selectEsPage`
- `POST /<mappingPath>/info`：使用 `EsDocSGetDTO` 调用 `getDataInfoById`
- `POST /<mappingPath>/add/simp`：使用 `DataAddSimpDTO` 调用 `addDataSimp`
- `POST /<mappingPath>/add/comp`：PRD 需要复杂新增时，使用 `DataAddCompDTO` 调用 `addDataComp`
- `POST /<mappingPath>/edit`：使用 `DataEditSimpDTO` 调用 `editData`
- `POST /<mappingPath>/delete/{id}`：单删，调用 `deleteData(id)`
- `POST /<mappingPath>/batch/delete`：批量删除，使用 `DataDelDTO`
- `POST /<mappingPath>/trans/scope`：转移数据权限，使用 `TransScopeDTO`
- `POST /<mappingPath>/import/temp`：导出导入模板，使用 `ExcelUtil.exportTemp`
- `POST /<mappingPath>/import`：导入，构建 `DataImportDTO`
- `POST /<mappingPath>/export`：导出，使用 `ExcelUtil.exportData`

Controller 需要导入主 PO、主 VO、主 Service、Data PO。新增、编辑、删除、导入、导出接口按附近模块习惯添加 `@Log`。

### Controller 方法 JavaDoc 模板

Controller 中每个接口方法都必须在 `@Operation` 前生成方法级 JavaDoc，不能省略。注释需要包含功能描述、所有入参、返回类型说明和作者，保持与现有业务模块一致。

分页接口模板：

```java
    /**
     * 功能描述:
     * 〈获取<moduleDesc>列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<Object>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取<moduleDesc>分页")
    @PostMapping("/<mappingPath>/page")
```

详情接口模板：

```java
    /**
     * 功能描述:
     * 〈获取当前<moduleDesc>信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Result<<ClassName>VO>}
     * @author 蝉鸣
     */
```

新增、编辑、删除、导入导出接口也必须按同样格式生成：

- 新增简版：`〈新增<moduleDesc>〉`，`@param dataAddSimpDTO dataAddSimpDTO`，`@return 正常返回:{@link Result<<ClassName>VO>}`
- 新增完整版：`〈新增<moduleDesc>〉`，`@param dataAddCompDTO dataAddCompDTO`，`@return 正常返回:{@link Result<<ClassName>VO>}`
- 编辑：`〈修改<moduleDesc>〉`，`@param dataEditDTO dataEditDTO`，`@return 正常返回:{@link Result<<ClassName>VO>}`
- 单删：`〈删除<moduleDesc>〉`，`@param dataId dataId`，`@return 正常返回:{@link Result<Boolean>}`
- 批量删除：`〈批量删除<moduleDesc>〉`，`@param delDTO delDTO`，`@return 正常返回:{@link Result<Boolean>}`
- 转移：`〈转移<moduleDesc>〉`，`@param transScopeDTO transScopeDTO`，`@return 正常返回:{@link Result<Boolean>}`
- 导入模板：`〈导入<moduleDesc>模板〉`，`@param headDTOS headDTOS`
- 导入：`〈导入<moduleDesc>〉`，包含 `moduleId`、`formId`、`file` 参数，`@return 正常返回:{@link Result<ImportVO>}`
- 导出：`〈导出<moduleDesc>〉`，包含 `exportDTO` 和 `response` 参数

## 主 Service

主 Service 继承 `IAppService<主PO>`：

```java
import com.platform.mesh.app.api.modules.app.service.IAppService;
import <package>.domain.po.<ClassName>;

public interface I<ClassName>Service extends IAppService<<ClassName>> {
    // PRD 明确的业务方法写在这里
}
```

如果 PRD 涉及金额同步、状态流转、跨模块初始化等，像 `ICrmOnContractService` 一样增加明确方法，例如 `updateReceivedMoney`、`initFmsReceivable`。

Service 接口中只要新增显式业务方法，必须写方法级 JavaDoc，格式与 Controller 一致：包含“功能描述”、所有 `@param`、非 void 方法的 `@return 正常返回:{@link ...}` 和 `@author 蝉鸣`。

## 主 ServiceImpl

主 ServiceImpl 继承 `AppServiceAbstract<Mapper, 主PO>`：

```java
import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class <ClassName>ServiceImpl extends AppServiceAbstract<<ClassName>Mapper, <ClassName>> implements I<ClassName>Service {

    @Autowired
    private <ClassName>ServiceManual <lClassName>ServiceManual;

    /**
     * 功能描述:
     * 〈新增<moduleDesc>动态字段数据〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<<ClassName>Data> dataPOList = BeanUtil.copyToList(dataList, <ClassName>Data.class);
        <lClassName>ServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 〈转移<moduleDesc>数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(<ClassName>::getScopeUserId, scopeUserId)
                .set(<ClassName>::getScopeOrgId, scopeOrgId)
                .in(<ClassName>::getId, dataIds)
                .update();
    }
}
```

常见覆写点：

- `selectEsPage`：增加聚合字段或列表 VO 解析。
- `addOtherAction`：新增主表后补充业务字段、保存子表、初始化跨模块数据。
- `getOtherAction`：详情返回时补充子表或扩展数据。
- `delOtherAction`：删除主表时清理子表、关联表或同步外部状态。
- `addDbRelBatch`：存在关系表时保存关联数据。

## Manual

Manual 使用 `@Service`，由主 ServiceImpl 注入。Manual 可以注入 DataService 和其他模块 Service，但不要注入当前主 Service。

Data 表保存必须先删旧数据再保存新数据：

```java
/**
 * 功能描述:
 * 〈DB Data 数据批量保存〉
 * @param dataList dataList
 * @author 蝉鸣
 */
public void addDbDataBatch(List<<ClassName>Data> dataList) {
    if (CollUtil.isEmpty(dataList)) {
        return;
    }
    <ClassName>Data data = CollUtil.getFirst(dataList);
    <lClassName>DataService.lambdaUpdate().eq(<ClassName>Data::getDataId, data.getDataId()).remove();
    <lClassName>DataService.saveBatch(dataList);
}
```

跨模块逻辑、子表保存、金额计算、ES 返回解析等辅助逻辑放在 Manual 中。

Manual 中所有 public 方法必须写方法级 JavaDoc。若有返回值，必须补充 `@return 正常返回:{@link ...}`；若是 void 方法则不写 `@return`。

## Data Service

Data Service 继承 `IAppDataService<DataPO>`：

```java
import com.platform.mesh.app.api.modules.app.service.IAppDataService;
import <dataPackage>.domain.po.<ClassName>Data;

public interface I<ClassName>DataService extends IAppDataService<<ClassName>Data> {
}
```

Data ServiceImpl 继承 `AppDataServiceAbstract<DataMapper, DataPO>`：

```java
import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class <ClassName>DataServiceImpl extends AppDataServiceAbstract<<ClassName>DataMapper, <ClassName>Data> implements I<ClassName>DataService {
}
```

## Mapper 与 XML

主 Mapper：

```java
public interface <ClassName>Mapper extends BaseMapper<<ClassName>> {
}
```

主 XML：

```xml
<mapper namespace="com.platform.mesh.crm.biz.modules.<business>.<entityFolder>.mapper.<ClassName>Mapper">
</mapper>
```

Data Mapper：

```java
public interface <ClassName>DataMapper extends BaseMapper<<ClassName>Data> {
}
```

Data XML：

```xml
<mapper namespace="com.platform.mesh.crm.biz.modules.<business>.<entityFolder>data.mapper.<ClassName>DataMapper">
</mapper>
```

如需自定义 SQL，例如统计客户金额，可在主 Mapper 中声明方法并在主 XML 中实现。

## Exception

主异常枚举和 Data 异常枚举都实现 `BaseExceptionEnum`。默认错误项：

- `ADD_NO_ARGS`
- `ADD_NO_INVALID`

`module` 字段默认使用对应表名：主异常用 `<tableName>`，Data 异常用 `<tableName>_data`。

## 数据库到接口可执行清单

生成新业务后必须检查：

- 主包中存在 `sql/<tableName>.sql`，且包含主表和 Data 表两段建表 SQL。
- 数据库存在主表 `<tableName>` 和 Data 表 `<tableName>_data`，字段满足 `AppPO` / `AppDataPO` 继承结构需要。
- 主 PO 和 Data PO 的 `@TableName` 与真实表名一致。
- 主 PO 业务字段与主表 SQL 字段一致。
- Mapper XML namespace 与 Mapper 接口全限定名一致。
- 主 ServiceImpl 实现 `addDbDataBatch`，保存动态字段到 Data 表。
- 主 ServiceImpl 实现 `transDbScopeBatch`，同步主表权限字段。
- Manual 中 Data 表保存按 `dataId` 删除旧数据后 `saveBatch`。
- Controller 新增接口传入主 PO class 和 Data PO class，例如 `addDataSimp(dto, <ClassName>.class, <ClassName>Data.class)`。
- Controller 编辑、导入接口同样传入主 PO class 和 Data PO class。
- ES 分页、详情、导出使用 `EsDocPGetDTO`、`EsDocSGetDTO`、`EsDocEGetDTO`。
- 单删、批量删除、转移接口调用 `deleteData`、`transData`。
- 新增业务如需同步客户、财务、审批或子表，已在 `addOtherAction`、`delOtherAction`、Manual 中实现。

## 校验命令

优先执行：

```powershell
rg "<ClassName>|<ClassName>Data|<tableName>|<tableName>_data|/<mappingPath>" services/mesh-crm
rg "CREATE TABLE `<tableName>`|CREATE TABLE `<tableName>_data`" services/mesh-crm/mesh-crm-biz/src/main/java
mvn -pl services/mesh-crm/mesh-crm-biz -am compile -DskipTests
```

如果完整编译过慢或依赖不可用，至少人工检查所有新增 Java/XML/SQL 文件的 package、import、class、route、namespace、`@TableName`、Service 泛型和建表字段是否一致。
