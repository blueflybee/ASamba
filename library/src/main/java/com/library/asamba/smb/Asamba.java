package com.library.asamba.smb;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.library.asamba.callbacks.DeleteCallBack;
import com.library.asamba.callbacks.DeleteResult;
import com.library.asamba.callbacks.DownloadCallBack;
import com.library.asamba.callbacks.DownloadResult;
import com.library.asamba.callbacks.FilesResult;
import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.data.Stack;
import com.library.asamba.tasks.DeleteTask;
import com.library.asamba.tasks.DownloadTask;
import com.library.asamba.tasks.FilesTask;

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
    private int mReadTimeout = 20000;
    private int mConnectTimeout = 10000;

    //
    private String mOrigin;
    private String mDestination;

    private String mTarget;

    private Context mContext;

    private Stack<SmbFile> mStack;

    private Asamba(Context context) {
        this.mContext = context;
        this.mStack = new Stack<>();
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

    /**
     * Path must end with "/".
     *
     * @param path
     * @return
     */
    public Asamba path(String path) {
        this.mPath = path;
        return mAsamba;
    }

    public Asamba readTimeout(int readTimeout) {
        this.mReadTimeout = readTimeout;
        return mAsamba;
    }

    public Asamba connectTimeout(int connectTimeout) {
        this.mConnectTimeout = connectTimeout;
        return mAsamba;
    }

    public Asamba init() {
        try {
            if (!TextUtils.isEmpty(URL)) return mAsamba;
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
            mSmbFile.setConnectTimeout(mConnectTimeout);
            mSmbFile.setReadTimeout(mReadTimeout);
            this.mStack.push(mSmbFile);

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

    public Asamba enter(SmbFile target) {
        try {
            if (target.isFile()) return mAsamba;
            this.mStack.push(target);
            this.mSmbFile = mStack.peek();
            return mAsamba;
        } catch (SmbException e) {
            e.printStackTrace();
            return mAsamba;
        }

    }

    public Asamba back() {
        if (mStack.size() == 1) {
            mSmbFile = mStack.peek();
        } else {
            mStack.pop();
            mSmbFile = mStack.peek();
        }
        return mAsamba;
    }

    public void files(FilesCallBack filesCallBack) {
        new FilesTask(mContext, filesCallBack){
            @Override
            protected void onPostExecute(FilesResult result) {
                super.onPostExecute(result);
                if (result.isFailed()) {
                    mStack.pop();
                }
            }
        }.execute(mSmbFile);
    }

    public Asamba from(String origin) {
        this.mOrigin = origin;
        return mAsamba;
    }

    public Asamba to(String destination) {
        this.mDestination = destination;
        return mAsamba;
    }

    public void get(DownloadCallBack downloadCallBack) {
        new DownloadTask(mContext, downloadCallBack).execute(mOrigin, mDestination);
    }

    public Asamba target(String target) {
        this.mTarget = target;
        return mAsamba;
    }

    public void delete(DeleteCallBack deleteCallBack) {
        new DeleteTask(mContext, deleteCallBack).execute(mTarget);
    }
}
