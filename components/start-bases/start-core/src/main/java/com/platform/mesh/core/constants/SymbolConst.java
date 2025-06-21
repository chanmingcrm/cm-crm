package com.platform.mesh.core.constants;

/**
 * @description 自定义符号常量
 * @author 蝉鸣
 */
public interface SymbolConst {

    String AMPERSAND = "&";

    String AMPERSAND_ENCODED = "&amp;";

    String APOSTROPHE = "'";

    String APOSTROPHE_AND_COMMA = "',";

    String APOSTROPHE_AND_COMMA_AND_APOSTROPHE = "','";

    String AT = "@";

    String BACK_SLASH = "\\";

    String BLANK = "";

    String CDATA_OPEN = "<![CDATA[";

    String CDATA_CLOSE = "]]>";

    String CLOSE_BRACKET = "]";

    String CLOSE_CURLY_BRACE = "}";

    String CLOSE_PARENTHESIS = ")";

    String COLON = ":";

    String DOUBLE_COLON = "::";

    String COMMA = ",";

    String COMMA_AND_APOSTROPHE = ",'";

    String COMMA_AND_SPACE = ", ";

    String DASH = "-";

    String DOUBLE_APOSTROPHE = "''";

    String DOUBLE_CLOSE_BRACKET = "]]";

    String DOUBLE_OPEN_BRACKET = "[[";

    String DOUBLE_SLASH = "//";

    String EQUAL = "=";

    String GREATER_THAN = ">";

    String GREATER_THAN_OR_EQUAL = ">=";

    String FORWARD_SLASH = "/";

    String FORWARD_SLASH_MORE = "/+";

    String FOUR_SPACES = "    ";

    String FINISH_LEFT_ANGLE = "</";

    String FINISH_RIGHT_ANGLE = "/>";

    String LEFT_ANGLE = "<";

    String LESS_THAN = "<";

    String LESS_THAN_OR_EQUAL = "<=";

    String MINUS = "-";

    String NBSP = "&nbsp;";

    String NEW_LINE = "\n";

    String NOT_EQUAL = "!=";

    String DB_NOT_EQUAL = "<>";

    String NULL = "null";

    String OPEN_BRACKET = "[";

    String OPEN_CURLY_BRACE = "{";

    String OPEN_PARENTHESIS = "(";

    String PERCENT = "%";

    String PERIOD = ".";

    String PIPE = "|";

    String PLUS = "+";

    String POUND = "#";

    String QUESTION = "?";

    String QUOTE = "\"";

    String RETURN = "\r";

    String RETURN_NEW_LINE = "\r\n";

    String RIGHT_ANGLE = ">";

    String SEMICOLON = ";";

    String SLASH = FORWARD_SLASH;

    String SPACE = " ";

    String STAR = "*";

    String TAB = "\t";

    String TILDE = "~";

    String UNDERLINE = "_";

    String HTML_BR = "<br>";

    String TIME_ZONE = "Asia/Shanghai";

    String HTTP_CONCAT = "://";

    //匹配类似于${param} 中的 param
    String PATTERN_EL = "\\$\\{([^}]+)}";
    String PATTERN_HTML = "<(\\S*?)[^>]*>.*?|<.*? />";
    String PATTERN_NUM = "\\D";
    String PATTERN_L_CHAR = "[a-z]";
    String PATTERN_U_CHAR = "[A-Z]";
    String PATTERN_S_CHAR = "[^a-zA-Z0-9]";

    String PATTERN_PLUS = "\\+";
    String PATTERN_PLUS_ESCAPE = "%20";

    String PATTERN_ENCRYPT_TYPE = "^\\{.*?\\}";

    String PATTERN_FORWARD_SLASH = "^/+";

    String PATTERN_MORE_QUOTATION = "^\"+.*\"+$";
}
