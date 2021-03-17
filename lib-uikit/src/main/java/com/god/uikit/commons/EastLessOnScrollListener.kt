package com.god.uikit.commons

import android.view.View
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
            val layoutManager = it as LinearLayoutManager;
            if (newState == RecyclerView.SCROLL_STATE_IDLE) { //当前未滑动
                val lcp = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lcp == layoutManager.getItemCount() - 2) {
                    // 倒数第2项
                    val fcp = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val child: View? = layoutManager.findViewByPosition(lcp)
                    child?.let {
                        val deltaY: Int = recyclerView.getBottom() - recyclerView.getPaddingBottom() - it.bottom
                        // fcp为0时说明列表滚动到了顶部, 不再滚动
                        if (deltaY > 0 && fcp !== 0) {
                            recyclerView.smoothScrollBy(0, -deltaY)
                        }
                    }
                }else if(lcp== layoutManager.getItemCount() - 1){
                    // 最后一项完全显示, 触发操作, 执行加载更多操作
                    currentPage++;
                    onScrollListener?.let { it.onLoadMore(currentPage) }
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

    fun setOnScrollListener(onScrollListener: OnLoadMoreListener){
        this.onScrollListener = onScrollListener;
    }

    fun loadMore(loadMore:((page:Int)->Unit)){
        this.onLoadMore = loadMore;
    }

    fun onRefresh(onRefresh : ()->Unit){
        this.onRefresh = onRefresh;
    }



    interface OnLoadMoreListener{

        fun onRefresh();

        fun onLoadMore(currentPage: Int);
    }

}
