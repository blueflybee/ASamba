package com.library.asamba.tasks;

import android.content.Context;
import android.os.AsyncTask;

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
public class FilesTask extends AsyncTask<SmbFile , Void, Void> {

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
    protected Void doInBackground(SmbFile ... params) {
        SmbFile smbFile = params[0];
        try {

            SmbFile[] smbFiles = smbFile.listFiles();
            if (mCallBack != null) {
                mCallBack.onSuccess(smbFiles);
            }
        } catch (SmbException e) {
            if (mCallBack != null) {
                mCallBack.onFail(e.getMessage());
            }
            e.printStackTrace();


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
