package com.stip.net.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * 分页数据，扩展了Iterable接口。
 * <p>创建日期：2015-05-22 13:51:34</p>
 * @author chenjunan
 * @version 2
 * @param <E>
 */
public class Pager<E> implements Iterable<E>, Serializable {

    private static final long serialVersionUID = 20100628L;

    private int totalRows;

    private List<E> data;

    /**
     * 构造函数
     */
    public Pager() {
    }

    /**
     * 构造函数
     * @param totalRows 总数
     * @param data 数据
     */
    public Pager(int totalRows, List<E> data) {
        this.totalRows = totalRows;
        this.data  = data;
    }

    /**
     * 统计出数据库记录的总数
     * @return 数据库记录数
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * <p>查询数据库返回的数据。</p>
     * @return 数据库记录
     */
    public List<E> getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator() {
        return data.iterator();
    }
}
