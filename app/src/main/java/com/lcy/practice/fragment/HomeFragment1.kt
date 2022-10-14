package com.lcy.practice.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.lcy.brick.BrickViewDragListenerAdapter
import com.lcy.brick.OnBrickViewDragListener
import com.lcy.brick.list.register
import com.lcy.practice.R
import com.lcy.practice.databinding.EmptyLayoutBinding
import com.lcy.practice.databinding.FragmentHome1Binding
import com.lcy.practice.entity.User
import com.lcy.practice.manager.ArticleManager
import com.lcy.practice.net.Api
import com.lcy.practice.net.HttpClient
import com.lcy.practice.net.entity.Article
import kotlinx.coroutines.*

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class HomeFragment1 : Fragment() {
    private lateinit var binding: FragmentHome1Binding
    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome1Binding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.rv.apply {
            val manager = ArticleManager()
//            register(Article::class.java, manager)

            // 设置刷新监听
            setOnRefreshListener {
                if (it) {
                    page = 0
                    fetchDataRefresh()
                } else {
                    page++
                    fetchDataLoadMore()
                }
            }

            manager.setOnItemClickListener { _, v, data, position ->
                if (v?.id == R.id.title) {
                    val article = data as Article
                    article.title = "我的名字变了"
                    binding.rv.adapter.notifyItemChanged(position)
                }
            }

            manager.setOnItemLongClickListener { holder, v, _, _ ->
                if (v?.id == R.id.title) {
                    binding.rv.startDrag(holder)
                }
            }

//            setLayoutManager(GridLayoutManager(requireContext(), 2))

            setOnDragListener(object: BrickViewDragListenerAdapter() {
                override fun onItemIdle(itemView: View?) {
                    itemView?.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }

                override fun onItemDrag(itemView: View?) {
                    itemView?.setBackgroundColor(Color.parseColor("#F1F1F1"))
                }

                override fun canDropOver() = true

                override fun canMove(
                    allData: MutableList<Any>,
                    formPosition: Int,
                    toPosition: Int
                ) = true
            })

            // 自动刷新
            autoRefresh()

        }

        setEmptyLayout()
    }

    private fun setEmptyLayout() {
        val emptyBinding = EmptyLayoutBinding.inflate(layoutInflater, null, false)
        binding.rv.setEmptyLayout(emptyBinding.root)
    }

    private fun getErrorLayoutView(): View {
        val emptyBinding = EmptyLayoutBinding.inflate(layoutInflater, null, false)
        emptyBinding.tvText.text = "加载失败，点击重试"
        emptyBinding.tvText.setOnClickListener {
            binding.rv.autoRefresh()
        }

        return emptyBinding.root
    }

    private fun fetchDataRefresh() {
        CoroutineScope(Dispatchers.Main).launch(
            CoroutineExceptionHandler { coroutineContext, throwable ->
                binding.rv.showErrorLayout(getErrorLayoutView())
                binding.rv.finishRefresh(false)
            }
        ) {
            val api = HttpClient.buildApi(Api::class.java)
            val response = api.getHomeList(page, 15)
            val list = mutableListOf<Any>()
            list.addAll(response.data?.datas!!)
            if (response.errorCode == 0) {
                binding.rv.fetchDataSuccess(true, list)
            } else {
                binding.rv.fetchDataFailure(true)
                throw CancellationException()
            }
        }
    }

    private fun fetchDataLoadMore() {
        CoroutineScope(Dispatchers.Main).launch(
            CoroutineExceptionHandler { coroutineContext, throwable ->
                binding.rv.finishLoadMore(false)
            }
        ) {
            val api = HttpClient.buildApi(Api::class.java)
            val response = api.getHomeList(page, 15)
            val list = mutableListOf<Any>()
            list.addAll(response.data?.datas!!)
            if (response.errorCode == 0) {
                binding.rv.fetchDataSuccess(false, list)
            } else {
                binding.rv.fetchDataFailure(false)
            }
        }
    }
}