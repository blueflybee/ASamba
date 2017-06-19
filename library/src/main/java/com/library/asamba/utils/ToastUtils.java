package com.library.asamba.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * <pre>
 *     author : blueflybee
 *     e-mail : wusj_2017@163.com
 *     time   : 2017/06/19
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class ToastUtils {

    /**
     * 显示吐司
     *
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

}
