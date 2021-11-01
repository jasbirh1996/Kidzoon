package com.htf.kidzoon.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.AgeAdapter
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.CenterZoomLayoutManager
import com.htf.kidzoon.utils.MyApplication
import com.htf.kidzoon.utils.RegExp
import kotlinx.android.synthetic.main.activity_set_up_profile.*
import kotlinx.android.synthetic.main.activity_whats_your_name.*
import kotlinx.android.synthetic.main.activity_whats_your_name.etUserName
import kotlinx.android.synthetic.main.activity_whats_your_name.ivCalender
import kotlinx.android.synthetic.main.activity_whats_your_name.tvDob
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import kotlinx.android.synthetic.main.row_age.view.*
import kotlinx.android.synthetic.main.transparent_toolbar.*
import java.text.SimpleDateFormat
import java.util.*


class WhatsYourNameActivity : LocalizeDarkActivity(), View.OnClickListener
{
    private var currActivity: Activity =this
    private var currContext:Context=MyApplication.getAppContext()
    private var dob=""
    private var strName=""
    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,WhatsYourNameActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_your_name)
        tvTitle.text=getString(R.string.what_is_your_name)
        setListener()
    }



    private fun setListener() {
        btnBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        ivCalender.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
         when(v!!.id){
             R.id.btnBack->{
                 onBackPressed()
             }
             R.id.btnNext->{
                 if (checkValidation()){
                     val user=AppPreferences.getInstance(currContext).getUserDetails()
                     val data=user?.data!!
                     data.name=strName
                     data.dob=dob
                     user.data=data
                     AppPreferences.getInstance(currContext).saveUserDetails(user)
                     SetUpProfileActivity.open(currActivity)
                 }

             }

             R.id.ivCalender->{
                 openDatePickerDialog()
             }

         }

    }

    @SuppressLint("SimpleDateFormat")
    private fun openDatePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd =
            DatePickerDialog(
                currActivity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val targetDateFormat = "dd MMM, yyyy"
                    val serverDateFormat = "yyyy-MM-dd"
                    val sdf = SimpleDateFormat(serverDateFormat)
                    val tdf = SimpleDateFormat(targetDateFormat)
                    sdf.timeZone = TimeZone.getTimeZone("UTC")
                    tdf.timeZone = TimeZone.getTimeZone("UTC")
                    val date = sdf.format(c.time)
                    dob=date
                    val targetDate = tdf.format(c.time)
                    tvDob.text = targetDate
                },
                year,
                month,
                day
            )

        dpd.datePicker.maxDate =
            System.currentTimeMillis() - 31556952000
        dpd.show()
    }

    private fun checkValidation(): Boolean {
        var isValid=true
        strName=etUserName.text.toString().trim()


        when{
            RegExp.chkEmpty(strName) -> {
                isValid = false
                val message = getString(R.string.username_required)
                AppUtils.showSnackBar(currActivity, tvWhatsYourNameError, message)
            }
            RegExp.chkEmpty(dob) -> {
                isValid = false
                val message = getString(R.string.date_of_birth_required)
                AppUtils.showSnackBar(currActivity, tvWhatsYourNameError, message)
            }
            else->
                tvWhatsYourNameError.visibility=View.GONE

        }
        return isValid


    }


}