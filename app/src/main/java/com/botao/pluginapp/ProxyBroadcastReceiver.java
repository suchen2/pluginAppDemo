package com.botao.pluginapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.botao.startder.IBroadcastReceiver;

/**
 * Author: su2
 * Date: 2019/11/14 11:45
 * Describe: 代理 service
 **/
public class ProxyBroadcastReceiver extends BroadcastReceiver {

    private String broadcastReceiverName;
    public ProxyBroadcastReceiver(String broadcastReceiverName) {
        this.broadcastReceiverName = broadcastReceiverName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // 反射创建插件内BroadcastReceiver类实例
            Class clazz = PluginManager.getInstance(context).getClassLoader().loadClass(broadcastReceiverName);
            Object instance    = clazz.newInstance();

            IBroadcastReceiver iReceiver = (IBroadcastReceiver) instance;

            // 调用方法
            iReceiver.onReceive(context,intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
