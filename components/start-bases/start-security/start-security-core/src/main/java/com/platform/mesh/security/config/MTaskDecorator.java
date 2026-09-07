package com.platform.mesh.security.config;

import com.platform.mesh.utils.context.ThreadContextSnapshot;
import org.jspecify.annotations.NonNull;
import org.springframework.core.task.TaskDecorator;

/**
 * @description 自定义上下文传递装饰器
 * @author 蝉鸣
 */
public class MTaskDecorator implements TaskDecorator {

    @Override
    public @NonNull Runnable decorate(@NonNull Runnable runnable) {
        return ThreadContextSnapshot.capture().wrap(runnable);
    }
}
