package com.library.asamba.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import static org.junit.Assert.*;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : Stack测试类
 *     version: 1.0
 * </pre>
 */
public class StackTest {

    private Stack<Book> stack;

    @Before
    public void setUp() throws Exception {
        stack = new Stack<>();
    }

    @After
    public void tearDown() throws Exception {
        stack.clear();
        stack = null;
    }

    @Test
    public void testPush() {
        Book book = new Book();
        stack.push(book);
        int size = stack.size();
        assertEquals(1, size);
        stack.push(book);
        assertEquals(2, stack.size());
    }

    @Test
    public void testPop() {
        Book book1 = new Book();
        book1.setName("name1");
        book1.setPageSize(100);
        book1.setAuthor("au1");
        stack.push(book1);

        Book book2 = new Book();
        book2.setName("name2");
        book2.setPageSize(200);
        book2.setAuthor("au2");
        stack.push(book2);

        assertEquals(2, stack.size());

        Book book = stack.pop();
        assertEquals(1, stack.size());
        assertEquals(book2, book);

        book = stack.pop();
        assertEquals(0, stack.size());
        assertEquals(book1, book);

        try {
            book = stack.pop();
            assertFalse(true);
        } catch (Exception e) {
            assertTrue("has throw exception",true);
            e.printStackTrace();
        }

    }

    @Test
    public void testClear() {
        Book book = new Book();
        stack.push(book);
        int size = stack.size();
        assertEquals(1, size);
        stack.push(book);
        assertEquals(2, stack.size());

        stack.clear();
        assertTrue(stack.isEmpty());
    }


}