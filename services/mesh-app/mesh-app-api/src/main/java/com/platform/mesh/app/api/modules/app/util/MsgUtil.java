package com.platform.mesh.app.api.modules.app.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.core.application.domain.bo.MsgNoticeSetBO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.upms.api.modules.event.SysModifyLogEvent;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.ModifyDataBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;

import java.time.LocalDateTime;
import java.util.Date;
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
	public static List<MsgNoticeBO> getMsgBO(AppModuleBaseBO moduleInfo, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
        List<MsgNoticeBO> msgNoticeBOS =  CollUtil.newArrayList();

		//看数据是否包含组件信息
		List<AppFormColumnBO> remindList = columnBOS.stream().filter(columnBO -> columnBO.getCompMac().equals(CompMacEnum.REMIND.getDesc())).toList();
		if(CollUtil.isEmpty(remindList)){
			return msgNoticeBOS;
		}
		String msgTitle = dataMap.get(StrConst.DATA_NAME).toString();
		if(StrUtil.isBlank(msgTitle)){
			msgTitle = dataMap.get(StrConst.DATA_MAC).toString();
		}
		String msgBody = StrUtil.EMPTY;
		if(dataMap.containsKey(StrConst.DATA_DESC)){
			msgBody = dataMap.get(StrConst.DATA_DESC).toString();
		}
		//如果是跟进记录
		List<String> relevanceAllFieldList = columnBOS.stream().filter(columnBO -> columnBO.getCompMac().equals(CompTypeEnum.RELEVANCE_ALL_FIELD.getDesc())).map(AppFormColumnBO::getColumnMac).toList();
		if(CollUtil.isNotEmpty(relevanceAllFieldList)){
			List<String> nameValue = CollUtil.newArrayList();
			for (String columnMac : relevanceAllFieldList) {
				nameValue.addAll(AppUtil.getListColumnNameValue(columnMac, dataMap));
			}
			if(CollUtil.isNotEmpty(nameValue)){
				msgBody =msgBody.concat(SymbolConst.COMMA).concat(String.join(SymbolConst.COMMA, nameValue));
			}
		}
		String finalMsgTitle = msgTitle;
		String finalMsgBody = msgBody;
		remindList
                .stream()
                .filter(columnBO -> dataMap.containsKey(columnBO.getColumnMac()) && ObjectUtil.isNotEmpty(dataMap.get(columnBO.getColumnMac())))
                .forEach(columnBO -> {
                    MsgNoticeBO msgNoticeBO = new MsgNoticeBO();
                    //获取下次提醒的时间值
                    Object object = dataMap.get(columnBO.getColumnMac());
                    //组装消息提醒信息
                    MsgNoticeSetBO noticeSetBO = BeanUtil.copyProperties(columnBO.getSetDataValue(), MsgNoticeSetBO.class);
                    msgNoticeBO.setModuleId(columnBO.getModuleId());
                    msgNoticeBO.setModuleName(moduleInfo.getModuleName());
                    msgNoticeBO.setDataId(Long.parseLong(dataMap.get(StrConst.ID).toString()));
                    msgNoticeBO.setMsgTitle(finalMsgTitle);
                    msgNoticeBO.setMsgBody(finalMsgBody);
                    msgNoticeBO.setMsgFlag(MsgFlagEnum.PLAN_TODO.getValue());
                    msgNoticeBO.setNoticeLoop(noticeSetBO.getNoticeLoop());
                    msgNoticeBO.setNoticeType(noticeSetBO.getNoticeType());
                    msgNoticeBO.setNoticePreDays(noticeSetBO.getNoticePreDays());
                    msgNoticeBO.setNoticeSufDays(noticeSetBO.getNoticeSufDays());
                    msgNoticeBO.setNoticeIntervalUnit(noticeSetBO.getNoticeIntervalUnit());
                    msgNoticeBO.setNoticeIntervalValue(noticeSetBO.getNoticeIntervalValue());
                    Date date = DateUtil.parse(StrUtil.toString(object.toString()));
                    LocalDateTime parse =  LocalDateTimeUtil.of(date);
                    msgNoticeBO.setNoticeSetTime(parse);
                    msgNoticeBO.setNoticeStartTime(msgNoticeBO.getNoticeSetTime().minusDays(noticeSetBO.getNoticePreDays()));
                    msgNoticeBO.setNoticeEndTime(msgNoticeBO.getNoticeSetTime().plusDays(noticeSetBO.getNoticeSufDays()));
                    msgNoticeBO.setMsgUserIds(CollUtil.newArrayList(Long.parseLong(dataMap.get(StrConst.CREATE_USER_ID).toString())));
					//获取下次联系时间
					LocalDateTime nextTime = DateTimeUtil.getNextTime(msgNoticeBO.getNoticeStartTime(), msgNoticeBO.getNoticeIntervalValue(), msgNoticeBO.getNoticeIntervalUnit());
					msgNoticeBO.setNoticeNextTime(nextTime);
					if (msgNoticeBO.getNoticeLoop().equals(NumberConst.NUM_1) || LocalDateTime.now().isBefore(msgNoticeBO.getNoticeEndTime())) {
                        msgNoticeBOS.add(msgNoticeBO);
                    }
                });
		return msgNoticeBOS;
	}

	/**
	 * 功能描述:
	 * 〈发送转移负责人日志信息〉
	 * @author 蝉鸣
	 */
	public static LogModifyBO addLogModify(Integer operateType,Long moduleId,Long dataId,String newValue){
		LogModifyBO modifyBO = new LogModifyBO();
		//无效信息不发送
		if(ObjectUtil.isEmpty(dataId)){
			return modifyBO;
		}
		ModifyDataBO modifyDataBO = new ModifyDataBO();
		modifyDataBO.setOperateType(operateType);
		modifyDataBO.setValueNew(newValue);
		modifyDataBO.setColumnMac("");
		modifyDataBO.setColumnName("");

		modifyBO.setModuleId(moduleId);
		modifyBO.setDataId(dataId);
		modifyBO.setOperateType(operateType);
		modifyBO.setValueJson(JSONUtil.toJsonStr(CollUtil.newArrayList(modifyDataBO)));
		SpringContextHolderUtil.publishEvent(new SysModifyLogEvent(modifyBO));
		return modifyBO;
	}

}