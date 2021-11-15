package com.yuwq.transformationlayout

import android.app.ActivityOptions
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.transition.PathMotion
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ChecksSdkIntAtLeast
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.parcelize.Parcelize

/**
 * @author liuyuzhe
 */
class TransformationLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val containerColor: Int = Color.TRANSPARENT
    private val zOrder: Int = R.id.content
    private val isHoldAtEndEnabled: Boolean = false
    private lateinit var targetView: View
    private val ELEVATION_NOT_SET: Float = -1.0F
    private val pathMotion: Motion = Motion.ARC
    private val duration: Long = 450L


    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    private var elevationShadowEnabled: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TransformationLayout)
        try {
            setTypeArray(typeArray)
        } finally {
            typeArray.recycle()
        }
    }

    private fun setTypeArray(typeArray: TypedArray) {
        typeArray.getResourceId(R.styleable.TransformationLayout_transformation_targetView, -1)
            .also {
                if (it != -1) post { bindTargetView(rootView.findViewById<View>(it)) }
            }
    }

    fun bindTargetView(target: View) {
        this.targetView = target.apply { visible(false) }
    }

    fun startTransform() {
        startTransform(parent as ViewGroup)
    }

    fun finishTransform() {
        finishTransform(parent as ViewGroup)
    }

    fun startTransform(container: ViewGroup) {
        container.post {

            beginDelayingAndTransform(container, this, targetView)
        }
    }

    fun finishTransform(container: ViewGroup) {
        container.post {
            beginDelayingAndTransform(container, targetView, this)
        }
    }

    private fun beginDelayingAndTransform(container: ViewGroup, startView: View, endView: View) {
        startView.visible(false)
        endView.visible(true)
        TransitionManager.beginDelayedTransition(container, createTransition(startView, endView))
    }

    private fun createTransition(startView: View, endView: View): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            this.startView = startView
            this.endView = endView
            this.setPathMotion(this@TransformationLayout.pathMotion.getPathMotion())
            this.containerColor = Color.TRANSPARENT
            this.setAllContainerColors(Color.TRANSPARENT)
            this.scrimColor = Color.TRANSPARENT
            this.drawingViewId = this@TransformationLayout.zOrder
            this.transitionDirection = MaterialContainerTransform.TRANSITION_DIRECTION_AUTO
            this.fadeMode = MaterialContainerTransform.FADE_MODE_IN
            this.fitMode = MaterialContainerTransform.FIT_MODE_AUTO
            this.startElevation = this@TransformationLayout.ELEVATION_NOT_SET
            this.endElevation = this@TransformationLayout.ELEVATION_NOT_SET
            this.isElevationShadowEnabled = this@TransformationLayout.elevationShadowEnabled
            this.isHoldAtEndEnabled = this@TransformationLayout.isHoldAtEndEnabled
            addTarget(endView)
            duration = this@TransformationLayout.duration
            addListener(object : SimpleTransitionListener() {
                override fun onTransitionCancel(transition: Transition) {
                    onFinishTransformation()
                }

                override fun onTransitionEnd(transition: Transition) {
                    onFinishTransformation()
                }
            })
        }
    }

    private fun onFinishTransformation() {

    }

    fun getParcelableParams(): Parcelable {
        return getParams()
    }

    private fun getParams(): Parcelable {
        return Params(
            duration = this@TransformationLayout.duration,
            pathMotion = this@TransformationLayout.pathMotion,
//            zOrder = this@TransformationLayout.zOrder,
//            containerColor = this@TransformationLayout.containerColor,
//            containerColor = this@TransformationLayout.containerColor,
            transitionName = transitionName
        )
    }

    fun withView(
        view: View,
        transitionName: String
    ): Bundle {
        setTransitionName(transitionName)
        val activity = view.context.getActivity()
        requireNotNull(activity)
        return ActivityOptions.makeSceneTransitionAnimation(activity, this, transitionName)
            .toBundle()
    }

    @Parcelize
    data class Params(
        override var duration: Long,
        override var pathMotion: Motion,
//        override var zOrder: Int,
//        override var containerColor: Int,
//        override var allContainerColors: Int,
//        override var scrimColor: Int,
//        override var direction: Direction,
//        override var fadeMode: FadeMode,
//        override var fitMode: FitMode,
//        override var startElevation: Float,
//        override var endElevation: Float,
//        override var elevationShadowEnable: Boolean,
//        override var holdAtEndEnable: Boolean,
        var transitionName: String
    ) : Parcelable,TransformationParams


    internal abstract class SimpleTransitionListener : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) {

        }

        override fun onTransitionEnd(transition: Transition) {

        }

        override fun onTransitionCancel(transition: Transition) {

        }

        override fun onTransitionPause(transition: Transition) {

        }

        override fun onTransitionResume(transition: Transition) {

        }

    }

    public enum class Motion(val value: Int) {
        ARC(0), LINEAR(1);

        public fun getPathMotion(): PathMotion? {
            if (value == 0) return MaterialArcMotion()
            return null
        }
    }

    /**
     * The Direction to be used by this transform
     */
    enum class Direction(val value: Int) {
        AUTO(MaterialContainerTransform.TRANSITION_DIRECTION_AUTO),
        ENTER(MaterialContainerTransform.TRANSITION_DIRECTION_ENTER),
        RETURN(MaterialContainerTransform.TRANSITION_DIRECTION_RETURN)
    }

    /**
     * The [FadeMode] to be used to swap the content of the start View with that of the end View
     */
    enum class FadeMode(val value: Int) {
        IN(MaterialContainerTransform.FADE_MODE_IN),
        OUT(MaterialContainerTransform.FADE_MODE_OUT),
        CROSS(MaterialContainerTransform.FADE_MODE_CROSS),
        THROUGH(MaterialContainerTransform.FADE_MODE_THROUGH)
    }

    enum class FitMode(val value:Int) {
        AUTO(MaterialContainerTransform.FIT_MODE_AUTO),
        WIDTH(MaterialContainerTransform.FIT_MODE_WIDTH),
        HEIGHT(MaterialContainerTransform.FIT_MODE_HEIGHT)
    }
}