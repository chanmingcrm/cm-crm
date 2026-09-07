package com.platform.mesh.feign.context;

import com.platform.mesh.utils.context.ThreadContextAccessor;
import com.platform.mesh.utils.context.ThreadContextRegistry;

import java.util.Objects;
import java.util.Optional;

/**
 * 功能描述:
 * 〈跨异步线程业务边界使用的可信 Feign 身份上下文〉
 *
 * @author qingfeng
 */
public final class FeignIdentityContext {

    private static final ThreadLocal<Identity> IDENTITY = new ThreadLocal<>();

    @SuppressWarnings("unused")
    private static final ThreadContextRegistry.Registration CONTEXT_REGISTRATION =
            ThreadContextRegistry.register(new ThreadContextAccessor<Identity>() {
                @Override
                public Identity capture() {
                    return IDENTITY.get();
                }

                @Override
                public Scope install(Identity snapshot) {
                    Identity previous = IDENTITY.get();
                    setIdentity(snapshot);
                    return () -> setIdentity(previous);
                }
            });

    private FeignIdentityContext() {
    }

    /**
     * 功能描述:
     * 〈在当前线程绑定可信 Feign 身份〉
     * @param encodedUser URL 编码后的登录用户
     * @param accountId 账号 ID
     * @return 上下文清理作用域
     * @author qingfeng
     */
    public static Scope open(String encodedUser, String accountId) {
        return open(encodedUser, accountId, false);
    }

    /**
     * 功能描述:
     * 〈在当前线程绑定可信 Feign 身份及其认证来源〉
     * @param encodedUser URL 编码后的登录用户
     * @param accountId 账号 ID
     * @param accessKey 是否来源于 Access Key 认证
     * @return 上下文清理作用域
     * @author qingfeng
     */
    public static Scope open(String encodedUser, String accountId,
            boolean accessKey) {
        Identity previous = IDENTITY.get();
        IDENTITY.set(new Identity(encodedUser, accountId, accessKey));
        return () -> {
            if (previous == null) IDENTITY.remove(); else IDENTITY.set(previous);
        };
    }

    /**
     * 功能描述:
     * 〈获取当前线程可信 Feign 身份〉
     *
     * @return 可信 Feign 身份
     * @author qingfeng
     */
    public static Optional<Identity> current() {
        return Optional.ofNullable(IDENTITY.get());
    }

    private static void setIdentity(Identity identity) {
        if (identity == null) {
            IDENTITY.remove();
        }
        else {
            IDENTITY.set(identity);
        }
    }

    /**
     * 功能描述:
     * 〈可信 Feign 身份〉
     *
     * @param encodedUser URL 编码后的登录用户
     * @param accountId 账号 ID
     * @author qingfeng
     */
    public record Identity(String encodedUser, String accountId,
            boolean accessKey) {

        public Identity {
            Objects.requireNonNull(encodedUser, "登录用户不能为空");
            Objects.requireNonNull(accountId, "账号 ID 不能为空");
        }

        /**
         * 功能描述:
         * 〈判断身份是否来源于 Access Key 认证〉
         * @return true 来源于 Access Key，false 来源于登录身份
         * @author qingfeng
         */
        public boolean isAccessKey() {
            return accessKey;
        }
    }

    /**
     * 功能描述:
     * 〈可信 Feign 身份清理作用域〉
     *
     * @author qingfeng
     */
    public interface Scope extends AutoCloseable {

        @Override
        void close();
    }
}
