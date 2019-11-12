package com.botao.startder;

import android.app.Activity;
import android.os.Bundle;

/**
 * Author: su2
 * Date: 2019/11/7 15:49
 * Describe: activity标准
 **/
public interface IActivity {
    /**
     * 宿主APP环境
     */
    void insertAppContext(Activity appActivity);

    /**
     * 声明周期
     */
    void onCreate(Bundle savedInstanceState);
    void onResume();
    void onDestroy();
}
