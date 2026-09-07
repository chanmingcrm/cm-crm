package com.platform.mesh.ai.biz.modules.cc.chat.domain;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Buffers Coze output without exposing structured lead JSON to chat clients.
 */
public class AiReplyStreamBuffer {

    private final StringBuilder content = new StringBuilder();
    private Mode mode = Mode.UNDECIDED;

    public String append(String delta) {
        if (delta == null || delta.isEmpty()) {
            return null;
        }
        content.append(delta);
        if (mode == Mode.UNDECIDED) {
            String trimmed = content.toString().stripLeading();
            if (trimmed.isEmpty()) {
                return null;
            }
            mode = detectMode(trimmed);
            if (mode == Mode.PLAIN_TEXT) {
                return content.toString();
            }
        }
        return mode == Mode.PLAIN_TEXT ? delta : null;
    }

    public Result finish() {
        String raw = content.toString();
        if (mode == Mode.STRUCTURED_JSON || mode == Mode.FENCED_JSON) {
            try {
                String structuredJson = extractStructuredJson(raw);
                if (structuredJson == null) {
                    return Result.invalid();
                }
                JSONObject json = JSONUtil.parseObj(structuredJson);
                String visibleText = json.getStr("context");
                if (StrUtil.isBlank(visibleText)) {
                    return Result.invalid();
                }
                Object data = json.get("data");
                JSONObject drainageData = data == null ? null : JSONUtil.parseObj(data);
                return new Result(visibleText, drainageData, true, true);
            } catch (Exception ignored) {
                return Result.invalid();
            }
        }
        if (StrUtil.isBlank(raw)) {
            return Result.invalid();
        }
        return new Result(raw, null, true, false);
    }

    private Mode detectMode(String trimmed) {
        if (trimmed.startsWith("{")) {
            return Mode.STRUCTURED_JSON;
        }
        if ("```".startsWith(trimmed) || trimmed.startsWith("```")) {
            return Mode.FENCED_JSON;
        }
        return Mode.PLAIN_TEXT;
    }

    private String extractStructuredJson(String raw) {
        String trimmed = raw.trim();
        if (trimmed.startsWith("{")) {
            return trimmed;
        }
        if (!trimmed.startsWith("```")) {
            return null;
        }
        int lineEnd = trimmed.indexOf('\n');
        if (lineEnd < 0) {
            return null;
        }
        String header = trimmed.substring(3, lineEnd).trim();
        if (!header.isEmpty() && !"json".equalsIgnoreCase(header)) {
            return null;
        }
        String bodyAndFence = trimmed.substring(lineEnd + 1).trim();
        if (!bodyAndFence.endsWith("```")) {
            return null;
        }
        String body = bodyAndFence.substring(0, bodyAndFence.length() - 3).trim();
        return body.startsWith("{") ? body : null;
    }

    private enum Mode {
        UNDECIDED,
        PLAIN_TEXT,
        STRUCTURED_JSON,
        FENCED_JSON
    }

    public record Result(String visibleText, JSONObject drainageData, boolean valid, boolean structured) {
        private static Result invalid() {
            return new Result(null, null, false, false);
        }
    }
}
