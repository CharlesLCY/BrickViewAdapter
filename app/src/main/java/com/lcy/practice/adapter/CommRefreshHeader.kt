package com.lcy.practice.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lcy.practice.R
import com.lcy.practice.databinding.CommonRefreshHeaderLayoutBinding
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleComponent


/**
 * <p> Created by CharlesLee on 2021/11/29
 * 15708478830@163.com
 */
class CommRefreshHeader : SimpleComponent, RefreshHeader {
    private lateinit var binding: CommonRefreshHeaderLayoutBinding
    constructor(context: Context): this(context, null) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        binding = CommonRefreshHeaderLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        binding.commonRefreshHeaderText.text = "加载完成"
        Glide.with(context).asDrawable().load(R.mipmap.news_loading).into(binding.commonRefreshHeaderImg)
        return super.onFinish(refreshLayout, success)
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        super.onStateChanged(refreshLayout, oldState, newState)
        when(newState) {
            RefreshState.None -> {
            }
            RefreshState.PullDownToRefresh -> {
                Glide.with(context).asDrawable().load(R.mipmap.news_loading).into(binding.commonRefreshHeaderImg)
                binding.commonRefreshHeaderText.text = "下拉刷新"
            }
            RefreshState.Refreshing ,
            RefreshState.RefreshReleased -> {
                binding.commonRefreshHeaderText.text = "加载中..."
                Glide.with(context).asGif().load(R.mipmap.news_loading).into(binding.commonRefreshHeaderImg)
            }
            RefreshState.ReleaseToRefresh -> {
                binding.commonRefreshHeaderText.text = "松开加载"
            }
            RefreshState.ReleaseToTwoLevel -> {
            }
            RefreshState.Loading -> {
            }
            else -> {}
        }
    }

    fun setHeaderBackgroundColor(color: Int) {
        binding.commonRefreshHeaderLayout.setBackgroundColor(color)
    }

    fun setHeaderBackgroundResource(@DrawableRes resId: Int) {
        binding.commonRefreshHeaderLayout.setBackgroundResource(resId)
    }
}