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
            Log.d(TAG,"setUikitImage_file_file_path=>${file?.path}");
            file?.let {
                when(imageView?.id){
                    else->{
                        imageView?.let {
                            Glide.with(imageView)
                                .load(file)
                                .into(it);
                        }
                    }
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,url:String?){
            Log.d(TAG,"setUikitImage_String_url=>${url}");
            url?.let {
                when(imageView?.id){
                    else->{
                        imageView?.let {
                            Glide.with(imageView)
                                .load(url)
                                .into(it);
                        }
                    }
                }
            }
        }


        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,resource:Int?){
            Log.d(TAG,"setUikitImage_INT=>${resource}");
            resource?.let {
                when(imageView?.id){
                    else->imageView?.setImageResource(it);
                }
            }

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
