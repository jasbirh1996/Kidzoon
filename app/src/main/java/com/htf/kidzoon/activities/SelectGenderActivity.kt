package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.utils.MyApplication
import kotlinx.android.synthetic.main.activity_select_gender.*
import kotlinx.android.synthetic.main.transparent_toolbar.*

class SelectGenderActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var gender=""

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,SelectGenderActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gender)
        tvTitle.text=getString(R.string.what_is_your_gender)
        setListener()
        selectGender(0)
    }

    private fun selectGender(selected: Int){
       rlMale.visibility=View.VISIBLE
        rlFemale.visibility=View.VISIBLE
        rlSelectedFemale.visibility=View.GONE
        rlSelectedMale.visibility=View.GONE
        btnSelectedNext.visibility=View.GONE
        btnNext.visibility=View.VISIBLE

        when(selected){
            1->{
                gender="male"
                rlSelectedMale.visibility=View.VISIBLE
                rlMale.visibility=View.GONE
                btnSelectedNext.visibility=View.VISIBLE
                btnNext.visibility=View.GONE
            }
            2->{
                gender="female"
                rlSelectedFemale.visibility=View.VISIBLE
                rlFemale.visibility=View.GONE
                btnSelectedNext.visibility=View.VISIBLE
                btnNext.visibility=View.GONE
            }
        }



    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        llMale.setOnClickListener(this)
        llFemale.setOnClickListener(this)
        btnSelectedNext.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.llMale->{
                selectGender(1)
            }
            R.id.llFemale->{
                selectGender(2)
            }
            R.id.btnSelectedNext->{
                val user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
                val data=user?.data!!
                data.gender=gender
                user.data=data
                AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(user)
                WhatsYourNameActivity.open(currActivity)
            }
        }

    }
}
