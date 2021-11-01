package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Friends

class FriendsRequestAdapter(
    private var currActivity: Activity,
    private var arrFriends:ArrayList<Friends>
):RecyclerView.Adapter<FriendsRequestAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsRequestAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_friends_request,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrFriends.size
    }

    override fun onBindViewHolder(holder: FriendsRequestAdapter.MyViewHolder, position: Int) {
        val model=arrFriends[position]
    }

}