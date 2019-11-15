package com.botao.pluginapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.botao.startder.Constant;
import com.botao.startder.IService;

import androidx.annotation.Nullable;

/**
 * Author: su2
 * Date: 2019/11/14 11:45
 * Describe: 代理 service
 **/
public class ProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            // 反射创建插件内service类实例
            Class clazz = PluginManager.getInstance(this).getClassLoader().loadClass(intent.getStringExtra(Constant.CLASS_NAME));
            Object instance    = clazz.newInstance();

            IService iService = (IService) instance;

            // 注入宿主环境
            iService.insertAppContext(this);

            iService.onStartCommand(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
