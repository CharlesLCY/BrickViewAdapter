package com.lcy.practice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lcy.brick.list.register
import com.lcy.practice.R
import com.lcy.practice.databinding.FragmentHome1Binding
import com.lcy.practice.entity.New
import com.lcy.practice.entity.User
import com.lcy.practice.manager.HomeViewManager
import com.lcy.practice.manager.NewsViewManager
import java.util.*

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class HomeFragment1 : Fragment() {
    private lateinit var binding: FragmentHome1Binding
    private var page = 1

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
        val manager = HomeViewManager()
        binding.rv.register(User::class.java, manager, mutableListOf(0, 1, 2, 3, 4))
        binding.rv.register(New::class.java, NewsViewManager())

        manager.setOnItemClickListener { _, v, data, position ->
            if (v?.id == R.id.image) {
                val user = data as User
                user.name = "我的名字变了"
                binding.rv.adapter.notifyItemChanged(position)
            }
        }

        manager.setOnItemLongClickListener { holder, v, _, _ ->
            if (v?.id == R.id.image) {
                binding.rv.adapter.startDrag(holder)
            }
        }

        binding.rv.setOnRefreshListener {
            if (it) {
                page = 1
                fetchDataRefresh()
            } else {
                page++
                fetchDataLoadMore()
            }
        }

        binding.rv.autoRefresh()
    }

    private fun fetchDataRefresh() {
        binding.rv.postDelayed({
            val list = mutableListOf<Any>()
            for (i in 1..15) {
                val user = User()
                user.type = Random().nextInt(4)
                user.name = "我真的无语了${user.type}"
                list.add(user)

                if (i == 3) {
                    val news = New()
                    news.title = "这是一个固定的布局哈哈哈"
                    list.add(news)
                }
            }

            binding.rv.fetchDataSuccess(true, list)
        }, 1000)
    }

    private fun fetchDataLoadMore() {
        binding.rv.postDelayed({
            val list = mutableListOf<Any>()
            if (page == 2) {
                for (i in 1..10) {
                    val user = User()
                    user.type = Random().nextInt(4)
                    user.name = "我真的无语了${user.type}"
                    list.add(user)
                }
            } else {
                for (i in 1..15) {
                    val user = User()
                    user.type = Random().nextInt(4)
                    user.name = "我真的无语了${user.type}"
                    list.add(user)
                }
            }

            binding.rv.fetchDataSuccess(false, list)
        }, 1000)
    }
}