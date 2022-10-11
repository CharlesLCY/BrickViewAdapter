package com.lcy.practice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.lcy.practice.adapter.BaseFragmentAdapter
import com.lcy.practice.databinding.ActivityMainBinding
import com.lcy.practice.databinding.HomeBottomTabLayoutBinding
import com.lcy.practice.fragment.DiscoverFragment
import com.lcy.practice.fragment.HomeFragment1
import com.lcy.practice.fragment.MineFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val titleList = mutableListOf("首页", "发现", "我的")
    private val fragmentList = mutableListOf(
        HomeFragment1(),
        DiscoverFragment(),
        MineFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding) {
            val adapter = MyFragmentPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(binding.viewPager)
            binding.viewPager.offscreenPageLimit = 3
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    setTabViewInfo(tab?.customView, tabLayout.selectedTabPosition, true)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    setTabViewInfo(tab?.customView, tab?.position!!, false)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            for (i in 0 until tabLayout.tabCount) {
                val tab = tabLayout.getTabAt(i)
                if (tab != null) {
                    val tabBinding =
                        HomeBottomTabLayoutBinding.inflate(layoutInflater, tab.view, false)
                    tab.customView = tabBinding.root
                    setTabViewInfo(tab.customView, i, false)
                }
            }

            setTabSelect(0)
        }
    }

    private fun setTabSelect(index: Int) {
        var tmpIndex = index
        if (index !in 0 until binding.tabLayout.tabCount) {
            tmpIndex = 0
        }
        binding.viewPager.currentItem = tmpIndex
        setTabViewInfo(binding.tabLayout.getTabAt(tmpIndex)?.customView, tmpIndex, true)
    }

    private fun setTabViewInfo(content: View?, i: Int, selected: Boolean) {
        val tabText = content?.findViewById<TextView>(R.id.tab_text)
        tabText?.text = titleList[i]
        tabText?.setTextColor(
            if (selected) ContextCompat.getColor(this, R.color.purple_500)
            else ContextCompat.getColor(this, R.color.text_color_3)
        )
    }

    inner class MyFragmentPagerAdapter(fm: FragmentManager) : BaseFragmentAdapter(fm) {
        override fun getCount() = fragmentList.size

        override fun getItem(position: Int) = fragmentList[position]
    }
}