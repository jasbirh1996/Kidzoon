package com.htf.kidzoon.adapters

import android.app.Activity
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.models.Notifications
import kotlinx.android.synthetic.main.row_notification.view.*


class NotificationAdapter(
    private var currActivity: Activity,
    private var arrNotification:ArrayList<Notifications>
):RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.ivOption.setOnClickListener {
                showPop()
            }

        }
        private fun showPop() {
            val popupMenu = PopupMenu(currActivity,itemView,Gravity.END)
            popupMenu.menuInflater.inflate(R.menu.notification_post_popup_menu, popupMenu.menu)
            popupMenu.show()

        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_notification,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrNotification.size
    }

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        val model=arrNotification[position]
    }

}