package com.lcy.practice.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.lcy.practice.R
import com.lcy.practice.databinding.FragmentDiscoverBinding
import com.lcy.practice.databinding.HomeBottomTabLayoutBinding
import com.lcy.practice.databinding.HomeVpContentLayoutBinding

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:42
 * 15708478830@163.com
 **/
class DiscoverFragment : Fragment() {
    private var scrollTo = -1
    private lateinit var binding: FragmentDiscoverBinding
    private val titleList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        for (i in 1..6) {
            titleList.add("自定义标题$i")
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        with(binding) {
            val adapter = MyPagerAdapter()
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(binding.viewPager)
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
                    val view = LayoutInflater.from(tab.view.context).inflate(R.layout.design_layout_tab_text, tab.view, false)
                    tab.customView = view
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
        val tabText = content as TextView
        tabText.text = titleList[i]
        tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP, if (selected) 16f else 15f)
        tabText.setTextColor(
            if (selected) ContextCompat.getColor(requireContext(), R.color.white)
            else ContextCompat.getColor(requireContext(), R.color.purple_200)
        )
    }

    inner class MyPagerAdapter : PagerAdapter() {
        override fun getCount() = titleList.size

        override fun isViewFromObject(view: View, obj: Any) = view == obj

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val pagerBinding = HomeVpContentLayoutBinding.inflate(layoutInflater)

            when (position % 3) {
                0 -> pagerBinding.contentLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.purple_200
                    )
                )
                1 -> pagerBinding.contentLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.purple_500
                    )
                )
                2 -> pagerBinding.contentLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.purple_700
                    )
                )
            }

            container.addView(pagerBinding.root)
            return pagerBinding.root
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }
    }
}