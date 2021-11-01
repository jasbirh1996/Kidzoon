package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.kidzoon.R
import com.htf.kidzoon.models.User
import com.htf.kidzoon.utils.MyApplication
import kotlinx.android.synthetic.main.red_background_toolbar.*

class UserActivity : LocalizeActivity(), View.OnClickListener {
    private var currActivity: Activity =this
    private var currContext:Context=MyApplication.getAppContext()
    private var user:User?=null

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,UserActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        tvTitle.text=getString(R.string.user_name)

        setListener()
        setPrivacy()
    }

    private fun setPrivacy() {

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
