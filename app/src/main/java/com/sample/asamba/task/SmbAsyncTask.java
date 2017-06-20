package com.sample.asamba.task;

import android.content.Context;
import android.os.AsyncTask;

import com.library.asamba.smb.Asamba;

import jcifs.smb.SmbFile;

/**
 * 任务类
 *
 */
public abstract class SmbAsyncTask extends AsyncTask<Void , Void, SmbFile[]> {

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
    protected SmbFile[] doInBackground(Void ... params) {
        return getData();
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

    private SmbFile[] getData() {
        Asamba asamba = Asamba.with(mContext)
                .username("shaojun")
                .password("123456")
                .host("192.168.1.102")
                .get();


        return asamba.listFiles();
    }
}
