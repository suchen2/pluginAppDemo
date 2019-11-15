package com.botao.startder;

import android.content.Context;
import android.content.Intent;

/**
 * Author: su2
 * Date: 2019/11/15 11:42
 * Describe: Broadcast 接口标准
 **/
public interface IBroadcastReceiver {

    void onReceive(Context context, Intent intent);
}
