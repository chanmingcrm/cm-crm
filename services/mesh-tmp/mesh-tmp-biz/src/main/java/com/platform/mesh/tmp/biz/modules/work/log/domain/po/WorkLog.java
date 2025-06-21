package com.platform.mesh.tmp.biz.modules.work.log.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 工作日志跟进DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "work_log", autoResultMap = true)
public class WorkLog extends AppPO {

}