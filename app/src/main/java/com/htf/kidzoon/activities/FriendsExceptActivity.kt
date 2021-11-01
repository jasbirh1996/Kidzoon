package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.FriendsExceptAdapter
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.activity_friends_except.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class FriendsExceptActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrFriends=ArrayList<Friends>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,FriendsExceptActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_except)
        setListener()
        initializer()
        initRecycler()
    }

    private fun initializer() {
        tvTitle.text=getString(R.string.friends_except)
        tvDone.visibility=View.VISIBLE

    }

    private fun initRecycler() {
        arrFriends.clear()
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        val mLayout=LinearLayoutManager(currActivity)
        rvExceptFriends.layoutManager=mLayout
        val mAdapter=FriendsExceptAdapter(currActivity, arrFriends)
        rvExceptFriends.adapter=mAdapter

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
