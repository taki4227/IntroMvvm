package com.sample.taki.intromvvm.mvc;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.sample.taki.intromvvm.R;

public class MvcActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private TextView inputTextView;
    private TextView outputTextView;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        inputTextView = (TextView) findViewById(R.id.inputTextView);
        outputTextView = (TextView) findViewById(R.id.outputTextView);

        // Enterボタンを押した時の処理をセット
        Button enterButton = (Button) findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new EnterOnClickListener());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 登録されているデータを取得
        MvcDao dao = new MvcDao(getApplicationContext());
        String outputText = dao.select();

        // 取得したデータをViewにセット
        outputTextView.setText(outputText);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();

        return false;
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

    /**
     * Enterボタンを押した時のリスナー
     */
    private class EnterOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String inputText = inputTextView.getText().toString();

            if (TextUtils.isEmpty(inputText)) {
                // 未入力エラー
                AlertDialog errorDialog = createErrorDialog();
                errorDialog.show();
            } else {
                // データ登録
                MvcDao dao = new MvcDao(getApplicationContext());
                dao.insertOrUpdate(inputText);

                // 取得したデータをViewにセット
                outputTextView.setText(inputText);
            }

            closeKeyboard();
        }
    }

}
