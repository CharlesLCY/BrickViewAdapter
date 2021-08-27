package com.lcy.practice.multiple;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lcy.practice.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * <Desc> BrickViewAdapter类用于管理BrickViewManager类.
 * Created by Charles Lee on 2021/8/27
 * 15708478830@163.com
 */
@SuppressWarnings({"unchecked", "ConstantConditions", "unused"})
public class BrickViewAdapter extends RecyclerView.Adapter<BrickViewHolder> {

    ////////////////////////////////////////////////////////////
    //////////////////// 构造方法
    ////////////////////////////////////////////////////////////

    private static final String TAG = "BrickViewAdapter";

    ////////////////////////////////////////////////////////////
    //////////////////// 成员变量
    ////////////////////////////////////////////////////////////

    /**
     * 与当前适配器关联的 RecyclerView 对象实例指针.
     * <p>
     * 这里仅仅是一层引用, 由外部初始化并传入.
     */
    private RecyclerView recyclerViewPointer;

    /**
     * 是否为自动滚动.
     * <p>
     * 默认为 false 关闭.
     */
    private boolean isAutoScroll = false;

    /**
     * 滚动模式.
     * <p>
     * 0 : 向顶部滚动, 1 : 向底部滚动.
     * 默认为 0 ;
     */
    private int scrollMode = 0;

    /**
     * Item 触摸事件帮助类.
     */
    @SuppressWarnings("FieldCanBeLocal")
    public ItemTouchHelper touchHelper;

    /**
     * 所有数据源.
     */
    private List<Object> allData;

    /**
     * Manager 映射.
     */
    private final ArrayMap<String, BrickViewManager> managers;

    /**
     * 布局 LayoutCode 映射.
     */
    private final SparseArrayCompat<Integer> multipleLayoutCodes;

    /**
     * 侧滑监听.
     */
    private OnBrickViewDragListener onMultipleViewDragListener;

    /**
     * 回收监听.
     */
    private OnBrickViewRecycledListener mOnBrickViewRecycledListener;

    /**
     * 无参构造器
     */
    public BrickViewAdapter() {
        this.managers = new ArrayMap<>();
        this.multipleLayoutCodes = new SparseArrayCompat<>();
    }

    /**
     * 设置数据源.
     *
     * @param listData 持有的数据源,适配器中不会初始化该数据源,由外部传入.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setAllData(List<Object> listData) {
        // 绑定数据
        allData = listData;
        // 刷新数据
        notifyDataSetChanged();
        // 自动滚动
        if (isAutoScroll && null != recyclerViewPointer && listData.size() > 1) {
            recyclerViewPointer.scrollToPosition(scrollMode == 0 ? 0 : listData.size() - 1);
        }
    }

    /**
     * 局部更新
     */
    public void setDiffData(List<Object> datas) {
        allData = datas;
    }

    /**
     * 设置自动滚动参数.
     * 注意,自动滚动,只有 {@link #setAllData(List)} 时触发判断.如果不符合需求,可手动调用相关方法.
     * 自行调用滚动方法参见 {@link #scrollListToTop()} 和 {@link #scrollListToBottom()}
     *
     * @param autoScroll 是否自动滚动. true 表示自动滚动, false 表示不自动滚动.
     * @param scrollMode 滚动模式. 0 : 自动滚动到顶部. 1 : 自动滚动到底部.
     */
    public void setAutoScroll(boolean autoScroll, int scrollMode) {
        this.isAutoScroll = autoScroll;
        this.scrollMode = scrollMode;
    }

    /**
     * 回收视图信息.
     *
     * @param holder Holder.
     */
    @Override
    public void onViewRecycled(@NonNull BrickViewHolder holder) {
        super.onViewRecycled(holder);
        if (null != mOnBrickViewRecycledListener) {
            mOnBrickViewRecycledListener.onViewRecycled(holder);
        }
    }

    /**
     * 滚动列表到顶部.
     * 封装了通用的计算,避免调用时计算和判断.
     */
    @SuppressWarnings("WeakerAccess")
    public void scrollListToTop() {
        // 判断滚动是否有意义: 非空 && 数据大于1
        if (null != recyclerViewPointer && getItemCount() > 1) {
            recyclerViewPointer.scrollToPosition(0);
        }
    }

    /**
     * 滚动列表到底部.
     * 封装了通用的计算,避免调用时计算和判断.
     */
    @SuppressWarnings("WeakerAccess")
    public void scrollListToBottom() {
        // 判断滚动是否有意义: 非空 && 数据大于1
        if (null != recyclerViewPointer && getItemCount() > 1) {
            recyclerViewPointer.scrollToPosition(getItemCount() - 1);
        }
    }

    /**
     * 通用初始化操作.
     * 初始化 Recycler View 列表.
     *
     * @param recyclerView Recycler View 列表实例.
     */
    public void initRecyclerView(@NonNull RecyclerView recyclerView) {
        // 初始化 RecyclerView 对象实例指针.
        this.recyclerViewPointer = recyclerView;
        // 绑定适配器
        recyclerView.setAdapter(this);
        // 绑定默认布局管理器.
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // 初始化触摸管理器,用于处理侧滑,拖动排序等.
        TouchCallback multipleViewTouchCallback = new TouchCallback();
        // 初始化触摸帮助类.
        touchHelper = new ItemTouchHelper(multipleViewTouchCallback);
        // 绑定触摸帮助类,关联 RecyclerView 对象实例,并由帮助类关联至触摸回调处理器.
        touchHelper.attachToRecyclerView(recyclerView);
        // 初始化默认动画执行器.
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        // 设置新增动画时长为 1 秒.
        defaultItemAnimator.setAddDuration(300);
        // 设置删除动画时长为 1 秒.
        defaultItemAnimator.setRemoveDuration(300);
        // 绑定默认动画执行器.
        recyclerView.setItemAnimator(defaultItemAnimator);
    }

    /**
     * 注册 Item 类及 Item 管理器.
     *
     * @param cls     Item 类名
     * @param manager Item 管理器.
     * @param <T>     Item 泛型约束.
     * @param <R>     管理器泛型约束.
     */
    public <T, R extends BrickViewManager<T>> void register(@NonNull Class<T> cls, @NonNull R manager) {
        managers.put(cls.getName(), manager);

    }

    /**
     * 注册 Item 类及 Item 管理器.
     *
     * @param cls         Item 类名.
     * @param manager     Item 管理器.
     * @param layoutCodes 可变长入参,传入多个布局 code , 用于实现不同 code 不同布局.
     * @param <T>         Item 泛型约束.
     * @param <R>         管理器泛型约束.
     */
    public <T, R extends BrickViewManager<T>> void register(@NonNull Class<T> cls, @NonNull R manager, int... layoutCodes) {
        for (int layoutCode : layoutCodes) {
            managers.put(cls.getName() + layoutCode, manager);
        }
    }

    /**
     * 当 RecyclerView 需要未创建的给定类型时调用.
     *
     * @param parent   表示数据集中给定位置应更新ViewHolder的项目内容。
     * @param viewType 新的视图类型.
     * @return 指定视图类型的 VH 对象实例.
     */
    @NonNull
    @Override
    public BrickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return managers.get(managers.keyAt(viewType)).createNewMultipleViewHolder(parent, multipleLayoutCodes.get(viewType) == null ? -1 : multipleLayoutCodes.get(viewType));
    }

    /**
     * 当 RecyclerView 需要复用并更新指定位置的数据时调用.
     *
     * @param holder   现有的 VH 对象实例.
     * @param position 指定位置的下标.
     */
    @Override
    public void onBindViewHolder(@NonNull BrickViewHolder holder, int position) {
        onBindViewHolder(holder, position, null);
    }

    /**
     * 当 RecyclerView 需要复用并更新指定位置的数据时调用.
     *
     * @param holder   现有的 VH 对象实例.
     * @param position 指定位置的下标.
     * @param payloads 指定更新部分view数据
     */
    @Override
    public void onBindViewHolder(@NonNull BrickViewHolder holder, int position, @Nullable List payloads) {
        // 获取指定下标的数据
        Object obj = allData.get(position);
        // 尝试通过类名从管理器映射中获取管理器对象
        BrickViewManager manager = managers.get(obj.getClass().getName());
        // 如果获取失败, 则判断是否支持多布局模式,如果支持则重新获取多布局模式管理器.
        if (null == manager && obj instanceof BrickViewSupport) {
            // 通过多布局模式重新在管理器映射中获取管理器对象实例.
            manager = managers.get(obj.getClass().getName() + ((BrickViewSupport) obj).getMultipleLayoutCode());
        }
        // 如果管理器依然为空,则使用未注册管理器,避免发生异常造成闪退.
        if (null == manager) {
            manager = new UnregisteredBrickViewManager();
            Logger.w(TAG, String.format(
                    Locale.getDefault(),
                    "数据:\r\n%s\r\nManager Key :%s\r\n%s%d",
                    JsonUtil.jsonBeautify(new Gson().toJson(obj)),
                    obj.getClass().getName(),
                    obj.getClass().getName(),
                    ((BrickViewSupport) obj).getMultipleLayoutCode()));
            obj = new UnregisteredObject("Not Found Manager",
                    String.format(
                            Locale.getDefault(),
                            "Manager Key :%s\r\n%s",
                            obj.getClass().getName(),
                            obj.getClass().getName() + ((BrickViewSupport) obj).getMultipleLayoutCode()
                    )
            );
        }
        if (payloads == null || payloads.size() <= 0) {
            // 通过管理器,绑定 VH 对象实例.
            manager.onBindViewHolder(holder, allData.size(), position, obj);
        } else {
            manager.onBindViewHolder(holder, position, obj, payloads);
        }

        // 通过管理器,绑定 VH 事件处理.
        manager.onBindViewEvent(holder, position, obj);
    }

    /**
     * 返回适配器所拥有的数据集中的项目总数。
     *
     * @return 数据集总数.
     */
    @Override
    public int getItemCount() {
        return allData.size();
    }

    /**
     * 当 RecyclerView 需要未创建的给定类型时,为确定 View Type 类型而调用.
     *
     * @param position 指定位置下标.
     * @return View Type 分类数值,每个数值代表一个类型.
     */
    @Override
    public int getItemViewType(int position) {
        Object obj = allData.get(position);
        int viewType = managers.indexOfKey(obj.getClass().getName());

        if (viewType < 0 && obj instanceof BrickViewSupport) {
            viewType = managers.indexOfKey(obj.getClass().getName() + ((BrickViewSupport) obj).getMultipleLayoutCode());
            multipleLayoutCodes.put(viewType, ((BrickViewSupport) obj).getMultipleLayoutCode());
        }

        if (viewType < 0) {
            for (String key : managers.keySet()) {
                Logger.i(TAG, String.format("Managers All Key is >>>> %s", key));
            }
            Logger.w(TAG, String.format("未找到<%s>相关ViewManager是否未注册???", obj.getClass().getName()));

            throw new RuntimeException(String.format(TAG + " >>>> ViewManager注册错误！\n未找到<%s>对应的ViewManager", obj.getClass().getName()));
        }

        if (viewType >= 0 && obj instanceof BrickViewSupport) {
            managers.put(obj.getClass().getName() + ((BrickViewSupport) obj).getMultipleLayoutCode(), managers.get(obj.getClass().getName()));
            viewType = managers.indexOfKey(obj.getClass().getName() + ((BrickViewSupport) obj).getMultipleLayoutCode());
            multipleLayoutCodes.put(viewType, ((BrickViewSupport) obj).getMultipleLayoutCode());
        }
        return viewType;
    }

    public void setOnMultipleViewDragListener(OnBrickViewDragListener onMultipleViewDragListener) {
        this.onMultipleViewDragListener = onMultipleViewDragListener;
    }

    /**
     * 获取指定下标的 Holder 对象实例.
     *
     * @param position 下标.
     * @return Holder.
     */
    @SuppressWarnings("WeakerAccess")
    public BrickViewHolder getPositionHolder(int position) {
        RecyclerView.ViewHolder holder = recyclerViewPointer.findViewHolderForAdapterPosition(position);
        return holder instanceof BrickViewHolder ? (BrickViewHolder) holder : null;
    }

    /**
     * 获取指定 position 下标 Holder 中的指定 View.
     *
     * @param position 指定获取 Holder 的下标.
     * @param id       指定 ID.
     * @return 指定 position 下标 Holder 中的指定 View.
     */
    public View getViewFromPositionHolder(int position, @IdRes int id) {
        return getPositionHolder(position) == null ? null : getPositionHolder(position).getView(id);
    }

    /**
     * 获取 Multiple View 回收监听器.
     *
     * @return Multiple View 回收监听器对象实例.
     */
    public OnBrickViewRecycledListener getOnMultipleViewRecycledListener() {
        return mOnBrickViewRecycledListener;
    }

    /**
     * 设置 Multiple View 回收监听器.
     *
     * @param onBrickViewRecycledListener Multiple View 回收监听器对象实例.
     */
    public void setOnMultipleViewRecycledListener(OnBrickViewRecycledListener onBrickViewRecycledListener) {
        this.mOnBrickViewRecycledListener = onBrickViewRecycledListener;
    }

    public class TouchCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
            int dragFlag;
            int swipeFlag;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                swipeFlag = 0;
            } else {
                dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
                swipeFlag = ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            if (null != onMultipleViewDragListener && onMultipleViewDragListener.canMove(allData, viewHolder.getAdapterPosition(), target.getAdapterPosition())) {
                Collections.swap(allData, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (null != onMultipleViewDragListener && onMultipleViewDragListener.canRemove(allData, viewHolder.getAdapterPosition())) {
                allData.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public boolean isLongPressDragEnabled() {
            if (null != onMultipleViewDragListener) {
                return onMultipleViewDragListener.isLongPressDragEnabled();
            }
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            if (null != onMultipleViewDragListener) {
                return onMultipleViewDragListener.isItemViewSwipeEnabled();
            }
            return false;
        }

        @Override
        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
            if (null != onMultipleViewDragListener) {
                return onMultipleViewDragListener.canDropOver();
            }
            return false;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            switch (actionState) {
                // 闲置状态
                case ItemTouchHelper.ACTION_STATE_IDLE:
                    if (null != onMultipleViewDragListener) {
                        onMultipleViewDragListener.onItemIdle(null == viewHolder ? null : viewHolder.itemView);
                    }
                    break;
                // 滑动状态
                case ItemTouchHelper.ACTION_STATE_SWIPE:
                    if (null != onMultipleViewDragListener) {
                        onMultipleViewDragListener.onItemSwipe(viewHolder.itemView);
                    }
                    break;
                // 拖拽状态
                case ItemTouchHelper.ACTION_STATE_DRAG:
                    if (null != onMultipleViewDragListener) {
                        onMultipleViewDragListener.onItemDrag(viewHolder.itemView);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (viewHolder != null) {
                viewHolder.itemView.setBackgroundColor(0);
            }
            notifyItemChanged(viewHolder.getAdapterPosition());
//            notifyDataSetChanged();
        }
    }
}
