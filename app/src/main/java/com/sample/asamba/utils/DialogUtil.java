/**
 *
 */
package com.sample.asamba.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * Dialog 工具类
 *
 */
public class DialogUtil {

	private static Dialog mAlertDialog = null;
	private static Dialog mProgressDialog = null;

	/**
	 * 取消所有弹出的对话框
	 */
	public static void dismissDialog() {
		if (mAlertDialog != null)
			mAlertDialog.dismiss();
		mAlertDialog = null;
	}

	/**
	 * 显示进度对话框（不可取消）
	 *
	 * @param title
	 * @param message
	 * @param onCancelListener
	 */
	public static void showProgress(Context context, String title, String message,
			DialogInterface.OnCancelListener onCancelListener) {
		hideProgress();
    mProgressDialog = new ProgressDialog(context);
		if (title != null) {
      mProgressDialog.setTitle(title);
		}
		if (message != null) {
			((ProgressDialog) mProgressDialog).setMessage(message);
		}
		if (onCancelListener != null) {
      mProgressDialog.setOnCancelListener(onCancelListener);
		}
    mProgressDialog.setCancelable(false);
    mProgressDialog.show();
	}

	public static void setMessage(String message) {
		if (mAlertDialog != null)
			if (mAlertDialog instanceof ProgressDialog) {
				if (message != null) {
					((ProgressDialog) mAlertDialog).setMessage(message);
				}
			}
	}

//	/**
//	 * 显示进度对话框（不可取消）
//	 *
//	 */
//
//	public static void showProgress(Context context) {
//		hideProgress();
//		mProgressDialog = CustomProgressDialog.createDialog(context);
//		mProgressDialog.setCancelable(true);
//		mProgressDialog.show();
//	}

	public static void hideProgress() {
		if (mProgressDialog != null) mProgressDialog.dismiss();
		mProgressDialog = null;
	}

//	/**
//	 * 显示”确定“ 对话框
//	 *
//	 * @param title
//	 * @param onOkClickListener
//	 */
//	public static void showOkAlertDialog(Context context, String title,
//			OnClickListener onOkClickListener) {
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				context);
//		alertDialogBuilder.setMessage(title);
//		alertDialogBuilder.setCancelable(false);
//		alertDialogBuilder.setPositiveButton(
//				context.getString(R.string.confirm), onOkClickListener);
//		mAlertDialog = alertDialogBuilder.show();
//	}

//	/**
//	 * 显示”是“ ”否“ 对话框
//	 *
//	 * @param title
//	 * @param onYesClickListener
//	 * @param onNoClickListener
//	 */
//	public static void showYesNoAlertDialog(Context context, String title,
//			OnClickListener onYesClickListener,
//			OnClickListener onNoClickListener) {
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				context);
//		if (title != null) {
//			alertDialogBuilder.setTitle(title);
//		}
//		if (onYesClickListener != null) {
//			alertDialogBuilder.setPositiveButton(
//					context.getString(R.string.yes), onYesClickListener);
//		}
//		if (onNoClickListener != null) {
//			alertDialogBuilder.setNegativeButton(
//					context.getString(R.string.no), onNoClickListener);
//		}
//		alertDialogBuilder.setCancelable(false);
//		mAlertDialog = alertDialogBuilder.show();
//	}

	/**
	 * 显示定义对话框
	 *
	 * @param title
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showCustomAlertDialog(Context context, String title,
			String confirmStr, String cancelStr,
			OnClickListener onYesClickListener,
			OnClickListener onNoClickListener) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		if (title != null) {
			alertDialogBuilder.setTitle(title);
		}

		if (onYesClickListener != null && confirmStr != null) {
			alertDialogBuilder
					.setPositiveButton(confirmStr, onYesClickListener);
		}
		if (onNoClickListener != null && cancelStr != null) {
			alertDialogBuilder.setNegativeButton(cancelStr, onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}

	/**
	 * 显示定义对话框
	 *
	 * @param title
	 * @param message
	 * @param confirmStr
	 * @param cancelStr
	 * @param onYesClickListener
	 * @param onNoClickListener
	 */
	public static void showCustomAlertDialogWithMessage(Context context,
			String title, String message, String confirmStr, String cancelStr,
			OnClickListener onYesClickListener,
			OnClickListener onNoClickListener) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		if (title != null) {
			alertDialogBuilder.setTitle(title);
		}
		if (message != null) {
			alertDialogBuilder.setMessage(message);
		}
		if (onYesClickListener != null && confirmStr != null) {
			alertDialogBuilder
					.setPositiveButton(confirmStr, onYesClickListener);
		}
		if (onNoClickListener != null && cancelStr != null) {
			alertDialogBuilder.setNegativeButton(cancelStr, onNoClickListener);
		}
		alertDialogBuilder.setCancelable(false);
		mAlertDialog = alertDialogBuilder.show();
	}

	/**
	 * 显示列表对话框
	 *
	 * @param context
	 * @param title
	 * @param items
	 * @param onItemClickListener
	 * @param negativeTxt
	 * @param onNegativeClickListener
	 * @param positiveTxt
	 * @param onPositiveClickListener
	 */
	public static void showCustomListDialog(Context context, String title,
			String[] items,
			OnClickListener onItemClickListener,
			String negativeTxt, OnClickListener onNegativeClickListener,
			String positiveTxt, OnClickListener onPositiveClickListener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setItems(items, onItemClickListener)
				.setCancelable(false)
				.setNegativeButton(negativeTxt, onNegativeClickListener)
				.setPositiveButton(positiveTxt, onPositiveClickListener);

		Dialog dialog = builder.show();
	}

	/**
	 * 显示列表对话框
	 *
	 * @param context
	 * @param title
	 * @param items
	 * @param onItemClickListener
	 */
	public static void showListDialog(Context context, String title,
			String[] items, OnClickListener onItemClickListener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setItems(items, onItemClickListener);

		Dialog dialog = builder.show();
	}

	/**
	 * 显示日期选择对话框
	 * @param context
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 */
	public static void showDatePickDialog(Context context,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		new DatePickerDialog(context, callBack, year,monthOfYear, dayOfMonth).show();

	}

}
