package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.activities.CommentsReplyActivity
import com.htf.kidzoon.activities.LikesActivity
import com.htf.kidzoon.models.Comments
import kotlinx.android.synthetic.main.row_feed_comments.view.*

class CommentsAdapter(
    private var currActivity: Activity,
    private var arrComments:ArrayList<Comments>
):RecyclerView.Adapter<CommentsAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.tvReply.setOnClickListener{
                CommentsReplyActivity.open(currActivity)

            }
            itemView.llLike.setOnClickListener {
                LikesActivity.open(currActivity)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_feed_comments,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrComments.size
    }

    override fun onBindViewHolder(holder: CommentsAdapter.MyViewHolder, position: Int) {
        val model=arrComments[position]
    }

}