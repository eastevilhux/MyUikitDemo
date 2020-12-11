package com.god.uikit.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.LayoutTabmenuBinding
import com.god.uikit.utils.dip2Px

class TabmenuLayout : FrameLayout{

    companion object{
        const val TAG = "TabmenuLayout";
        const val LINE_WIDTH_DEFAULT = 50;
        const val LINE_HEIGHT_DEFAULT = 2;
        const val MARGIN_BOTTOM_DEFAULT = 5;
    }

    private var dataBinding : LayoutTabmenuBinding? = null;

    private val menuText = ObservableField<String>();
    private val lineState = ObservableField<Boolean>();
    private var lineColor : Int = Color.WHITE;
    private var menuColor : Int = Color.WHITE;
    private var lineWidth : Int = ConstraintLayout.LayoutParams.WRAP_CONTENT;
    private var lineHeight : Int = ConstraintLayout.LayoutParams.WRAP_CONTENT;
    private var lineMarginBottom : Int = MARGIN_BOTTOM_DEFAULT.dip2Px();
    private var selectColor : Int = Color.WHITE;
    private var lineBackground : Int = 0;
    private var menuTextSize : Float = 15.dip2Px().toFloat();

    constructor(context: Context) : super(context) {

    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        initAttr(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
    }


    private fun initAttr(@Nullable attrs : AttributeSet){
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TabmenuLayout);
        menuText.set(ta.getString(R.styleable.TabmenuLayout_lyn_menuText));
        lineState.set(ta.getBoolean(R.styleable.TabmenuLayout_lyn_lineState,true));
        menuColor = ta.getColor(R.styleable.TabmenuLayout_lyn_menuTextColor,Color.WHITE);
        lineColor = ta.getColor(R.styleable.TabmenuLayout_lyn_lineColor, Color.WHITE);
        lineWidth = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_lyn_line_width,LINE_WIDTH_DEFAULT.dip2Px());
        lineHeight = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_lyn_line_height,LINE_HEIGHT_DEFAULT.dip2Px());
        menuTextSize = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_lyn_menuTextSize,15).toFloat();
        lineMarginBottom = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_lyn_lineMarginButtom,
            MARGIN_BOTTOM_DEFAULT.dip2Px());
        selectColor = ta.getColor(R.styleable.TabmenuLayout_lyn_menuSelectColor,Color.WHITE);
        lineBackground = ta.getResourceId(R.styleable.TabmenuLayout_lyn_lineBackground,R.drawable.line_recycler_default);
        ta.recycle();
        init();
    }

    private fun init(){
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_tabmenu,
            this, true
        )
        dataBinding?.menuText = menuText;
        dataBinding?.lineState = lineState;
        dataBinding?.tvItemtext?.setTextSize(menuTextSize)

        dataBinding?.tvItemtext!!.setTextColor(menuColor);

        if(lineBackground != 0 && lineBackground != R.drawable.line_recycler_default){
            dataBinding?.tvLine!!.setBackgroundResource(lineBackground)
        }else{
            dataBinding?.tvLine!!.setBackgroundColor(lineColor);
        }

        var lineParams = dataBinding?.tvLine!!.layoutParams as MarginLayoutParams;
        lineParams.width = lineWidth;
        lineParams.height = lineHeight;
        lineParams.setMargins(0,0,0,lineMarginBottom);
        dataBinding?.tvLine!!.layoutParams = lineParams;
    }


    fun setLineState(lineState:Boolean){
        this.lineState.set(lineState);
        if(lineState){
            dataBinding?.tvItemtext!!.setTextColor(selectColor);
        }else{
            dataBinding?.tvItemtext!!.setTextColor(menuColor);
        }
    }

    fun getLineState():Boolean{
        return lineState.get() ?: false;
    }

}