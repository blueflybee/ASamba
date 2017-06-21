package com.library.asamba.smb;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.data.Stack;

import java.net.MalformedURLException;


import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : samba文件操作类
 *     version: 1.0
 * </pre>
 */
public final class Asamba {
    private static final String TAG = Asamba.class.getSimpleName();
    public static final String SMB_AUTH = "smb://";

    private static Asamba mAsamba;
    private static String URL;

    private SmbFile mSmbFile;

    private String mUsername = "";
    private String mPassword = "";
    private String mHost = "";
    private String mPath = "";

    private Context mContext;

    private Stack<SmbFile> mStack = new Stack();

    private Asamba(Context context) {
        this.mContext = context;
    }

    public static Asamba with(Context context) {
        if (mAsamba == null) {
            mAsamba = new Asamba(context);
        }
        return mAsamba;
    }

    public Asamba username(String username) {
        this.mUsername = username;
        return mAsamba;
    }

    public Asamba password(String password) {
        this.mPassword = password;
        return mAsamba;
    }

    public Asamba host(String host) {
        this.mHost = host;
        return mAsamba;
    }

    public Asamba path(String path) {
        this.mPath = path;
        return mAsamba;
    }

    public Asamba init() {
        try {
            if (TextUtils.isEmpty(URL)) {
                StringBuilder builder = new StringBuilder();
                if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
                    builder.append(SMB_AUTH)
                            .append(mHost)
                            .append(mPath);
                } else {
                    builder.append(SMB_AUTH)
                            .append(mUsername)
                            .append(":")
                            .append(mPassword)
                            .append("@")
                            .append(mHost)
                            .append(mPath);
                }

                URL = builder.toString();
                Log.d(TAG, URL);
                this.mSmbFile = new SmbFile(URL);
            }
            return mAsamba;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
            return mAsamba;
        }
    }


    public Context getContext() {
        return mContext;
    }

    public void files(FilesCallBack filesCallBack) {


    }

}
