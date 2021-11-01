package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.FollowRequest

class FollowRequestAdapter(
    private var currActivity: Activity,
    private var arrRequest:ArrayList<FollowRequest>
):RecyclerView.Adapter<FollowRequestAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowRequestAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_request,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrRequest.size
    }

    override fun onBindViewHolder(holder: FollowRequestAdapter.MyViewHolder, position: Int) {
        val model=arrRequest[position]
    }

}