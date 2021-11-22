package com.yuwq.transformationlayout

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View


fun View.visible(vis: Boolean) {
    if (vis) {
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}