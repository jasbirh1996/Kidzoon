package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Video

class VideoAdapters(
    private var currActivity: Activity,
    private var arrVideo:ArrayList<Video>
):RecyclerView.Adapter<VideoAdapters.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapters.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_video,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrVideo.size
    }

    override fun onBindViewHolder(holder: VideoAdapters.MyViewHolder, position: Int) {
        val model=arrVideo[position]
    }

}