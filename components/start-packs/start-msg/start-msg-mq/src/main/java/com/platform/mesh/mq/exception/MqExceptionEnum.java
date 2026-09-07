package com.platform.mesh.mq.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 功能描述:
 * 〈MQ 组件异常枚举〉
 * @author 蝉鸣
 */
@Schema(description = "MQ 组件异常枚举", enumAsRef = true)
public enum MqExceptionEnum implements BaseExceptionEnum<MqExceptionEnum, String> {

    /**
     * 异常信息
     */
    PUBLISH_REQUEST_EMPTY("start-mq", 500, null, "MQ 发布请求不能为空"),
    PROVIDER_EMPTY("start-mq", 501, null, "MQ 提供方不能为空"),
    TOPIC_EMPTY("start-mq", 502, null, "MQ 主题不能为空"),
    MODE_EMPTY("start-mq", 503, null, "MQ 投递模式不能为空"),
    PAYLOAD_EMPTY("start-mq", 504, null, "MQ 消息载荷不能为空"),
    ORDERING_KEY_EMPTY("start-mq", 505, null, "队列模式的顺序键不能为空"),
    MODE_UNSUPPORTED("start-mq", 506, null, "MQ 提供方不支持当前投递模式：{}"),
    PROVIDER_TYPE_EMPTY("start-mq", 507, null, "MQ 提供方类型不能为空"),
    PROVIDER_DUPLICATE("start-mq", 508, null, "MQ 提供方类型重复：{}"),
    PROVIDER_DISABLED("start-mq", 509, null, "MQ 提供方未启用：{}"),
    LISTENER_INVALID("start-mq", 510, null, "MQ 监听方法定义无效：{}"),
    LISTENER_DUPLICATE("start-mq", 511, null, "MQ 监听方法重复：{}"),
    LISTENER_ACCESS_FAILED("start-mq", 512, null, "MQ 监听方法无法访问"),
    LISTENER_EXECUTE_FAILED("start-mq", 513, null, "MQ 监听方法执行失败"),
    SUBSCRIPTION_CLOSE_FAILED("start-mq", 514, null, "MQ 订阅关闭失败"),
    STREAM_PROVIDER_TYPE_INVALID("start-mq", 515, null, "Stream MQ 提供方类型无效"),
    QUEUE_PARTITIONS_INVALID("start-mq", 516, null, "MQ 队列分区数必须大于零"),
    PUBLISH_FAILED("start-mq", 517, null, "MQ 消息发布失败：{}"),
    BINDING_SERVICE_MISSING("start-mq", 518, null, "MQ 消费订阅缺少 BindingService"),
    MESSAGE_FORMAT_INVALID("start-mq", 519, null, "MQ 消息格式无效"),
    DIGEST_FAILED("start-mq", 520, null, "MQ 消费者标识摘要生成失败"),
    QUEUE_SHARDS_INVALID("start-mq", 521, null, "Redis 队列分片数必须大于零"),
    DEFAULT_PROVIDER_EMPTY("start-mq", 522, null,
            "未指定 MQ 提供方，请配置 mesh.mq.default-provider 或在发布时显式指定"),
    DEFAULT_PROVIDER_DISABLED("start-mq", 523, null,
            "默认 MQ 提供方 %s 未启用，请设置 mesh.mq.%s.enabled=true，或修改 mesh.mq.default-provider"),
    DEFAULT_PROVIDER_UNAVAILABLE("start-mq", 524, null,
            "默认 MQ 提供方 %s 已启用但未注册，请检查对应客户端 Bean、Binder 依赖及自动配置"),
    RETRY_POLICY_INVALID("start-mq", 525, null,
            "MQ 重试参数无效，maxAttempts 必须大于零，retryBackoffMillis 不能小于零：{}"),
    KAFKA_DELIVERY_METADATA_MISSING("start-mq", 526, null, "Kafka 消息缺少分区或偏移量元数据"),
    STREAM_BINDER_UNAVAILABLE("start-mq", 527, null, "MQ Binder 不可用：{}"),
    DEAD_LETTER_PUBLISH_FAILED("start-mq", 528, null, "MQ 死信消息写入失败：{}"),
    DELIVERY_INTERRUPTED("start-mq", 529, null, "MQ 消息确认前消费线程被中断"),
    SUBSCRIPTION_CLOSED("start-mq", 530, null, "MQ 消息确认前订阅已关闭"),
    REDIS_PENDING_PAYLOAD_MISSING("start-mq", 531, null, "Redis 待确认消息载荷缺失：{}"),
    REDIS_CONSUMER_IDENTITY_CONFLICT("start-mq", 532, null,
            "Redis 消费者标识冲突（组标识、已登记消费者、当前消费者）：{}");

    /**
     * 所属模块
     */
    private final String module;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误码对应的参数
     */
    private final Object[] args;

    /**
     * 错误消息
     */
    private final String desc;

    /**
     * 功能描述:
     * 〈初始化 MQ 组件异常信息〉
     * @param module 所属模块
     * @param code 错误码
     * @param args 错误码对应的参数
     * @param desc 错误消息
     * @author 蝉鸣
     */
    MqExceptionEnum(String module, Integer code, Object[] args, String desc) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.desc = desc;
    }

    /**
     * 功能描述:
     * 〈获取所属模块〉
     * @return 所属模块
     * @author 蝉鸣
     */
    @Override
    public String getModule() {
        return module;
    }

    /**
     * 功能描述:
     * 〈获取错误码〉
     * @return 错误码
     * @author 蝉鸣
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 功能描述:
     * 〈获取错误码对应的参数〉
     * @return 错误码对应的参数
     * @author 蝉鸣
     */
    @Override
    public Object[] getArgs() {
        return args;
    }

    /**
     * 功能描述:
     * 〈获取错误消息〉
     * @return 错误消息
     * @author 蝉鸣
     */
    @Override
    public String getDesc() {
        return desc;
    }
}
