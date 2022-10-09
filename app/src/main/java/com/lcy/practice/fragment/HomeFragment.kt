package com.lcy.practice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lcy.brick.BrickViewAdapter
import com.lcy.practice.R
import com.lcy.practice.adapter.CommRefreshHeader
import com.lcy.practice.databinding.FragmentHomeBinding
import com.lcy.practice.entity.New
import com.lcy.practice.entity.User
import com.lcy.practice.manager.HomeViewManager
import com.lcy.practice.manager.NewsViewManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter = BrickViewAdapter()
    private val listData = mutableListOf<Any>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        adapter.initRecyclerView(binding.rv)
        val manager = HomeViewManager()
        adapter.register(User::class.java, manager, mutableListOf(0, 1, 2, 3, 4))
        adapter.register(New::class.java, NewsViewManager())

        manager.setOnItemClickListener { holder, v, data, position ->
            if (v?.id == R.id.text) {
                listData.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }

        manager.setOnItemLongClickListener { holder, v, data, position ->
            if (v?.id == R.id.image) {
                adapter.startDrag(holder)
            }
        }

        binding.add.setOnClickListener {
            val user = User()
            user.type = Random().nextInt(5)
            user.name = "我真的无语了${user.type}"
            listData.add(user)
            adapter.notifyDataSetChanged()
        }

        with(binding.refreshLayout) {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setRefreshHeader(CommRefreshHeader(requireContext()))
            setRefreshFooter(ClassicsFooter(requireContext()))
            setEnableAutoLoadMore(true)
            setEnableOverScrollBounce(true)
            setEnableOverScrollDrag(true)

            setOnRefreshLoadMoreListener(object: OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    fetchDataRefresh()
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    fetchDataLoadMore()
                }
            })
        }

        binding.refreshLayout.autoRefresh()
    }

    private fun fetchDataRefresh() {
        binding.refreshLayout.postDelayed({
            listData.clear()
            for (i in 1..10) {
                val user = User()
                user.type = Random().nextInt(4)
                user.name = "我真的无语了${user.type}"
                listData.add(user)

                if (i == 3) {
                    val news = New()
                    news.title = "这是一个固定的布局哈哈哈"
                    listData.add(news)
                }
            }

            adapter.setAllData(listData)
            binding.refreshLayout.finishRefresh(true)
        }, 1000)
    }

    private fun fetchDataLoadMore() {
        binding.refreshLayout.postDelayed({
            val start = listData.size - 1
            for (i in 1..10) {
                val user = User()
                user.type = Random().nextInt(4)
                user.name = "我真的无语了${user.type}"
                listData.add(user)
            }

            adapter.notifyItemInserted(start + 1 )
            binding.refreshLayout.finishLoadMore(true)
        }, 1000)
    }
}