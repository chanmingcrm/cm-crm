package com.platform.mesh.core.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.platform.mesh.core.constants.DateConst;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @description java 8 时间默认序列化
 * @author 蝉鸣
 */
public class JavaTimeModule extends SimpleModule {

	public JavaTimeModule() {
		super(PackageVersion.VERSION);

		// 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
		this.addSerializer(Long.class, NumberSerializer.INSTANCE);
		this.addSerializer(Long.TYPE, NumberSerializer.INSTANCE);
		// ======================= 时间序列化规则 ===============================
		// yyyy-MM-dd HH:mm:ss
		this.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS)));
		// yyyy-MM-dd
		this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
		// HH:mm:ss
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
		// Instant 类型序列化
		this.addSerializer(Instant.class, InstantSerializer.INSTANCE);

		// ======================= 时间反序列化规则 ==============================
		// yyyy-MM-dd HH:mm:ss
		this.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS)));
		// yyyy-MM-dd
		this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
		// HH:mm:ss
		this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
		// Instant 反序列化
		this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
	}

}
