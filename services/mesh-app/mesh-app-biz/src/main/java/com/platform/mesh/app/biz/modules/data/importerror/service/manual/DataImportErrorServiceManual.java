package com.platform.mesh.app.biz.modules.data.importerror.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.biz.modules.data.importerror.domain.po.DataImportError;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 导入错误信息
 * @author 蝉鸣
 */
@Service
public class DataImportErrorServiceManual {

    private final static Logger log = LoggerFactory.getLogger(DataImportErrorServiceManual.class);

    public PageVO<Object> packPageVO(MPage<DataImportError> page) {
        if(CollUtil.isEmpty(page.getRecords())){
            return new PageVO<>();
        }
        List<Object> records= CollUtil.newArrayList();
        for (DataImportError record : page.getRecords()) {
            if(StrUtil.isBlank(record.getRowData())){
                continue;
            }
            if(!JSONUtil.isTypeJSON(record.getRowData())){
                continue;
            }
            JSONObject entries = JSONUtil.parseObj(record.getRowData());
            //添加错误信息
            StringBuilder builder = StrUtil.builder();
            builder.append("第");
            builder.append(record.getRowNum());
            builder.append("行: ");
            builder.append(record.getErrorRecord());
            entries.set(NumberConst.NUM_0.toString(),builder.toString());
            records.add(entries);
        }
        PageVO<Object> pageVO = MPageUtil.convertToVO(page, Object.class);
        pageVO.setRecords(records);
        return pageVO;
    }
}