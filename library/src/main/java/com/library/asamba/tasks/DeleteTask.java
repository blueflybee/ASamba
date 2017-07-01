package com.library.asamba.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.library.asamba.callbacks.DeleteCallBack;
import com.library.asamba.callbacks.DeleteResult;
import com.library.asamba.callbacks.FilesCallBack;
import com.library.asamba.callbacks.FilesResult;

import java.net.MalformedURLException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : 删除smb文件夹任务类
 *     version: 1.0
 * </pre>
 */
public class DeleteTask extends AsyncTask<String, Void, DeleteResult> {

    private Context mContext;
    private DeleteCallBack mCallBack;


    public DeleteTask(Context context, DeleteCallBack callBack) {
        this.mContext = context;
        mCallBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DeleteResult doInBackground(String... params) {
        DeleteResult result = new DeleteResult();
        if (params.length == 0) {
            result.setSuccess(false);
            result.setMessage("文件未删除");
            return result;
        }

        SmbFile smbFile = null;
        SmbFile parentSmbFile = null;
        try {
            smbFile = new SmbFile(params[0]);
            parentSmbFile = new SmbFile(smbFile.getParent());
            smbFile.delete();
            if (smbFile.exists()) {
                result.setSuccess(false);
                result.setMessage("无法删除文件");
                return result;
            } else {
                result.setSuccess(true);
                result.setMessage(smbFile.getPath() + "已删除");
                result.setSmbFiles(parentSmbFile.listFiles());
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("找不到目标文件");
            return result;
        } catch (SmbException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("无法删除文件");
            return result;
        }


//        if (smbFile == null) return result;
//        try {
//            result.setSmbFiles(smbFile.listFiles());
//            return result;
//        } catch (SmbException e) {
//            e.printStackTrace();
//            result.setMessage(e.getMessage());
//            result.setSuccess(false);
//            return result;
//
//        }
    }

    @Override
    protected void onPostExecute(DeleteResult result) {
        super.onPostExecute(result);
        if (result.isSuccess()) {
            mCallBack.onSuccess(result.getSmbFiles(), result.getMessage());
        } else {
            mCallBack.onFail(result.getMessage());
        }
    }
}
