package com.god.uikit.commons

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.god.uikit.utils.dip2Px

class EastLessOnScrollListener : RecyclerView.OnScrollListener() {

    companion object{
        private const val TAG ="EastLessOnScrollListener==>"
    }

    private var onScrollListener : OnLoadMoreListener? = null;

    //当前页，从0开始
    private var currentPage = 0;

    private var onLoadMore : ((page:Int)->Unit)? = null;
    private var onRefresh : (()->Unit)? = null;
    private var sideDis = 80.dip2Px();

    var isScroll = false;//手势滑动的位置
    var isUpScroll = false //是否正在上滑标记

    @SuppressLint("LongLogTag")
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(newState == RecyclerView.SCROLL_STATE_IDLE){
            val layoutManager = recyclerView.layoutManager
            if (layoutManager == null) {
                Log.e(TAG, "NullPointException:  layoutManager is null")
                return
            }
            if(layoutManager.itemCount <= 1){
                return;
            }
            if (layoutManager is GridLayoutManager) {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                //获取最后一个可见view的位置
                val lastPosition = gridLayoutManager!!.findLastVisibleItemPosition()
                val itemCount = gridLayoutManager.itemCount
                val spanCount = gridLayoutManager.spanCount
                // 提前3个进行预加载
                if (lastPosition + spanCount * 3 >= itemCount - 1) {
                    currentPage++;
                    onLoadMore?.invoke(currentPage);
                    onScrollListener?.onLoadMore(currentPage)
                }
            } else if (layoutManager is LinearLayoutManager) {
                val linearManager = layoutManager as LinearLayoutManager?
                //获取最后一个可见view的位置
                val lastPosition = linearManager!!.findLastVisibleItemPosition()
                val itemCount = linearManager.itemCount
                // 提前3个进行预加载
                if (lastPosition >= itemCount - 4) {
                    currentPage++;
                    onLoadMore?.invoke(currentPage);
                    onScrollListener?.onLoadMore(currentPage)
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        Log.d(TAG,"BEFOR_DX==>${dx},DY==>${dy}")
        super.onScrolled(recyclerView, dx, dy)
        Log.d(TAG,"LAST_DX==>${dx},DY==>${dy}")
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isUpScroll = dy > 0;
    }


    fun setOnScrollListener(onScrollListener: OnLoadMoreListener){
        this.onScrollListener = onScrollListener;
    }

    fun loadMore(loadMore:((page:Int)->Unit)){
        this.onLoadMore = loadMore;
    }

    fun onRefresh(onRefresh : ()->Unit){
        this.onRefresh = onRefresh;
    }

    fun setSideDis(sideDisDip : Int){
        this.sideDis = sideDisDip.dip2Px();
    }

    interface OnLoadMoreListener{
        fun onLoadMore(currentPage: Int);
    }

}
