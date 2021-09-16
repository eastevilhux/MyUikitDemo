package com.god.uikit.commons

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.R
import com.god.uikit.utils.format
import java.io.File
import java.util.*

class ViewAttAdapter {

    companion object{
        private const val TAG = "UIKIT_ViewAttAdapter==>"

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, text: Int) {
            when(textView.id){
                else->textView.setText(text);
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView,file:File?){
            Log.d(TAG,"WTF");
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,url:String?){
        }


        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,resource:Int?){
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, text: Date){
            when(textView.id){
                R.id.tv_date->{
                    textView.text = text.format("yyyy-MM-dd")
                }
            }
        }
    }

}
