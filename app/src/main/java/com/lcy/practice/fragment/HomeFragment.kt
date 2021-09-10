package com.lcy.practice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lcy.practice.R
import com.lcy.practice.databinding.FragmentHomeBinding
import com.lcy.practice.entity.New
import com.lcy.practice.entity.User
import com.lcy.practice.manager.HomeViewManager
import com.lcy.practice.manager.NewsViewManager
import com.lcy.practice.multiple.BrickViewAdapter
import java.util.*

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
        val adapter = BrickViewAdapter()
        val listData = mutableListOf<Any>()
        for (i in 1..3) {
            val user = User()
            user.type = i
            user.name = "我真的无语了$i"
            listData.add(user)

//            if (i == 3) {
//                val news = New()
//                news.title = "这是一个固定的布局哈哈哈"
//                listData.add(news)
//            }
        }
        adapter.initRecyclerView(binding.rv)
        adapter.setAllData(listData)
        val manager = HomeViewManager()
        adapter.register(User::class.java, manager, 0, 1, 2, 3, 4)
        adapter.register(New::class.java, NewsViewManager())

        manager.setOnItemClickListener { holder, v, data, position ->
            if (v?.id == R.id.text) {
                listData.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }

        binding.add.setOnClickListener {
            val user = User()
            user.type = Random().nextInt(5)
            user.name = "我真的无语了${user.type}"
            listData.add(user)
            adapter.notifyDataSetChanged()
        }
    }
}