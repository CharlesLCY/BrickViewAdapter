package com.lcy.brick.list

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.recyclerview.widget.*
import com.lcy.brick.BrickViewAdapter
import com.lcy.brick.databinding.LayoutBrickRecyclerViewBinding
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*


/**
 * <Desc> 封装刷新，空布局占位等
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
class BrickRecyclerView : FrameLayout {
    private var binding =
        LayoutBrickRecyclerViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var emptyLayout : View ?= null
    private var errorLayout : View ?= null

    /**
     * 列表Adapter
     */
    var adapter = BrickViewAdapter()

    /**
     * 列表数据源
     */
    var listData = mutableListOf<Any>()

    /**
     * 分页加载条数，默认为15
     */
    var pageSize = 15

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : this(context, attrs, defStyleAttr, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        // 初始化SmartRefreshLayout默认配置
        with(binding.smartRefreshLayout) {
            // 默认开启下拉刷新和上拉加载
            setEnableRefresh(true)
            setEnableLoadMore(true)
            // 开启上划自动加载更多
            setEnableAutoLoadMore(true)
            // 设置没有更多数据提示随着列表滑出
            setEnableFooterFollowWhenNoMoreData(true)
            // 设置滑动阻尼
            setDragRate(0.7f)
            // 在内容不满一页的时候不开启上拉加载
            setEnableLoadMoreWhenContentNotFull(false)
            // 列表在刷新或者加载的时候禁止操作内容
            setDisableContentWhenRefresh(false)
            setDisableContentWhenLoading(false)

            //设置默认的刷新头和刷新底
            setRefreshHeader(MaterialHeader(context))
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "已经到底了~"
            setRefreshFooter(ClassicsFooter(context).setAccentColor(Color.parseColor("#CCCCCC")))
        }

        // 初始化BrickViewAdapter
        adapter.initRecyclerView(binding.recyclerView)
        adapter.setAllData(mutableListOf())

        initListener()
    }

    private fun initListener() {
    }

    /**
     * 获取SmartRefreshLayout实例
     */
    fun getRefreshLayout() = binding.smartRefreshLayout

    /**
     * 获取RecyclerView实例
     */
    fun getRecyclerView() = binding.recyclerView

    /**
     * 获取EmptyView实例
     */
    fun getEmptyLayout() = binding.emptyLayout

    /**
     * 设置刷新监听
     */
    fun setOnRefreshListener(onRefresh: (Boolean) -> Unit = {}) {
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                onRefresh.invoke(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                onRefresh.invoke(false)
            }
        })
    }

    /**
     * 通用方法
     * 设置列表加载完成状态和数据适配
     * @param isRefresh true 下拉刷新，false 上拉加载，默认为true
     * @param dataList 列表数据源
     */
    @JvmOverloads
    fun fetchDataSuccess(isRefresh: Boolean = true, dataList: MutableList<Any>?) {
        // 数据适配
        if (isRefresh) {
            // 下拉刷新
            if (dataList.isNullOrEmpty()) {
                // 显示空布局
                showEmptyLayout()
                binding.smartRefreshLayout.finishRefreshWithNoMoreData()
                listData.clear()
                adapter.setAllData(listData)
            } else {
                // 隐藏空布局
                hideEmptyLayout()
                this.listData = dataList
                adapter.setAllData(listData)
                // 显示已经到底了
                if (dataList.size < pageSize) {
                    binding.smartRefreshLayout.finishRefreshWithNoMoreData()
                } else {
                    binding.smartRefreshLayout.finishRefresh(true)
                }
            }
        } else {
            // 上拉加载更多
            if (!dataList.isNullOrEmpty()) {
                this.listData.addAll(dataList)
                adapter.setAllData(listData)
            }
            // 显示已经到底了
            if (dataList.isNullOrEmpty() || dataList.size < pageSize) {
                binding.smartRefreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                binding.smartRefreshLayout.finishLoadMore(true)
            }
        }
    }

    /**
     * 设置加载失败状态
     */
    @JvmOverloads
    fun fetchDataFailure(isRefresh: Boolean = true) {
        if (isRefresh) {
            finishRefresh(false)
        } else {
            finishLoadMore(false)
        }
    }

    /**
     * 设置自动刷新
     */
    fun autoRefresh() {
        binding.smartRefreshLayout.autoRefresh()
    }

    /**
     * 设置下拉刷新完成，调用SmartRefreshLayout API
     */
    fun finishRefresh(success: Boolean) {
        binding.smartRefreshLayout.finishRefresh(success)
    }

    /**
     * 设置上拉加载完成
     */
    fun finishLoadMore(success: Boolean) {
        binding.smartRefreshLayout.finishLoadMore(success)
    }

    /**
     * 开启/禁用下拉刷新，默认开启
     */
    fun enableRefresh(isEnabled: Boolean) {
        binding.smartRefreshLayout.setEnableRefresh(isEnabled)
    }

    /**
     * 开启/禁用上拉加载更多，默认开启
     */
    fun enableLoadMore(isEnabled: Boolean) {
        binding.smartRefreshLayout.setEnableLoadMore(isEnabled)
    }

    /**
     * 显示分割线
     * @param dividerHeight 分割线高度，单位dp
     */
//    fun showDividerLine(dividerHeight: Int) {
//        binding.recyclerView.addItemDecoration(MyFollowListItemDecoration(context, dividerHeight))
//    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration) {
        binding.recyclerView.addItemDecoration(decor)
    }

    /**
     * 自定义空页面
     */
    fun setEmptyLayout(emptyLayout: View) {
        this.emptyLayout = emptyLayout
    }

    /**
     * 显示空页面
     */
    fun showEmptyLayout() {
        binding.emptyLayout.removeAllViews()
        binding.emptyLayout.addView(emptyLayout)
        binding.emptyLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    /**
     * 自定义错误页面
     */
    fun setErrorLayout(errorLayout: View) {
        this.errorLayout = errorLayout
    }

    /**
     * 显示错误页面
     */
    fun showErrorLayout() {
        binding.emptyLayout.removeAllViews()
        binding.emptyLayout.addView(errorLayout)
        binding.emptyLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    /**
     * 隐藏占位布局
     */
    private fun hideEmptyLayout() {
        binding.emptyLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    /**
     * 滑动到指定位置
     */
    fun scrollToPosition(position: Int) {
        adapter.scrollListToPosition(position)
    }

    /**
     * 拖动排序时，固定某个Item不能拖动
     * 需在setAdapter前设置才生效
     * @param fixedPosition 固定的行的索引
     */
    fun setDragFixPosition(fixedPosition: Int) {
    }
}