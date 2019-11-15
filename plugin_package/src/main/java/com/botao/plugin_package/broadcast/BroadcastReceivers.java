package com.botao.plugin_package.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.botao.startder.IBroadcastReceiver;

/**
 * Author: su2
 * Date: 2019/11/15 11:50
 * Describe: 动态广播
 **/
public class BroadcastReceivers extends BroadcastReceiver implements IBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BroadcastReceivers: 收到动态广播", Toast.LENGTH_SHORT).show();
        System.out.println("BroadcastReceivers: 收到动态广播");
    }
}
