package com.platform.mesh.app.api.modules.app.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.IAppDataService;

public abstract class AppDataServiceAbstract<M extends BaseMapper<D>, D extends AppDataPO> extends CrudRepository<M, D> implements IAppDataService<D> {

}
