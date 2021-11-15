package com.yuwq.transformationlayout

import android.view.View


fun View.visible(vis:Boolean){
    if (vis){
        visibility = View.VISIBLE
    }else{
        visibility = View.INVISIBLE
    }
}