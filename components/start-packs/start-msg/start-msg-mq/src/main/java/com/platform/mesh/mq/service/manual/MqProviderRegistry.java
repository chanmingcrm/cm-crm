package com.platform.mesh.mq.service.manual;

import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.service.MqProvider;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 〈已启用 MQ 提供方注册表〉
 * @author 蝉鸣
 */
public class MqProviderRegistry {

    private final Map<MqProviderType, MqProvider> providers;
    /**
     * 功能描述:
     * 〈初始化并校验已启用的 MQ 提供方〉
     * @param providers 已启用的 MQ 提供方
     * @author 蝉鸣
     */
    public MqProviderRegistry(Iterable<MqProvider> providers) {
        Map<MqProviderType, MqProvider> providerMap = new EnumMap<>(MqProviderType.class);
        for (MqProvider provider : providers) {
            if (provider == null || provider.type() == null) {
                throw MqExceptionEnum.PROVIDER_TYPE_EMPTY.getBaseException();
            }
            if (providerMap.putIfAbsent(provider.type(), provider) != null) {
                throw MqExceptionEnum.PROVIDER_DUPLICATE
                        .getBaseException(List.of(provider.type()));
            }
        }
        this.providers = Map.copyOf(providerMap);
    }

    /**
     * 功能描述:
     * 〈获取指定类型的已启用 MQ 提供方〉
     * @param providerType MQ 提供方类型
     * @return MQ 提供方
     * @author 蝉鸣
     */
    public MqProvider requiredMqProvider(MqProviderType providerType) {
        MqProvider provider = providers.get(providerType);
        if (provider == null) {
            throw MqExceptionEnum.PROVIDER_DISABLED.getBaseException(List.of(providerType));
        }
        return provider;
    }

}
