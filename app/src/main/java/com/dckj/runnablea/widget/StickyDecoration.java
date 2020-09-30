package com.dckj.runnablea.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义分割线 实现悬浮效果
 */
public class StickyDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private int mDecorationHeight = 60;
    OnTagListener listener;
    private final Paint mTextPaint;
    private int ispaint = 0;
    /**
     * RecyclerView头部数量
     * 最小为0
     */
    int mHeaderCount;

    public StickyDecoration(OnTagListener listener) {
        super();
        this.listener = listener;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#F6F8F5"));
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#03DAC5"));
        mTextPaint.setTextSize(30);
    }

    /**
     *在item 之后进行绘制， 会覆盖在item之上
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();//获取所有item的数量
        int childCount = parent.getChildCount();//获取当前屏幕显示的item数量
        int left = parent.getLeft();
        int right = parent.getRight();

        String preTag;
        String curTag = null;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);//获取在列表中的位置
            preTag = curTag;
            curTag = listener.getTag(position);//获取当前位置tag
            if (curTag==null|| TextUtils.equals(preTag,curTag)){//如果两个item属于同一个tag，就不绘制

                continue;
            }
            ispaint = 0;
            int bottom = child.getBottom(); //获取item 的bottom
            float tagBottom = Math.max(mDecorationHeight,child.getTop());//计算出tag的bottom
            if (position+1<itemCount)  //判断是否是最后一个
            {
                String nextTag = listener.getTag(position + 1); //获取下个tag
                if (!TextUtils.equals(curTag,nextTag)&&bottom<tagBottom) //被顶起来的条件 当前tag与下个tag不等且item的bottom已小于分割线高度
                {
                    tagBottom = bottom; //将item的bottom值赋给tagBottom 就会实现被顶上去的效果
                }
            }
            c.drawRect(left,tagBottom-mDecorationHeight,right,tagBottom,paint); //绘制tag文字
            c.drawText(curTag,right/2,tagBottom-mDecorationHeight/2,mTextPaint); //将tag绘制出来
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int position = parent.getChildAdapterPosition(view);
//        int realPosition = getRealPosition(position);
//        RecyclerView.LayoutManager manager = parent.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            //网格布局
//            int spanCount = ((GridLayoutManager) manager).getSpanCount();
//            if (!isHeader(realPosition)) {
//                if (isFirstLineInGroup(realPosition, spanCount)) {
//                    //为悬浮view预留空间
//                    outRect.top = mDecorationHeight;
//                } else {
//                    //为分割线预留空间
//                    outRect.top = 4;
//                }
//            }
//        } else {
//            //其他的默认为线性布局
//            //只有是同一组的第一个才显示悬浮栏
//            if (!isHeader(realPosition)) {
//                if (isFirstInGroup(realPosition)) {
//                    //为悬浮view预留空间
//                    outRect.top = mDecorationHeight;
//                } else {
//                    //为分割线预留空间
//                    outRect.top = 4;
//                }
//            }
//        }
//        outRect.top = mDecorationHeight;
    }
    /**
     * 获取真实的position
     * @param position
     * @return
     */
    protected int getRealPosition(int position) {
        return position - mHeaderCount;
    }


    public interface OnTagListener{
        String getTag(int position); //为了获取当前tag
    }
}
