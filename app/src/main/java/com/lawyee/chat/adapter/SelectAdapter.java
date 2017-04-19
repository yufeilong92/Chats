package com.lawyee.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.chat.R;
import com.lawyee.chat.bean.addSelectData;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Chat
 * @Package com.lawyee.chat.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/19 15:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContxt;
    private List<addSelectData.data> mData;
    private final LayoutInflater mInflater;


    private OnRecyclerViewItemClicklistener itemClicklistener = null;

    public SelectAdapter(Context mContxt, List<addSelectData.data> mData) {
        this.mContxt = mContxt;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContxt);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_add_select, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        addSelectData.data data = mData.get(position);
        holder.mIvShowPic.setImageResource(data.getId());
        holder.mTvName.setText(data.getName());

        holder.itemView.setTag(data.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClicklistener != null) {
            itemClicklistener.OnItemClick(v, (String) v.getTag());
        }
    }


    public static interface OnRecyclerViewItemClicklistener {
        void OnItemClick(View view, String name);
    }

    public void setItemClicklistener(OnRecyclerViewItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mIvShowPic;
        private final TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvShowPic = (ImageView) itemView.findViewById(R.id.iv_show);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
}
