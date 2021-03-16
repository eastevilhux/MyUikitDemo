package com.god.uikit.commons

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

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val manager = recyclerView.getLayoutManager();
        manager?.let {
            val linearLayoutManager = it as LinearLayoutManager;
            if (newState === RecyclerView.SCROLL_STATE_IDLE) { //当前未滑动
                val itemCount: Int = manager.getItemCount() //总数
                val lastItemPosition: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition() //最后显示的位置
                if (lastItemPosition == itemCount - 1 && isUpScroll) {
                    currentPage++;
                    onScrollListener?.onLoadMore(currentPage);
                    recyclerView.scrollToPosition(lastItemPosition);
                    onLoadMore?.let {
                        it.invoke(currentPage);
                    }
                }
                val fristItemPosition: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition() //第一个显示的位置
                if (fristItemPosition == 0 && !isUpScroll) {
                    onScrollListener?.onRefresh()
                    onRefresh?.let {
                        it.invoke();
                    }
                }
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy > sideDis){
            // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
            isUpScroll = dy > 0;
        }
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
