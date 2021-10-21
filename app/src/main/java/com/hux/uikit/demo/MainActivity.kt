package com.hux.uikit.demo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.god.uikit.commons.GlideApp
import com.god.uikit.entity.Image
import com.god.uikit.entity.ItemText
import com.god.uikit.widget.InputLayout
import com.god.uikit.widget.dialog.CalendarDialog
import com.god.uikit.widget.dialog.ListDialog
import com.god.uikit.widget.dialog.MessageDialog
import com.god.uikit.widget.dialog.NumberpsdDialog

class MainActivity : AppCompatActivity(), InputLayout.OnCountdownListener {
    private var calendarDialog : CalendarDialog? = null;
    private var listDialog : ListDialog? = null;
    private var msgDialog : MessageDialog? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_test_datedialog).setOnClickListener {
            calendarDialog?:let {
                calendarDialog = CalendarDialog.Builder(this)
                    .builder();
            }
            if(!calendarDialog!!.isShowing){
                calendarDialog!!.show();
            }
        }
        var i = 0;
        val list = mutableListOf<ItemText>();
        while(i<5){
            list.add(ItemText.Builder().text("${i}——text").builder());
            i++;
        }
        findViewById<TextView>(R.id.tv_list_dialogtext).setOnClickListener {
            listDialog?:let {
                listDialog = ListDialog.Builder(this)
                    .title("asdfasdfas")
                    .addList(list)
                    .selectType(ListDialog.SELECT_TYPE_SHOW)
                    .submitText("what a fuck")
                    .titleBackgroundResource(R.color.colorAccent)
                    .titleColor(Color.BLACK)
                    .builder()
            }
            listDialog!!.show();
        }

        findViewById<TextView>(R.id.tv_msg_dialog).setOnClickListener {
            msgDialog?:let {
                msgDialog = MessageDialog.Builder(this)
                    .title("title")
                    .message("asdfasdfasdf")
                    .btnBackgroundResource(R.drawable.shape_day_select,R.drawable.shape_take_photo)
                    .build();
            }
            msgDialog!!.show();
        }

        findViewById<TextView>(R.id.tv_number_dialog).setOnClickListener {
            var numberpsdDialog = NumberpsdDialog.Builder(this)
                .psdNumber(6)
                .build();
            numberpsdDialog.show();
        }


        val layout = findViewById<InputLayout>(R.id.input_layout);
        layout.setOnInputMenuClickListener {
            layout.startTime(6000);
        }
        layout.onCountdownListener = this;


        var img = "https://c-ssl.duitang.com/uploads/item/201801/09/20180109191129_huyVW.jpeg"
        val i1 = findViewById<ImageView>(R.id.iv_text1)
        GlideApp.with(i1)
            .load(img)
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(i1);

        val i2 = findViewById<ImageView>(R.id.iv_text2);
        GlideApp.with(this)
            .load(R.drawable.icon_no_data_default)
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(i2);

    }

    override fun onTime(view: TextView?, time: Int) {
        view?.setText("${time}秒后")
        Log.d("fuck=>","onTime=>${time}");
    }

    override fun onStart(view: TextView?) {
        Log.d("fuck=>","onStart");
    }

    override fun onStop(view: TextView?) {
        view?.setText("send msg")
    }

}
