package com.yuwq.hy_transformationlayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yuwq.hy_transformationlayout.databinding.ActivityDetailBinding
import com.yuwq.transformationlayout.TransformationCompat
import com.yuwq.transformationlayout.TransformationLayout
import com.yuwq.transformationlayout.onTransformationEndContainer

/**
 * @author liuyuzhe
 */
class DetailActivity : AppCompatActivity() {

    companion object {
        const val posterExtraName = "posterExtraName"
        fun startActivity(
            context: Context,
            itemPosterTransformationLayout: TransformationLayout,
            item: Poster
        ) {
            TransformationCompat.startActivity(
                transformationLayout = itemPosterTransformationLayout,
                intent = Intent(context,DetailActivity::class.java)
                    .putExtra(posterExtraName,item)
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(TransformationCompat.activityTransitionName))
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Poster>(posterExtraName)?.let {
            Glide.with(this)
                .load(it.poster).into(binding.detailImage)
            binding.detailTitle.text = it.name
            binding.detailDescription.text = it.description
        }
    }
}