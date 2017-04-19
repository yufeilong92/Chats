package com.lawyee.chat.bean;

import com.lawyee.chat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Chat
 * @Package com.lawyee.chat.bean
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/19 15:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class addSelectData  {

    public final static List<data> getInputData(){
        ArrayList<data> list = new ArrayList<>();
        data data = new data();
        data.setId(R.mipmap.ic_input_picture);
        data.setName("图片");
        list.add(data);
        data data1 = new data();
        data1.setId(R.mipmap.ic_input_here);
        data1.setName("位置");
        list.add(data1);
        return list;
    }

    public static class data{
        String name;
        int id;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
