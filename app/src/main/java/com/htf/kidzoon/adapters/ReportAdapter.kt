package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Report

class ReportAdapter(
    private var currActivity: Activity,
    private var arrReport:ArrayList<Report>
):RecyclerView.Adapter<ReportAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_report,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrReport.size
    }

    override fun onBindViewHolder(holder: ReportAdapter.MyViewHolder, position: Int) {
        val model=arrReport[position]

    }

}