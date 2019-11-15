package com.botao.startder;

import android.app.Service;
import android.content.Intent;

/**
 * Author: su2
 * Date: 2019/11/14 11:14
 * Describe: service 接口标准
 **/
public interface IService {
    void insertAppContext(Service appService);

    void onStartCommand(Intent intent);
}
