package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Chat

class ChatAdapter(
    private var currActivity: Activity,
    private var arrChat:ArrayList<Chat>
):RecyclerView.Adapter<ChatAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_chat,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrChat.size
    }

    override fun onBindViewHolder(holder: ChatAdapter.MyViewHolder, position: Int) {
        val model=arrChat[position]
    }

}