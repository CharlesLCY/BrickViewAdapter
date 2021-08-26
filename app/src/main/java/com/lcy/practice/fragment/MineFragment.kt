package com.lcy.practice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lcy.practice.databinding.FragmentMineBinding

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class MineFragment : Fragment() {
    private lateinit var binding: FragmentMineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.text.text = "我的"
    }
}