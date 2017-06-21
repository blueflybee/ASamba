package com.library.asamba.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.library.asamba.callbacks.FileResult;
import com.library.asamba.callbacks.FilesCallBack;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : 浏览smb文件夹任务类
 *     version: 1.0
 * </pre>
 */
public class FilesTask extends AsyncTask<SmbFile, Void, FileResult> {

    private Context mContext;
    private FilesCallBack mCallBack;


    public FilesTask(Context context, FilesCallBack callBack) {
        this.mContext = context;
        mCallBack = callBack;
    }

    /*
     * 子类一般不需覆盖此函数。 (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /*
     * 子类一般不需覆盖此函数。 (non-Javadoc)
     *
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected FileResult doInBackground(SmbFile... params) {
        FileResult result = new FileResult();
        if (params.length == 0) return result;
        SmbFile smbFile = params[0];
        if (smbFile == null) return result;
        try {
            result.setSmbFiles(smbFile.listFiles());
            return result;
        } catch (SmbException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            return result;

        }
    }

    @Override
    protected void onPostExecute(FileResult result) {
        super.onPostExecute(result);
        if (result.getSmbFiles() == null) {
            mCallBack.onFail(result.getMessage());
        } else {
            mCallBack.onSuccess(result.getSmbFiles());
        }
    }
}
