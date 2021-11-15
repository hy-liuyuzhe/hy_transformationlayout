package com.yuwq.transformationlayout

import android.app.Activity
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.transition.Transition
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback


fun Activity.onTransformationStartContainer(){
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
}


fun Activity.onTransformationEndContainer(params:TransformationLayout.Params?){
    requireNotNull(params,{
        "TransformationLayout.Params must not be a null. check your intent key value is correct."
    })
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    ViewCompat.setTransitionName(findViewById(android.R.id.content),params.transitionName)
    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementEnterTransition = params.getMaterialContainerTransform()
    window.sharedElementReturnTransition = params.getMaterialContainerTransform()
}