package com.lawyee.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lawyee.chat.util.ActivityContorl;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Chat
 * @Package com.lawyee.chat
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/18 17:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        ActivityContorl.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContorl.removerActivity(this);
    }

}
