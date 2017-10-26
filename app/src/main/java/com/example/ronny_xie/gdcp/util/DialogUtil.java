package com.example.ronny_xie.gdcp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Administrator on 2017/7/16.
 */

public class DialogUtil {
	private Context context;
	private int resouceId;
	private String dialogTitle;
	private String dialogMessge;
	private String positvieText;
	private String negativeText;

	private View dialogView;
	private OnDialogListener listener;

	public DialogUtil(Context context, String dialogTitle, String dialogMessge, String positvieText, String negativeText) {
		this.context = context;
		this.dialogTitle = dialogTitle;
		this.dialogMessge = dialogMessge;
		this.positvieText = positvieText;
		this.negativeText = negativeText;
	}

	public DialogUtil(Context context, String dialogTitle, int resouceId, String positvieText, String negativeText) {
		this.context = context;
		this.dialogView=View.inflate(context,resouceId,null);
		this.dialogTitle = dialogTitle;
		this.positvieText = positvieText;
		this.negativeText = negativeText;
	}

	public View getDialogView() {
		return dialogView;
	}

	public void setDialogView(View dialogView) {
		this.dialogView = dialogView;
	}

	public DialogUtil setDialogListener(OnDialogListener listener) {
		this.listener = listener;
		return this;
	}

	public void showDialog(){
		CustomDialog.Builder dialog = new CustomDialog.Builder(context);
		dialog.setTitle(dialogTitle);
		if (dialogMessge!=null){
			dialog.setMessage(dialogMessge);
		}else {
			dialog.setContentView(dialogView);
		}
		dialog.setPositiveButton(positvieText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				dialogInterface.dismiss();
				if (listener!=null){
					listener.dialogNegativeListener(dialogView,dialogInterface,which);
				}
			}
		}).setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				dialogInterface.dismiss();
				if (listener!=null){
					listener.dialogPositiveListener(dialogView,dialogInterface,which);
				}
			}
		}).create().show();
	}
}
