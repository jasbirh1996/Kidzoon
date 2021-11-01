package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Data
import com.htf.kidzoon.models.User
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.MyApplication
import com.htf.kidzoon.utils.RegExp
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_set_up_profile.*
import kotlinx.android.synthetic.main.activity_set_up_profile.etEmail
import kotlinx.android.synthetic.main.activity_set_up_profile.etMobileNumber
import kotlinx.android.synthetic.main.activity_set_up_profile.etPassword
import kotlinx.android.synthetic.main.transparent_toolbar.*

class SetUpProfileActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var currContext:Context=MyApplication.getAppContext()
    private var strName=""
    private var strEmail=""
    private var strMobile=""
    private var strPassword=""
    private var strConfirmPassword=""
    private var user:User?=null
    private var strDialCode=""
    lateinit var disposable:CompositeDisposable

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,SetUpProfileActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up_profile)
        tvTitle.text=getString(R.string.setup_your_profile)
        disposable=CompositeDisposable()
        setUserData()
        setListener()
    }
    private fun setUserData() {
        user=AppPreferences.getInstance(currContext).getUserDetails()
        val data=user?.data!!
        strDialCode=data.dial_code
        tvDialCodeSatup.text="+$strDialCode"
        etMobileNumber.setText(data.mobile)
        if (data.gender=="male"){
            ivUser.setImageResource(R.drawable.male)
        }else{
            ivUser.setImageResource(R.drawable.female)
        }
        etUserName.setText(data.name)

    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        tvStart.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.tvStart->{
                if (checkValidation()) {
                    completeProfile()
                }
            }
        }

    }

    private fun checkValidation(): Boolean {
        var isValid=true
        strName=etUserName.text.toString().trim()
        strEmail=etEmail.text.toString().trim()
        strMobile=etMobileNumber.text.toString().trim()
        strPassword=etPassword.text.toString().trim()
        strConfirmPassword=etConfirmPassword.text.toString().trim()

        when{
            RegExp.chkEmpty(strName) -> {
                isValid = false
                val message = getString(R.string.username_required)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }

            RegExp.chkEmpty(strEmail) -> {
                isValid = false
                val message = getString(R.string.email_required)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }
            !RegExp.isValidEmail(strEmail) -> {
                isValid = false
                val message = getString(R.string.email_invalid)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }
            RegExp.chkEmpty(strPassword) -> {
                isValid = false
                val message = getString(R.string.password_required)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }
            !RegExp.isValidPASSWORD(strPassword) -> {
                isValid = false
                val message = getString(R.string.password_invalid)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }

            (strConfirmPassword!=strPassword)->{
                isValid = false
                val message = getString(R.string.password_does_not_match)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }

            RegExp.chkEmpty(strMobile) -> {
                isValid = false
                val message = getString(R.string.mobile_required)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }
            !RegExp.isValidPhone(strMobile) -> {
                isValid = false
                val message = getString(R.string.mobile_invalid)
                AppUtils.showSnackBar(currActivity, tvProfileError, message)
            }
            else->
                tvProfileError.visibility=View.GONE

        }
        return isValid
    }

    private fun completeProfile(){
        val call=AppClient.getClient().completeProfile(
            device_id = AppSession.deviceId,
            device_type = AppSession.deviceType,
            locale = AppSession.locale,
            name = strName,
            email = strEmail,
            dial_code = strDialCode,
            mobile = strMobile,
            password = strPassword,
            dob = user?.data?.dob!!,
            gender = user?.data?.gender!!,
            image = "",
            image_directory = "",
            image_extension = "",
            image_mime_type = "",
            image_size = 0
        )

        ClientResponse<BaseResponse<Data>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<Data>>{
            override fun onResponse(response: BaseResponse<Data>?) {
                if (response!=null){
                    val completeProfile=response.data
                    user?.data=completeProfile
                    AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(user!!)
                    HomeActivity.open(currActivity)
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
