package com.lcy.practice.multiple;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lcy.practice.R;


/**
 * 未注册 Item 管理器.
 * <p>
 * Create By 吴荣 at 2018-09-25 17:11
 */
public class UnregisteredBrickViewManager extends BrickViewManager<UnregisteredException> {

    /**
     * 获取 Layout 资源 ID.
     *
     * @param layoutCode {@link MultipleLayoutSupport} 接口获取的布局编号,由数据对象实现,可用于实现同数据的不同布局.
     */
    @Override
    public int getLayoutResId(int layoutCode) {
        return R.layout.unregistered_multiple_layout;
    }

    /**
     * 绑定 Item 资源.
     *
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    @Override
    public void onBindViewHolder(@NonNull MultipleViewHolder holder, int count, int position, @NonNull UnregisteredException data) {
        TextView title = holder.getView(R.id.text_view_title);
        TextView message = holder.getView(R.id.text_view_message);
        title.setText(data.title);
        message.setText(data.message);
    }
}
