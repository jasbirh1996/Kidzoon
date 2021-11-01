package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.activities.ChatDetailActivity
import com.htf.kidzoon.models.ChatList

class ChatListAdapter(
    private var currActivity: Activity,
    private var arrChat:ArrayList<ChatList>
):RecyclerView.Adapter<ChatListAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                ChatDetailActivity.open(currActivity)

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_chat_list,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrChat.size
    }

    override fun onBindViewHolder(holder: ChatListAdapter.MyViewHolder, position: Int) {
        val model=arrChat[position]
    }

}