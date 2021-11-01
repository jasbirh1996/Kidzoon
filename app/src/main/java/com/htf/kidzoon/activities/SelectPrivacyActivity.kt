package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.kidzoon.R
import kotlinx.android.synthetic.main.activity_select_privacy.*
import kotlinx.android.synthetic.main.transparent_toolbar.*

class SelectPrivacyActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,SelectPrivacyActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_privacy)
        setListener()
        tvTitle.text=getString(R.string.select_privacy)
    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        ivFriendsExcept.setOnClickListener(this)
        ivSpecificFriends.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.ivFriendsExcept->{
                FriendsExceptActivity.open(currActivity)
            }
            R.id.ivSpecificFriends->{
                SpecificFriendsActivity.open(currActivity)
            }
        }

    }
}
