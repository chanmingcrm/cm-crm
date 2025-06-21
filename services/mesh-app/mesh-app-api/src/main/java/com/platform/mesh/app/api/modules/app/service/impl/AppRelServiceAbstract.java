package com.platform.mesh.app.api.modules.app.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.service.IAppRelService;

public abstract class AppRelServiceAbstract<M extends BaseMapper<D>, D extends AppRelPO> extends CrudRepository<M, D> implements IAppRelService<D> {

}
