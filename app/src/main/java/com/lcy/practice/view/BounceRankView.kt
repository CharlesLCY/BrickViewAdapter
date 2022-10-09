package com.lcy.practice.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Half.toFloat
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.util.forEach
import com.lcy.practice.databinding.LayoutBounceRankViewBinding
import com.lcy.practice.entity.UserEntity


/**
 *
 * <p> Created by CharlesLee on 2022/8/18
 * 15708478830@163.com
 */
class BounceRankView : FrameLayout {
    private lateinit var binding: LayoutBounceRankViewBinding

    private var oldRankData = mutableListOf<UserEntity>()
    private var newRankData = mutableListOf<UserEntity>()
    /**
     * 存储元素位置变化
     */
    private var sparseArray = SparseArray<String>()

    private var oldViewArray = SparseArray<TextView>()
    private var tempViewArray = SparseArray<TextView>()

    private var rankOneTop = 0
    private var rankTwoTop = 0
    private var rankThreeTop = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        binding = LayoutBounceRankViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.root.post {
            rankOneTop = binding.tvRankOne.top
            rankTwoTop = binding.tvRankTwo.top
            rankThreeTop = binding.tvRankThree.top
        }
    }

    /**
     * 设置排名数据
     */
    fun setRankData(rankData: MutableList<UserEntity>) {
        this.oldRankData = rankData
        binding.tvRankOne.text = "${rankData[0].name}：${rankData[0].score}"
        binding.tvRankTwo.text = "${rankData[1].name}：${rankData[1].score}"
        binding.tvRankThree.text = "${rankData[2].name}：${rankData[2].score}"

        oldViewArray.append(0, binding.tvRankOne)
        oldViewArray.append(1, binding.tvRankTwo)
        oldViewArray.append(2, binding.tvRankThree)
    }

    /**
     * 刷新排名数据
     */
    fun updateRankData(rankData: MutableList<UserEntity>) {
        this.newRankData = rankData
        tempViewArray.clear()
        oldViewArray.forEach { key, value ->
            tempViewArray.append(key, value)
        }
        setupDataAndMayDoAnimation()
    }

    private fun setupDataAndMayDoAnimation() {
        newRankData.forEachIndexed { newIndex, newEntity ->
            // 用于判断新元素是否在旧数据中
            var isIn = false
            var oldPos = -1
            oldRankData.forEachIndexed { oldIndex, oldEntity ->
                if (newEntity.id == oldEntity.id) {
                    isIn = true
                    oldPos = oldIndex
                    oldViewArray[oldIndex].text = "${newEntity.name}：${newEntity.score}"
                }
            }
            if (isIn) {
                // 如果在旧数据中，判断是否需要交换位置
                if (newIndex == oldPos) {
                    // 排名未变，不执行动画
                    sparseArray.append(newIndex, "$newIndex,$newIndex")
                } else {
                    sparseArray.append(newIndex, "$oldPos,$newIndex")
                }
            } else {
                // 如果不在，则直接执行底部交换动画(-1到newIndex)，-1指从视图边界外
                sparseArray.append(newIndex, "-1,$newIndex")
            }
        }

        // 遍历稀疏数组，获取交换位置数据，执行动画
        sparseArray.forEach { _, value ->
            val old = value.split(",")[0].toInt()
            val new = value.split(",")[1].toInt()

            if (old < new) {
                doDownAnimation(old, new)
            } else if (old > new){
                doUpAnimation(old, new)
            } else {
                // 排名不变的情况
                oldViewArray.append(new, tempViewArray[old])
            }
        }

        this.oldRankData = newRankData
    }

    private fun doUpAnimation(old: Int, new: Int) {
        if (old != new) {
            val transY = ObjectAnimator.ofInt(tempViewArray[old], "top", 0, getTopForRank(new) - getTopForRank(old))
            transY.duration = 300
            transY.interpolator = AccelerateDecelerateInterpolator()
            transY.addListener(onEnd = {
                // 更新view缓存
                oldViewArray.append(new, tempViewArray[old])
            })
            transY.start()

            val alpha = ObjectAnimator.ofFloat(tempViewArray[old], "alpha", 1f, 0.5f, 1f)
            alpha.duration = 300
            alpha.interpolator = AccelerateDecelerateInterpolator()
            alpha.start()
        }
    }

    private fun doDownAnimation(old: Int, new: Int) {
        if (old != new) {
            val transY = ObjectAnimator.ofInt(tempViewArray[old], "top", 0, getTopForRank(new) - getTopForRank(old))
            transY.duration = 300
            transY.interpolator = AccelerateDecelerateInterpolator()
            transY.addListener(onEnd = {
                // 更新view缓存
                oldViewArray.append(new, tempViewArray[old])
            })
            transY.start()

            val alpha = ObjectAnimator.ofFloat(tempViewArray[old], "alpha", 1f, 0.5f, 1f)
            alpha.duration = 300
            alpha.interpolator = AccelerateDecelerateInterpolator()
            alpha.start()
        }
    }

    private fun getTopForRank(rank: Int) = when(rank) {
        0 -> {
            rankOneTop
        }
        1 -> {
            rankTwoTop
        }
        else -> {
            rankThreeTop
        }
    }
}