package com.platform.mesh.upms.biz.modules.sys.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.account.domain.dto.AccountPageDTO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.SysAccountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 蝉鸣
 * 
 * @description 账户信息Mapper
 */
public interface SysAccountMapper extends BaseMapper<SysAccount> {

    MPage<SysAccountVO> selectMPage(IPage<SysAccount> accountMPage, @Param("pageDTO") AccountPageDTO accountPageDTO);

    List<SysAccount> getPassAccountByPhone(@Param("phone") String phone, @Param("sourceFlag") Integer sourceFlag);
}
