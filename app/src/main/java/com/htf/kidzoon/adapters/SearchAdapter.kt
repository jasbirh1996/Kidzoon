package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Search

class SearchAdapter (
    private var currActivity: Activity,
    private var arrSearch:ArrayList<Search>
):RecyclerView.Adapter<SearchAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_search,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrSearch.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        val model=arrSearch[position]
    }

}