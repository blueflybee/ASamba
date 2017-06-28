package com.library.asamba.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.library.asamba.callbacks.DownloadCallBack;
import com.library.asamba.callbacks.DownloadResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DownloadTask extends AsyncTask<String, Integer, DownloadResult> {

    private Context mContext;
    private DownloadCallBack mCallBack;
    private long totOriginLength = 0;
    private long totDesLength = 0;


    public DownloadTask(Context context, DownloadCallBack callBack) {
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
    protected DownloadResult doInBackground(String... params) {
        DownloadResult result = new DownloadResult();
        if (params.length < 2) {
            result.setSuccess(false);
            result.setMessage("文件无法下载");
            return result;
        }

        String origin = params[0];
        String des = params[1];
        if (TextUtils.isEmpty(origin) ||
                TextUtils.isEmpty(des)) {
            result.setSuccess(false);
            result.setMessage("文件无法下载");
            return result;
        }

        try {
            SmbFile smbFile = new SmbFile(origin);
            calculateOriginFileLength(smbFile);
            System.out.println("totleLength = " + totOriginLength);
            if (smbFile.isFile()) {
                return downloadFile(origin, des);
            } else {
                return downloadDir(origin, des);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("文件下载出错");
            return result;
        }
    }

    private void calculateOriginFileLength(SmbFile smbFile) throws SmbException {
        if (smbFile.isFile()) {
            totOriginLength += smbFile.length();
        } else if (smbFile.isDirectory()) {
            SmbFile[] smbFiles = smbFile.listFiles();
            for (SmbFile file : smbFiles) {
                if (file.isFile()) {
                    totOriginLength += file.length();
                }
                if (file.isDirectory()) {
                    calculateOriginFileLength(file);
                }
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mCallBack.onProgress(values[0]);
    }

    @NonNull
    private DownloadResult downloadFile(String origin, String des) {
        DownloadResult result = new DownloadResult();
        SmbFile smbFile = null;

        try {
            smbFile = new SmbFile(origin);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("目标文件路径错误");
            return result;
        }
        SmbFileInputStream in = null;
        try {
            in = new SmbFileInputStream(smbFile);
        } catch (SmbException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("目标文件无法访问");
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("目标文件路径错误");
            return result;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("未知主机地址");
            return result;
        }

        File file = new File(des);
        System.out.println("file.getPath() = " + file.getAbsolutePath());

        if (file.exists()) {
            file.delete();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage("本地文件无法创建");
                return result;
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("本地文件不存在");
            return result;
        }

//        System.back.println(file.exists());
//        System.back.println(file.isDirectory());

        long t0 = System.currentTimeMillis();

        byte[] b = new byte[2048];
        int n, tot = 0;
        long t1 = t0;
        try {
            while ((n = in.read(b)) > 0) {
                System.out.println("t1 = " + t1);
                out.write(b, 0, n);
                tot += n;
//                System.back.println("totDesLength++++++++++++++++++++++++++ = " + totDesLength);
                totDesLength += n;
                int persent = (int) ((double) totDesLength / totOriginLength * 100);
                System.out.println("persent = " + persent);

                publishProgress(persent);

                System.out.print('#');
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("文件下载出错，请重新下载");
            return result;
        }

        long t = System.currentTimeMillis() - t0;

        System.out.println();
        System.out.println(tot + " bytes transfered in " + (t / 1000) + " seconds at " + ((tot / 1000) / Math.max(1, (t / 1000))) + "Kbytes/sec");

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setSuccess(true);
        result.setMessage("文件下载成功！");
        result.setDes(des);
        return result;
    }

    @NonNull
    private DownloadResult downloadDir(String origin, String des) {
        System.out.println("origin = " + origin);
        System.out.println("des = " + des);
        DownloadResult result = new DownloadResult();
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile(origin);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("目标文件路径错误");
            return result;
        }


        File file = new File(des);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println("file.getPath() = " + file.getAbsolutePath());
        System.out.println("file.exists() = " + file.exists());
        System.out.println("file.isDirectory() = " + file.isDirectory());


        SmbFile[] smbFiles = null;
        try {
            smbFiles = smbFile.listFiles();
        } catch (SmbException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("文件下载出错");
            return result;
        }
        try {
            for (SmbFile originFile : smbFiles) {

                String originPath = originFile.getPath();
                String desPath = new File(file, originFile.getName()).getAbsolutePath();
                System.out.println("originPath = " + originPath);
                System.out.println("desPath = " + desPath);
                if (originFile.isFile()) {
                    downloadFile(originPath, desPath);
                } else {
                    downloadDir(originPath, desPath);
                }

            }

        } catch (SmbException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("文件下载出错");
        }

        result.setSuccess(true);
        result.setMessage("下载成功！");
        result.setDes(des);
        return result;
    }

    @Override
    protected void onPostExecute(DownloadResult result) {
        super.onPostExecute(result);
        if (result.isSuccess()) {
            mCallBack.onSuccess(result.getMessage(), result.getDes());
        } else {
            mCallBack.onFail(result.getMessage());
        }
    }
}
