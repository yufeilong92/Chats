package com.lawyee.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.chat.R;

import java.util.List;

/**
 * @Author : YFL  is Creating a porject in YFPHILPS
 * @Email : yufeilong92@163.com
 * @Time :2017/1/4 08:38
 * @Purpose :
 */
public class FaceVAdapter extends BaseAdapter {


    private List<String> mData;
    private Context mContext;



    public FaceVAdapter(Context context, List<String> mData) {
        super();
        this.mData = mData;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData == null ? null : mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converview, ViewGroup viewGroup) {
        ViewHolder holder;
        if (converview == null) {
            converview = LayoutInflater.from(mContext).inflate(R.layout.face_imager, null);
            holder = new ViewHolder(converview);
            holder.mFaceImg = (ImageView) converview.findViewById(R.id.face_img);
            holder.mFaceText = (TextView) converview.findViewById(R.id.face_text);
            converview.setTag(holder);
        } else {
            holder = (ViewHolder) converview.getTag();
        }
        try {

            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("face/png/" + mData.get(i)));
            holder.mFaceImg.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
            holder.mFaceText.setText("face/png/"+mData.get(i));

        return converview;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mFaceImg;
        public TextView mFaceText;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mFaceImg = (ImageView) rootView.findViewById(R.id.face_img);
            this.mFaceText = (TextView) rootView.findViewById(R.id.face_text);
        }

    }
}
