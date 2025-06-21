package com.platform.mesh.app.api.modules.app.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;

import java.util.List;
import java.util.Map;

/**
 * @description 消息工具类
 * @author 蝉鸣
 */
public class MsgUtil {

	/**
	 * 获取参数容器
	 * @return 装载参数的容器
	 */
	public static MsgNoticeBO getMsgBO(AppModuleBaseBO moduleInfo, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
		MsgNoticeBO msgNoticeBO = new MsgNoticeBO();
		//看数据是否包含组件信息
		List<AppFormColumnBO> remindList = columnBOS.stream().filter(columnBO -> columnBO.getCompMac().equals(CompMacEnum.REMIND.getDesc())).toList();
		if(CollUtil.isEmpty(remindList)){
			return msgNoticeBO;
		}
		AppFormColumnBO columnBO = CollUtil.getFirst(remindList);
		if(!dataMap.containsKey(columnBO.getColumnMac()) || ObjectUtil.isEmpty(dataMap.get(columnBO.getColumnMac()))){
			return msgNoticeBO;
		}
		//获取下次提醒的时间值
		Object object = dataMap.get(columnBO.getColumnMac());
		//组装消息提醒信息
		msgNoticeBO.setModuleId(columnBO.getModuleId());
		msgNoticeBO.setModuleName(moduleInfo.getModuleName());
		msgNoticeBO.setDataId(Long.parseLong(dataMap.get(StrConst.ID).toString()));
		msgNoticeBO.setMsgTitle(dataMap.get(StrConst.DATA_NAME).toString());
		msgNoticeBO.setNoticeStartTime(LocalDateTimeUtil.parse(object.toString(), DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter()));
		msgNoticeBO.setNoticeEndTime(LocalDateTimeUtil.parse(object.toString(), DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter()));
		msgNoticeBO.setNoticeNextTime(LocalDateTimeUtil.parse(object.toString(), DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter()));
		msgNoticeBO.setNoticeLoop(NumberConst.NUM_1);
		msgNoticeBO.setNoticeType(NumberConst.NUM_2);
		msgNoticeBO.setMsgUserIds(CollUtil.newArrayList(Long.parseLong(dataMap.get(StrConst.CREATE_USER_ID).toString())));
		return msgNoticeBO;
	}

}