package com.botao.plugin_package;

import android.content.Intent;
import android.os.Bundle;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);// 父类重写了此方法

        findViewById(R.id.btn_plugin_start).setOnClickListener( v -> startPluginMain());
    }
    // 不能在插件Activity的xml里使用onClick直接设置点击事件,需要用id的方式


    public void startPluginMain() {
        // 插件内跳转Activity必须回到宿主APP,也就是在ProxyActivity内启动了pluginActivity,再启动ProxyActivity再启动pluginMainActivity
        Intent intent = new Intent(appActivity, PluginMainActivity.class);

        startActivity(intent);
    }


}
