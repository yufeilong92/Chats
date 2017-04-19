package com.lawyee.chat;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.chat.adapter.FaceVAdapter;
import com.lawyee.chat.adapter.FaceVPAdapter;
import com.lawyee.chat.adapter.SelectAdapter;
import com.lawyee.chat.bean.addSelectData;
import com.lawyee.chat.ui.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.lv_SwipRefresh)
    SwipeRefreshListView lvSwipRefresh;
    @BindView(R.id.iv_emoji)
    ImageView ivEmoji;
    @BindView(R.id.et_Input)
    EditText etInput;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.face_viewpager)
    ViewPager faceViewpager;
    @BindView(R.id.face_dots_container)
    LinearLayout mFaceDotsContainer;//表情下面小圆圈
    @BindView(R.id.btn_Send)
    Button btnSend;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewpage)
    LinearLayout viewpage;
    private List<String> mStaticFacesList;
    private LinearLayout mChatfaceContainer;
    // 7列3行
    private int columns = 6;
    private int rows = 4;
    //表情页
    private List<View> views = new ArrayList<View>();
    public static final String TAG = "测试==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initStaticFaces();
        initView();

    }

    /**
     * 初始化表情
     */
    private void initStaticFaces() {
        mStaticFacesList = new ArrayList<>();
        try {
            String[] faces = getAssets().list("face/png");
            for (int i = 0; i < faces.length; i++) {
                mStaticFacesList.add(faces[i]);
            }
            mStaticFacesList.remove("emotion_del_normal.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mChatfaceContainer = (LinearLayout) findViewById(R.id.chat_facae_container);
        //滑动监听
        faceViewpager.setOnPageChangeListener(changeListener);
        InitViewPager();
        etInput.addTextChangedListener(watcher);
        SelectAdapter selectAdapter = new SelectAdapter(this, addSelectData.getInputData());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(selectAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick({R.id.iv_emoji, R.id.et_Input, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_emoji:
                hideSoftInputView();//隐藏键盘

                if (mChatfaceContainer.getVisibility() == View.GONE) {
                    mChatfaceContainer.setVisibility(View.VISIBLE);

                }else if (mChatfaceContainer.getVisibility()==View.VISIBLE){
                    mChatfaceContainer.setVisibility(View.GONE);
                }
                if (viewpage.getVisibility() == View.GONE) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewpage.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else if (viewpage.getVisibility() == View.VISIBLE) {
                    viewpage.setVisibility(View.GONE);
                }
                break;
            case R.id.et_Input:
                if (mChatfaceContainer.getVisibility() == View.VISIBLE) {
                    mChatfaceContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_add:
                    break;
        }
    }



    /**
     * 输入框内容改变监听
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals("")) {
                ivAdd.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
            } else {
                ivAdd.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * 初始化表情
     */
    private void InitViewPager() {
//    获取表情页数

        for (int i = 0; i < getPagerCount(); i++) {
            views.add(viewPagerItem(i));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(16, 16);
            mFaceDotsContainer.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        faceViewpager.setAdapter(mVpAdapter);
        mFaceDotsContainer.getChildAt(0).setSelected(true);


    }

    private ImageView dotsItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dot_image, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */

    private int getPagerCount() {
        int size = mStaticFacesList.size();
        return size % (columns * rows - 1) == 0 ? size / (columns * rows - 1) :
                size / (columns * rows - 1) + 1;
    }

    //添加布局
    private View viewPagerItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
         * */
        ArrayList<String> subList = new ArrayList<String>();
        subList.addAll(mStaticFacesList
                .subList(position * (columns * rows - 1),
                        (columns * rows - 1) * (position + 1) > mStaticFacesList.size() ?
                                mStaticFacesList.size() : (columns * rows - 1) * (position + 1)));

        /**
         * 末尾添加删除图标
         */
        subList.add("emotion_del_normal.png");

        FaceVAdapter faceVAdapter = new FaceVAdapter(this, subList);
        gridview.setAdapter(faceVAdapter);
        gridview.setNumColumns(columns);
        //点击表情执行操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
                    if (!png.contains("emotion_del_normal")) {//如果不是删除图标
                        insert(getFace(png));
                    } else {
                        delete();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return gridview;
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {

            /**
             * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
             * 所以这里对这个tempText值做特殊处理
             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
             * */
            String tempText = "#[" + png + "]#";
            sb.append(tempText);
            sb.setSpan(new ImageSpan(this, BitmapFactory.decodeStream(
                    getAssets().open(png))), sb.length() - tempText.length(), sb.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 删除图标执行事件
     * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
     */
    private void delete() {
        if (etInput.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(etInput.getText());
            int iCursorStart = Selection.getSelectionStart(etInput.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "#[face/png/f_static_000.png]#";
                        ((Editable) etInput.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ((Editable) etInput.getText()).delete(iCursorEnd - 1,
                                iCursorEnd);
                    }
                } else {
                    ((Editable) etInput.getText()).delete(iCursorStart,
                            iCursorEnd);
                }
            }
        }

    }

    /**
     * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
     **/
    private boolean isDeletePng(int cursor) {
        String st = "#[face/png/f_static_000.png]#";
        String content = etInput.getText().toString().substring(0, cursor);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),
                    content.length());
            String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;

    }

    /**
     * 想输入框添加表情
     */
    private void insert(CharSequence text) {
        //开始的位置
        int iCursoriStart = Selection.getSelectionStart((etInput.getText()));
        //结束位置
        int iCursorEnd = Selection.getSelectionEnd((etInput.getText()));
        if (iCursorEnd != iCursoriStart) {
            ((Editable) etInput.getText()).replace(iCursoriStart, iCursorEnd, "");
        }
        int iCuros = Selection.getSelectionEnd((etInput.getText()));
        ((Editable) etInput.getText()).insert(iCuros, text);
    }

    /**
     * 表情页改变时，dots效果也要跟着
     */
    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mFaceDotsContainer.getChildCount(); i++) {
                mFaceDotsContainer.getChildAt(i).setSelected(false);
            }
            mFaceDotsContainer.getChildAt(position).setSelected(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * .隐藏键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        boolean b = getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        if (b) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    //一个静态变量存储高度


}
