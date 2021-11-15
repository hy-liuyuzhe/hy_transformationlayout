package com.yuwq.hy_transformationlayout

import com.yuwq.hy_transformationlayout.XClickUtils

/**
 * 防止快速点击
 *
 */
object XClickUtils {
    private var lastClickTime: Long = 0
    private var lastClickId: Long = 0
    private const val DURATION = 1000

    fun isFastClick(clickId: Int = 0, interval: Long = DURATION.toLong()): Boolean {
        val curtTime = System.currentTimeMillis()
        if (curtTime - lastClickTime < interval && clickId.toLong() == lastClickId) {
            return true
        }
        lastClickTime = curtTime
        lastClickId = clickId.toLong()
        return false
    }
}