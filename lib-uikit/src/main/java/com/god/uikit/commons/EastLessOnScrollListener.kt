package com.god.uikit.commons

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.NullPointerException


class EastLessOnScrollListener : RecyclerView.OnScrollListener {

    //声明一个LinearLayoutManager
    private var mLinearLayoutManager: LinearLayoutManager? = null

    //当前页，从0开始
    private var currentPage = 0;
    //已经加载出来的Item的数量
    private var totalItemCount = 0

    //主要用来存储上一个totalItemCount
    private var previousTotal = 0

    //在屏幕上可见的item数量
    private var visibleItemCount = 0

    //在屏幕可见的Item中的第一个
    private var firstVisibleItem = 0

    //是否正在上拉数据
    private var loading = true

    private var onLoadMoreListener : OnLoadMoreListener? = null;

    private var onLoadMore : ((page:Int)->Unit)? = null;

    constructor(mLinearLayoutManager : LinearLayoutManager){
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    constructor(recyclerView: RecyclerView){
        val layoutManager = recyclerView.layoutManager
            ?: throw NullPointerException("the layoutManager of RecyclerView is null");
        if(layoutManager is LinearLayoutManager){
            this.mLinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager;
        }else{
            throw IllegalAccessException("the layoutManager is not LinearLayoutManager");
        }
    }

    constructor(mLinearLayoutManager : LinearLayoutManager,onLoadMoreListener : OnLoadMoreListener){
        this.mLinearLayoutManager = mLinearLayoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    constructor(recyclerView: RecyclerView,onLoadMoreListener : OnLoadMoreListener){
        val layoutManager = recyclerView.layoutManager
            ?: throw NullPointerException("the layoutManager of RecyclerView is null");
        if(layoutManager is LinearLayoutManager){
            this.mLinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager;
        }else{
            throw IllegalAccessException("the layoutManager is not LinearLayoutManager");
        }
        this.onLoadMoreListener = onLoadMoreListener;
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager?.getItemCount()?:0;
        firstVisibleItem = mLinearLayoutManager?.findFirstVisibleItemPosition()?:0;
        if(loading){
            Log.d("wnwn","firstVisibleItem: " +firstVisibleItem);
            Log.d("wnwn","totalPageCount:" +totalItemCount);
            Log.d("wnwn", "visibleItemCount:" + visibleItemCount);

            if(totalItemCount > previousTotal){
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount-visibleItemCount <= firstVisibleItem){
            currentPage ++;
            onLoadMoreListener?.let {
                it.onLoadMore(currentPage);
            }
            onLoadMore?.let {
                it.invoke(currentPage);
            }
            loading = true;
        }
    }

    /**
     * RecyclerView下拉加载监听事件
     * create by Administrator at 2021/1/18 11:10
     * @author Administrator
     * @param onLoadMore
     *      监听事件触发后的流程代码块
     * @return
     *      void
     */
    fun onLoadMore(onLoadMore:((page:Int)->Unit)){
        this.onLoadMore = onLoadMore;
    }


    interface OnLoadMoreListener{
        /**
         * RecyclerView下拉加载监听事件
         * create by Administrator at 2021/1/18 11:08
         * @author Administrator
         * @param page
         *      当前页下标
         * @return
         *      void
         */
        fun onLoadMore(page:Int);
    }
}
