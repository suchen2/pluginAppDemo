package com.botao.plugin_package.activity;

import android.content.Intent;
import android.os.Bundle;

import com.botao.plugin_package.R;
import com.botao.plugin_package.base.BaseActivity;
import com.botao.plugin_package.service.PluginService;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);// 父类重写了此方法

        // 不能在插件Activity的xml里使用onClick直接设置点击事件,需要用id的方式
        findViewById(R.id.btn_plugin_start).setOnClickListener( v -> startPluginMain());

        findViewById(R.id.btn_plugin_start_service).setOnClickListener( v -> startPluginService());

    }

    /**
     * 启动插件内service
     */
    private void startPluginService() {
        startService(new Intent(appActivity, PluginService.class));
    }


    /**
     * 启动插件内其他Activity
     */
    public void startPluginMain() {
        // 插件内跳转Activity必须回到宿主APP,也就是在ProxyActivity内启动了pluginActivity,再启动ProxyActivity再启动pluginMainActivity
        Intent intent = new Intent(appActivity, PluginMainActivity.class);

        startActivity(intent);
    }


}
