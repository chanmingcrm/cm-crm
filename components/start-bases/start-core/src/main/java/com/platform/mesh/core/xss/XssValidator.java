package com.platform.mesh.core.xss;


import com.platform.mesh.core.constants.SymbolConst;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 自定义xss校验注解实现
 * @author 蝉鸣
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		return !containsHtml(value);
	}

	public static boolean containsHtml(String value) {
		Pattern pattern = Pattern.compile(SymbolConst.PATTERN_HTML);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}