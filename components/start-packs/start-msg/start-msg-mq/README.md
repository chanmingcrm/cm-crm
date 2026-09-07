# start-msg-mq

为 EVENT、Redis、Kafka、RabbitMQ、RocketMQ 提供统一发布接口和消费注解。所有使用、部署、页面与排查说明集中于本文。

- [一、使用示例](#一使用示例)
- [二、部署教程](#二部署教程)
  - [1. 普通部署（非 Docker）](#1-普通部署非-docker)
  - [2. Docker 部署](#2-docker-部署)

## 一、使用示例

### 核心约定

- 发布者可以使用配置的默认 `provider`，也可以显式指定；`mode` 决定广播或队列语义。
- 消费者只声明 `@MqListener(topic = "...")`，不关心 provider、mode 或消费者名称。
- `BROADCAST` 会交给同一 topic 的每个监听方法，适合一个事件触发日志、合同、通知等多项业务。
- `QUEUE` 必须提供稳定的 `orderingKey`。同一会话或用户进入同一分片并顺序执行，不同 key 可并行。
- 消费逻辑应使用 `messageId` 或业务唯一键保证幂等。EVENT 和 Redis Topic 广播不提供持久化重投保证；外部 MQ 的确认、重试和持久化能力取决于对应实现及配置。

### 统一配置（默认通道与开关）

默认通道与各 provider 开关写在同一份配置中，不要重复声明 `mesh.mq`。下面以 Kafka 为默认通道，完整列出所有 provider；实际使用时只开启需要的 provider，并完成对应连接配置。`default-provider` 指向的 provider 必须启用：

```yaml
mesh:
  mq:
    enabled: true
    default-provider: kafka
    event:
      enabled: true
    redis:
      enabled: true
      queue-shards: 16
    kafka:
      enabled: true
      queue-shards: 16
    rabbitmq:
      enabled: true
      queue-shards: 16
    rocketmq:
      enabled: true
      queue-shards: 16
```

`mesh.mq.enabled=false` 时整个组件不注册。单个 provider 还要求对应客户端或 Spring Cloud Stream binder 可用。`queue-shards` 决定队列并行上限；修改它会改变 key 到物理分片的映射，生产环境应谨慎变更。

### 默认发布与固定通道

沿用上面的统一配置：省略 provider 时使用 `default-provider: kafka`；显式指定时使用指定通道。`default-provider` 支持 `event`、`redis`、`kafka`、`rabbitmq`、`rocketmq`，框架没有硬编码默认值；切换默认通道时，同时确保该 provider 已启用且连接配置完整。

```java
// 跟随 default-provider 配置；切换中间件无需修改业务发布代码。
mqPublisher.broadcast("crm.contract.created", payload);

// 固定通道，优先于默认配置；下列为独立示例，对应 provider 必须已启用。
mqPublisher.broadcast(MqProviderType.REDIS, "system.notice", payload);
mqPublisher.broadcast(MqProviderType.EVENT, "local.changed", payload);
mqPublisher.broadcast(MqProviderType.KAFKA, "third.party.order", payload);
mqPublisher.broadcast(MqProviderType.RABBITMQ, "order.created", payload);
mqPublisher.broadcast(MqProviderType.ROCKETMQ, "task.created", payload);

// 完整请求支持自定义 headers 和顺序键；provider 为 null 时同样读取默认配置。
mqPublisher.publish(new MqPublishRequest<>(
        null, "ai.message.requested", MqMode.QUEUE,
        conversationId, Map.of("traceId", traceId), payload));
```

配置默认 provider 后，应用启动时会校验其启用状态和注册情况。例如 `default-provider: kafka` 但未启用 Kafka 时，启动失败并提示“默认 MQ 提供方 kafka 未启用，请设置 mesh.mq.kafka.enabled=true，或修改 mesh.mq.default-provider”。已启用但 Provider 未注册时，提示检查客户端 Bean、Binder 依赖及自动配置；此校验不代表中间件连接健康检查。

显式指定的 provider 未启用时直接报错，不回退到默认通道。未配置默认值时，显式发布仍可使用；省略 provider 的发布会提示配置 `mesh.mq.default-provider`。EVENT 仅支持 BROADCAST，默认配置为 EVENT 时也不能发布 QUEUE 消息。

消费者继续使用 `@MqListener(topic = "...")`，无需随默认发送通道变化修改代码。默认值只控制发送路由，不会关闭其他已启用 provider 的订阅，也不会自动向多个 provider 重复发送。普通配置变更在应用重启后生效；这不是动态热切换机制。

### 快速上手：生产与消费

#### 1. 引入模块

版本由项目 BOM 管理：

```xml
<dependency>
    <groupId>com.platform</groupId>
    <artifactId>start-msg-mq</artifactId>
</dependency>
```

#### 2. 配置默认通道

无需外部中间件的本地示例使用 EVENT，生产者和消费者需在同一 JVM 中：

```yaml
mesh:
  mq:
    enabled: true
    default-provider: event
    event:
      enabled: true
```

需要跨服务时，将默认值改为所需 provider，并按下方对应章节启用和配置连接。默认值可选 `redis`、`kafka`、`rabbitmq`、`rocketmq`；业务发布方法保持不变。

#### 3. 生产者

业务消息类型：

```java
public record ContractCreatedPayload(Long contractId) {
}
```

发布服务（`ContractCreatedPayload` 放在业务自己的包中）：

```java
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.service.MqPublisher;
import org.springframework.stereotype.Service;

@Service
public class ContractEventPublisher {
    private final MqPublisher mqPublisher;

    public ContractEventPublisher(MqPublisher mqPublisher) {
        this.mqPublisher = mqPublisher;
    }

    public MqPublishResult publishCreated(Long contractId) {
        return mqPublisher.broadcast("crm.contract.created",
                new ContractCreatedPayload(contractId));
    }
}
```

#### 4. 消费者

```java
import com.platform.mesh.mq.annotation.MqListener;
import org.springframework.stereotype.Component;

@Component
public class ContractCreatedConsumer {
    @MqListener(topic = "crm.contract.created")
    public void consume(ContractCreatedPayload payload) {
        // 使用 payload.contractId() 执行业务，并按业务唯一键保证幂等。
    }
}
```

调用 `contractEventPublisher.publishCreated(10001L)` 即可触发该监听器。指定通道发布则使用 `broadcast(MqProviderType.EVENT, topic, payload)` 等重载；消费者注解不变。

### 配置校验与常见提示

当 `mesh.mq.enabled=true` 且配置了 `default-provider` 时，启动会检查默认 provider 是否启用并注册。即使业务只打算显式发布，也必须修正无效的默认配置，或移除 `default-provider`。

| 场景 | 检查时机 | 提示与处理 |
| --- | --- | --- |
| 默认值为 `kafka`，未配置 `kafka.enabled` 或设为 `false` | 启动 | 默认 MQ 提供方 kafka 未启用，请设置 mesh.mq.kafka.enabled=true，或修改 mesh.mq.default-provider |
| 默认 provider 已启用，但没有注册对应 Provider | 启动 | 默认 MQ 提供方 kafka 已启用但未注册，请检查对应客户端 Bean、Binder 依赖及自动配置；provider 名称按实际配置显示 |
| 未配置默认值，调用 `broadcast(topic, payload)` | 发布 | 未指定 MQ 提供方，请配置 mesh.mq.default-provider 或在发布时显式指定 |
| 显式指定的 provider 未注册 | 发布 | MQ 提供方未启用；检查所指定的 provider，不会回退到默认值 |
| 默认或显式指定 EVENT，发布 QUEUE | 发布 | MQ 提供方不支持当前投递模式 |
| QUEUE 未提供有效 `orderingKey` | 发布 | 队列模式的顺序键不能为空 |

Kafka 默认通道的启用配置示例（连接配置见 Kafka 章节）：

```yaml
mesh:
  mq:
    enabled: true
    default-provider: kafka
    kafka:
      enabled: true
      queue-shards: 16
```

Provider 注册成功不代表中间件一定在线；连接、认证或 Binder 初始化失败时，还需检查对应客户端异常日志。未配置默认值时，不做默认 provider 启动校验，仍可显式发布。

### 通用消费方式

监听方法既可以直接接收业务对象，也可以接收包含元数据的 `MqMessage<T>`：

```java
@Component
public class ContractCreatedListener {
    /**
     * 功能描述:
     * 〈合同创建成功后保存团队成员〉
     * @param payload 合同创建消息
     * @author 蝉鸣
     */
    @MqListener(topic = "crm.contract.created")
    public void addGroupMember(ContractCreatedPayload payload) {
        // 保存团队成员
    }
    /**
     * 功能描述:
     * 〈合同创建成功后初始化财务应收〉
     * @param message MQ 消息
     * @author 蝉鸣
     */
    @MqListener(topic = "crm.contract.created")
    public void initReceivable(MqMessage<ContractCreatedPayload> message) {
        String messageId = message.messageId();
        ContractCreatedPayload payload = message.payload();
        // 使用 messageId 做幂等校验后初始化应收
    }
}
```

无论消息由哪个 provider 发出，上述监听方法都不需要修改。

### EVENT

EVENT 使用 Spring 应用事件，适合当前 JVM 内解耦，不跨微服务，仅支持广播。

```yaml
mesh:
  mq:
    enabled: true
    event:
      enabled: true
```

```java
mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.EVENT,
        "crm.contract.created",
        MqMode.BROADCAST,
        null,
        Map.of("source", "crm"),
        new ContractCreatedPayload(contractId)));
```

```java
@MqListener(topic = "crm.contract.created")
public void saveContractLog(ContractCreatedPayload payload) {
    // 保存合同日志
}
```

EVENT 会继续调用同一主题的其他监听方法，再向发布方抛出汇总异常。它不提供持久化、自动重试或死信；发布方重发可能再次触发已经成功的监听方法。

### Redis

Redis 广播使用 Topic，队列使用分片 Stream 和消费组。应用需提供 `RedissonClient`。

```yaml
mesh:
  mq:
    enabled: true
    redis:
      enabled: true
      queue-shards: 16
```

```java
// 广播
mqPublisher.broadcast(MqProviderType.REDIS, "crm.contract.created",
        new ContractCreatedPayload(contractId));

// 同会话有序队列
mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.REDIS, "ai.message.requested", MqMode.QUEUE,
        conversationId, Map.of(), new AiMessagePayload(conversationId, content)));
```

```java
@MqListener(topic = "ai.message.requested")
public void executeAiMessage(MqMessage<AiMessagePayload> message) {
    // 同 conversationId 顺序执行，不同 conversationId 可并行
}
```

### Kafka

```yaml
spring:
  cloud:
    stream:
      binders:
        kafka:
          type: kafka
      kafka:
        binder:
          brokers: 192.168.0.84:9092
mesh:
  mq:
    enabled: true
    kafka:
      enabled: true
      queue-shards: 16
```

```java
mqPublisher.broadcast(MqProviderType.KAFKA, "crm.contract.created",
        new ContractCreatedPayload(contractId));

mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.KAFKA, "ai.message.requested", MqMode.QUEUE,
        conversationId, Map.of(), new AiMessagePayload(conversationId, content)));
```

```java
@MqListener(topic = "crm.contract.created")
public void createContractArchive(ContractCreatedPayload payload) {
    // 每个逻辑监听者都收到广播
}

@MqListener(topic = "ai.message.requested")
public void executeAiMessage(AiMessagePayload payload) {
    // 同 orderingKey 顺序消费
}
```

### RabbitMQ

```yaml
spring:
  cloud:
    stream:
      binders:
        rabbitmq:
          type: rabbit
  rabbitmq:
    host: 192.168.0.84
    port: 5672
    username: startmq
    password: startmq
mesh:
  mq:
    enabled: true
    rabbitmq:
      enabled: true
      queue-shards: 16
```

```java
mqPublisher.broadcast(MqProviderType.RABBITMQ, "crm.contract.created",
        new ContractCreatedPayload(contractId));

mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.RABBITMQ, "ai.message.requested", MqMode.QUEUE,
        userId, Map.of(), new AiMessagePayload(conversationId, content)));
```

```java
@MqListener(topic = "crm.contract.created")
public void createReceivable(ContractCreatedPayload payload) {
    // 初始化财务应收
}

@MqListener(topic = "ai.message.requested")
public void executeAiMessage(AiMessagePayload payload) {
    // 同 userId 顺序消费，不同 userId 可并行
}
```

### RocketMQ

```yaml
spring:
  cloud:
    stream:
      binders:
        rocketmq:
          type: rocketmq
      rocketmq:
        binder:
          name-server: 192.168.0.84:9876
mesh:
  mq:
    enabled: true
    rocketmq:
      enabled: true
      queue-shards: 16
```

```java
mqPublisher.broadcast(MqProviderType.ROCKETMQ, "crm.contract.created",
        new ContractCreatedPayload(contractId));

mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.ROCKETMQ, "ai.message.requested", MqMode.QUEUE,
        conversationId, Map.of(), new AiMessagePayload(conversationId, content)));
```

```java
@MqListener(topic = "crm.contract.created")
public void notifyContractCreated(MqMessage<ContractCreatedPayload> message) {
    // 发送合同创建通知
}

@MqListener(topic = "ai.message.requested")
public void executeAiMessage(MqMessage<AiMessagePayload> message) {
    // 按 conversationId 有序执行
}
```

### 多 provider 同时使用

同一个应用可以让轻量通知使用 Redis、第三方消息使用 Kafka、重业务队列使用 RocketMQ：

```java
mqPublisher.broadcast(MqProviderType.REDIS, "system.notice", notice);
mqPublisher.broadcast(MqProviderType.KAFKA, "third.party.order", order);
mqPublisher.publish(new MqPublishRequest<>(
        MqProviderType.ROCKETMQ, "ai.message.requested", MqMode.QUEUE,
        conversationId, Map.of(), aiMessage));
```

所有启用的 provider 都会为 `@MqListener` 建立订阅。同一业务消息不要无意间通过多个 provider 重复发布；需要多通道发布时，消费者必须跨通道幂等。

### 能力说明

| Provider | BROADCAST | QUEUE | 跨服务 | 同 key 有序 |
| --- | --- | --- | --- | --- |
| EVENT | 当前 JVM 内所有监听者 | 不支持 | 否 | 否 |
| Redis | Topic 扇出 | Stream 消费组 | 是 | Stream 分片 |
| Kafka | 每个监听者独立消费组 | 分片共享消费组 | 是 | 物理分片 |
| RabbitMQ | 每个监听者独立队列 | 分片共享队列 | 是 | 物理分片 |
| RocketMQ | 每个监听者独立消费组 | 分片共享消费组 | 是 | 顺序发送至物理分片 |

Spring Cloud Stream provider 会为每个逻辑监听方法创建一个广播绑定和 `queue-shards` 个队列分片绑定。框架附加 `mesh_mq_ordering_key`、`mesh_mq_message_id`、`mesh_mq_mode` 消息头，并把业务 topic 转换为兼容各中间件命名规则的物理名称。

### 重试与死信

Redis **队列**、Kafka、RabbitMQ、RocketMQ 使用以下参数（各 provider 分别配置）：

```yaml
mesh:
  mq:
    default-provider: kafka
    kafka:
      enabled: true
      queue-shards: 16
      max-attempts: 3
      retry-backoff-millis: 1000
```

`max-attempts` 包含首次调用，默认 3 次；`retry-backoff-millis` 默认 1000 毫秒。Redis 使用上限 30 秒的指数退避，外部 MQ 的业务重试使用固定间隔。监听方法应抛出异常表示失败；吞掉异常会被视为消费成功。

达到次数上限后，将原消息和失败原因写入死信，**死信确认成功后才确认原消息并继续后续消息**。死信不可用时保留原消息并重试死信写入。该分片的其他 key 也可能因此等待；修复死信通道后恢复处理。EVENT 与 Redis Topic 广播不适用此持久重试机制。

死信名称为原物理 Stream、Topic 或交换机名称加 `_dlq`：Redis 为 Stream，Kafka/RocketMQ 为 Topic，RabbitMQ 为独立持久队列。死信保留原消息内容、消息 ID、原始来源、逻辑消费组、尝试次数和失败原因；多个逻辑监听者的死信可能汇入同一位置，可按消费组区分。

先修复业务原因，再人工按原 provider、业务 topic 和 orderingKey 重放死信。重放发生在后续消息之后，不恢复原先的全局业务顺序；业务必须幂等，不能只因消息进入死信就删除故障记录。

### 可靠性与容量边界

- Redis 队列先恢复 pending，再读取新消息；消费组从已有消息起点建立。新建逻辑监听者可能消费该 Stream 保留的历史消息，需在上线前确认保留范围。
- Redis 使用自动续期锁保持正常运行时的分片串行。超过锁租期的进程暂停或网络隔离仍可能产生重复业务执行，需要业务幂等或数据库版本条件更新保护。
- RabbitMQ 使用同步事务发送、单个活跃消费者及预取 1；Kafka 使用同步发送；RocketMQ 使用同步发送和顺序消费。顺序以 Broker 接收及分片顺序为准，多生产者同时提交同一个 key 时不能推导业务先后。
- 外部 MQ 的业务重试次数是当前投递/进程内计数，进程崩溃或消费权迁移后可能重新计数。消息处理与确认、死信写入与原消息确认均不是跨系统事务，因此仍可能重复，不能视为 exactly-once。
- Kafka 单次业务处理或同步发送耗时须小于 `max.poll.interval.ms`；按业务最长耗时配置客户端超时。不能用无限慢回调保持消费权。
- Redis 消费者 ID 仍兼容旧组名，组件登记完整 ID 并拒绝哈希冲突。身份登记用于长期保护旧组，不应随意删除；旧版本运行实例不受新校验保护，升级应统一进行。
- 已确认的 Redis Stream 消息不会自动裁剪。应监控 Stream、pending、死信、积压和磁盘，结合所有消费组进度制定保留策略，避免直接裁剪未消费消息。
- 同步确认和保序会降低吞吐。分片数决定并行能力，生产容量需按实际网络、消息大小、业务耗时及多节点故障测试验收；单机短时回归不能证明生产高可用。


### 管理页面与死信查看

| 中间件 | 当前 VM 页面 | 当前测试账号 |
| --- | --- | --- |
| Kafka | http://192.168.0.84:18080 | startmq |
| RabbitMQ | http://192.168.0.84:15672 | startmq |
| RocketMQ | http://192.168.0.84:18081 | startmq |

当前 VM 沿用现有测试口令；下文全新 Docker 安装会生成新密码并在完成时显示，不沿用旧密码。页面连接的是各自配置的 Broker。Kafka/RocketMQ 支持查询保留期内消息，RabbitMQ 已确认消息不保留历史正文。

#### 查看普通消息

- Kafka：可先选 `mesh.start.mq.e2e.queue`，Messages 中从最早记录开始读取。已验证 offset 0、1 的消息正文、headers、messageId 和 orderingKey。该页面通过独立预览消费者读取日志，不推进应用消费者组的 offset。消息保留期内，已被业务消费者处理的记录仍可能可查。
- RabbitMQ：先选独立演示队列 `start_mq_admin_preview`，Get messages 设数量 `1`，Ack mode 选择 **Nack message requeue true**（API 为 `ack_requeue_true`）。已验证读出 JSON 后 Ready 仍为 `1`、Unacked 为 `0`。此操作实际取出并重新入队，会设置 redelivered，可能影响队列顺序；不要拿业务队列做预览实验。已 ACK 的历史消息不会保留在队列中，也无法通过这个页面恢复查看。可先只看 Ready/Unacked、消费者及速率。
- RocketMQ：可先选 `mesh_start_mq_e2e_queue_3`，将查询时间范围覆盖 `2026-09-04`。已验证查询和详情返回正文中的 `payload`、`messageId`、`orderingKey`，broker 为 `192.168.0.84:10911`。查询读取存储数据，不直接 ACK 应用消费。历史消费者离线时详情可能显示 `NOT_ONLINE`，这不等于查询失败。

物理名称与业务逻辑 topic 不一定相同；统一 MQ 层会根据业务主题、投递模式和分片生成物理名称。请以实际 Topic / Queue 列表为准。

#### 查看失败死信

本项目统一死信目的地是 **原物理目的地 + `_dlq`**：

- Kafka：Topics 搜索 `_dlq`，进入 Messages。
- RabbitMQ：Queues and Streams 搜索 `_dlq`。这是同名持久队列，直接查队列，不需要先查 exchange。预览仍须选择 requeue true。
- RocketMQ：在普通 Topic 列表或 Message 页面搜索/选择 `_dlq`。项目的 `_dlq` 是自定义普通 Topic，**不等于 RocketMQ 原生 `%DLQ%`**；不要只依赖 Dashboard 的原生 Dead Letter 分类。
- Redis Stream：同样是原 stream key + `_dlq`，本次三个页面不覆盖 Redis。

死信目的地在失败达到重试上限后才产生；没有失败消息时列表中可能尚不存在。回归测试的 `qa` / `qafix` 相关目的地可用于确认失败消息被保留、后续消息仍继续处理。广播订阅重启后的持久语义以及死信重放需以统一 MQ 实现为准，管理页面不自动重放。

#### 权限与资源边界

- Kafbat UI 开启登录和 `readOnly=true`，关闭动态配置和 MCP；已验证匿名访问跳转登录，写入类请求被只读过滤器拒绝。
- RocketMQ 开启登录，`startmq` 使用普通用户；独立 `rocketmq-admin-permissions.yml` 仅放行查询，排除发送、直接消费、重发死信、修改配置及删除。已验证真实删除 API 对不存在的探测 Topic 返回 `no permission`，未删除任何资源。
- RabbitMQ 沿用测试环境原有管理账号，权限较高，**并非只读**。不要点 Purge、Delete、ACK/Requeue false 或修改业务队列。
- 新页面只绑定 VM 的内网 IP；HTTP 登录应仅在可信局域网或 SSH 隧道下使用，不要映射到公网。测试 Broker 端口沿用原配置，页面登录不等于 Broker 端启用了 ACL。
- 新增容器各限制 `768 MiB`，JVM 最大堆 `512 MiB`。初次验证时各使用约 `290 MiB`；RocketMQ broker 原有 `2 GiB` 限制保留。
- 管理页面和 5 个测试容器保持运行，供回归与查看。未对业务队列执行 Get/ACK；Rabbit 演示仅操作独立 `start_mq_admin_preview`。


#### 本轮可直接查看的死信样例

以下为 2026-09-05 中午最终回归产生的独立测试数据，均保留在 Broker。验证了处理器失败恰好 3 次、死信元数据 `mesh_mq_attempts=3`、同一顺序键下一条消息成功，以及消费者/死信 `occurredAt` 毫秒时间和 payload 数值保持正确。

| 页面 | 搜索的物理死信名称 | 正文原始 messageId |
| --- | --- | --- |
| Kafka Topics → Messages | `mesh_qafix1788580854681_poison_kafka_c401b1da6b7e54d8_queue_3_dlq` | `5f6e86ec-d049-4112-aff0-b041a5dbc61a` |
| RabbitMQ Queues and Streams | `mesh_qafix1788580697870_poison_rabbitmq_4902179b68efad76_queue_3_dlq` | `1805c2ed-3c99-427c-8639-f518a2135d26` |
| RocketMQ Message → Topic 查询 | `mesh_qafix1788580865223_poison_rocketmq_f5d6ae50ef2a29e5_queue_3_dlq` | `bc3b21fd-ceb8-4f0e-9d50-5532fd60cbfd` |

RocketMQ 的消息查询时间范围可设为 `2026-09-05 11:55` 到 `2026-09-05 12:10`（Asia/Shanghai）。正文 `messageId` 同时保留在消息属性 `KEYS`，可按 Topic + Key 查找；按 Broker Message ID 查询时应使用 `240882201B18E7E0594E86764199E4928CAC6BC7C054172E12BA0062`，它与业务信封 UUID 不同。

最终页面 API 已确认 Kafka/RocketMQ 死信正文直接为 JSON，RabbitMQ 死信 Ready=`1`、Unacked=`0`。RabbitMQ 原生回归读取后已重新入队。旧回归目的地可能保留修复前的 Base64/异常编码样例，正常验收请使用本表的最终数据；旧证据未删除。

## 二、部署教程

### 1. 普通部署（非 Docker）

在 Linux 上直接运行进程和 JAR，命令使用 Bash。示例 IP 为 `192.168.0.84`；该 VM 当前已有 Docker 实例，应在新主机演练或安排迁移，不能同时占用相同端口。EVENT 在应用 JVM 内运行，无需独立安装。切换部署方式不会自动迁移消息、账号和消费进度。

#### 1. 环境与目录

| 组件 | 本文版本/要求 | 安装方式 |
| --- | --- | --- |
| 后端应用 | JDK 21 | 本项目构建产物 |
| Redis | 7.4.x | 官方发行版或源码编译 |
| Kafka | 3.9.1，本文使用 JDK 17 | 二进制发行包，KRaft 模式 |
| RabbitMQ | 3.13.x + 兼容的 Erlang | 官方系统软件包 |
| RocketMQ | 5.3.2，本文使用 JDK 17 | 二进制发行包 |
| Kafbat UI | 1.5.0，JDK 25 | 官方可执行 JAR |
| RocketMQ Dashboard | 2.1.0，JDK 17、Maven | 官方源码构建 JAR |

这些版本用于复现当前环境，不表示都是最新生产推荐版本。安装包从官方来源下载并校验发行方校验和。RabbitMQ 安装前核对 [Erlang 兼容矩阵](https://www.rabbitmq.com/docs/which-erlang)。Kafbat 1.5.0 的 [构建配置](https://github.com/kafbat/kafka-ui/blob/v1.5.0/build.gradle) 使用 Java 25，与业务后端 Java 21 分开指定。

用普通服务账号运行进程，数据与安装包分开：

```bash
export MQ_BASE="$HOME/start-mq-native"
mkdir -p "$MQ_BASE"/{apps,conf,data,logs,run}
# 按实际安装位置填写，并用各自 java -version 确认。
export JAVA17_HOME=/opt/jdk-17
export JAVA25_HOME=/opt/jdk-25
ss -lntp | grep -E ':(16379|9092|9093|5672|15672|9876|10909|10911|18080|18081)\b'
```

本文是单机配置。限制访问来源到应用主机/可信局域网；页面登录和 Broker 认证是独立配置。长期托管时使用专用 systemd 服务，配置工作目录、用户、环境文件、日志轮转、重启策略和关闭超时；前台命令适合先完成安装验收。

#### 2. Redis

按 [Redis 官方源码安装说明](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-redis/install-redis-from-source/) 获取所需 7.4.x 源码包，解压并重命名为 `$MQ_BASE/apps/redis`。准备 GCC、Make 后编译：

```bash
cd "$MQ_BASE/apps/redis"
make -j2
mkdir -p "$MQ_BASE/data/redis"
```

创建 `$MQ_BASE/conf/redis.conf`，将 `<MQ_BASE>` 替换成实际绝对路径，密码换成自己的密码，文件权限设为 `600`：

```conf
bind 127.0.0.1 192.168.0.84
protected-mode yes
port 16379
daemonize no
appendonly yes
appendfsync everysec
dir <MQ_BASE>/data/redis
requirepass <实际Redis密码>
```

启动（前台终端 1）：

```bash
"$MQ_BASE/apps/redis/src/redis-server" "$MQ_BASE/conf/redis.conf"
```

验证（终端 2，先重新设置 `MQ_BASE`）：

```bash
read -rsp 'Redis password: ' REDISCLI_AUTH
export REDISCLI_AUTH
"$MQ_BASE/apps/redis/src/redis-cli" -h 127.0.0.1 -p 16379 ping
unset REDISCLI_AUTH
```

返回 `PONG` 为成功。前台进程可用 Ctrl+C 正常关闭；若用 CLI 关闭，先设置认证环境变量，再执行对应 `redis-cli -h 127.0.0.1 -p 16379 shutdown`。不要删除 AOF/RDB 数据目录。

应用需提供指向 `redis://192.168.0.84:16379` 的 `RedissonClient`，同时传入密码；只有 `mesh.mq.redis.enabled=true` 不会自动创建 Redisson 连接。

#### 3. Kafka 3.9.1（KRaft，无 ZooKeeper）

下载并解压 [Kafka 3.9.1 官方发行包](https://archive.apache.org/dist/kafka/3.9.1/)，目录为 `$MQ_BASE/apps/kafka_2.13-3.9.1`。本文使用静态 controller quorum，与仓库 compose 一致，不混用 Kafka 4.x 的配置文件路径。

创建 `$MQ_BASE/conf/kafka.properties`，替换数据目录占位符：

```properties
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@127.0.0.1:9093
listeners=PLAINTEXT://0.0.0.0:9092,CONTROLLER://127.0.0.1:9093
advertised.listeners=PLAINTEXT://192.168.0.84:9092
controller.listener.names=CONTROLLER
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
inter.broker.listener.name=PLAINTEXT
log.dirs=<MQ_BASE>/data/kafka
num.partitions=16
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
group.initial.rebalance.delay.ms=0
```

仅在**全新空数据目录**初始化一次：

```bash
cd "$MQ_BASE/apps/kafka_2.13-3.9.1"
export JAVA_HOME="$JAVA17_HOME"
MQ_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
bin/kafka-storage.sh format -t "$MQ_CLUSTER_ID" -c "$MQ_BASE/conf/kafka.properties"
```

已有数据时跳过格式化，重启使用同一配置与数据目录。启动并验证：

```bash
bin/kafka-server-start.sh -daemon "$MQ_BASE/conf/kafka.properties"
bin/kafka-topics.sh --bootstrap-server 192.168.0.84:9092 --list
```

日志在发行目录 `logs/`，启动失败查看 `server.log`。需要停止时单独运行 `bin/kafka-server-stop.sh`；该脚本适用于本机只有这一套 Kafka 的场景，多实例使用各自 systemd 单元或核实 PID 后单独停止。

配置依据：[3.9.1 原版配置](https://github.com/apache/kafka/blob/3.9.1/config/kraft/server.properties)、[3.9 快速入门](https://kafka.apache.org/39/getting-started/quickstart/)。官方动态 quorum 示例需要另一套配置，不与本文静态 quorum 命令混用。

#### 4. RabbitMQ 与内置管理页面

按 [官方 Debian/Ubuntu 安装说明](https://www.rabbitmq.com/docs/install-debian) 配置与系统相符的软件源，并按兼容矩阵安装 Erlang。检查仓库提供的版本，再输入完整的 3.13.x 包版本号安装：

```bash
sudo apt-get update
apt-cache madison rabbitmq-server
read -rp '完整 RabbitMQ 3.13.x 包版本号: ' MQ_RABBIT_PACKAGE_VERSION
sudo apt-get install "rabbitmq-server=$MQ_RABBIT_PACKAGE_VERSION"
unset MQ_RABBIT_PACKAGE_VERSION
```

若仓库已不提供该版本，应从官方历史发行包及配套依赖安装，或单独安排升级验证，不能把新版安装命令当成 3.13 环境复现。安装完成后：

```bash
sudo systemctl enable --now rabbitmq-server
sudo rabbitmq-plugins enable rabbitmq_management
sudo rabbitmq-diagnostics -q ping
```

只在账号不存在时创建用户，交互输入密码以避免写入命令历史：

```bash
read -rsp 'RabbitMQ password: ' MQ_RABBIT_PASSWORD
sudo rabbitmqctl add_user startmq "$MQ_RABBIT_PASSWORD"
unset MQ_RABBIT_PASSWORD
sudo rabbitmqctl set_user_tags startmq management
sudo rabbitmqctl set_permissions -p / startmq '.*' '.*' '.*'
```

业务连接 `192.168.0.84:5672`；管理页为 `http://192.168.0.84:15672`，使用刚创建的账号。这里使用独立测试 vhost `/`，生产按业务 vhost 分配权限。不要依赖默认 guest 账号远程登录。插件说明见 [Management Plugin](https://www.rabbitmq.com/docs/3.13/management)。

查看日志使用 `sudo journalctl -u rabbitmq-server -n 100 --no-pager`。需要停止/启动时分别使用 `sudo systemctl stop rabbitmq-server`、`sudo systemctl start rabbitmq-server`。

#### 5. RocketMQ NameServer 与 Broker

下载 [RocketMQ 5.3.2 二进制发行包](https://archive.apache.org/dist/rocketmq/5.3.2/)，解压到 `$MQ_BASE/apps/rocketmq-all-5.3.2-bin-release`。创建 `$MQ_BASE/conf/rocketmq-broker.conf`：

```properties
brokerClusterName=DefaultCluster
brokerName=start-mq-broker
brokerId=0
brokerIP1=192.168.0.84
listenPort=10911
brokerRole=ASYNC_MASTER
flushDiskType=ASYNC_FLUSH
autoCreateTopicEnable=true
deleteWhen=04
fileReservedTime=48
storePathRootDir=<MQ_BASE>/data/rocketmq
storePathCommitLog=<MQ_BASE>/data/rocketmq/commitlog
```

替换 `<MQ_BASE>` 后，分别在两个终端运行；两个终端都配置相同目录变量与 `JAVA_HOME`：

```bash
# 终端 1：先启动 NameServer。
cd "$MQ_BASE/apps/rocketmq-all-5.3.2-bin-release"
export JAVA_HOME="$JAVA17_HOME"
sh bin/mqnamesrv
```

```bash
# 终端 2：再启动 Broker。
cd "$MQ_BASE/apps/rocketmq-all-5.3.2-bin-release"
export JAVA_HOME="$JAVA17_HOME"
sh bin/mqbroker -n 127.0.0.1:9876 -c "$MQ_BASE/conf/rocketmq-broker.conf"
```

启动脚本默认堆内存可能较大，先检查发行包 `bin/runserver.sh`、`bin/runbroker.sh` 的 JVM 配置与主机内存，需要调整时按该版本脚本支持的方式修改，不直接照搬 Docker 内存限制。本组件使用 Broker 客户端，当前示例无需额外启动 Proxy。

在发行目录运行 `sh bin/mqadmin clusterList -n 127.0.0.1:9876` 验收。日志通常位于服务账号的 `~/logs/rocketmqlogs/`。需要停止时先执行 `sh bin/mqshutdown broker`，再执行 `sh bin/mqshutdown namesrv`；仅适用于本机这一套实例，也可在前台终端 Ctrl+C 关闭对应进程。

官方步骤见 [本地运行 RocketMQ](https://rocketmq.apache.org/docs/quickStart/01quickstart/)。单主节点、异步刷盘不能代替复制集群与容灾配置。

#### 6. Kafka 页面：Kafbat UI JAR

从 [v1.5.0 官方发布页](https://github.com/kafbat/kafka-ui/releases/tag/v1.5.0) 下载 `api-v1.5.0.jar` 到 `$MQ_BASE/apps/`。该 JAR 使用 **Java 25**，不要用前面的 JDK 17 或业务 JDK 21 启动。

创建 `$MQ_BASE/conf/kafka-ui.yml`：

```yaml
server:
  address: 192.168.0.84
  port: 18080
kafka:
  clusters:
    - name: start-mq-native
      bootstrapServers: 192.168.0.84:9092
      readOnly: true
auth:
  type: LOGIN_FORM
spring:
  security:
    user:
      name: startmq
      password: ${MQ_UI_PASSWORD}
dynamic:
  config:
    enabled: false
mcp:
  enabled: false
```

```bash
read -rsp 'Kafka UI password: ' MQ_UI_PASSWORD
export MQ_UI_PASSWORD
"$JAVA25_HOME/bin/java" -Xms128m -Xmx512m \
  -jar "$MQ_BASE/apps/api-v1.5.0.jar" \
  --spring.config.additional-location="file:$MQ_BASE/conf/kafka-ui.yml"
```

登录 `http://192.168.0.84:18080`，确认集群 ONLINE，进入 Topics → Messages。Ctrl+C 停止前台页面，退出后执行 `unset MQ_UI_PASSWORD`。使用 systemd 时通过权限为 `600` 的环境文件提供密码，不把密码写进命令参数或 Git。配置参考 [Kafbat 官方配置](https://ui.docs.kafbat.io/configuration/configuration-file)。

#### 7. RocketMQ Dashboard JAR

从 [官方 2.1.0 源码发行页](https://dlcdn.apache.org/rocketmq/rocketmq-dashboard/2.1.0/) 下载源码并解压，在源码目录执行：

```bash
export JAVA_HOME="$JAVA17_HOME"
export PATH="$JAVA_HOME/bin:$PATH"
mvn clean package -Dmaven.test.skip=true
cp target/rocketmq-dashboard-2.1.0.jar "$MQ_BASE/apps/"
mkdir -p "$MQ_BASE/conf/rocketmq-dashboard"
```

构建包括前端资源，首次构建需要下载 Maven / 前端构建依赖。版本和 JDK 要求见 [2.1.0 POM](https://github.com/apache/rocketmq-dashboard/blob/rocketmq-dashboard-2.1.0/pom.xml)。

在 `$MQ_BASE/conf/rocketmq-dashboard/users.properties` 配置 `startmq=<实际页面密码>,0`，文件设为 `600`；按下面的完整内容创建同目录的 `role-permission.yml`，使用查询权限规则：

```yaml
# Normal users can inspect messages; publish/retry/reset/delete APIs are excluded.
rolePerms:
  Normal:
    - /rocketmq/*.query
    - /ops/*.query
    - /dashboard/*.query
    - /topic/*.query
    - /topic/list.queryTopicType
    - /producer/*.query
    - /message/*.query
    - /messageTrace/*.query
    - /monitor/*.query
    - /consumer/*.query
    - /cluster/*.query
    - /dlqMessage/*.query
```

启动：

```bash
"$JAVA17_HOME/bin/java" -Xms128m -Xmx512m \
  -Dserver.address=192.168.0.84 -Dserver.port=18081 \
  -Drocketmq.namesrv.addr=127.0.0.1:9876 \
  -Drocketmq.config.loginRequired=true \
  -Drocketmq.config.dataPath="$MQ_BASE/conf/rocketmq-dashboard" \
  -jar "$MQ_BASE/apps/rocketmq-dashboard-2.1.0.jar"
```

登录 `http://192.168.0.84:18081`，检查集群、Topic 和消息查询。Ctrl+C 停止前台进程；停止页面不会停止 Broker。

若该发行包也出现 `start-mq-broker:undefined`，先将下列完整代码保存为 `$MQ_BASE/conf/patch-dashboard-label.py`：

```python
"""Create static overrides for the pinned Dashboard's brokerId label bug."""
import argparse
import hashlib
import re
from pathlib import Path
from urllib.parse import urljoin
from urllib.request import urlopen


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--base-url', required=True)
    parser.add_argument('--output', required=True, type=Path)
    args = parser.parse_args()
    with urlopen(args.base_url, timeout=30) as response:
        html = response.read().decode('utf-8')
    scripts = re.findall(r'<script[^>]+src="([^"]+)"', html)
    main_scripts = [src for src in scripts if '/static/js/main.' in src]
    if len(main_scripts) != 1:
        raise SystemExit('Expected one pinned Dashboard main script; no files changed')
    source = main_scripts[0]
    with urlopen(urljoin(args.base_url, source), timeout=30) as response:
        script = response.read().decode('utf-8')
    old = 'l.push("".concat(e.brokerName,":").concat(e.index))'
    new = 'l.push("".concat(e.brokerName,":").concat(e.brokerId))'
    if script.count(old) != 1:
        raise SystemExit('Expected exactly one original label expression; no files changed')
    patched = script.replace(old, new)
    digest = hashlib.sha256(patched.encode('utf-8')).hexdigest()[:12]
    target = '/static/js/main.broker-id-' + digest + '.js'
    output_script = args.output / target.lstrip('/')
    output_script.parent.mkdir(parents=True, exist_ok=True)
    output_script.write_text(patched, encoding='utf-8')
    (args.output / 'index.html').write_text(html.replace(source, target), encoding='utf-8')
    print('Created static override:', target)


if __name__ == '__main__':
    main()
```

再生成覆盖：

```bash
python3 "$MQ_BASE/conf/patch-dashboard-label.py" \
  --base-url http://192.168.0.84:18081 --output "$MQ_BASE/conf/dashboard-overrides"
```

脚本只匹配已确认的错误表达式，若提示不匹配，不要强行替换。生成后停止页面，在原启动命令的 **`-jar` 之前**追加如下 JVM 参数再启动：

```text
-Dspring.web.resources.static-locations=file:<MQ_BASE>/conf/dashboard-overrides/,classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
```

替换绝对路径后生效，浏览器 Ctrl+F5 刷新。去掉该参数即可恢复 JAR 原版资源。原生源码构建与当前 Docker 镜像可能不同，不保证两者都出现同一标签问题。

#### 8. 后端连接与验收

应用配置示例（按需开启，不必全部开启）：

```yaml
mesh:
  mq:
    enabled: true
    default-provider: kafka
    redis:
      enabled: true
      queue-shards: 16
    kafka:
      enabled: true
      queue-shards: 16
      max-attempts: 3
      retry-backoff-millis: 1000
    rabbitmq:
      enabled: true
      queue-shards: 16
    rocketmq:
      enabled: true
      queue-shards: 16
spring:
  rabbitmq:
    host: 192.168.0.84
    port: 5672
    username: startmq
    password: ${MQ_RABBIT_PASSWORD}
  cloud:
    stream:
      binders:
        kafka:
          type: kafka
        rabbitmq:
          type: rabbit
        rocketmq:
          type: rocketmq
      kafka:
        binder:
          brokers: 192.168.0.84:9092
      rocketmq:
        binder:
          name-server: 192.168.0.84:9876
```

Redis 连接通过应用已有 Redisson 配置提供，使用本教程的密码与端口。上述 YAML 不会替你创建 `RedissonClient`。RabbitMQ 密码变量是应用进程自己的环境变量，创建账号后 unset 的终端变量不会自动传给应用服务。

按 README 示例先发布独立验收主题，确认消费者收到相同 messageId；队列用同一 orderingKey 检查顺序。构造独立失败消息，验证业务总共尝试 3 次、`_dlq` 存在原消息、后续消息继续。死信名称与页面操作见 [管理页面与死信查看](#管理页面与死信查看)。RabbitMQ 已 ACK 的历史正文不可查，预览未消费消息会重新入队并可能影响顺序。

故障定位先检查端口占用、启动日志、Java 版本、密码/ACL、Kafka advertised.listeners、RocketMQ brokerIP1、客户端到实际 Broker 的连通性。原生步骤已核对官方资料，但未在现有 VM 另装运行；生产容量、持久化、复制与容灾需独立验收。

### 2. Docker 部署

#### 2.1 完整复制执行

适用于**全新 Linux 主机**，先安装 Docker Engine、Compose 插件及 Python 3，并确保当前用户能运行 `docker info`。至少为中间件和页面预留足够内存、磁盘，当前配置限制总计约 7.25 GiB，主机还需预留系统余量。下面是独立单机部署，不依赖仓库中的任何配置文件。

**完整复制下面一个代码块执行**，输入主机 IP（回车默认 `192.168.0.84`）。脚本生成所有配置、随机密码、持久化数据卷，启动 Redis、Kafka、RabbitMQ、RocketMQ 和两个页面，并自动修复当前 Dashboard 标签问题。生成目录为 `$HOME/start-mq-standalone`；发现已有目录、同名容器或占用端口会停止，不覆盖现有部署。当前 VM 已运行同名容器，不需要重新安装。

```bash
bash <<'MQ_INSTALL'
set -euo pipefail
command -v docker >/dev/null
command -v python3 >/dev/null
docker compose version >/dev/null
docker info >/dev/null
read -rp '部署主机内网 IPv4 [192.168.0.84]: ' MQ_HOST </dev/tty
MQ_HOST="${MQ_HOST:-192.168.0.84}"
python3 - "$MQ_HOST" <<'PY_IP'
import ipaddress, sys
ipaddress.IPv4Address(sys.argv[1])
PY_IP
MQ_DIR="$HOME/start-mq-standalone"
if [ -e "$MQ_DIR" ]; then
  printf '目录已存在：%s。为保留原配置，本脚本停止；已有部署请使用下文启停命令。\n' "$MQ_DIR"
  exit 1
fi
for name in start-mq-redis start-mq-kafka start-mq-rabbitmq start-mq-rocketmq-namesrv start-mq-rocketmq-broker start-mq-kafka-ui start-mq-rocketmq-dashboard; do
  if docker container inspect "$name" >/dev/null 2>&1; then
    printf '已有容器 %s，请先安排迁移或改用新主机。\n' "$name"
    exit 1
  fi
done
# 先检查端口，避免与现有服务争用。
python3 - "$MQ_HOST" <<'PY_PORT'
import socket, sys
for port in (16379, 9092, 5672, 15672, 9876, 10909, 10911, 18080, 18081):
    with socket.socket() as sock:
        sock.bind((sys.argv[1], port))
PY_PORT
umask 077
mkdir -p "$MQ_DIR/dashboard-overrides"
cd "$MQ_DIR"
MQ_PASSWORD="$(python3 -c 'import secrets; print(secrets.token_hex(12))')"
printf 'MQ_HOST=%s\nMQ_PASSWORD=%s\n' "$MQ_HOST" "$MQ_PASSWORD" > .env
printf 'startmq=%s,0\n' "$MQ_PASSWORD" > users.properties
cat > docker-compose.yml <<'MQ_COMPOSE'
services:
  redis:
    image: redis:7.4-alpine
    container_name: start-mq-redis
    restart: unless-stopped
    ports:
    - ${MQ_HOST}:16379:6379
    deploy:
      resources:
        limits:
          memory: 256m
    command:
    - redis-server
    - --appendonly
    - 'yes'
    - --requirepass
    - ${MQ_PASSWORD}
    volumes:
    - redis-data:/data
    environment:
      REDISCLI_AUTH: ${MQ_PASSWORD}
  kafka:
    image: apache/kafka:3.9.1
    container_name: start-mq-kafka
    restart: unless-stopped
    ports:
    - ${MQ_HOST}:9092:9092
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://${MQ_HOST}:9092
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@localhost:9093
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_NUM_PARTITIONS: 16
      KAFKA_LOG_DIRS: /var/lib/kafka/data
    deploy:
      resources:
        limits:
          memory: 2g
    volumes:
    - kafka-data:/var/lib/kafka/data
  rabbitmq:
    image: rabbitmq:3.13-management
    container_name: start-mq-rabbitmq
    restart: unless-stopped
    ports:
    - ${MQ_HOST}:5672:5672
    - ${MQ_HOST}:15672:15672
    environment:
      RABBITMQ_DEFAULT_USER: startmq
      RABBITMQ_DEFAULT_PASS: ${MQ_PASSWORD}
    deploy:
      resources:
        limits:
          memory: 1g
    volumes:
    - rabbitmq-data:/var/lib/rabbitmq
  rocketmq-namesrv:
    image: apache/rocketmq:5.3.2
    container_name: start-mq-rocketmq-namesrv
    restart: unless-stopped
    command: sh mqnamesrv
    ports:
    - ${MQ_HOST}:9876:9876
    deploy:
      resources:
        limits:
          memory: 512m
  rocketmq-broker:
    image: apache/rocketmq:5.3.2
    container_name: start-mq-rocketmq-broker
    restart: unless-stopped
    depends_on:
    - rocketmq-namesrv
    command: sh mqbroker -n rocketmq-namesrv:9876 -c /home/rocketmq/rocketmq-5.3.2/conf/broker.conf
    ports:
    - ${MQ_HOST}:10909:10909
    - ${MQ_HOST}:10911:10911
    volumes:
    - ./rocketmq-broker.conf:/home/rocketmq/rocketmq-5.3.2/conf/broker.conf:ro
    - rocketmq-data:/home/rocketmq/store
    deploy:
      resources:
        limits:
          memory: 2g
  kafka-ui:
    image: ghcr.io/kafbat/kafka-ui:v1.5.0@sha256:7cda86a33344160309fdb65146332e4da65db81a945614f2fe32e210803f6fd1
    container_name: start-mq-kafka-ui
    restart: unless-stopped
    mem_limit: 768m
    ports:
    - ${MQ_HOST}:18080:8080
    environment:
      JAVA_OPTS: -Xms128m -Xmx512m
      KAFKA_CLUSTERS_0_NAME: start-mq-test
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: start-mq-kafka:9092
      KAFKA_CLUSTERS_0_READONLY: 'true'
      MCP_ENABLED: 'false'
      DYNAMIC_CONFIG_ENABLED: 'false'
      AUTH_TYPE: LOGIN_FORM
      SPRING_SECURITY_USER_NAME: startmq
      SPRING_SECURITY_USER_PASSWORD: ${MQ_PASSWORD}
  rocketmq-dashboard:
    image: apacherocketmq/rocketmq-dashboard:2.1.0@sha256:ce78506bd6fe01095bf1b37c954625e363ba5afae5d1539b77f395785d445e33
    container_name: start-mq-rocketmq-dashboard
    restart: unless-stopped
    mem_limit: 768m
    ports:
    - ${MQ_HOST}:18081:8080
    environment:
      JAVA_OPTS: -Xms128m -Xmx512m -Dserver.port=8080 -Drocketmq.namesrv.addr=start-mq-rocketmq-namesrv:9876 -Drocketmq.config.loginRequired=true
        -Drocketmq.config.dataPath=/tmp/rocketmq-console/data -Dspring.web.resources.static-locations=file:/opt/dashboard-overrides/,classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
    volumes:
    - ./users.properties:/tmp/rocketmq-console/data/users.properties:ro
    - ./rocketmq-admin-permissions.yml:/tmp/rocketmq-console/data/role-permission.yml:ro
    - ./dashboard-overrides:/opt/dashboard-overrides:ro
volumes:
  redis-data: {}
  kafka-data: {}
  rabbitmq-data: {}
  rocketmq-data: {}
MQ_COMPOSE
cat > rocketmq-broker.conf <<MQ_BROKER
brokerClusterName=DefaultCluster
brokerName=start-mq-broker
brokerId=0
brokerIP1=${MQ_HOST}
deleteWhen=04
fileReservedTime=48
brokerRole=ASYNC_MASTER
flushDiskType=ASYNC_FLUSH
autoCreateTopicEnable=true
listenPort=10911

storePathRootDir=/home/rocketmq/store
storePathCommitLog=/home/rocketmq/store/commitlog
MQ_BROKER
cat > rocketmq-admin-permissions.yml <<'MQ_PERMISSIONS'
# Normal users can inspect messages; publish/retry/reset/delete APIs are excluded.
rolePerms:
  Normal:
    - /rocketmq/*.query
    - /ops/*.query
    - /dashboard/*.query
    - /topic/*.query
    - /topic/list.queryTopicType
    - /producer/*.query
    - /message/*.query
    - /messageTrace/*.query
    - /monitor/*.query
    - /consumer/*.query
    - /cluster/*.query
    - /dlqMessage/*.query
MQ_PERMISSIONS
cat > patch-dashboard-label.py <<'MQ_PATCH'
"""Create static overrides for the pinned Dashboard's brokerId label bug."""
import argparse
import hashlib
import re
from pathlib import Path
from urllib.parse import urljoin
from urllib.request import urlopen


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--base-url', required=True)
    parser.add_argument('--output', required=True, type=Path)
    args = parser.parse_args()
    with urlopen(args.base_url, timeout=30) as response:
        html = response.read().decode('utf-8')
    scripts = re.findall(r'<script[^>]+src="([^"]+)"', html)
    main_scripts = [src for src in scripts if '/static/js/main.' in src]
    if len(main_scripts) != 1:
        raise SystemExit('Expected one pinned Dashboard main script; no files changed')
    source = main_scripts[0]
    with urlopen(urljoin(args.base_url, source), timeout=30) as response:
        script = response.read().decode('utf-8')
    old = 'l.push("".concat(e.brokerName,":").concat(e.index))'
    new = 'l.push("".concat(e.brokerName,":").concat(e.brokerId))'
    if script.count(old) != 1:
        raise SystemExit('Expected exactly one original label expression; no files changed')
    patched = script.replace(old, new)
    digest = hashlib.sha256(patched.encode('utf-8')).hexdigest()[:12]
    target = '/static/js/main.broker-id-' + digest + '.js'
    output_script = args.output / target.lstrip('/')
    output_script.parent.mkdir(parents=True, exist_ok=True)
    output_script.write_text(patched, encoding='utf-8')
    (args.output / 'index.html').write_text(html.replace(source, target), encoding='utf-8')
    print('Created static override:', target)


if __name__ == '__main__':
    main()
MQ_PATCH
# Broker 容器使用非 root 用户，允许读取不含口令的 Broker 配置。
chmod 644 rocketmq-broker.conf
docker compose -p start-mq-standalone config --quiet
docker compose -p start-mq-standalone up -d
# 等待页面可达，再生成标签补丁。超时会明确失败，配置和数据保留以便排查。
python3 - "$MQ_HOST" <<'PY_READY'
import sys, time, urllib.request
url = 'http://' + sys.argv[1] + ':18081/'
for attempt in range(90):
    try:
        with urllib.request.urlopen(url, timeout=3) as response:
            if response.status == 200:
                break
    except OSError:
        pass
    time.sleep(2)
else:
    raise SystemExit('Dashboard 启动超时，请查看 docker compose logs rocketmq-dashboard')
PY_READY
python3 patch-dashboard-label.py --base-url "http://$MQ_HOST:18081" --output ./dashboard-overrides
# 覆盖目录已挂载，仅重启页面以刷新静态资源缓存。
docker compose -p start-mq-standalone restart rocketmq-dashboard
# 等待中间件就绪；不发布消息、不修改消费进度。
for attempt in $(seq 1 60); do
  if docker exec start-mq-redis redis-cli ping >/dev/null 2>&1 \
    && docker exec start-mq-kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list >/dev/null 2>&1 \
    && docker exec start-mq-rabbitmq rabbitmq-diagnostics -q ping >/dev/null 2>&1 \
    && docker exec start-mq-rocketmq-broker sh mqadmin clusterList -n rocketmq-namesrv:9876 >/dev/null 2>&1; then
    break
  fi
  if [ "$attempt" -eq 60 ]; then
    echo '中间件未全部就绪，请查看 docker compose logs；不要重复运行安装脚本。'
    exit 1
  fi
  sleep 2
done
docker compose -p start-mq-standalone ps
printf '\n部署目录：%s\nKafka：http://%s:18080\nRabbitMQ：http://%s:15672\nRocketMQ：http://%s:18081\n' "$MQ_DIR" "$MQ_HOST" "$MQ_HOST" "$MQ_HOST"
printf '页面账号：startmq\n本次生成密码：%s\nRedis 与 RabbitMQ 使用同一生成密码，请保存 .env。\n' "$MQ_PASSWORD"
MQ_INSTALL
```

#### 2.2 登录与业务连接

三个页面的用户名均为 `startmq`，密码由脚本随机生成，保存在部署目录的 `.env` 中。Redis 不使用该用户名，仅使用生成密码；RabbitMQ 业务连接使用 `startmq` 和生成密码。Kafka 和 RocketMQ Broker 的示例连接为内网明文，页面账号不等于 Broker ACL。生产环境应单独配置认证、复制与容灾。

| 连接 | 主机端口 |
| --- | --- |
| Redis | 16379（启用密码与 AOF） |
| Kafka | 9092 |
| RabbitMQ / Management | 5672 / 15672 |
| RocketMQ NameServer / Broker / VIP | 9876 / 10911 / 10909 |
| Kafbat UI / RocketMQ Dashboard | 18080 / 18081 |

业务配置见上方普通部署的“后端连接与验收”，将 Redis 和 RabbitMQ 密码换成这次生成的密码。查看普通消息和死信见 [管理页面与死信查看](#管理页面与死信查看)。Kafka 的 advertised.listeners 和 RocketMQ brokerIP1 已写入输入的主机 IP；更换 IP 时同步调整 `.env` 和 `rocketmq-broker.conf`，不要仅改网页地址。

#### 2.3 日常启停与排查

以下命令分别按需执行，不是安装步骤：

```bash
cd "$HOME/start-mq-standalone"
docker compose -p start-mq-standalone ps
docker compose -p start-mq-standalone logs --tail=100
```

停止：

```bash
cd "$HOME/start-mq-standalone"
docker compose -p start-mq-standalone stop
```

再次启动：

```bash
cd "$HOME/start-mq-standalone"
docker compose -p start-mq-standalone start
```

脚本中已为四种中间件配置命名卷。不要运行 `down -v` 或手工删除数据卷；迁移时需要备份 `.env`、配置和各数据卷。RabbitMQ 首次创建用户后，单改 `.env` 不会更新已有数据库中的密码。

若首次启动超时，先在部署目录查看对应服务日志，修复后使用 `docker compose -p start-mq-standalone up -d`，而不是重跑会生成新密码的安装脚本。标签补丁脚本只处理固定镜像中已确认的表达式；升级 Dashboard 时需要重新核查，不能盲目应用旧补丁。移除静态覆盖 JVM 参数与挂载可以回退到镜像原版资源。

当前 VM 的旧部署项目名/路径与新脚本不同：Broker 位于 `/home/chanming/start-mq-e2e/integration`（项目 `integration`），页面位于 `/home/chanming/start-mq-admin`（项目 `start-mq-admin`）。这些是服务器已有运行文件，不属于仓库目录；本次文档整理不会删除或重启它们。

本文 Docker 合并脚本完成静态配置检查，不代表已在现有 VM 重新部署；历史中间件收发、死信和页面验收结果仍以专项报告为准。普通部署步骤已核对官方资料，生产高可用与容量需独立验收。
