package com.platform.mesh.upms.biz.modules.log.modify.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.dto.LogModifyPageDTO;
import com.platform.mesh.upms.biz.modules.log.modify.domain.po.LogModify;
import com.platform.mesh.upms.biz.modules.log.modify.domain.vo.LogModifyVO;
import com.platform.mesh.upms.biz.modules.log.modify.mapper.LogModifyMapper;
import com.platform.mesh.upms.biz.modules.log.modify.service.ILogModifyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 修改日志
 * @author 蝉鸣
 */
@Service()
public class LogModifyServiceImpl extends ServiceImpl<LogModifyMapper, LogModify> implements ILogModifyService {

    /**
     * 功能描述:
     * 〈获取变更日志分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<LogModifyVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<LogModifyVO> selectPage(LogModifyPageDTO pageDTO) {
        MPage<LogModify> mPage = MPageUtil.pageEntityToMPage(pageDTO, LogModify.class);
        MPage<LogModify> pages = this.lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(pageDTO.getModuleId()), LogModify::getModuleId, pageDTO.getModuleId())
                .eq(LogModify::getDataId, pageDTO.getDataId())
                .orderByDesc(LogModify::getCreateTime)
                .page(mPage);
        PageVO<LogModifyVO> pageVO = MPageUtil.convertToVO(pages, LogModifyVO.class);
        if(CollUtil.isNotEmpty(pageVO.getRecords())){
            pageVO.getRecords().forEach(item -> {
                item.setValueJson(JSONUtil.parseArray(item.getValueJson()));
            });
        }
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈新增修改日志〉
     * @param modifyBO modifyBO
     * @author 蝉鸣
     */
    @Override
    public void addModifyLog(LogModifyBO modifyBO) {
        LogModify logModify = BeanUtil.copyProperties(modifyBO, LogModify.class);
        logModify.setCreateTime(LocalDateTime.now());
        this.save(logModify);
    }
}

