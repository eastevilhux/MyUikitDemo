package com.hux.uikit.demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.TextView
import com.god.uikit.entity.ItemText
import com.god.uikit.widget.dialog.CalendarDialog
import com.god.uikit.widget.dialog.ListDialog
import com.god.uikit.widget.dialog.MessageDialog

class MainActivity : AppCompatActivity() {
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
    }
}