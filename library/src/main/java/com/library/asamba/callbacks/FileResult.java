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

public class FileResult {

    private SmbFile[] smbFiles;
    private String message;

    public SmbFile[] getSmbFiles() {
        return smbFiles;
    }

    public void setSmbFiles(SmbFile[] smbFiles) {
        this.smbFiles = smbFiles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
