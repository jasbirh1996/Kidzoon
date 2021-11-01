package com.htf.kidzoon.adapters

import android.app.Activity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Country
import com.htf.kidzoon.models.Likes
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.Constants
import kotlinx.android.synthetic.main.row_age.view.*
import kotlinx.android.synthetic.main.row_country.view.*

class AgeAdapter(
    private var currActivity: Activity,
    private var arrList:ArrayList<String>
):RecyclerView.Adapter<AgeAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_age,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(holder: AgeAdapter.MyViewHolder, position: Int) {
        val model=arrList[position]
        holder.itemView.apply {
            tvAge.text=model
        }
    }
}