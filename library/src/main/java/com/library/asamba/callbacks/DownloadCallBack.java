package com.library.asamba.callbacks;

import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   : 文件夹浏览callback
 *     version: 1.0
 * </pre>
 */
public interface DownloadCallBack {

    void onSuccess(String message, String des);

    void onFail(String message);

    void onProgress(int progress);
}
