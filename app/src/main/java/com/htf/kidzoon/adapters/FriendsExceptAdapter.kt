package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.row_friends_except.view.*

class FriendsExceptAdapter (
    private var currActivity: Activity,
    private var arrFriends:ArrayList<Friends>
):RecyclerView.Adapter<FriendsExceptAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.ivExcept.setOnClickListener {
                val friends=arrFriends[adapterPosition]
                if (friends.isSelected){
                    friends.isSelected=false
                }
                else{
                    friends.isSelected=true
                }
                notifyDataSetChanged()
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsExceptAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_friends_except,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrFriends.size
    }

    override fun onBindViewHolder(holder: FriendsExceptAdapter.MyViewHolder, position: Int) {
        val model=arrFriends[position]
        if (!model.isSelected){
            holder.itemView.ivExcept.setImageResource(R.drawable.except)
        }
        else
        {
            holder.itemView.ivExcept.setImageResource(R.drawable.except_select)
        }
    }

}