# Pub Gitee 数据库初始化最终修复报告

## 状态

完成。最终评审提出的 Critical、Important 和 Minor 项均已处理；保留原有 8 库范围和
CRM 模块裁剪逻辑。整个修复与验证过程未连接数据库、未执行任何 SQL。

主实现提交：`7a39e33d5`（`fix: restore public SQL runtime compatibility`）

## 修复内容

### 运行时 schema 兼容性

- 依据当前 `pub_gitee` 的显式 `@TableName` 实体恢复租户相关列。
- 生成结果覆盖 212 个保留实体、135 个 `tenant_id` 列和 2 个
  `scope_tenant_id` 列，共 137 个租户相关字段映射。
- 租户列使用历史 Git schema 提交
  `783d104438ce57bf8fd6b6629d52928bfc845633` 中的可空 `bigint` 约定。
- 新增独立实体到 DDL 校验：每个显式 `@TableName` 必须解析到对应数据库中的表，
  每个 `tenantId`/`*TenantId` 字段必须解析到对应 snake_case 列。
- 恢复活跃的 `app_form_column_mapping` 表。字段契约来自 Git 中该实体的演进提交
  `433f850c`、`faa77dbd`、`a51ae3c7`、`fdc1b965`，类型、字符集、审计列和表选项沿用
  同一 `mesh_app` 基线中的相邻表约定；测试固定完整 `CREATE TABLE` 文本，防止漂移。

### 可重复生成与源码覆盖

- 将 8 个已评审的完整 schema 输入放入
  `builds/script/sql/pub-gitee/baseline/`，不再运行 `git show pro_single:...`。
- `manifest.json` 按数据库固定每个输入的仓库相对路径和 SHA-256；缺失、越界或内容
  改动都会使生成失败。
- `.gitattributes` 强制基线和生成 SQL 使用 LF，避免 Windows fresh checkout 因
  `core.autocrlf` 导致校验和或确定性校验失效。
- 无 `.git` 元数据的临时仓库测试已覆盖完整 baseline 加载和生成路径。
- manifest 明确声明每个数据库的源码根，包括 `supports/mesh-bpm`、
  `supports/mesh-gen`、`supports/mesh-uaa`、`supports/mesh-upms`；声明的非空目录缺失时
  生成和校验均失败。`mesh_tmp` 明确声明为空目录列表，而不是被静默跳过。

### 包级完整性与 schema-only 边界

- 外键检查从单文件本地解析扩展到完整 8 库包，schema-qualified 跨库目标必须存在。
- DML 分类器覆盖普通 DML、`WITH ... UPDATE/DELETE` 和 MySQL
  `/*!80000 ... */` 可执行注释形式；基线生成阶段和产物校验阶段均拒绝 DML。
- 已评审基线不存在视图、触发器、存储过程、函数或事件。生成器和校验器明确拒绝
  这些对象（包括在 `all.sql` 中出现），避免未来静默扩大支持范围。
- spec、plan、包 README 和 baseline README 已统一为 schema-only，不承诺或包含
  种子数据/业务数据。
- PowerShell 部署示例改为
  `cmd /c "mysql --default-character-set=utf8mb4 -u root -p < all.sql"`，避免把
  `cmd.exe` 重定向语法直接当成 PowerShell 语法。

## 红绿测试证据

修复前新增的针对性测试分别复现了以下失败：缺少 baseline/source-root manifest
字段、依赖本地 `pro_single` ref、`app_form_column_mapping` 缺失、实体租户列缺失、
跨库外键漏检、CTE/可执行注释 DML 漏检、非表对象漏检、缺失源码根静默跳过、
`@TableName` 表和租户字段未校验，以及 Windows checkout 的 LF 固定缺失。

修复后：

- `python -m unittest builds.script.test.test_pub_gitee_sql -v`
  - 59 个测试全部通过。
- `python builds/script/sql/pub-gitee/generate.py --check`
  - 通过，表数如下：
    - `mesh_ai`: 23
    - `mesh_app`: 46
    - `mesh_bpm`: 31
    - `mesh_crm`: 95
    - `mesh_gen`: 11
    - `mesh_tmp`: 24
    - `mesh_uaa`: 8
    - `mesh_upms`: 46
    - 合计：284
- `mvn.cmd -q -DskipTests compile`
  - 退出码 0，无错误输出。
- 二次运行 `python builds/script/sql/pub-gitee/generate.py`
  - 9 个生成输出的 SHA-256 全部保持一致。
  - 指定最终修复文件的 `git diff --exit-code` 通过。
- `git -c core.whitespace=cr-at-eol diff --check`
  - 通过。

## 风险与限制

- 按任务约束未连接 MySQL，因此没有真实 MySQL 8 实例上的执行级 smoke test；当前证据
  为静态 SQL 校验、确定性校验、源码契约校验和完整 Maven 编译。
- Git 历史中未找到 `app_form_column_mapping` 的已提交字面量 `CREATE TABLE`；本次 DDL
  由可追溯的实体字段演进与同库相邻表 DDL 共同恢复，并由完整文本回归测试锁定。
- 固定基线使仓库增加约 800 KiB 的 schema 输入，这是 fresh-clone 可重复生成的预期
  成本，不是运行时依赖。

没有遗留阻断项。
