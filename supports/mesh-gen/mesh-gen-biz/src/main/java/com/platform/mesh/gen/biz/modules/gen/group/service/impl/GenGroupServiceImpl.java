package com.platform.mesh.gen.biz.modules.gen.group.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.gen.group.domain.dto.GenAllGroupDTO;
import com.platform.mesh.gen.biz.modules.gen.group.domain.po.GenGroup;
import com.platform.mesh.gen.biz.modules.gen.group.domain.vo.GenAllGroupVO;
import com.platform.mesh.gen.biz.modules.gen.group.exception.GenGroupExceptionEnum;
import com.platform.mesh.gen.biz.modules.gen.group.mapper.CodeGroupMapper;
import com.platform.mesh.gen.biz.modules.gen.group.service.IGenGroupService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.stereotype.Service;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 分组信息
 * @author 蝉鸣
 */
@Service
public class GenGroupServiceImpl extends ServiceImpl<CodeGroupMapper, GenGroup> implements IGenGroupService {

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link GenAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public GenAllGroupVO addAllGroup(GenAllGroupDTO allGroupDTO) {
        GenGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, GenGroup.class);
        this.save(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, GenAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link GenAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public GenAllGroupVO editAllGroup(GenAllGroupDTO allGroupDTO) {
        if(ObjectUtil.isEmpty(allGroupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(GenAllGroupDTO::getId);
            throw GenGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        GenGroup appAllGroup = BeanUtil.copyProperties(allGroupDTO, GenGroup.class);
        this.updateById(appAllGroup);
        return BeanUtil.copyProperties(appAllGroup, GenAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroup(Long allGroupId) {

        return this.removeById(allGroupId);
    }
}
