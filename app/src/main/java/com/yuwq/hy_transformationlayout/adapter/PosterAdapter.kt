package com.yuwq.hy_transformationlayout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuwq.hy_transformationlayout.DetailActivity
import com.yuwq.hy_transformationlayout.Poster
import com.yuwq.transformationlayout.XClickUtils
import com.yuwq.hy_transformationlayout.databinding.ItemPosterBinding

/**
 * @author liuyuzhe
 */
class PosterAdapter : RecyclerView.Adapter<PosterAdapter.PosterRecyclerViewHolder>() {

    var dataList = ArrayList<Poster>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterRecyclerViewHolder {
        return PosterRecyclerViewHolder(
            ItemPosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PosterRecyclerViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding.run {
            Glide.with(holder.itemView.context).load(item.poster).into(itemPosterPost)
            itemPosterTitle.text = item.name
            itemPosterRunningTime.text = item.playtime
            root.setOnClickListener {
                if (XClickUtils.isFastClick())return@setOnClickListener
                DetailActivity.startActivity(root.context,itemPosterTransformationLayout,item)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setNewData(mockPosters: List<Poster>) {
        this.dataList.addAll(mockPosters)
        notifyItemRangeChanged(0, dataList.size)
    }

    class PosterRecyclerViewHolder(val binding: ItemPosterBinding) :
        RecyclerView.ViewHolder(binding.root)
}