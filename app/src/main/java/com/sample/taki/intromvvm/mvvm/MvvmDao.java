package com.sample.taki.intromvvm.mvvm;

import android.content.Context;
import android.content.SharedPreferences;

import com.sample.taki.intromvvm.common.PreferenceConst;

/**
 * Created by taki on 2017/05/14.
 */

class MvvmDao {

    private Context mContext;

    public MvvmDao(Context context) {
        mContext = context;
    }

    /**
     * データ登録
     *
     * @param text 入力値
     */
    public void insertOrUpdate(String text) {
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(PreferenceConst.PREF_DATA, Context.MODE_PRIVATE).edit();

        editor.putString(PreferenceConst.PREF_KEY, text);
        editor.apply();
    }

    /**
     * データ取得
     *
     * @return 取得結果
     */
    public String select() {
        SharedPreferences preferences =
                mContext.getSharedPreferences(PreferenceConst.PREF_DATA, Context.MODE_PRIVATE);

        return preferences.getString(PreferenceConst.PREF_KEY, "");
    }
}
