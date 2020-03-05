package com.sample.taki.intromvvm.mvvm;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by taki 2017/05/14.
 */

public class MvvmViewModel {

    private Context mContext;

    private ObservableField<String> inputText = new ObservableField<>();
    private ObservableField<String> outputText = new ObservableField<>();

    public MvvmViewModel(Context context) {
        mContext = context;
    }

    public void onCreate() {
    }

    public void onStart() {
        // 登録されているデータを取得
        MvvmDao dao = new MvvmDao(mContext);
        String outputText = dao.select();

        // 取得したデータをViewにセット
        this.outputText.set(outputText);
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    /**
     * Enterボタンが押された時の処理
     */
    public void onEnterClick() {
        if (TextUtils.isEmpty(inputText.get())) {
            // 未入力エラー
            EventBus.getDefault().post(new InputErrorEvent());
        } else {
            // データ登録
            MvvmDao dao = new MvvmDao(mContext);
            dao.insertOrUpdate(inputText.get());

            // 取得したデータをViewにセット
            outputText.set(inputText.get());
        }
    }

    // getter + setter

    public ObservableField<String> getInputText() {
        return inputText;
    }

    public void setInputText(ObservableField<String> inputText) {
        this.inputText = inputText;
    }

    public ObservableField<String> getOutputText() {
        return outputText;
    }

    public void setOutputText(ObservableField<String> outputText) {
        this.outputText = outputText;
    }
}
