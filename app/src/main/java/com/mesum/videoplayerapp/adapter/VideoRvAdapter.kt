package com.mesum.videoplayerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesum.videoplayerapp.R
import com.mesum.videoplayerapp.`interface`.VideoClickInterface
import com.mesum.videoplayerapp.model.ViewRvModel

val  diffUtil = object : DiffUtil.ItemCallback<ViewRvModel>(){
    override fun areItemsTheSame(oldItem: ViewRvModel, newItem: ViewRvModel): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: ViewRvModel, newItem: ViewRvModel): Boolean {
        return oldItem.videoName == newItem.videoName
    }
}
class VideoRvAdapter(var ctx : Context,var videoClickInterface: VideoClickInterface ) : ListAdapter<ViewRvModel,VideoRvAdapter.VideoViewHolder>(diffUtil) {

    val context = ctx
    inner class VideoViewHolder(val view : View): RecyclerView.ViewHolder(view){
        fun bind(item : ViewRvModel){
            itemView.findViewById<ImageView>(R.id.thumbnailRv).setImageBitmap(item.thumbNail)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_rv_item, parent,false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
                videoClickInterface.onVideoClick(position = position )

        }
    }
}