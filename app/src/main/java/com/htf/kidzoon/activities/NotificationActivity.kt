package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.NotificationAdapter
import com.htf.kidzoon.models.Notifications
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class NotificationActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrNotification=ArrayList<Notifications>()


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,NotificationActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setListener()
        initRecycler()
        tvTitle.text=getString(R.string.notifications)
    }

    private fun initRecycler() {
        arrNotification.clear()
        arrNotification.add(Notifications())
        arrNotification.add(Notifications())
        arrNotification.add(Notifications())
        arrNotification.add(Notifications())
        arrNotification.add(Notifications())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val notificationAdapter=NotificationAdapter(currActivity,arrNotification)
        recycler.adapter=notificationAdapter


    }


    private fun setListener() {
        btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }
}
