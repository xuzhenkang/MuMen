package com.uesugi.mumen.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;

public class ShowAlertDialog {

	private Context mContext = null;
	private ProgressDialog mProgressDlg = null;
	private AlertDialog mAlertDlg = null;

	public ShowAlertDialog(Context context) {

		mContext = context;
	}

	public void showProgressDlg(String msg) {
		if (mProgressDlg != null) {
			mProgressDlg.dismiss();
			mProgressDlg = null;
		}

		mProgressDlg = new ProgressDialog(mContext);
		mProgressDlg.setMessage(msg);
		mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDlg.setIndeterminate(false);
		mProgressDlg.setCancelable(false);
		mProgressDlg.show();
	}

	public void dismissProgressDlg() {
		if (mProgressDlg != null) {
			mProgressDlg.dismiss();
			mProgressDlg = null;
		}
	}

	public void showAlertDialog(String msg) {

		dismissRegisterPromptDlg();

		Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("提示");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);

		mAlertDlg = builder.create();
		mAlertDlg.show();

	}

	public void dismissRegisterPromptDlg() {
		if (mAlertDlg != null) {
			mAlertDlg.dismiss();
			mAlertDlg = null;
		}
	}

}
