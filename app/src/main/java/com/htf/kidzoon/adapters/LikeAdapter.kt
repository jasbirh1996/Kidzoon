package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Likes

class LikeAdapter(
    private var currActivity: Activity,
    private var arrLike:ArrayList<Likes>
):RecyclerView.Adapter<LikeAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_likes,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrLike.size
    }

    override fun onBindViewHolder(holder: LikeAdapter.MyViewHolder, position: Int) {
        val model=arrLike[position]
    }

}