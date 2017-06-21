package com.library.asamba.data;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : 用于对象操作的堆栈实现
 *     version: 1.0
 * </pre>
 */
public class Stack<T> extends java.util.Stack<T>{

    public Stack() {
        super();
    }

    @Override
    public T push(T item) {
        return super.push(item);
    }

    public int size() {
        return super.size();
    }

    @Override
    public synchronized T pop() {
        return super.pop();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
