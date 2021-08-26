package com.lcy.practice.multiple;

import android.view.View;

import java.util.List;

/**
 * 拖拽/滑动回掉
 */
public interface OnMultipleViewDragChangeListener {

    void onItemIdle(View itemView);

    void onItemSwipe(View itemView);

    void onItemDrag(View itemView);

    boolean canDropOver();

    boolean isItemViewSwipeEnabled();

    boolean isLongPressDragEnabled();

    /**
     * 用于判断拖动后是否完成数据交换
     *
     * @param allData      所有的数据
     * @param formPosition 预交换位置
     * @param toPosition   交换目标位置
     * @return 返回true 则会交换位置,否则不予交换,任何情况都交换就直接返回true
     */
    boolean canMove(List<Object> allData, int formPosition, int toPosition);

    /**
     * 用于判断侧滑的条目 是否删除
     *
     * @param allData        所有数据
     * @param removePosition 将要被删除的Item
     * @return 返回true 将会删除,否则不予删除
     */
    boolean canRemove(List<Object> allData, int removePosition);
}
