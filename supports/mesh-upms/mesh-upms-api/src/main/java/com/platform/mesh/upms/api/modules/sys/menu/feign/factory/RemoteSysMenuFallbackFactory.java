package com.platform.mesh.upms.api.modules.sys.menu.feign.factory;

import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.RemoteSysMenuService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @author 蝉鸣
 */
@Component
public class RemoteSysMenuFallbackFactory  implements FallbackFactory<RemoteSysMenuService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteSysMenuFallbackFactory.class);

    @Override
    public RemoteSysMenuService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteSysMenuService() {
            @Override
            public Result<Boolean> addOrEditMenu(AppMenuBO appMenuBO) {
                return Result.error();
            }

            @Override
            public Result<Boolean> appModuleMenuDelete(List<Long> moduleIds) {
                return Result.error();
            }

            @Override
            public Result<Boolean> appModuleMenuClear(List<Long> moduleIds) {
                return Result.error();
            }
        };
    }
}
