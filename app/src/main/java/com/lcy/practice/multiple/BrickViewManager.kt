package com.lcy.practice.multiple;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * <Desc> BrickViewManager类,主要负责处理相同类型的 ItemView.
 * <p>
 * 在使用时需要指定泛型约束,并将 Item 中所有的视图进行了缓存,在需要重用时取出,减少每次初始化操作的时间消耗,
 * 是典型的空间换时间,能大幅提升列表滑动时的流畅性.
 * <p>
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public abstract class BrickViewManager<T> {

    /**
     * 获取 Layout 资源 ID.
     *
     * @param layoutCode {@link BrickViewSupport} 接口获取的布局编号,由数据对象实现,可用于实现同数据的不同布局.
     */
    @LayoutRes
    public abstract int getLayoutResId(int layoutCode);

    /**
     * 绑定 Item 资源.
     *
     * @param holder   VH 对象.
     * @param count 子元素数目.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    public void onBindViewHolder(@NonNull BrickViewHolder holder, int count, int position, @NonNull T data) {
    }
    /**
     * 绑定 Item 资源.
     *
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    public void onBindViewHolder(@NonNull BrickViewHolder holder, int position, @NonNull T data, List payloads) {
        // 此处默认为空实现,子类按需实现当前方法.
    }


    /**
     * 绑定 Event 资源.
     *
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    public void onBindViewEvent(@NonNull BrickViewHolder holder, int position, @NonNull T data) {
        // 此处默认为空实现,子类按需实现当前方法.
    }

    /**
     * 创建新的 VH 对象实例,由包内的 MultipleAdapter 适配器调用,用于生成相同且重复的布局.
     *
     * @param parent 父视图.
     * @return 返回新的 VH 对象.
     */
    BrickViewHolder createNewMultipleViewHolder(@NonNull ViewGroup parent, int layoutCode) {
        if (getLayoutResId(layoutCode) == 0) {
            return new BrickViewHolder(layoutCode, getHolderView(parent.getContext()) == null ? new View(parent.getContext()) : getHolderView(parent.getContext()));
        } else {
            return new BrickViewHolder(layoutCode, LayoutInflater.from(parent.getContext())
                  .inflate(getLayoutResId(layoutCode), parent, false)
            );
        }
    }

    ////////////////////////////////////////////////////////////
    ////////////////////  辅助方法
    ////////////////////////////////////////////////////////////

    /**
     * 设置隐藏.
     *
     * @param parent 父视图.
     * @param value  字符串值,用于判断.
     * @param ids    显示/隐藏的视图 ID.
     */
    public void setVisibility(View parent, String value, @IdRes int... ids) {
        boolean isVisible = !TextUtils.isEmpty(value);
        for (int id : ids) {
            parent.findViewById(id).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置隐藏.
     *
     * @param parent    父视图.
     * @param isVisible 显示/隐藏标识.
     * @param ids       显示/隐藏的视图 ID.
     */
    public void setVisibility(View parent, boolean isVisible, @IdRes int... ids) {
        for (int id : ids) {
            parent.findViewById(id).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public View getHolderView(Context context) {
        return new View(context);
    }
}
