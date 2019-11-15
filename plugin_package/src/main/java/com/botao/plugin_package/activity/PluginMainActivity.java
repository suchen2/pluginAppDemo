package com.botao.plugin_package.activity;

import android.os.Bundle;


import com.botao.plugin_package.R;
import com.botao.plugin_package.base.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Author: su2
 * Date: 2019/11/8 15:22
 * Describe: 插件内其他页面
 **/
public class PluginMainActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
    }
}
