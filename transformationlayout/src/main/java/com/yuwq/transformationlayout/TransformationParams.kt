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
    var scrimColor: Int
    var direction: TransformationLayout.Direction
    var fadeMode: TransformationLayout.FadeMode
    var fitMode: TransformationLayout.FitMode
    var startElevation: Float
    var endElevation: Float
    var elevationShadowEnable: Boolean
}

internal fun TransformationParams.getMaterialContainerTransform(): MaterialContainerTransform {
    val params = this
    return MaterialContainerTransform().apply {
        addTarget(android.R.id.content)
        duration = params.duration
        pathMotion = params.pathMotion.getPathMotion()
        scrimColor = params.scrimColor
        transitionDirection = params.direction.value
        fadeMode = params.fadeMode.value
        fitMode = params.fitMode.value
        startElevation = params.startElevation
        endElevation = params.endElevation
    }
}