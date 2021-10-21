package com.god.uikit.utils

import android.app.Activity
import android.graphics.Point
import android.util.Log
import android.view.Display

fun screenSize(activity: Activity):Array<Int>{
    val display: Display = activity.getWindowManager().getDefaultDisplay()
    val point = Point()
    display.getSize(point)
    Log.d("SCREEN_SIZE=>","${point.x} X ${point.y}")
    return arrayOf(point.x,point.y)
}

