package com.yuwq.transformationlayout

import android.graphics.Color
import com.google.android.material.transition.platform.MaterialContainerTransform
import java.io.Serializable

/**
 * interface definition about the [TransformationLayout]'s parameters
 */
internal interface TransformationParams : Serializable {
    var duration: Long
    var pathMotion: TransformationLayout.Motion
//    var zOrder: Int
//    var containerColor: Int
//    var allContainerColors: Int
//    var scrimColor: Int
//    var direction: TransformationLayout.Direction
//    var fadeMode: TransformationLayout.FadeMode
//    var fitMode: TransformationLayout.FitMode
//    var startElevation: Float
//    var endElevation: Float
//    var elevationShadowEnable: Boolean
//    var holdAtEndEnable: Boolean
}

//internal object DefaultParamValues : TransformationParams {
//    override var duration: Long = 450L
//    override var pathMotion: TransformationLayout.Motion = TransformationLayout.Motion.ARC
//
//
//
//}

internal fun TransformationParams.getMaterialContainerTransform(): MaterialContainerTransform {
    val params = this
    return MaterialContainerTransform().apply {
        addTarget(android.R.id.content)
        duration = params.duration
        pathMotion = params.pathMotion.getPathMotion()
        setAllContainerColors(Color.TRANSPARENT)
        scrimColor = Color.TRANSPARENT
        drawingViewId = android.R.id.content
        this.transitionDirection = MaterialContainerTransform.TRANSITION_DIRECTION_AUTO
        this.fadeMode = MaterialContainerTransform.FADE_MODE_IN
        this.fitMode = MaterialContainerTransform.FIT_MODE_AUTO
    }
}