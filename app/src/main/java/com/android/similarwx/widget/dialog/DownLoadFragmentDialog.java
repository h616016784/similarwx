package com.android.similarwx.widget.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.similarwx.R;

@SuppressLint("NewApi")
public class DownLoadFragmentDialog extends DialogFragment {
	private ProgressBar progress_horizontal;
	private TextView about_textper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_fragment_download, container);
		progress_horizontal=(ProgressBar) view.findViewById(R.id.progress_horizontal);
		about_textper=(TextView) view.findViewById(R.id.about_textper);
		progress_horizontal.setProgress(0);
		return view;
	}
	public void setProgress(int progress){
		progress_horizontal.setProgress(progress);
	}
	public void setProgressMax(int max){
		progress_horizontal.setMax(max);
	}
	public void setText(String percent){
		about_textper.setText(percent+" %");
	}
}
