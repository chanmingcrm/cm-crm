package com.platform.mesh.upms.biz.modules.msg.notice.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;

/**
 * @description 消息提醒
 * @author 蝉鸣
 */
public interface MsgNoticeMapper extends BaseMapper<MsgNotice> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<MsgNotice> getAllHandleNotice(IPage<MsgNotice> page);

    @InterceptorIgnore(tenantLine = "true")
    void clearNotice();
}