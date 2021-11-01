package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.NotificationSetting
import kotlinx.android.synthetic.main.row_notification_setting.view.*

class NotificationSettingAdapter(
    private var currActivity:Activity,
    private var arrNotification:ArrayList<NotificationSetting>
):RecyclerView.Adapter<NotificationSettingAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationSettingAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_notification_setting,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrNotification.size
    }

    override fun onBindViewHolder(holder: NotificationSettingAdapter.MyViewHolder, position: Int) {
        val model=arrNotification[position]
        holder.itemView.apply {
            ivNotificationSettingImage.setImageResource(model.image!!)
            tvNotificationSettingName.text=model.settingName
            if (position==arrNotification.size-1){
                viewNotificationSetting.visibility=View.GONE
            }else{
                viewNotificationSetting.visibility=View.VISIBLE
            }
        }
    }

}