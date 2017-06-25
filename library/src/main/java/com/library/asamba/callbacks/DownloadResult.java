package com.library.asamba.callbacks;

import jcifs.smb.SmbFile;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/03/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class DownloadResult {

    private String des;
    private String message;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
