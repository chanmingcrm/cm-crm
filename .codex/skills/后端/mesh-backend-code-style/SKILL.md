---
name: mesh-backend-code-style
description: 当在 mesh-platform 仓库中实现、修改或评审后端 Java 代码时使用本 Skill，尤其适用于 services/mesh-crm/mesh-crm-biz 下 com.platform.mesh.crm.biz.modules 动态业务模块、生成的 edu/crm 模块、Controller、Service、ServiceImpl、ServiceManual、DTO/VO/PO、Mapper、枚举、导入导出接口，以及所有需要遵循本项目后端编码风格的功能开发。
---

# Mesh 后端代码规范

## 核心流程

1. 修改前先阅读目标模块：Controller、Service 接口、ServiceImpl、Manual、PO/VO/DTO、Mapper、XML，以及相邻同类模块。
2. 优先复用现有生成代码风格，不引入不必要的新抽象。改动范围只围绕用户需求和当前模块。
3. 保持生成代码的 CRUD、导入、导出方法顺序。新增自定义方法追加在已有生成方法后面，不要穿插在标准方法中间。
4. 可复用的转换、过滤、校验、解析、业务辅助逻辑放到 `service/manual/*ServiceManual.java`。
5. 后端改动完成后，条件允许时执行编译验证：

```powershell
mvn -pl services/mesh-crm/mesh-crm-biz -am -DskipTests compile
```

## 分层职责

- Controller 只注入当前模块主 Service。除非本地已有明确模式，否则不要在 Controller 中注入 Manual、Mapper 或其他模块 Service。
- Service 接口只声明当前 ServiceImpl 要实现的业务 API。新增声明追加在接口末尾。
- ServiceImpl 只实现当前 Service 接口相关方法，以及基类要求重写的方法。不要在 ServiceImpl 中保留私有工具方法、转换方法或复用封装逻辑。
- Manual 负责辅助逻辑、跨模块辅助、DB Data 同步、过滤、校验、转换、解析等可复用逻辑。
- Manual 不要注入当前主 Service，避免循环依赖。Manual 可以按需注入 DataService 或其他模块 Service。
- Mapper/XML 只在 lambda query 或已有基础能力无法安全表达查询时修改。

## 生成模块约定

- 动态 CRM 模块保持主包和 data 包成对出现：
  - 主业务包：`modules/<business>/<entity>`
  - 数据包：`modules/<business>/<entity>data`
- 主 PO 继承 `AppPO`，VO 继承 `AppVO`，Data PO 继承 `AppDataPO`。
- 主 Service 继承 `IAppService<T>`，ServiceImpl 继承 `AppServiceAbstract<Mapper, T>`。
- Data Service 继承 `IAppDataService<T>`，Data ServiceImpl 继承 `AppDataServiceAbstract<Mapper, T>`。
- 生成模块的主 ServiceImpl 按基类要求实现 `addDbDataBatch` 和 `transDbScopeBatch`。
- DB Data 保存通过 Manual 处理，常见模式是先按 `dataId` 删除旧数据，再 `saveBatch` 新数据。
- 动态业务模块中，如果主 PO 继承 `AppPO` 且包含 `Long xxId` 字段，需要在对应 `xxServiceImpl` 重写 `addOtherAction`，从 `dataAddDTO.getDocData()` 中通过 `AppUtil.getSingleColumnIdValue(<业务常量>, docData)` 取关联控件首个 id，赋值给 PO 的 `xxId` 字段，并调用 `this.updateById(dataPO)` 同步主表。示例参考 `oncontract` 的 `addOtherAction`：先获取 `docData`，再按 `CrmConst.CUSTOMER`、`CrmConst.BUSINESS` 取 id 并调用 setter。
- 字段常量优先放在当前业务域对应的 `*Const` 中，并使用与实际字段前缀一致的常量名和值。例如 CRM 模块使用 `CrmConst.CUSTOMER = "customer"`，edu 模块使用 `EduConst.CAMPUS = "campus"`、`EduConst.ROOM = "room"`、`EduConst.CLAZZ = "clazz"`。

## 接口和方法位置

- Controller 路由通常使用 `@PostMapping("/<business>/<route>/...")`，并延续当前模块已有路径风格。
- Controller 标准生成顺序保持为：分页、详情、新增、编辑、删除、转移、导入模板、导入、导出。
- 自定义 Controller 接口追加在导出接口后面。
- 自定义 ServiceImpl 方法追加在生成重写方法之后，例如 `addDbDataBatch`、`transDbScopeBatch` 后面。
- 自定义 Service 接口声明追加在接口末尾。
- 每个新增 public 方法都写完整 JavaDoc，沿用本项目格式：功能描述、参数、返回值、作者。
- 重写 `addOtherAction` 时也必须写 JavaDoc 方法注释，至少包含功能描述、参数和作者；与上一个方法之间保留一行空行，不要让 `transDbScopeBatch` 的结束括号和 `addOtherAction` 注释紧贴在一起。

## 查询和 DTO 约定

- 请求结构无法复用现有 App/ES DTO 时，在 `domain/dto` 下新增请求 DTO。
- 响应形态与模块数据一致时优先返回已有 VO；只有响应结构明显不同才新增 VO。
- 简单过滤优先使用 MyBatis-Plus lambda query，并保持租户、数据权限行为与周边代码一致。
- 当前项目中 active 数据通常使用 `delFlag = YesOrNoEnum.YES.getValue()` 过滤。
- 日期、本周、周期规则等逻辑，优先在 DB 层做粗筛，再把业务匹配、日历匹配、复发规则放到 Manual 中处理。

## 编辑纪律

- 不要无意义重排生成代码的 import、方法、注释或格式。
- 新增功能时不要顺手修改无关 PO/VO 的格式和注释。
- 保持文件原有编码和换行风格。Windows 下特别注意不要给 Java 文件写入 UTF-8 BOM。
- 手工编辑优先使用 `apply_patch`。如需脚本做机械移动，移动后必须检查 diff。
- 修改现有方法时，优先沿用原有 JavaDoc 和注释结构，不要擅自改写成新的表述、顺序或语言。
- 任何新增或调整的 public 方法注释，都要保持本项目既有结构：`功能描述`、`参数`、`返回值`、`作者`；需要改动时只改对应内容，不要重排整体格式。
- 如果终端里中文注释显示乱码，先通过 `git show`、源码文件或明确的 UTF-8 读取方式恢复原注释，再编辑；不要把乱码文本回写到代码里。
- 除非用户明确要求重写注释，否则不要主动润色、翻译或替换现有中文注释。

## 验证要求

- 后端功能改动后优先执行：

```powershell
mvn -pl services/mesh-crm/mesh-crm-biz -am -DskipTests compile
```

- 编译失败时优先检查：BOM/编码问题、方法移动后的括号、未清理 import、新增 DTO 包路径、枚举路径、Manual 方法可见性。
- 最终回复前用 `git diff -- <target-module>` 和 `git status --short` 确认改动面符合预期。
