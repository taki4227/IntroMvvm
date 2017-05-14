package com.sample.taki.intromvvm.mvvm;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.sample.taki.intromvvm.R;
import com.sample.taki.intromvvm.databinding.ActivityMvvmBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MvvmActivity extends AppCompatActivity {

    private MvvmViewModel mViewModel;

    private ConstraintLayout mainLayout;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModelをViewにセット（DataBinding)
        mViewModel = new MvvmViewModel(getApplicationContext());
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        binding.setViewModel(mViewModel);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);

        mViewModel.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 画面がタッチされたら、キーボードを閉じる
        closeKeyboard();

        return false;
    }

    /**
     * 入力エラー時の処理
     */
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInputErrorEvent(InputErrorEvent errorEvent) {
        AlertDialog errorDialog = createErrorDialog();
        errorDialog.show();
    }


    /**
     * キーボードを閉じる
     */
    private void closeKeyboard() {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();
    }

    /**
     * エラーダイアログ表示
     *
     * @return エラーダイアログ
     */
    private AlertDialog createErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getText(R.string.error_dialog_title))
                .setMessage(getText(R.string.error_dialog_message))
                .setPositiveButton(
                        getText(R.string.error_dialog_positive_button_text)
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                .setCancelable(true);

        return builder.create();
    }
}
