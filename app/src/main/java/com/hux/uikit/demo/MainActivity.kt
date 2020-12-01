package com.hux.uikit.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.god.uikit.widget.dialog.CalendarDialog

class MainActivity : AppCompatActivity() {
    private var calendarDialog : CalendarDialog? = null;

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
    }
}