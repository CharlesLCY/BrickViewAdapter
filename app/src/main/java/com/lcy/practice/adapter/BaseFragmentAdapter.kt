package com.lcy.practice.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.PagerAdapter

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 09:54
 * 15708478830@163.com
 **/
abstract class BaseFragmentAdapter(private val fragmentManager: FragmentManager) : PagerAdapter() {
    private var transaction: FragmentTransaction ?= null

    override fun isViewFromObject(view: View, obj: Any) = (obj as Fragment).view == view

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (transaction == null) {
            transaction = fragmentManager.beginTransaction()
        }

        val tagName = "${container.id}:${getItemId(position)}}"
        var fragment = fragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            transaction?.attach(fragment)
        } else {
            fragment = getItem(position)
            transaction?.add(container.id, fragment, tagName)
        }
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        if (transaction == null) {
            transaction = fragmentManager.beginTransaction()
        }
        transaction?.detach(obj as Fragment)
    }

    abstract fun getItem(position: Int): Fragment

    fun getItemId(position: Int): Long = position.toLong()

    override fun finishUpdate(container: ViewGroup) {
        super.finishUpdate(container)
        if (transaction != null) {
            transaction!!.commitNowAllowingStateLoss()
            transaction = null
        }
    }
}