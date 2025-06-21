package com.platform.mesh.bpm.biz.test;

import cn.hutool.core.collection.CollUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.manual.BpmInstVariableServiceManual;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BpmTest {

    private static final Logger log = LoggerFactory.getLogger(BpmTest.class);
    @Autowired
    private BpmInstVariableServiceManual bpmInstVariableService;

    @Test
    public void buildPipe() {
        //获取数据库
        Boolean aBoolean = bpmInstVariableService.compareType(LogicTypeEnum.AND.getValue(), true, true);
        System.out.println(aBoolean);

        //获取数据库
        Boolean bBoolean = bpmInstVariableService.compareRef(LogicRefEnum.EQ.getValue(), CollUtil.newArrayList("11"), CollUtil.newArrayList("11"));
        System.out.println(bBoolean);
    }

    @Test
    public void aviatorTest() {
        // 定义表达式
        String expressionString = "a + b * (c - d)";
        // 编译表达式
        Expression expression = AviatorEvaluator.compile(expressionString);
        // 准备变量
        Long a = 10L;
        Long b = 20L;
        Long c = 30L;
        Long d = 5L;

        // 执行表达式求值
        Long result = (Long) expression.execute(
                AviatorEvaluator.newEnv("a", a, "b", b, "c", c, "d", d)
        );

        // 输出结果
        System.out.println("Result: " + result); // 应该输出 260
    }

}
