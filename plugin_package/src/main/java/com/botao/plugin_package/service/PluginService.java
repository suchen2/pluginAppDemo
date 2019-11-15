package com.botao.plugin_package.service;

import android.content.Intent;
import android.widget.Toast;

import com.botao.plugin_package.base.BaseService;

/**
 * Author: su2
 * Date: 2019/11/14 11:16
 * Describe: 插件service
 **/
public class PluginService extends BaseService {

    public void onStartCommand(Intent intent) {

        Toast.makeText(appService, "PluginService 启动成功!", Toast.LENGTH_SHORT).show();
        System.out.println("PluginService 启动成功!");
    }
}
