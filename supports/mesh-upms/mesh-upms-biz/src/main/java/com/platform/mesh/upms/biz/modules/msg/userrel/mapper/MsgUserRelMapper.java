package com.platform.mesh.upms.biz.modules.msg.userrel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgUserRelPageDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.MsgUserRelVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadModuleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 消息接收
 * @author 蝉鸣
 */
public interface MsgUserRelMapper extends BaseMapper<MsgUserRel> {

    MPage<MsgUserRelVO> selectMPage(IPage<MsgUserRel> iPage,@Param("pageDTO") MsgUserRelPageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    List<UnReadModuleVO> selectUnReadMsgCount(@Param("userId") Long userId, @Param("readFlag") Integer readFlag, @Param("delFlag") Integer delFlag);
}