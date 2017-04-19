package com.lawyee.chat.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Chat
 * @Package com.lawyee.chat
 * @Description: 管理所有activity
 * @author: YFL
 * @date: 2017/4/18 17:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ActivityContorl {

    private final static  List<Activity> lists = new ArrayList<>();

    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    public static void removerActivity(Activity activity) {
        lists.remove(activity);

    }

    /**
     * 实现一键退出
     */
    public static void finishAll() {
        if (lists != null) {
            for (Activity list : lists) {
                if (list != null) {
                    if (!list.isFinishing()) {
                        list.finish();
                    }
                }
            }
        }
    }
}
