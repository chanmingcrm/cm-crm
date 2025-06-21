package com.platform.mesh.bpm.biz.data.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelAddDTO;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelEditDTO;
import com.platform.mesh.bpm.biz.data.form.domain.dto.BpmDataFormRelPageDTO;
import com.platform.mesh.bpm.biz.data.form.domain.po.BpmDataFormRel;
import com.platform.mesh.bpm.biz.data.form.domain.vo.BpmDataFormRelVO;
import com.platform.mesh.bpm.biz.data.form.exception.DataFormRelExceptionEnum;
import com.platform.mesh.bpm.biz.data.form.mapper.BpmDataFormRelMapper;
import com.platform.mesh.bpm.biz.data.form.service.IBpmDataFormRelService;
import com.platform.mesh.bpm.biz.data.form.service.manual.BpmDataFormRelServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 数据流程表单关系信息
 * @author 蝉鸣
 */
@Service()
public class BpmDataFormRelServiceImpl extends ServiceImpl<BpmDataFormRelMapper, BpmDataFormRel> implements IBpmDataFormRelService {


    @Autowired
    private BpmDataFormRelServiceManual bpmDataFormRelServiceManual;

    /**
     * 获取封装方法
     */
    public BpmDataFormRelServiceManual getServiceManual(){
        return bpmDataFormRelServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取分页信息〉
     * @return 正常返回:{@link PageVO<BpmDataFormRelVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmDataFormRelVO> selectPage(BpmDataFormRelPageDTO pageDTO) {
        MPage<BpmDataFormRel> mPage = MPageUtil.pageEntityToMPage(pageDTO, BpmDataFormRel.class);
        LambdaQueryWrapper<BpmDataFormRel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BpmDataFormRel::getModuleId, pageDTO.getModuleId());
        MPage<BpmDataFormRel> relMPage = this.page(mPage, queryWrapper);
        return MPageUtil.convertToVO(relMPage,BpmDataFormRelVO.class);
    }

    /**
     * 功能描述:
     * 〈根据流程Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormRel getDataFormRelByProcessId(Long processId) {
        return lambdaQuery()
                .eq(BpmDataFormRel::getTempProcessId, processId)
                .one();
    }

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    @Override
    public List<BpmDataFormRel> getDataFormRelByFormId(Long formId) {
        return lambdaQuery()
                .eq(BpmDataFormRel::getFormId, formId)
                .list();
    }

    /**
     * 功能描述:
     * 〈根据应用Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link List<BpmDataFormRel>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmDataFormRel> getDataFormRelByAppId(Long appId) {
        return lambdaQuery()
                .eq(BpmDataFormRel::getAppId, appId)
                .list();
    }

    /**
     * 功能描述:
     * 〈添加流程与业务表单绑定关系〉
     * @return 正常返回:{@link BpmDataFormRel}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormRel addDataFormRel(BpmDataFormRelAddDTO addDTO) {
        //删除旧数据 ：后续转化校验逻辑
        lambdaUpdate()
                .eq(BpmDataFormRel::getAppId,addDTO.getAppId())
                .eq(BpmDataFormRel::getModuleId,addDTO.getModuleId())
                .eq(BpmDataFormRel::getFormId,addDTO.getFormId())
                .eq(BpmDataFormRel::getTempProcessId,addDTO.getTempProcessId())
                .remove();
        //添加新数据
        BpmDataFormRel bpmDataFormRel = BeanUtil.copyProperties(addDTO, BpmDataFormRel.class);
        save(bpmDataFormRel);
        return bpmDataFormRel;
    }

    /**
     * 功能描述:
     * 〈修改流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result < BpmDataFormRelVO >}
     * @author 蝉鸣
     */
    @Override
    public BpmDataFormRel editDataFormRel(BpmDataFormRelEditDTO editDTO) {
        if(ObjectUtil.isEmpty(editDTO.getId())){
            throw DataFormRelExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        BpmDataFormRel bpmDataFormRel = BeanUtil.copyProperties(editDTO, BpmDataFormRel.class);
        updateById(bpmDataFormRel);
        return bpmDataFormRel;
    }

    /**
     * 功能描述:
     * 〈删除流程与业务表单绑定关系〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDataFormRel(Long relId) {
        removeById(relId);
        return Boolean.TRUE;
    }

}

