package com.example.worktools.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.example.worktools.R;
import com.example.worktools.dialog.dialoginstener.DialogNoClick;
import com.example.worktools.dialog.dialoginstener.DialogYesClick;


/**
 * Created by xuhao on 2017/7/27.
 */

public class TextDialog extends Dialog implements View.OnClickListener {
    private DialogNoClick leftClick;
    private DialogYesClick rightClick;

    public TextDialog setLeftClick(DialogNoClick leftClick) {
        this.leftClick = leftClick;
        return this;
    }

    public TextDialog setRightClick(DialogYesClick rightClick) {
        this.rightClick = rightClick;
        return this;
    }
    public TextDialog setTitle(String message){
        TextView textView= getWindow().findViewById(R.id.tv_title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
        return this;
    }
    public TextDialog setMessage(String message){
        TextView textView= getWindow().findViewById(R.id.tv_message);
        textView.setText(message);
        return this;
    }
    public TextDialog setLeftButton(final String message){
       final TextView textView= getWindow().findViewById(R.id.tv_no);
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(message);
            }
        });
        textView.setOnClickListener(this);
        return this;
    }
    public TextDialog setLeftButton(final int id){
        final TextView textView= getWindow().findViewById(R.id.tv_no);
        textView.post(() -> textView.setText(id));
        textView.setOnClickListener(this);
        return this;
    }
    public TextDialog setRightButton(final int message){
        final TextView textView= getWindow().findViewById(R.id.tv_yes);
        textView.post(() -> textView.setText(message));
        textView.setOnClickListener(this);
        return this;
    }
    public TextDialog setRightButton(final String message){
        final TextView textView= getWindow().findViewById(R.id.tv_yes);
        textView.post(() -> textView.setText(message));
        textView.setOnClickListener(this);
        return this;
    }
    public TextDialog  setOnTouchOutside(boolean value){
        setCanceledOnTouchOutside(value);
        return this;
    }
    public TextDialog(@NonNull Context context) {
        super(context);
        initDialog();
    }

    public TextDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    public static TextDialog createDialog(Context context) {
        return new TextDialog(context, R.style.AppDialogTheme);
    }

    private void initDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tip, null);
        setContentView(view);
        Window win = getWindow();
        win.setGravity(Gravity.CENTER);
        win.setWindowAnimations(R.style.DialogAnimationStyle);
        setCancelable(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_no) {
            if (leftClick != null) {
                leftClick.onNoClick();
            }
        } else if (id == R.id.tv_yes) {
            if (rightClick != null) {
                rightClick.onYseClick(null);
            }
        }
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
