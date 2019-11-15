package com.botao.plugin_package.base;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import com.botao.startder.IService;

import androidx.annotation.Nullable;


/**
 * Author: su2
 * Date: 2019/11/14 11:17
 * Describe: base service
 **/
public class BaseService extends Service implements IService {
    protected Service appService;

    @Override
    public void insertAppContext(Service appService) {
        this.appService = appService;
    }

    @Override
    public void onStartCommand(Intent intent) {}

//////////////////////////////////// 上面是实现的标准里的方法, 下面是重写的父类的方法 //////////////////////////////////////////////////////////////////
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startService(Intent service) {
        // base类重写一个用宿主环境启动service的方法,让子类使用
        return appService.startService(service);
    }
}
