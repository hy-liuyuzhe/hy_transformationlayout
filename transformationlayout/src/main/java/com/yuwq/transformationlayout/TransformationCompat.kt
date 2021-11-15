package com.yuwq.transformationlayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat

/**
 * @author liuyuzhe
 */
object TransformationCompat {

    /**
     * A common definition of the activity's transition name.
     */
    const val activityTransitionName = "com.yuwq.transformationlayout"

    fun startActivity(transformationLayout: TransformationLayout, intent: Intent) {
        transformationLayout.startActivityWithBundleOptions(intent) { workedIntent, bundle ->
            ActivityCompat.startActivity(transformationLayout.context, workedIntent, bundle)
        }
    }


    fun TransformationLayout.startActivityWithBundleOptions(
        intent: Intent,
        block: (i: Intent, Bundle) -> Unit
    ) {
        val bundle = withView(this, activityTransitionName)
        intent.putExtra(activityTransitionName, getParcelableParams())
        block(intent, bundle)
    }
}