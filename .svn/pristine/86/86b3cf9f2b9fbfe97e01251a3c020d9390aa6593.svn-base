package com.example.ronny_xie.gdcp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ronny_xie.gdcp.R;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CustomDialog extends Dialog {
	public CustomDialog(@NonNull Context context) {
		super(context);
	}

	public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
		super(context, themeResId);
	}

	public static class Builder{
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

	public Builder(Context context) {
		this.context = context;
	}
	public Builder setMessage(String message){
		this.message = message;
		return this;
	}
	public Builder setMessage(int message){
		this.message = (String) context.getText(message);
		return this;
	}
	public Builder setTitle(int title) {
		this.title = (String) context.getText(title);
		return this;
	}
	public Builder setTitle(String title) {
		this.title = title;
		return this;
	}
	public Builder setContentView(View v) {
		this.contentView = v;
		return this;
	}
	public Builder setPositiveButton(int positiveButtonText,
									 OnClickListener listener) {
		this.positiveButtonText = (String) context
				.getText(positiveButtonText);
		this.positiveButtonClickListener = listener;
		return this;
	}

	public Builder setPositiveButton(String positiveButtonText,
									 OnClickListener listener) {
		this.positiveButtonText = positiveButtonText;
		this.positiveButtonClickListener = listener;
		return this;
	}

	public Builder setNegativeButton(int negativeButtonText,
									 OnClickListener listener) {
		this.negativeButtonText = (String) context
				.getText(negativeButtonText);
		this.negativeButtonClickListener = listener;
		return this;
	}

	public Builder setNegativeButton(String negativeButtonText,
									 OnClickListener listener) {
		this.negativeButtonText = negativeButtonText;
		this.negativeButtonClickListener = listener;
		return this;
	}
	public CustomDialog create(){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
		);
		final CustomDialog dialog = new CustomDialog(context, R.style.dialog);
		View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
		dialog.addContentView(layout,new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		((TextView) layout.findViewById(R.id.title)).setText(title);
		if (positiveButtonText != null) {
			((Button) layout.findViewById(R.id.positiveButton))
					.setText(positiveButtonText);
			if (positiveButtonClickListener != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								positiveButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_POSITIVE);
							}
						});
			}
		} else {
			// if no confirm button just set the visibility to GONE
			layout.findViewById(R.id.positiveButton).setVisibility(
					View.GONE);
		}
		// set the cancel button
		if (negativeButtonText != null) {
			((Button) layout.findViewById(R.id.negativeButton))
					.setText(negativeButtonText);
			if (negativeButtonClickListener != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								negativeButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_NEGATIVE);
							}
						});
			}
		} else {
			// if no confirm button just set the visibility to GONE
			layout.findViewById(R.id.negativeButton).setVisibility(
					View.GONE);
		}
		if (message != null) {
			((TextView) layout.findViewById(R.id.message)).setText(message);
		} else if (contentView != null) {
			// if no message set
			// add the contentView to the dialog body
			((LinearLayout) layout.findViewById(R.id.content))
					.removeAllViews();
			((LinearLayout) layout.findViewById(R.id.content)).addView(
					contentView, new ViewGroup.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
		}
		dialog.setContentView(layout);
		return dialog;
	}

}
}
