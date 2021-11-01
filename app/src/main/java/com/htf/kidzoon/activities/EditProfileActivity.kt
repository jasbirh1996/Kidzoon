package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.models.User
import com.htf.kidzoon.utils.MyApplication
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class EditProfileActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var currContext: Context = MyApplication.getAppContext()
    private var user: User?=null
    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,EditProfileActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        tvTitle.setText(R.string.edit_profile)
        setListener()
        setUserData()
    }

    private fun setUserData() {
        user= AppPreferences.getInstance(currContext).getUserDetails()
        val data=user?.data
        etUserName.setText(data?.name)
        etEmail.setText(data?.email)
        etMobileNumber.setText(data?.mobile)
        tvDialCode.text="+${data?.dial_code}"
        tvGender.text=data?.gender
        tvDob.text=data?.dob
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
