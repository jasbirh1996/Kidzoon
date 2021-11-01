package com.htf.kidzoon.adapters

import android.app.Activity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.Room.Listeners.CountryListener
import com.htf.kidzoon.models.Country
import com.htf.kidzoon.models.Likes
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.Constants
import kotlinx.android.synthetic.main.row_country.view.*

class CountryAdapter(
    private var currActivity: Activity,
    private var arrList:ArrayList<Country>,
    private var searchText:String,
    private var listener:CountryListener
):RecyclerView.Adapter<CountryAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val model=arrList[adapterPosition]
                listener.onCountryClickListener(model)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_country,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(holder: CountryAdapter.MyViewHolder, position: Int) {
        val model=arrList[position]
        holder.itemView.apply {
            AppUtils.setPicassoImage(Constants.COUNTRY_IMAGE_URL+model.flag,ivFlag,R.drawable.country_placeholder)
            tvDialCode.text="+${model.dial_code}"
            when(AppSession.locale){
                "ar"->{
                    if (searchText.isNotEmpty()) {
                        //color your text here
                        var index: Int = model.name.indexOf(searchText)
                        val sb = SpannableStringBuilder(model.name)
                        while (index > -1) {
                            val fcs =
                                ForegroundColorSpan(ContextCompat.getColor(currActivity,R.color.colorRed)) //specify color here
                            val ss=StyleSpan(android.graphics.Typeface.BOLD)
                            sb.setSpan(fcs, index, index+searchText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            sb.setSpan(ss,index,index+searchText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            index = model.name.indexOf(searchText, index + 1)!!
                        }
                        tvCountryName.text=sb
                    } else {
                        tvCountryName.text=model.name
                    }
                }
                "en"->  {
                    if (searchText.isNotEmpty()) {
                        //color your text here
                        var index: Int = model.en_name.toLowerCase().indexOf(searchText)
                        val sb = SpannableStringBuilder(model.en_name)
                        while (index > -1) {
                            val fcs =
                                ForegroundColorSpan(ContextCompat.getColor(currActivity,R.color.colorRed)) //specify color here
                            val ss=StyleSpan(android.graphics.Typeface.BOLD)
                            sb.setSpan(fcs, index, index+searchText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            sb.setSpan(ss, index, index+searchText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            index = model.en_name.indexOf(searchText, index + 1)
                        }
                        tvCountryName.text=sb
                    } else {
                        tvCountryName.text=model.en_name
                    }
                }
            }
        }
    }

    fun setFilter(
        countryModels: List<Country>?,
        searchText: String?
    ) {
        arrList = ArrayList()
        arrList.addAll(countryModels!!)
        this.searchText = searchText!!
        notifyDataSetChanged()

    }

}