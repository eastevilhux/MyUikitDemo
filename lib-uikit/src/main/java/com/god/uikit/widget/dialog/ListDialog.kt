package com.god.uikit.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.style.LineBackgroundSpan
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.R
import com.god.uikit.adapter.ItemTextAdapter
import com.god.uikit.commons.Constants
import com.god.uikit.databinding.DialogListBinding
import com.god.uikit.entity.Item
import com.god.uikit.entity.ItemText
import com.god.uikit.presenter.ItemTextPresenter
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.isNotNullOrEmpty
import kotlinx.android.synthetic.main.layout_selectitem.*

class ListDialog private constructor(builder:Builder) : Dialog(builder.context, R.style.DialogStyle),
    ItemTextPresenter{

    companion object{
        private const val TAG = "ListDialog=>";
        const val SELECT_TYPE_DISMISS = 0x01;
        const val SELECT_TYPE_SHOW = 0x02;
        const val TYPE_IMAGE = 0x01;
        const val TYPE_TEXT = 0x02;
    }

    private lateinit var dataBinding : DialogListBinding;
    private lateinit var adapter : ItemTextAdapter;
    private val title = ObservableField<String>();
    private val haveTitle = ObservableField<Boolean>();
    private val haveBack = ObservableField<Boolean>();
    private val haveMenu = ObservableField<Boolean>();
    private val haveSubmit = ObservableField<Boolean>();
    private val backText = ObservableField<String>();
    private val menuText = ObservableField<String>();
    private val submitText = ObservableField<String>();
    private var itemList : MutableList<ItemText>? = null;
    private var selectType : Int = SELECT_TYPE_DISMISS;
    private var onSlectedListener : OnSelectedListener? = null;
    private var backWidth : Int = 0;
    private var backHeight : Int = 0;
    private var menuWidth : Int = 0;
    private var menuHeight : Int = 0;
    private var backType : Int = TYPE_TEXT;
    private var menuType : Int = TYPE_TEXT;

    private var backSrc : Int = R.drawable.back_arrow;
    private var menuSrc : Int = R.drawable.ic_title_menu_white;
    private var backUrl : String? = null;
    private var menuUrl : String? = null;
    private var backImageType : Int = Constants.IMAGE_TYPE_RESOURCE;
    private var menuImageType : Int = Constants.IMAGE_TYPE_RESOURCE;
    private var backgroundResource : Int = R.drawable.bg_button;

    private var lastSelect : Int = -1;


    private var select : ((itemText : ItemText)->Unit)? = null;

    init {
        title.set(builder.title)
        haveTitle.set(builder.haveTitle);
        haveBack.set(builder.haveBack);
        haveMenu.set(builder.haveMenu)
        haveSubmit.set(builder.haveSubmit)
        backText.set(builder.backText);
        menuText.set(builder.menuText)
        submitText.set(builder.submitText)
        itemList = builder.itemList;
        onSlectedListener = builder.onSlectedListener;
        selectType = builder.selectType;
        backWidth = builder.backWidth;
        backHeight = builder.backHeight;
        menuWidth = builder.menuWidth;
        menuHeight = builder.menuHeight;
        backType = builder.backType;
        menuType = builder.menuType;
        backSrc = builder.backSrc;
        menuSrc = builder.menuSrc;
        backUrl = builder.backUrl;
        menuUrl = builder.menuUrl;
        backImageType = builder.backImageType;
        menuImageType = builder.menuImageType;
        backgroundResource = builder.backgroundResource;
        adapter = ItemTextAdapter(itemList,builder.context);
        adapter.setPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_list,null,false);
        dataBinding.adapter = adapter;
        dataBinding.haveBack = haveBack;
        dataBinding.haveTitle = haveTitle;
        dataBinding.haveMenu = haveMenu;
        dataBinding.haveSubmit = haveSubmit;
        dataBinding.titleText = title;
        dataBinding.backText = backText;
        dataBinding.menuText = menuText;
        dataBinding.submitText = submitText;
        dataBinding.dialog = this;
        dataBinding.menuButton.setBackgroundResource(backgroundResource);
        initBack();
        initMenu();
        countSize();
        setContentView(dataBinding.root);
    }

    private fun countSize(){
        val titleHeight = 30.dip2Px();
        val bottomHeight = 50.dip2Px();
        val size = itemList?.size?:0;
        var itemHeight : Int = 0;
        if(size > 0){
            itemHeight = (size * 40.dip2Px()) + ((size-1).dip2Px());
        }
        val height = titleHeight + bottomHeight + itemHeight;

        val dialogWindow = window
        val lp = dialogWindow!!.attributes

        lp.width = ViewUtil.getScreenSize(context)[0] - 100.dip2Px();
        lp.height = height
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        dialogWindow!!.attributes = lp
    }

    override fun onItemText(itemText: ItemText) {
        if(selectType == SELECT_TYPE_DISMISS){
            dismiss();
        }else{
            itemText.selected = true;
            if(lastSelect != -1){
                itemList?.get(lastSelect)?.selected = false
                lastSelect = itemList?.indexOf(itemText)?:-1;
            }else{
                lastSelect = itemList?.indexOf(itemText)?:-1;
            }
            adapter.list = itemList;
            adapter.notifyDataSetChanged();
        }
        select?.invoke(itemText);
        onSlectedListener?.onSelect(itemText);
    }


    private fun initBack() {
        if(backWidth > 0 && backHeight > 0){
            val params = dataBinding.tvBack.layoutParams;
            params.width = backWidth.dip2Px();
            params.height = backHeight.dip2Px();
            dataBinding.tvBack.layoutParams = params;
        }
        if(backType == TYPE_IMAGE){
            //返回为图片类型
            if(backImageType == Constants.IMAGE_TYPE_RESOURCE){
                //图片为资源类型
                dataBinding.tvBack.setBackgroundResource(backSrc);
            }else{
                //图片为网络类型，暂不支持
            }
        }else{
            //返回为文本形式，不做处理,已通过dataBinding方式设置文本显示
        }
    }

    private fun initMenu() {
        if(menuWidth > 0 && menuHeight > 0){
            val params = dataBinding.tvMenu.layoutParams;
            params.width = menuWidth.dip2Px();
            params.height = menuHeight.dip2Px();
            dataBinding.tvMenu.layoutParams = params;
        }
        if(menuType == TYPE_IMAGE){
            //返回为图片类型
            if(menuImageType == Constants.IMAGE_TYPE_RESOURCE){
                //图片为资源类型
                dataBinding.tvMenu.setBackgroundResource(backSrc);
            }else{
                //图片为网络类型，暂不支持
            }
        }else{
            //返回为文本形式，不做处理,已通过dataBinding方式设置文本显示

        }
    }

    fun notifyList(itemList: MutableList<ItemText>){
        this.itemList = itemList;
        countSize();
        adapter.list = itemList;
        adapter.notifyDataSetChanged();
    }

    fun setSelectType(selectType : Int){
        this.selectType = selectType;
    }

    fun setTitle(title : String){
        this.title.set(title);
        if(title.isNotNullOrEmpty()){
            this.haveMenu.set(true);
        }
    }

    fun setBack(backText: String){
        this.backText.set(backText);
        this.backType = TYPE_TEXT;
        initBack();
    }

    fun setBack(backSrc: Int){
        this.backSrc = backSrc;
        this.backImageType = Constants.IMAGE_TYPE_RESOURCE;
        initBack();
    }

    fun setMenu(menuText : String){
        this.menuText.set(menuText);
        this.menuType = TYPE_TEXT;
        initMenu();
    }

    fun setMenu(menuSrc: Int){
        this.menuSrc = menuSrc;
        this.menuImageType = Constants.IMAGE_TYPE_RESOURCE;
        initMenu();
    }

    fun setSubmitText(submitText: String){
        this.submitText.set(submitText);
        if(submitText.isNotNullOrEmpty()){
            this.haveSubmit.set(true);
        }
    }

    fun setSubmitBackground(backgroundResource : Int){
        this.backgroundResource = backgroundResource;
        dataBinding.menuButton.setBackgroundResource(backgroundResource);
    }


    class Builder(){
        lateinit var context : Context;

        internal var haveTitle : Boolean = false;
        internal var haveBack : Boolean = false;
        internal var haveMenu : Boolean = false;
        internal var haveSubmit : Boolean = false;
        internal var title : String? = null;
        internal var backText : String? = null;
        internal var menuText : String? = null;
        internal var submitText : String? = null;
        internal var itemList : MutableList<ItemText>? = null;
        internal var selectType : Int = SELECT_TYPE_DISMISS;
        internal var onSlectedListener : OnSelectedListener? = null;
        internal var backWidth : Int = 0;
        internal var backHeight : Int = 0;
        internal var menuWidth : Int = 0;
        internal var menuHeight : Int = 0;
        internal var backType : Int = TYPE_TEXT;
        internal var menuType : Int = TYPE_TEXT;
        internal var backImageType : Int = Constants.IMAGE_TYPE_RESOURCE;
        internal var menuImageType : Int = Constants.IMAGE_TYPE_RESOURCE;
        internal var backSrc : Int = R.drawable.back_arrow;
        internal var menuSrc : Int = R.drawable.ic_title_menu_white;
        internal var backUrl : String? = null;
        internal var menuUrl : String? = null;
        internal var backgroundResource : Int = R.drawable.bg_button;

        constructor(context: Context) : this() {
            this.context = context;
        }

        fun title(title : String) : Builder{
            this.title = title;
            if(title.isNotNullOrEmpty()){
                haveTitle = true;
            }
            return this;
        }

        fun backText(backText : String) : Builder{
            this.backText = backText;
            if(backText.isNotNullOrEmpty()){
                haveBack = true;
            }
            this.backType = TYPE_TEXT;
            return this;
        }

        fun backSrc(backSrc: Int): Builder {
            this.backSrc = backSrc;
            this.backType = TYPE_IMAGE;
            this.backImageType = Constants.IMAGE_TYPE_RESOURCE;
            return this;
        }

        /**
         * 设置返回图片为网络类型,暂不支持
         * create by Administrator at 2020/11/30 16:49
         * @author Administrator
         * @param
         * @return
         */
        fun backUrl(backUrl : String): Builder {
            this.backUrl = backUrl;
            this.backType = TYPE_IMAGE;
            this.backImageType = Constants.IMAGE_TYPE_URL;
            return this;
        }

        fun menuText(menuText : String): Builder {
            this.menuText = menuText;
            if(menuText.isNotNullOrEmpty()){
                haveMenu = true;
            }
            this.menuType = TYPE_TEXT;
            return this;
        }

        fun haveTitle(haveTitle : Boolean): Builder {
            this.haveTitle = haveTitle;
            return this;
        }

        fun haveMenu(haveMenu : Boolean): Builder {
            this.haveMenu = haveMenu;
            return this;
        }

        fun haveBack(haveBack : Boolean): Builder {
            this.haveBack = haveBack;
            return this;
        }

        fun haveSubmit(haveSubmit : Boolean): Builder {
            this.haveSubmit = haveSubmit;
            return this;
        }

        fun submitText(submitText : String) : Builder{
            this.submitText = submitText;
            if(submitText.isNotNullOrEmpty()){
                this.haveSubmit = true;
            }
            return this;
        }

        fun menuSrc(menuSrc : Int): Builder {
            this.menuSrc = menuSrc;
            this.menuType = TYPE_IMAGE;
            this.menuImageType = Constants.IMAGE_TYPE_RESOURCE;
            return this;
        }

        fun menuUrl(menuUrl : String): Builder {
            this.menuUrl = menuUrl;
            this.menuType = TYPE_IMAGE;
            this.menuImageType = Constants.IMAGE_TYPE_URL;
            return this;
        }

        fun backSize(backWidth : Int,backHeight:Int): Builder {
            this.backWidth = backWidth;
            this.backHeight = backHeight;
            return this;
        }

        fun menuSize(menuWidth : Int,menuHeight:Int) : Builder{
            this.menuWidth = menuWidth;
            this.menuHeight = menuHeight;
            return this;
        }

        fun selectType(selectType : Int): Builder {
            this.selectType = selectType;
            return this;
        }

        fun addSlectedListener(onSlectedListener : OnSelectedListener): Builder {
            this.onSlectedListener = onSlectedListener;
            return this;
        }

        fun addList(itemList : MutableList<ItemText>): Builder {
            this.itemList = itemList;
            return this;
        }

        fun backgroundResource(backgroundResource : Int): Builder {
            this.backgroundResource = backgroundResource;
            return this;
        }

        fun builder(): ListDialog {
            return ListDialog(this);
        }
    }


    interface OnSelectedListener{
        fun onSelect(itemText: ItemText);
    }


    fun createDefaultItemText(itemText : String): ItemText {
        return ItemText.Builder()
            .text(itemText)
            .selected(false)
            .selectedImageType(Constants.IMAGE_TYPE_RESOURCE)
            .builder();
    }

    fun createDefaultItemText(itemText : Array<String>) : MutableList<ItemText>{
        val list = mutableListOf<ItemText>();
        for(i in itemText){
            list.add(ItemText.Builder()
                .text(i)
                .selected(false)
                .selectedImageType(Constants.IMAGE_TYPE_RESOURCE)
                .builder());
        }
        return list;
    }
}