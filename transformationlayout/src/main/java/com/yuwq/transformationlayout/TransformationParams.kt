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

internal object DefaultParamValues : TransformationParams {
    override var duration: Long = 450L
    override var pathMotion: TransformationLayout.Motion = TransformationLayout.Motion.ARC
    override var scrimColor: Int = Color.TRANSPARENT
    override var direction: TransformationLayout.Direction
        get() = TODO("Not yet implemented")
        set(value) {}
    override var fadeMode: TransformationLayout.FadeMode
        get() = TODO("Not yet implemented")
        set(value) {}
    override var fitMode: TransformationLayout.FitMode
        get() = TODO("Not yet implemented")
        set(value) {}
    override var startElevation: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var endElevation: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var elevationShadowEnable: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
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