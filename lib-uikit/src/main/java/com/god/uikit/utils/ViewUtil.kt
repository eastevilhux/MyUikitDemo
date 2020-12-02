package com.god.uikit.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import java.lang.IndexOutOfBoundsException


class ViewUtil{
    companion object{
        fun dip2px(content:Context,dipValue :Int): Int {
            var scale = content.resources.displayMetrics.density;
            return (dipValue * scale+ 0.5f).toInt();
        }

        fun px2dip(content: Context,pxValue : Int): Int {
            var scale = content.resources.displayMetrics.density;
            return (pxValue / scale + 0.5f).toInt();
        }

        fun getScreenSize(content: Context) : Array<Int> {
            var dm = content.resources.displayMetrics;
            return arrayOf(dm.widthPixels,dm.heightPixels);
        }


        /**
         * 创建一个带圆角的背景定义
         * create by Administrator at 2020/12/2 13:31
         * @author Administrator
         * @param color
         *      填充颜色
         * @param strokeColor
         *      边框颜色
         * @param strokeSize
         *      边框大小，单位PX
         * @param radius
         *      圆角角度，数量必须为1或者4，分表表示4个角度相等，或者为左上,右上,右下,左下
         * @return
         */
        fun createRectangleDrawable(
            color: Int? = null,
            strokeColor:Int? = null,
            strokeSize:Int? = null, radius: FloatArray): GradientDrawable? {
            var dra = GradientDrawable();
            //设置Shape类型
            dra.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            if(color != null) {
                dra.setColor(color);
            }
            //设置线条粗心和颜色,px
            if(strokeColor != null && strokeSize != null) {
                dra.setStroke(strokeSize, strokeColor);
            }
            if(radius.size == 1){
                dra.cornerRadius = radius[0];
            }else{
                if(radius.size != 4){
                    throw IndexOutOfBoundsException("the radius size must be 4");
                    return null;
                }
                dra.cornerRadii = floatArrayOf(
                    radius[0],
                    radius[0],
                    radius[1],
                    radius[1],
                    radius[2],
                    radius[2],
                    radius[3],
                    radius[3]
                )
            }
            return dra;
        }
    }
}