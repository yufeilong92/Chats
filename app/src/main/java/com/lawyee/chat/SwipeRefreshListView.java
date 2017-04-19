package com.lawyee.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Chat
 * @Package com.lawyee.chat
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/18 16:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class SwipeRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private LayoutInflater mInflater;
    private FrameLayout mfl;
    private LinearLayout mHeadView;
    private ProgressBar mPrgBar;

    public static final String TAG = "=ListView=";
    private int firstItemIndex;//第一显示的滑动刷新
    private int state;
    private boolean isback;
    private int startY;

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;
    private boolean isRefreshableHeader;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;
    private int headContentheight;
    private int headContentWith;


    public SwipeRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public SwipeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /**
     * 添加下拉刷新布局
     *
     * @param context
     */
    private void init(Context context) {
        setCacheColorHint(context.getResources().getColor(android.R.color.transparent));
        //添加布局
        mInflater = LayoutInflater.from(context);
        mfl = (FrameLayout) mInflater.inflate(R.layout.dropdown_lv_head, null);
        mHeadView = (LinearLayout) mfl.findViewById(R.id.drop_down_head);
        mPrgBar = (ProgressBar) mfl.findViewById(R.id.loading);

        measureView(mHeadView);

        headContentheight = mHeadView.getMeasuredHeight();
        headContentWith = mHeadView.getMeasuredWidth();
        //打印出手机宽高
        Log.d(TAG, "with: " + headContentWith + "//height:" + headContentheight);
        //添加到listvie头
        addHeaderView(mfl, null, false);
        //设置滑动监听
        setOnScrollListener(this);

        state=DONE;
        isRefreshableHeader=false;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            // TODO: 2017/4/18 滑动底部处理监听
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisibleItem;
    }

    /**
     * 用于估计下滑的高度
     * 此处是“估计”headView的width以及height
     *
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params != null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWith = ViewGroup.getChildMeasureSpec(0, 0, params.width);

        int lpheigh = params.height;
        int childheightSpac;
        if (lpheigh > 0) {
            childheightSpac = MeasureSpec.makeMeasureSpec(lpheigh, MeasureSpec.EXACTLY);
        } else {
            childheightSpac = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWith, childheightSpac);
    }

    /**
     * 触摸事件判断
     * @param event
     * @return
     */
 public boolean onTouchEvent(MotionEvent event){
     if (isRefreshableHeader){
         switch(event.getAction()) {
             case MotionEvent.ACTION_DOWN :
                 if (firstItemIndex ==0&& !isRecored){
                         isRecored =true;
                      startY=(int)event.getY();

                     Log.w(TAG, "在down时候记录当前位置" );
                 }
               break;
             case  MotionEvent.ACTION_UP:
                 Log.d(TAG, "== "+state);
                 if (state!=REFRESHING &&state!=LOADING)
                   if (state==DONE){
                       //什么都不做
                   }
                   if (state ==PULL_To_REFRESH ){
                       state=DONE;
                       changeHeaderViewByState();
                   }
                     break;
           default:
               break;
         }
     }

     return super.onTouchEvent(event);
 }

    /**
     * 当状态改变时候，调用改方法，以跟新界面
     */
    private void changeHeaderViewByState() {
        switch(state) {
            case RELEASE_To_REFRESH :
                mPrgBar.setVisibility(VISIBLE);
                if (isback){
                    isback=false;
                }else {

                }
                Log.d(TAG, "当前状态。下拉刷新 ");
              break;
            case  REFRESHING:
                mHeadView.setPadding(0,0,0,0);
                mPrgBar.setVisibility(VISIBLE);
                Log.d(TAG, "当前状态，正在刷新。。。");
               break;
            case  DONE:
               break;
          default:
              break;
        }

    }
}
