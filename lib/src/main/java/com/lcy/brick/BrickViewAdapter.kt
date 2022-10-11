package com.lcy.brick

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.collection.ArrayMap
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.*
import java.util.*

/**
 * BrickViewAdapter类用于管理BrickViewManager类.（目前是java，转换成Kotlin后onBindViewHolder()参数存在报错问题）
 * Created by Charles Lee on 2021/8/27
 * 15708478830@163.com
 */
class BrickViewAdapter : RecyclerView.Adapter<BrickViewHolder>() {

    companion object {
        private const val TAG = "BrickViewAdapter"
    }

    /**
     * 与当前适配器关联的 RecyclerView 对象实例指针.
     *
     *
     * 这里仅仅是一层引用, 由外部初始化并传入.
     */
    private var recyclerViewPointer: RecyclerView? = null

    /**
     * 是否为自动滚动.
     *
     *
     * 默认为 false 关闭.
     */
    private var isAutoScroll = false

    /**
     * 滚动模式.
     *
     *
     * 0 : 向顶部滚动, 1 : 向底部滚动.
     * 默认为 0 ;
     */
    private var scrollMode = 0

    /**
     * Item 触摸事件帮助类.
     */
    private var touchHelper: ItemTouchHelper? = null

    /**
     * 所有数据源.
     */
    private var allData = mutableListOf<Any>()

    /**
     * Manager 映射.
     */
    private val managers: ArrayMap<String, BrickViewManager<Any>> = ArrayMap()

    /**
     * 布局 LayoutCode 映射.
     */
    private val layoutCodes: SparseArrayCompat<Int?> = SparseArrayCompat()

    /**
     * 侧滑监听.
     */
    private var onBrickViewDragListener: OnBrickViewDragListener? = object: BrickViewDragListenerAdapter() {
        override fun canDropOver(): Boolean {
            return true
        }

        override fun canMove(
            allData: MutableList<Any>,
            formPosition: Int,
            toPosition: Int
        ): Boolean {
            return true
        }
    }

    /**
     * 回收监听.
     */
    private var onBrickViewRecycledListener: OnBrickViewRecycledListener? = null

    /**
     * 设置数据源.
     *
     * @param listData 持有的数据源，由外部传入，适配器中默认初始化了空数据源。
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(listData: MutableList<Any>) {
        // 绑定数据
        allData = listData
        // 刷新数据
        notifyDataSetChanged()
        // 自动滚动
        if (isAutoScroll && null != recyclerViewPointer && listData.size > 1) {
            recyclerViewPointer?.scrollToPosition(if (scrollMode == 0) 0 else listData.size - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(listData: MutableList<Any>) {
        allData.addAll(listData)
        // 刷新数据
        notifyDataSetChanged()
    }

    /**
     * 局部更新
     */
    fun setDiffData(dataList: MutableList<Any>) {
        allData = dataList
    }

    /**
     * 设置自动滚动参数.
     * 注意,自动滚动,只有 [.setAllData] 时触发判断.如果不符合需求,可手动调用相关方法.
     * 自行调用滚动方法参见 [.scrollListToTop] 和 [.scrollListToBottom]
     *
     * @param autoScroll 是否自动滚动. true 表示自动滚动, false 表示不自动滚动.
     * @param scrollMode 滚动模式. 0 : 自动滚动到顶部. 1 : 自动滚动到底部.
     */
    fun setAutoScroll(autoScroll: Boolean, scrollMode: Int) {
        this.isAutoScroll = autoScroll
        this.scrollMode = scrollMode
    }

    /**
     * 回收视图信息.
     *
     * @param holder Holder.
     */
    override fun onViewRecycled(holder: BrickViewHolder) {
        super.onViewRecycled(holder)
        if (null != onBrickViewRecycledListener) {
            onBrickViewRecycledListener?.onViewRecycled(holder)
        }
    }

    /**
     * 滚动列表到顶部.
     * 封装了通用的计算,避免调用时计算和判断.
     */
    fun scrollListToTop() {
        // 判断滚动是否有意义: 非空 && 数据大于1
        if (null != recyclerViewPointer && itemCount > 1) {
            recyclerViewPointer?.scrollToPosition(0)
        }
    }

    /**
     * 滚动列表到底部.
     * 封装了通用的计算,避免调用时计算和判断.
     */
    fun scrollListToBottom() {
        // 判断滚动是否有意义: 非空 && 数据大于1
        if (null != recyclerViewPointer && itemCount > 1) {
            recyclerViewPointer?.scrollToPosition(itemCount - 1)
        }
    }

    /**
     * 滚动列表到指定位置.
     * 封装了通用的计算,避免调用时计算和判断.
     */
    fun scrollListToPosition(position: Int) {
        // 判断滚动是否有意义: 非空 && 数据大于1
        if (null != recyclerViewPointer && itemCount > 1) {
            recyclerViewPointer?.scrollToPosition(position)
        }
    }

    /**
     * 通用初始化操作.
     * 初始化 Recycler View 列表.
     *
     * @param recyclerView Recycler View 列表实例.
     */
    fun initRecyclerView(recyclerView: RecyclerView) {
        // 初始化 RecyclerView 对象实例指针.
        recyclerViewPointer = recyclerView
        // 绑定适配器
        recyclerView.adapter = this
        // 绑定默认布局管理器.
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        // 初始化触摸管理器,用于处理侧滑,拖动排序等.
        val brickViewTouchCallback = TouchCallback()
        // 初始化触摸帮助类.
        touchHelper = ItemTouchHelper(brickViewTouchCallback)
        // 绑定触摸帮助类,关联 RecyclerView 对象实例,并由帮助类关联至触摸回调处理器.
        touchHelper?.attachToRecyclerView(recyclerView)
        // 初始化默认动画执行器.
        val defaultItemAnimator = DefaultItemAnimator()
        // 设置新增动画时长为 1 秒.
        defaultItemAnimator.addDuration = 300
        // 设置删除动画时长为 1 秒.
        defaultItemAnimator.removeDuration = 300
        // 绑定默认动画执行器.
        recyclerView.itemAnimator = defaultItemAnimator
    }

    /**
     * 注册 Item 类及 Item 管理器.
     *
     * @param cls     Item 类名
     * @param manager Item 管理器.
     * @param <T>     Item 泛型约束.
     * @param <M>     管理器泛型约束.
    </M></T> */
    fun <T, M : BrickViewManager<T>?> register(cls: Class<T>, manager: M) {
        managers[cls.name] = manager as BrickViewManager<Any>
    }

    /**
     * 注册 Item 类及 Item 管理器.
     *
     * @param cls         Item 类名.
     * @param manager     Item 管理器.
     * @param layoutCodes 可变长入参,传入多个布局 code , 用于实现不同 code 不同布局.
     * @param <T>         Item 泛型约束.
     * @param <M>         管理器泛型约束.
    </M></T> */
    fun <T, M : BrickViewManager<T>?> register(cls: Class<T>, manager: M, vararg layoutCodes: Int) {
        for (layoutCode in layoutCodes) {
            managers[cls.name + layoutCode] = manager as BrickViewManager<Any>
        }
    }

    /**
     * 注册 Item 类及 Item 管理器.
     *
     * @param cls         Item 类名.
     * @param manager     Item 管理器.
     * @param layoutCodes 多布局layoutCode集合.
     * @param <T>         Item 泛型约束.
     * @param <M>         管理器泛型约束.
    </M></T> */
    fun <T, M : BrickViewManager<T>?> register(cls: Class<T>, manager: M, layoutCodes: List<Int>) {
        for (layoutCode in layoutCodes) {
            managers[cls.name + layoutCode] = manager as BrickViewManager<Any>
        }
    }

    fun startDrag(viewHolder: RecyclerView.ViewHolder?) {
        if (touchHelper != null) {
            touchHelper?.startDrag(viewHolder!!)
        }
    }

    /**
     * 当 RecyclerView 需要未创建的给定类型时调用.
     *
     * @param parent   表示数据集中给定位置应更新ViewHolder的项目内容。
     * @param viewType 新的视图类型.
     * @return 指定视图类型的 VH 对象实例.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrickViewHolder {
        return managers[managers.keyAt(viewType)]!!
            .createNewBrickViewHolder(
                parent,
                (if (layoutCodes[viewType] == null) -1 else layoutCodes[viewType])!!
            )
    }

    /**
     * 当 RecyclerView 需要复用并更新指定位置的数据时调用.
     *
     * @param holder   现有的 VH 对象实例.
     * @param position 指定位置的下标.
     */
    override fun onBindViewHolder(holder: BrickViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    /**
     * 当 RecyclerView 需要复用并更新指定位置的数据时调用.
     *
     * @param holder   现有的 VH 对象实例.
     * @param position 指定位置的下标.
     * @param payloads 指定更新部分view数据
     */
    override fun onBindViewHolder(
        holder: BrickViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        // 获取指定下标的数据
        var obj = allData[position]
        // 尝试通过类名从管理器映射中获取管理器对象
        var manager: BrickViewManager<Any>?
        // 如果获取失败, 则判断是否支持多布局模式,如果支持则重新获取多布局模式管理器.
        manager = if (obj is BrickViewSupport) {
            // 通过多布局模式重新在管理器映射中获取管理器对象实例.
            managers[obj.javaClass.name + obj.layoutCode]
        } else {
            managers[obj.javaClass.name]
        }
        // 如果管理器依然为空,则使用未注册管理器,避免发生异常造成闪退.
        if (null == manager) {
            manager = UnregisteredBrickViewManager() as BrickViewManager<Any>
            obj = UnregisteredObject(
                "Not Found Manager", String.format(
                    Locale.getDefault(),
                    "Manager Key :%s\r\n%s",
                    obj.javaClass.name,
                    obj.javaClass.name + (obj as BrickViewSupport?)!!.layoutCode
                )
            )
        }
        if (payloads.isEmpty()) {
            // 通过管理器,绑定 VH 对象实例.
            manager.onBindViewHolder(holder, allData.size, position, obj)
        } else {
            manager.onBindViewHolder(holder, position, obj, payloads)
        }

        // 通过管理器,绑定 VH 事件处理.
        manager.onBindViewEvent(holder, position, obj)
    }

    /**
     * 返回适配器所拥有的数据集中的项目总数。
     *
     * @return 数据集总数.
     */
    override fun getItemCount(): Int {
        return allData.size
    }

    /**
     * 当 RecyclerView 需要未创建的给定类型时,为确定 View Type 类型而调用.
     *
     * @param position 指定位置下标.
     * @return View Type 分类数值,每个数值代表一个类型.
     */
    override fun getItemViewType(position: Int): Int {
        val obj = allData[position]
        var viewType = managers.indexOfKey(obj.javaClass.name)
        if (viewType < 0 && obj is BrickViewSupport) {
            viewType = managers.indexOfKey(obj.javaClass.name + obj.layoutCode)
            layoutCodes.put(viewType, obj.layoutCode)
        }
        if (viewType < 0) {
            for (key in managers.keys) {
                Log.i(TAG, String.format("Already registered obj >>>> %s", key))
            }
            val errorMsg = String.format(
                "$TAG >>>>>>>>>> register error！！may not register <%s> for BrickViewAdapter",
                obj.javaClass.name
            )
            Log.i(TAG, errorMsg)
            throw RuntimeException(errorMsg)
        }
        return viewType
    }

    fun setOnBrickViewDragListener(onBrickViewDragListener: OnBrickViewDragListener?) {
        this.onBrickViewDragListener = onBrickViewDragListener
    }

    fun setOnBrickViewRecycledListener(onBrickViewRecycledListener: OnBrickViewRecycledListener?) {
        this.onBrickViewRecycledListener = onBrickViewRecycledListener
    }

    /**
     * 获取指定下标的 Holder 对象实例.
     *
     * @param position 下标.
     * @return Holder.
     */
    fun getPositionHolder(position: Int): BrickViewHolder? {
        val holder = recyclerViewPointer?.findViewHolderForAdapterPosition(position)
        return if (holder is BrickViewHolder) holder else null
    }

    /**
     * 获取指定 position 下标 Holder 中的指定 View.
     *
     * @param position 指定获取 Holder 的下标.
     * @param id       指定 ID.
     * @return 指定 position 下标 Holder 中的指定 View.
     */
    fun getViewFromPositionHolder(position: Int, @IdRes id: Int): View? {
        return if (getPositionHolder(position) == null) null else getPositionHolder(position)!!.getView(
            id
        )
    }

    inner class TouchCallback : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlag: Int
            val swipeFlag: Int
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                dragFlag =
                    ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                swipeFlag = 0
            } else {
                dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP
                swipeFlag = ItemTouchHelper.END
            }
            return makeMovementFlags(dragFlag, swipeFlag)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if (null != onBrickViewDragListener && onBrickViewDragListener!!.canMove(
                    allData,
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
            ) {
                Collections.swap(allData, viewHolder.adapterPosition, target.adapterPosition)
                notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (null != onBrickViewDragListener && onBrickViewDragListener!!.canRemove(
                    allData,
                    viewHolder.adapterPosition
                )
            ) {
                allData.removeAt(viewHolder.adapterPosition)
                notifyItemRemoved(viewHolder.adapterPosition)
            }
        }

        override fun isLongPressDragEnabled(): Boolean {
            return if (null != onBrickViewDragListener) {
                onBrickViewDragListener!!.isLongPressDragEnabled
            } else false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return if (null != onBrickViewDragListener) {
                onBrickViewDragListener!!.isItemViewSwipeEnabled
            } else false
        }

        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return if (null != onBrickViewDragListener) {
                onBrickViewDragListener!!.canDropOver()
            } else false
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            when (actionState) {
                ItemTouchHelper.ACTION_STATE_IDLE -> if (null != onBrickViewDragListener) {
                    onBrickViewDragListener!!.onItemIdle(viewHolder?.itemView)
                }
                ItemTouchHelper.ACTION_STATE_SWIPE -> if (null != onBrickViewDragListener) {
                    onBrickViewDragListener!!.onItemSwipe(viewHolder!!.itemView)
                }
                ItemTouchHelper.ACTION_STATE_DRAG -> if (null != onBrickViewDragListener) {
                    onBrickViewDragListener!!.onItemDrag(viewHolder!!.itemView)
                }
                else -> {}
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.setBackgroundColor(0)
            notifyItemChanged(viewHolder.adapterPosition)
//            notifyDataSetChanged()
        }
    }
}