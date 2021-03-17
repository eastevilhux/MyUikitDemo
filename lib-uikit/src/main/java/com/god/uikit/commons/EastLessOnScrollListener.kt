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
            val manager = recyclerView.getLayoutManager();
            Log.d(TAG,"isUpScroll==>${isUpScroll}")
            manager?.let {
                Log.d(TAG,"run in onScrollStateChanged");
                var lastVisiblePosition: Int = 0;
                if(it is GridLayoutManager){
                    lastVisiblePosition = it.findLastVisibleItemPosition()
                }else if(it is LinearLayoutManager){
                    lastVisiblePosition = it.findLastVisibleItemPosition()
                }
                Log.d(TAG,"ChildCount==>${it.getChildCount()},lastvisiblePosition==>${lastVisiblePosition}," +
                        "ItemCount==>${it.getItemCount()}")
                if (it.getChildCount() > 0             //当当前显示的item数量>0
                    && lastVisiblePosition>= it.itemCount -2           //当当前屏幕最后两个加载项位置>=所有item的数量
                ){
                    currentPage++;
                    Log.d(TAG,"run onLoadMore");
                    onLoadMore?.invoke(currentPage);
                    onScrollListener?.let {
                        it.onLoadMore(currentPage);
                    }
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

        fun onRefresh();

        fun onLoadMore(currentPage: Int);
    }

}
