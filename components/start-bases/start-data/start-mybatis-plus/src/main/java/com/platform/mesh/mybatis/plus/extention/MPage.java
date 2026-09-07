package com.platform.mesh.mybatis.plus.extention;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.annotation.JSONType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @description 实体基类
 * @author 蝉鸣
 */
@JSONType(includes = {"records", "total"})
@ToString
public class MPage<T> implements IPage<T> {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 查询数据列表
     */
    protected List<T> records = CollUtil.newArrayList();
    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 20
     */
    protected long size = 20;
    /**
     * 当前页
     */
    protected long current = 1;
    /**
     * 排序字段信息
     */
    @Getter
    @Setter
    protected List<OrderItem> orders = CollUtil.newArrayList();
    /**
     * 自动优化 COUNT SQL
     */
    protected boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    protected boolean isSearchCount = true;
    /**
     * 是否命中count缓存
     */
    protected boolean hitCount = false;
    /**
     * countId
     */
    @Getter
    @Setter
    protected String countId;
    /**
     * countId
     */
    @Getter
    @Setter
    protected Long maxLimit;

    public MPage() {
    }

    /**
     * 分页构造函数
     * @param current 当前页
     * @param size    每页显示条数
     */
    public MPage(long current, long size) {
        this(current, size, 0);
    }

    public MPage(long current, long size, long total) {
        this(current, size, total, true);
    }

    public MPage(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public MPage(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public MPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public MPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public MPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public MPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String countId() {
        return getCountId();
    }

    @Override
    public Long maxLimit() {
        return getMaxLimit();
    }

    /**
     * 查找 order 中正序排序的字段数组
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     * @param items 条件
     * @return 返回分页参数本身
     */
    public MPage<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     * @param items 条件
     * @return 返回分页参数本身
     */
    public MPage<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return getOrders();
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql();
    }

    public MPage<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public MPage<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

}
