package com.platform.mesh.upms.biz.modules.msg.userrel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.biz.modules.msg.base.domain.po.MsgBase;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.po.MsgNotice;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgReadDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgUserRelPageDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.po.MsgUserRel;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.MsgUserRelVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadModuleVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadUserVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.mapper.MsgUserRelMapper;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.IMsgUserRelService;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.manual.MsgUserRelServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 消息接收
 * @author 蝉鸣
 */
@Service
public class MsgUserRelServiceImpl extends ServiceImpl<MsgUserRelMapper, MsgUserRel> implements IMsgUserRelService  {

    @Autowired
    private MsgUserRelServiceManual msgUserRelServiceManual;

    /**
     * 功能描述:
     * 〈用户消息分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<MsgUserRelVO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<MsgUserRelVO> selectPage(MsgUserRelPageDTO pageDTO) {
        //设置当前用户
        Long userId = UserCacheUtil.getUserId();
        pageDTO.setUserId(userId);
        MPage<MsgUserRel> userRelMPage = MPageUtil.pageEntityToMPage(pageDTO, MsgUserRel.class);
        pageDTO.setDelFlag(YesOrNoEnum.YES.getValue());
        return this.getBaseMapper().selectMPage(userRelMPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈已读消息〉
     * @param msgReadDTO msgReadDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean readUserMsg(MsgReadDTO msgReadDTO) {
        //全部标记已读
        Long userId = UserCacheUtil.getUserId();
        if(YesOrNoEnum.YES.getValue().equals(msgReadDTO.getAll())){
            this.lambdaUpdate()
                    .set(MsgUserRel::getReadFlag,YesOrNoEnum.NO.getValue())
                    .eq(MsgUserRel::getUserId,userId)
                    .ne(MsgUserRel::getReadFlag, YesOrNoEnum.NO.getValue())
                    .update();
            return Boolean.TRUE;
        }
        //批量已读
        if(CollUtil.isEmpty(msgReadDTO.getIds())){
            return Boolean.FALSE;
        }
        this.lambdaUpdate()
                .set(MsgUserRel::getReadFlag,YesOrNoEnum.NO.getValue())
                .eq(MsgUserRel::getUserId,userId)
                .in(MsgUserRel::getId,msgReadDTO.getIds())
                .update();
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈未读消息数量角标〉
     * @return 正常返回:{@link List<UnReadVO>}
     * @author 蝉鸣
     */
    @Override
    public List<UnReadVO> countUnReadBase() {
        Long userId = UserCacheUtil.getUserId();
        //获取未读统计
        List<UnReadModuleVO> unReadModuleVOS = this.getBaseMapper().selectUnReadMsgCount(userId, YesOrNoEnum.YES.getValue(), YesOrNoEnum.YES.getValue());
        List<UnReadVO> unReadVOS = CollUtil.newArrayList();
        //计算总未读数：msgFlag = 0 前端视为全部
        UnReadVO totalVO = new UnReadVO();
        totalVO.setMsgFlag(MsgFlagEnum.INIT.getValue());
        if(CollUtil.isEmpty(unReadModuleVOS)){
            totalVO.setUnReadCount(NumberConst.NUM_0);
            unReadVOS.add(totalVO);
            return unReadVOS;
        }else{
            Integer total = unReadModuleVOS.stream().mapToInt(UnReadModuleVO::getUnReadCount).sum();
            totalVO.setUnReadCount(total);
            unReadVOS.add(totalVO);
        }
        //根据类型再次聚合
        //计算每个分类下总数
        Map<Integer, List<UnReadModuleVO>> unReadMap = unReadModuleVOS.stream()
                .filter(msg-> ObjectUtil.isNotEmpty(msg.getMsgFlag()))
                .collect(Collectors.groupingBy(UnReadModuleVO::getMsgFlag, Collectors.toList()));
        unReadMap.forEach((key,value)->{
            UnReadVO unReadVO = new UnReadVO();
            unReadVO.setMsgFlag(key);
            unReadVO.setModuleUnRead(value);
            if(CollUtil.isEmpty(value)){
                unReadVO.setUnReadCount(NumberConst.NUM_0);
            }else{
                Integer sum = value.stream().mapToInt(UnReadModuleVO::getUnReadCount).sum();
                unReadVO.setUnReadCount(sum);
            }
            unReadVOS.add(unReadVO);
        });
        return unReadVOS;
    }

    /**
     * 功能描述:
     * 〈保存消息〉
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void saveByMsg(MsgBase msgBase, List<Long> msgUserIds) {
        if(ObjectUtil.isEmpty(msgBase) || CollUtil.isEmpty(msgUserIds)){
            return;
        }
        List<MsgUserRel> msgUserRels = msgUserIds.stream().map(msgUserId -> {
            MsgUserRel userRel = new MsgUserRel();
            userRel.setMsgId(msgBase.getId());
            userRel.setUserId(msgUserId);
            userRel.setReadFlag(YesOrNoEnum.YES.getValue());
            userRel.setDelFlag(YesOrNoEnum.YES.getValue());
            return userRel;
        }).toList();
        this.saveBatch(msgUserRels);
        //查询人员未读数量
        List<UnReadUserVO> unReadUserVOS = this.getBaseMapper().selectUnReadUserCount(msgUserIds,
                YesOrNoEnum.YES.getValue(), YesOrNoEnum.YES.getValue());
        //极光推送
        msgUserRelServiceManual.jPush(msgBase,unReadUserVOS);
    }

    /**
     * 功能描述:
     * 〈保存消息〉
     * @author 蝉鸣
     */
    @Override
    public void saveByNotice(MsgBase msgBase, MsgNotice msgNotice) {
        //添加提醒人
        if(ObjectUtil.isEmpty(msgNotice.getNoticeUserId())){
            return;
        }
        MsgUserRel userRel = new MsgUserRel();
        userRel.setMsgId(msgBase.getId());
        userRel.setUserId(msgNotice.getNoticeUserId());
        userRel.setReadFlag(YesOrNoEnum.YES.getValue());
        userRel.setDelFlag(YesOrNoEnum.YES.getValue());
        userRel.setCreateUserId(msgNotice.getCreateUserId());
        userRel.setCreateTime(LocalDateTime.now());
        userRel.setUpdateUserId(msgNotice.getCreateUserId());
        userRel.setUpdateTime(LocalDateTime.now());
        this.save(userRel);
        //查询人员未读数量
        //查询人员未读数量
        List<UnReadUserVO> unReadUserVOS = this.getBaseMapper().selectUnReadUserCount(
                CollUtil.newArrayList(msgNotice.getNoticeUserId()), YesOrNoEnum.YES.getValue(), YesOrNoEnum.YES.getValue());
        //极光推送
        msgUserRelServiceManual.jPush(msgBase,unReadUserVOS);
    }

}
