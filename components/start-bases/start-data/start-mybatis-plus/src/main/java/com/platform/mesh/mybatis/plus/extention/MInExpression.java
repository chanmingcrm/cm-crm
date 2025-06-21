package com.platform.mesh.mybatis.plus.extention;

import net.sf.jsqlparser.expression.operators.relational.InExpression;

public class MInExpression extends InExpression {

    @Override
    public String toString() {
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append(super.getLeftExpression());
        statementBuilder.append(" ");
        if (super.isGlobal()) {
            statementBuilder.append("GLOBAL ");
        }

        if (super.isNot()) {
            statementBuilder.append("NOT ");
        }

        statementBuilder.append("IN ");
        statementBuilder.append("(");
        statementBuilder.append(this.getRightExpression());
        statementBuilder.append(")");
        return statementBuilder.toString();
    }
}
