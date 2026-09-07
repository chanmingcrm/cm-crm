package com.platform.mesh.ai.biz.chat.constant;

import com.platform.mesh.core.constants.NumberConst;

/**
 * @description AI基本变量
 * @author 蝉鸣
 */
public interface AiChatConst {

    Integer OCR_MAX_FILES = NumberConst.NUM_5;

    Integer OCR_MAX_FILE_BYTES = NumberConst.NUM_10 * NumberConst.NUM_1024 * NumberConst.NUM_1024;

    Integer OCR_MAX_TEXT_CHARS = NumberConst.NUM_10000 * NumberConst.NUM_4;

    String OCR_PROMPT_FORMAT = "%s%n%s";

    String OCR_FILE_TEXT_FORMAT = "%n%s:%n%s";

    Character JSON_OBJECT_START = '{';

    Character JSON_OBJECT_END = '}';

    Character JSON_STRING_MARK = '"';

    Character JSON_ESCAPE_MARK = '\\';

}
