package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.FollowRequestAdapter
import com.htf.kidzoon.models.FollowRequest
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class FollowRequestActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity:Activity=this
    private var arrFollowRequest=ArrayList<FollowRequest>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,FollowRequestActivity::class.java)
            currActivity.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_request)
        setListener()
        tvTitle.text=getString(R.string.follow_request)
        initRecycler()
    }

    private fun initRecycler() {
        arrFollowRequest.clear()
        arrFollowRequest.add(FollowRequest())
        arrFollowRequest.add(FollowRequest())
        arrFollowRequest.add(FollowRequest())
        arrFollowRequest.add(FollowRequest())
        arrFollowRequest.add(FollowRequest())
        arrFollowRequest.add(FollowRequest())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val requestAdapter=FollowRequestAdapter(currActivity,arrFollowRequest)
        recycler.adapter=requestAdapter

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
