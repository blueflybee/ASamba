package com.library.asamba;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String SMB_URL_PWD = "smb://shaojun:123456@192.168.1.102/homes/";
    private static final String SMB_URL = "smb://192.168.1.102/homes/";

    @Test
    public void listString() {

        SmbFile file = null;
        try {
            file = new SmbFile(SMB_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            file.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        long t1 = System.currentTimeMillis();
        String[] files = new String[0];
        try {
            files = file.list();
        } catch (SmbException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis() - t1;

        for (int i = 0; i < files.length; i++) {
            System.out.println(" " + files[i]);
        }
        System.out.println();
        System.out.println(files.length + " files in " + t2 + "ms");
    }

    @Test
    public void testConnect() {

        SmbFile file = null;
        try {
            file = new SmbFile(SMB_URL_PWD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            file.connect();
            System.out.println(file.canRead());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void listFiles()  {

        int a = 0;
        SmbFile file = null;
        SmbFile[] files = new SmbFile[0];

        try {
            file = new SmbFile(SMB_URL_PWD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        long t1 = System.currentTimeMillis();

        try {
            files = file.listFiles();
        } catch (SmbException e) {
            e.printStackTrace();
        }

        long t2 = System.currentTimeMillis() - t1;

        for (int i = 0; i < files.length; i++) {
            System.out.print(" " + files[i].getName());
        }
        System.out.println();
        System.out.println(files.length + " files in " + t2 + "ms");
    }


}