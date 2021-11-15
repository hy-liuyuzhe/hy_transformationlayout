package com.yuwq.transformationlayout

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.transition.PathMotion
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform

/**
 * @author liuyuzhe
 */
class TransformationLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val isHoldAtEndEnabled: Boolean = false
    private lateinit var targetView: View
    private val ELEVATION_NOT_SET: Float = -1.0F
    private val pathMotion: Motion = Motion.ARC

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
            this.transitionDirection = MaterialContainerTransform.TRANSITION_DIRECTION_AUTO
            this.fadeMode = MaterialContainerTransform.FADE_MODE_IN
            this.fitMode = MaterialContainerTransform.FIT_MODE_AUTO
            this.startElevation = this@TransformationLayout.ELEVATION_NOT_SET
            this.endElevation = this@TransformationLayout.ELEVATION_NOT_SET
            this.isElevationShadowEnabled = this@TransformationLayout.elevationShadowEnabled
            this.isHoldAtEndEnabled = this@TransformationLayout.isHoldAtEndEnabled
            addTarget(endView)
            duration = 450
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
}