package com.god.uikit.commons

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EastLessOnScrollListener : RecyclerView.OnScrollListener() {

    companion object{
        private const val TAG ="EastLessOnScrollListener==>"
    }

    private var onScrollListener : OnLoadMoreListener? = null;

    //当前页，从0开始
    private var currentPage = 0;

    private var onLoadMore : ((page:Int)->Unit)? = null;
    private var onRefresh : (()->Unit)? = null;

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
                    onScrollListener?.onRefresh();
                    onRefresh?.let {
                        it.invoke();
                    }
                }
                val fristItemPosition: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition() //第一个显示的位置
                if (fristItemPosition == 0 && !isUpScroll) {
                    currentPage++;
                    onScrollListener?.onLoadMore(currentPage);
                    onLoadMore?.let {
                        it.invoke(currentPage);
                    }
                }
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isUpScroll = dy > 0;
    }


    interface OnLoadMoreListener{

        fun onRefresh();

        fun onLoadMore(currentPage: Int);
    }

}
