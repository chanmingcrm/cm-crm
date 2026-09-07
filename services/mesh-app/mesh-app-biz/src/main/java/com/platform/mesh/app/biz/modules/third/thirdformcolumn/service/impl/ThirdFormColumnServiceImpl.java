package com.platform.mesh.app.biz.modules.third.thirdformcolumn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.dto.ThirdFormColumnDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.po.ThirdFormColumn;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.vo.ThirdFormColumnVO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.mapper.ThirdFormColumnMapper;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.service.IThirdFormColumnService;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
@Service
public class ThirdFormColumnServiceImpl extends ServiceImpl<ThirdFormColumnMapper, ThirdFormColumn> implements IThirdFormColumnService {


    /**
     * 功能描述:
     * 〈获取当前第三方字段信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link List<ThirdFormColumnVO>}
     * @author 蝉鸣
     */
    public List<ThirdFormColumnVO> getThirdFormColumn(Integer sourceFlag){
        List<ThirdFormColumn> listed = this.getBaseMapper().getThirdFormColumn(sourceFlag);
        if(CollUtil.isEmpty(listed)){
            return CollUtil.newArrayList();
        }
        return listed.stream().map(column->{
            ThirdFormColumnVO columnVO = BeanUtil.copyProperties(column, ThirdFormColumnVO.class);
            if(ObjectUtil.isNotEmpty(column.getColumnOption())){
                columnVO.setColumnOptionJson(JSONUtil.parseObj(column.getColumnOption()));
            }
            return columnVO;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈新增第三方字段〉
     * @param thirdFormColumnDTOS thirdFormColumnDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addThirdFormColumn(List<ThirdFormColumnDTO> thirdFormColumnDTOS){
        List<ThirdFormColumnDTO> columnDTOS = thirdFormColumnDTOS.stream()
                .filter(column -> ObjectUtil.isNotEmpty(column.getSourceFlag())).toList();
        if(CollUtil.isEmpty(columnDTOS)){
            return Boolean.FALSE;
        }
        ThirdFormColumnDTO columnDTO = CollUtil.getFirst(columnDTOS);
        List<String> columnMacs = thirdFormColumnDTOS.stream().map(ThirdFormColumnDTO::getColumnMac).toList();
        //删除已存在旧数据
        this.lambdaUpdate()
                .eq(ThirdFormColumn::getSourceFlag,columnDTO.getSourceFlag())
                .in(ThirdFormColumn::getColumnMac,columnMacs)
                .remove();
        List<ThirdFormColumn> thirdFormColumns = BeanUtil.copyToList(thirdFormColumnDTOS, ThirdFormColumn.class);
        this.saveBatch(thirdFormColumns);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈新增第三方字段〉
     * @param columnIds columnIds
     * @author 蝉鸣
     */
    public void delThirdFormColumn(List<Long> columnIds){
        //删除旧数据
        this.lambdaUpdate()
                .eq(ThirdFormColumn::getId,columnIds)
                .remove();
    }


}
