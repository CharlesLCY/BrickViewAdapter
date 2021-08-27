package com.lcy.practice.list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.lcy.practice.R;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * Desc:自定义footer
 *
 * @author FanCoder.LCY
 * @date 2018/10/9 11:44
 * @email 15708478830@163.com
 **/
public class NormalListFooter extends ClassicsFooter {
    private String mNoMoreDataText = "已经到底啦";

    public NormalListFooter(Context context) {
        super(context);
        initFooter();
    }

    public NormalListFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooter();
    }

    private void initFooter() {
        mTitleText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_3));
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mArrowView;
            final View progressView = mProgressView;
            if (noMoreData) {
                mTitleText.setText(mNoMoreDataText);
                mTitleText.setEnabled(true);
                arrowView.setVisibility(GONE);
                progressView.setVisibility(GONE);
            } else {
                mTitleText.setText(REFRESH_FOOTER_PULLING);
                mTitleText.setEnabled(false);
                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    /**
     * 设置无更多数据提示文字
     */
    public NormalListFooter setNoMoreDataText(String text) {
        mNoMoreDataText = text;
        return this;
    }

    /**
     * 设置文字点击监听
     */
    public NormalListFooter setOnTextClickListener(OnClickListener listener) {
        mTitleText.setOnClickListener(listener);
        return this;
    }
}
