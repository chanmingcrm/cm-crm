---
name: mesh-crm-module-generator
description: 根据 PRD 需求文档、数据库表或业务规格生成 mesh-crm-biz 动态业务模块。适用于需要在 com.platform.mesh.crm.biz.modules 下创建类似 oncontract/oncontractdata 的成对业务包，生成 AppPO/AppDataPO 实体、Controller、Service、Manual、Mapper、XML、Exception、ES 查询、导入导出接口，并确保从数据库表到接口链路可执行的场景。
---

# Mesh CRM 模块生成器

## 概述

用于根据 PRD 需求文档或数据库表定义生成 `mesh-crm-biz` 业务模块代码。新业务默认采用 `oncontract` 模式：一个主业务包对应主表，一个 `data` 业务包对应动态字段数据表，例如 `oncontract` 对应 `crm_on_contract`，`oncontractdata` 对应 `crm_on_contract_data`。

## 工作流程

1. 确认仓库根目录存在 `services/mesh-crm/mesh-crm-biz`。
2. 从 PRD、SQL、表结构或用户说明中提取业务包、实体名、表名、字段、接口、状态和业务规则。
3. 阅读 `references/mesh-crm-patterns.md`，并以 `crm/oncontract` 与 `crm/oncontractdata` 作为主要参考。
4. 为每个业务实体生成两套包：
   - 主业务包：`modules/<business>/<entity>`，例如 `modules/crm/oncontract`。
   - 数据业务包：`modules/<business>/<entity>data`，例如 `modules/crm/oncontractdata`。
5. 主 PO 继承 `AppPO`，写入业务专有字段；主 VO 继承 `AppVO`，暴露接口需要返回的业务字段；Data PO 继承 `AppDataPO`，通常不写业务字段。
6. Controller 使用 App 动态模块接口风格，覆盖分页、详情、新增、编辑、删除、批量删除、转移、导入模板、导入、导出；每个接口方法必须生成完整 JavaDoc，包含“功能描述”、`@param`、`@return 正常返回:{@link ...}` 和 `@author 蝉鸣`，格式参照 `references/mesh-crm-patterns.md` 的 Controller 方法注释模板。
7. Service 使用 `IAppService` / `AppServiceAbstract`；DataService 使用 `IAppDataService` / `AppDataServiceAbstract`；Service 接口自定义方法、ServiceImpl 重写方法、DataService 方法都必须生成完整 JavaDoc。
8. Manual 负责主表与 Data 表同步、子表/关联表保存、跨模块辅助逻辑；不要注入当前主 Service；Manual 中每个 public 方法必须生成完整 JavaDoc。
9. 生成或检查数据库表、Mapper XML namespace、路由、类名、包名和导入，确保从数据库到接口链路可执行。

## PRD 与表结构提取

优先从 PRD 需求文档中提取结构化的业务模块、业务流程、字段、接口和验收规则。保留 PRD 中的中文业务名称，用于 JavaDoc、Swagger 注解和日志模块名。

需要提取：

- 业务包编码，例如 `crm`、`edu`、`fms`。
- 实体英文名、实体中文名、接口路径分段。
- 主表名和 Data 表名；若只给主表名，则 Data 表默认追加 `_data`。
- 主表业务字段、Java 类型、默认值、是否需要 BigDecimal 初始化。
- 动态字段、导入导出字段、ES 查询字段、聚合字段。
- 新增、编辑、删除、转移、导入、导出、状态流转和跨模块同步规则。

## 生成原则

- 新动态业务默认必须生成主包和 Data 包，不能只生成主包。
- 主表业务字段写在 `<ClassName>`，例如 `CrmOnContract`。
- Data 表实体写在 `<ClassName>Data`，继承 `AppDataPO`，对应 `<tableName>_data`。
- VO 继承 `AppVO`，字段与接口需要展示的主表业务字段保持一致。
- Controller 只注入当前主 Service；主 Service 再通过 Manual 处理 Data 表和其他模块。
- Controller 每个对外接口方法都必须带完整方法 JavaDoc，不能只写 `@Operation`。JavaDoc 内容要和接口语义一致，例如分页接口使用“获取<模块中文名>列表”，返回说明写 `正常返回:{@link Result<PageVO<Object>>}`。
- Service、ServiceImpl、DataService、Manual 中所有显式声明的方法都必须带方法级 JavaDoc，包含“功能描述”、所有 `@param`、非 void 方法的 `@return` 和 `@author 蝉鸣`；不能只依赖类级注释。
- 主 Service 必须实现 `addDbDataBatch`，把 `AppDataPO` 转为 `<ClassName>Data` 并保存到 Data 表。
- 主 Service 必须实现 `transDbScopeBatch`，同步主表的数据权限字段。
- Manual 中保存 Data 表时，先按 `dataId` 删除旧数据，再批量保存新数据。
- 若存在关联表、子表、金额同步、审批通过后动作等需求，在 ServiceImpl 中覆写 `addOtherAction`、`getOtherAction`、`delOtherAction` 或增加明确业务方法。
- 生成后至少做 `rg` 检查；可行时编译 `mesh-crm-biz`。

## 实施检查

编辑前搜索冲突：

- `rg "<ClassName>|<ClassName>Data|/<business>/<route>" services/mesh-crm`
- `rg "<table_name>|<table_name>_data" services/mesh-crm`
- `rg "modules/<business>/<entity>" services/mesh-crm/mesh-crm-biz/src/main/java`

编辑后确认：

- 主包和 Data 包都存在，且 package 与路径一致。
- 主表 `@TableName` 指向 `<tableName>`，Data 表 `@TableName` 指向 `<tableName>_data`。
- 主 PO 继承 `AppPO`，VO 继承 `AppVO`，Data PO 继承 `AppDataPO`。
- 主 Service 继承 `IAppService`，主 ServiceImpl 继承 `AppServiceAbstract`。
- DataService 继承 `IAppDataService`，DataServiceImpl 继承 `AppDataServiceAbstract`。
- Controller 使用 `DataAddSimpDTO` / `DataEditSimpDTO` / `DataDelDTO` / `EsDoc*GetDTO` 等 App 动态模块 DTO。
- XML namespace 完全匹配 Mapper 全限定名。
- 路由不重复，导入导出接口、ES 分页和详情接口可调用。

## 参考资料

生成代码前阅读 `references/mesh-crm-patterns.md`。该文件记录了 `oncontract` / `oncontractdata` 案例、代码模板映射、包结构、基类和校验清单。
