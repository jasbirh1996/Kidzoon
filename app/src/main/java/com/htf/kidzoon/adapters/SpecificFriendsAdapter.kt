package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.row_specific_friends.view.*

class SpecificFriendsAdapter(
    private var currActivity: Activity,
    private var arrFriends:ArrayList<Friends>
):RecyclerView.Adapter<SpecificFriendsAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.ivSpecific.setOnClickListener {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificFriendsAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_specific_friends,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrFriends.size
    }

    override fun onBindViewHolder(holder: SpecificFriendsAdapter.MyViewHolder, position: Int) {
        val model=arrFriends[position]
        if (!model.isSelected){
            holder.itemView.ivSpecific.setImageResource(R.drawable.privacy_setting_untick_bg)
        }
        else{
            holder.itemView.ivSpecific.setImageResource(R.drawable.right_tik)
        }
    }

}