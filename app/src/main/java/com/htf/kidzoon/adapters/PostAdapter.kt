package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Post

class PostAdapter (
    private var currActivity: Activity,
    private var arrPost:ArrayList<Post>
):RecyclerView.Adapter<PostAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_post_video_photo,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrPost.size
    }

    override fun onBindViewHolder(holder: PostAdapter.MyViewHolder, position: Int) {
        val model=arrPost[position]
    }

}