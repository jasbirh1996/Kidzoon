package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.FriendsRequestAdapter
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class FriendRequestActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrFriendRequest=ArrayList<Friends>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,FriendRequestActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_request)
        initRecycler()
        tvTitle.text=getString(R.string.friend_request)
        setListener()
    }

    private fun setListener() {
        btnBack.setOnClickListener(this)

    }

    private fun initRecycler() {
        arrFriendRequest.clear()
        arrFriendRequest.add(Friends())
        arrFriendRequest.add(Friends())
        arrFriendRequest.add(Friends())
        arrFriendRequest.add(Friends())
        arrFriendRequest.add(Friends())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val requestAdapter=FriendsRequestAdapter(currActivity,arrFriendRequest)
        recycler.adapter=requestAdapter

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }
}
