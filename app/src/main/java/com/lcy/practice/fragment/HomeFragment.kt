package com.lcy.practice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lcy.practice.databinding.FragmentHomeBinding
import com.lcy.practice.entity.User
import com.lcy.practice.manager.HomeViewManager
import com.lcy.practice.multiple.MultipleViewAdapter

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
        val adapter = MultipleViewAdapter()
        val listData = mutableListOf<Any>()
        for (i in 1..10) {
            val user = User()
            user.type = i
            user.name = "我真的无语了$i"
            listData.add(user)
        }
        adapter.initRecyclerView(binding.rv)
        adapter.setAllData(listData)
        adapter.register(User::class.java, HomeViewManager())
    }
}