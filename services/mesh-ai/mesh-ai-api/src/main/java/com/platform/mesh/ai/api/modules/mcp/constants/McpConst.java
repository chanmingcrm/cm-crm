package com.platform.mesh.ai.api.modules.mcp.constants;

import com.platform.mesh.core.constants.HttpConst;

import java.time.Duration;
import java.util.regex.Pattern;

/**
 * 功能描述:
 * 〈MCP 通用常量〉
 *
 * @author qingfeng
 */
public interface McpConst {

     String SERVER_PATH = "/mcp";
     String MANAGEMENT_PATH = "/ai/mcp";
     String TOOL_INVOKE_PATH = HttpConst.INTERNAL_MCP_TOOL_INVOKE_PATH;
     String TOOL_SESSION_INVOKE_PATH = "/api/mcp/tools/invoke";
     String ACCESS_KEY_CREDENTIAL_TYPE = "ai_mcp";
     String ACCESS_KEY_PREFIX = "wos_mcp_";
     String PRINCIPAL_PREFIX = "mcp:";
     Pattern ACCESS_KEY_PATTERN = Pattern.compile(
            "^" + ACCESS_KEY_PREFIX + "([A-Za-z0-9_-]{16})\\.[A-Za-z0-9_-]{43}$");
     Duration RATE_INTERVAL = Duration.ofMinutes(1);
     Duration LAST_USED_DEBOUNCE = Duration.ofMinutes(5);
     int ORIGIN_FILTER_ORDER_OFFSET = 10;
     int OAUTH_IDENTITY_FILTER_ORDER = -90;
     int ACCESS_KEY_STATUS_ACTIVE = 1;
     int ACCESS_KEY_STATUS_REVOKED = 0;
     int POLICY_TENANT_SCOPE = 1;
     int POLICY_ACCOUNT_SCOPE = 2;
     int DEFAULT_RATE_LIMIT = 60;
     String CONTEXT_PRINCIPAL = "mesh.mcp.principal";
     String CONTEXT_TENANT_ID = "mesh.mcp.tenant-id";
     String CONTEXT_ACCESS_KEY = "mesh.mcp.access-key";
     String REDIS_RATE_LIMIT_PREFIX = "ai:mcp:rate:";
     String REDIS_LAST_USED_PREFIX = "ai:mcp:last-used:";
     String REDIS_KEY_SEPARATOR = ":";
}
