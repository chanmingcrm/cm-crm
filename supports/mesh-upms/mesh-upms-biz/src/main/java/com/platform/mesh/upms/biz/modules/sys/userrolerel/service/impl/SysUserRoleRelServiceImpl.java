package com.platform.mesh.upms.biz.modules.sys.userrolerel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.vo.SysUserRoleRelVO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.exception.UserRoleRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.mapper.SysUserRoleRelMapper;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.ISysUserRoleRelService;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.manual.SysUserRoleRelServiceManual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 用户信息
 * @author 蝉鸣
 */
@Service
public class SysUserRoleRelServiceImpl extends ServiceImpl<SysUserRoleRelMapper, SysUserRoleRel> implements ISysUserRoleRelService {

	private static final Logger log = LoggerFactory.getLogger(SysUserRoleRelServiceImpl.class);

	@Autowired
	private SysUserRoleRelServiceManual sysUserRoleRelServiceManual;

	/**
	 * 功能描述:
	 * 〈根据条件分页查询用户列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link MPage <SysUserRoleRelVO>}
	 * @author 蝉鸣
	 */
	@Override
	public MPage<SysUserRoleRelVO> selectPage(SysUserRoleRelPageDTO pageDTO) {
		if(ObjectUtil.isEmpty(pageDTO.getUserId())){
			throw UserRoleRelExceptionEnum.ADD_NO_ARGS.getBaseException();
		}
		MPage<SysUserRoleRel> userMPage = MPageUtil.pageEntityToMPage(pageDTO, SysUserRoleRel.class);
		return this.getBaseMapper().selectPageRel(userMPage,pageDTO);
	}

	/**
	 * 功能描述:
	 * 〈添加用户角色关系〉
	 * @param sysUserRoleRelDTO sysUserRoleRelDTO
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	@Transactional(rollbackFor = BaseException.class)
	public Boolean addUserRole(SysUserRoleRelDTO sysUserRoleRelDTO) {
		//删除旧的
		this.lambdaUpdate()
				.eq(SysUserRoleRel::getUserId, sysUserRoleRelDTO.getUserId())
//				.in(SysUserRoleRel::getRoleId, sysUserRoleRelDTO.getRoleIds())
				.remove();
		//新增
		List<SysUserRoleRel> list = CollUtil.newArrayList();
		for (Long roleId : sysUserRoleRelDTO.getRoleIds()) {
			SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
			sysUserRoleRel.setUserId(sysUserRoleRelDTO.getUserId());
			sysUserRoleRel.setRoleId(roleId);
			list.add(sysUserRoleRel);
		}
		this.saveBatch(list);
		return Boolean.TRUE;
	}

	/**
	 * 功能描述:
	 * 〈删除用户角色关系〉
	 * @param relId relId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	@Override
	public Boolean deleteUserRole(Long relId) {
		removeById(relId);
		return Boolean.TRUE;
	}
}
