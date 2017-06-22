package com.sample.asamba.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.library.asamba.smb.Asamba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * 任务类
 */
public abstract class SmbAsyncTask extends AsyncTask<Void, Void, SmbFile[]> {

    private static final String url = "http://gw.api.taobao.com/router/rest";
    private static final String appkey = "23757061";
    private static final String secret = "ea0ad68c933fbcc6babc9385c5b6131e";

    // Response数据解析
    private Context mContext;

    public SmbAsyncTask(Context context) {
        this.mContext = context;
    }

    /*
     * 子类一般不需覆盖此函数。 (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.before();
    }

    /*
     * 子类一般不需覆盖此函数。 (non-Javadoc)
     *
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected SmbFile[] doInBackground(Void... params) {

        try {
            SmbFile f = new SmbFile("smb://shaojun:123456@192.168.1.102/homes/pic/a.txt");
            SmbFileInputStream in = new SmbFileInputStream(f);
            String filePath = mContext.getCacheDir().getAbsolutePath()+ "/a.txt";
            System.out.println("filePath = " + filePath);
            File file = new File(mContext.getCacheDir(), "b.txt");
            System.out.println("file.getPath() = " + file.getPath());

            if (file.exists()) {
                file.delete();
            }
//            file.mkdirs();
            if (!file.exists()) {
                file.createNewFile();

            }
            FileOutputStream out = new FileOutputStream(file);

            System.out.println(file.exists());
            System.out.println(file.isDirectory());

            long t0 = System.currentTimeMillis();

            byte[] b = new byte[8192];
            int n, tot = 0;
            long t1 = t0;
            while ((n = in.read(b)) > 0) {
                System.out.println("t1 = " + t1);
                out.write(b, 0, n);
                tot += n;
                System.out.print('#');
            }

            long t = System.currentTimeMillis() - t0;

            System.out.println();
            System.out.println(tot + " bytes transfered in " + (t / 1000) + " seconds at " + ((tot / 1000) / Math.max(1, (t / 1000))) + "Kbytes/sec");

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SmbFile[] smbFiles) {
        super.onPostExecute(smbFiles);
        after(smbFiles);
    }


    /**
     * 异步过程执行后会在UI线程调用此函数。子类实现此函数可做些前期后处理
     *
     * @param
     */
    protected abstract void after(SmbFile[] files);

    /**
     * 异步过程执行前会在UI线程先调用此函数。子类实现此函数可做些前期处理
     */
    protected void before() {

    }


}
