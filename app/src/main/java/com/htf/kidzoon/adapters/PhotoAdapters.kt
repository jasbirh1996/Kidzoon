package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Photo
import com.htf.kidzoon.models.Video

class PhotoAdapters(
    private var currActivity: Activity,
    private var arrPhoto:ArrayList<Photo>
):RecyclerView.Adapter<PhotoAdapters.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapters.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_video,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrPhoto.size
    }

    override fun onBindViewHolder(holder: PhotoAdapters.MyViewHolder, position: Int) {
        val model=arrPhoto[position]
    }

}