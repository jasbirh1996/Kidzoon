package com.htf.kidzoon.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.Room.AppDatabase
import com.htf.kidzoon.Room.DbMethods.CountryMethods
import com.htf.kidzoon.Room.Listeners.CountryListener
import com.htf.kidzoon.adapters.CountryAdapter
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Country
import com.htf.kidzoon.models.LoginWithOtp
import com.htf.kidzoon.models.User
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_country.view.*
import kotlinx.android.synthetic.main.otp_layout.view.*
import java.util.concurrent.TimeUnit

class LoginActivity : LocalizeActivity(),View.OnClickListener,CountryListener {
    private var currActivity: Activity =this
    private var currContext:Context=MyApplication.getAppContext()
    private var strMobile=""
    private var strEmail=""
    private var strPassword=""
    private var dialCode="966"
    lateinit var db:AppDatabase
    private var arrCountry=ArrayList<Country>()
    lateinit var dialog:Dialog
    lateinit var disposable:CompositeDisposable
    private var is_otp_login="Yes"
    private var is_mobile="Yes"
    lateinit var otpDialog: Dialog
    lateinit var otpDialogView: View
    private var countDownTimer: CountDownTimer?=null
    private var loginWithOtp:LoginWithOtp?=null

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListener()
        disposable= CompositeDisposable()
        db= AppDatabase.getAppDataBase(currContext)!!
        CountryMethods.getAllCountries(disposable,db,this)
    }

    private fun setListener() {
        tvSigninSignup.setOnClickListener(this)
        tvSignInWithPassword.setOnClickListener(this)
        tvSignInWithOtp.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)
        //cvSkip.setOnClickListener(this)
        llDialCode.setOnClickListener(this)
        cvLang.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tvSigninSignup->{
                if (checkPhoneValidation()){
                    userLoginWithOtp()
                }

            }
            R.id.tvSignInWithPassword->{
                is_mobile="No"
                is_otp_login="No"
                llMobile.visibility=View.GONE
                llEmailAndPassword.visibility=View.VISIBLE
                tvSignIn.visibility=View.VISIBLE
                tvSigninSignup.visibility=View.GONE
                tvSignInWithPassword.visibility=View.GONE
                tvSignInWithOtp.visibility=View.VISIBLE
            }
            R.id.tvSignInWithOtp->{
                is_mobile="Yes"
                is_otp_login="Yes"
                llMobile.visibility=View.VISIBLE
                llEmailAndPassword.visibility=View.GONE
                tvSignIn.visibility=View.GONE
                tvSigninSignup.visibility=View.VISIBLE
                tvSignInWithPassword.visibility=View.VISIBLE
                tvSignInWithOtp.visibility=View.GONE
            }
            R.id.tvSignIn->{
                if (checkValidation()){
                    userLoginWithPassword()
                }
            }


            R.id.llDialCode->{
                countryDialog()
            }

            R.id.cvLang->{
                switchLanguage()
            }

        }

    }

    private fun switchLanguage(){
        if (AppSession.locale == "ar") {
            tvLang.text=getString(R.string.english)
            AppSession.locale = "en"
            AppPreferences.getInstance(currActivity)
                .saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = true
            open(currActivity)
        }else{
            tvLang.text=getString(R.string.arabic)
            AppSession.locale = "ar"
            AppPreferences.getInstance(currActivity)
                .saveInPreference(Constants.KEY_PREF_USER_LANGUAGE, AppSession.locale)
            AppSession.isLocaleEnglish = false
            open(currActivity)
        }
    }

    private fun checkValidation(): Boolean {
        var isValid=true
        strEmail=etEmail.text.toString().trim()
        strPassword=etPassword.text.toString().trim()
        when {
            RegExp.chkEmpty(strEmail) -> {
                isValid = false
                val message = getString(R.string.email_required)
                AppUtils.showSnackBar(currActivity, tvError, message)
            }
            !RegExp.isValidEmail(strEmail) -> {
                isValid = false
                val message = getString(R.string.email_invalid)
                AppUtils.showSnackBar(currActivity, tvError, message)
            }
            RegExp.chkEmpty(strPassword) -> {
                isValid = false
                val message = getString(R.string.password_required)
                AppUtils.showSnackBar(currActivity, tvError, message)
            }
            !RegExp.isValidPASSWORD(strPassword) -> {
                isValid = false
                val message = getString(R.string.password_invalid)
                AppUtils.showSnackBar(currActivity, tvError, message)
            }

            else ->
                tvError.visibility = View.GONE

        }
        return isValid

    }

    private fun checkPhoneValidation(): Boolean {
        var isValid=true
        strMobile=etMobileNumber.text.toString().trim()
        when{
            RegExp.chkEmpty(strMobile)->{
                isValid=false
                val message=getString(R.string.mobile_required)
                AppUtils.showSnackBar(currActivity,tvError,message)
            }
            !RegExp.isValidPhone(strMobile)->{
                isValid=false
                val message=getString(R.string.mobile_invalid)
                AppUtils.showSnackBar(currActivity,tvError,message)
            }
            else->tvError.visibility=View.GONE
        }
        return isValid

    }

    private fun otpDialog() {
        otpDialog= Dialog(currActivity,R.style.DialogAnimationStyle)
        otpDialogView = currActivity.layoutInflater.inflate(R.layout.otp_layout, null)
        otpDialog.setContentView(otpDialogView)
        otpDialog.setCancelable(true)

        countDownTimer(otpDialogView)

        otpDialogView.apply {
            otpView.setOtpCompletionListener { otp ->
                if (otp != "") {
                    verifyOtp(otp)
                }
            }
            ivClose.setOnClickListener {
                countDownTimer?.cancel()
                otpDialog.dismiss()
            }

            tvResend.setOnClickListener {
                countDownTimer?.cancel()
                countDownTimer(otpDialogView)
                resendOtp()
            }

        }

        otpDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        otpDialog.window!!.setGravity(Gravity.BOTTOM)
        otpDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        otpDialog.show()

    }

    private fun fetchCountryList(){
        val call1= AppClient.getClient().
        getCountriesList(AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale)
        ClientResponse<BaseResponse<List<Country>>>().getRxResponse(disposable,call1,currActivity,null,false,object :APIResponse<BaseResponse<List<Country>>>{
            override fun onResponse(response: BaseResponse<List<Country>>?) {
                if (response!=null){
                    val list=response.data
                    //saved country list into local db
                    CountryMethods.insertCountryList(db,list)
                }
            }

        })

    }

    override fun onGetAllCountries(list: List<Country>?) {
        when {
            list!=null -> {
                arrCountry.addAll(list)
            }
            else -> {
                fetchCountryList()
            }
        }
    }

    override fun onCountryClickListener(country: Country) {
        tvDialCode.text="+${country.dial_code}"
        dialCode=country.dial_code
        if (dialog.isShowing){
            dialog.dismiss()
        }
    }

    private fun countryDialog() {
        dialog = Dialog(currActivity,R.style.DialogAnimationStyle)
        val view = currActivity.layoutInflater.inflate(R.layout.dialog_country, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)

        view.apply {
            rvCountry.layoutManager=LinearLayoutManager(currContext)
            val mCountryAdapter=CountryAdapter(currActivity,arrCountry,"",this@LoginActivity)
            rvCountry.adapter=mCountryAdapter

            tvCancel.setOnClickListener {
                dialog.dismiss()

            }

            etSearch.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    val searchText=s.toString()
                    if (searchText!=""){
                        val filteredList=arrCountry.filter { it.en_name?.toLowerCase()?.contains(searchText.toLowerCase())!!}
                        if (filteredList.isNotEmpty()){
                            mCountryAdapter.setFilter(filteredList,searchText)
                        }else{
                            mCountryAdapter.setFilter(filteredList,searchText)
                        }
                    }else{
                        mCountryAdapter.setFilter(arrCountry,"")
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })

        }

        dialog.window.apply {
            this?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            this?.setGravity(Gravity.BOTTOM)
            this?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.show()
    }
    private fun userLoginWithOtp(){
        val call= AppClient.getClient().userLoginWithOtp(
            AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale,
            is_otp_login,
            is_mobile,
            dialCode,
            strMobile,
            ""
        )

        ClientResponse<BaseResponse<LoginWithOtp>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<LoginWithOtp>>{
            override fun onResponse(response: BaseResponse<LoginWithOtp>?) {
                if (response!=null){
                    loginWithOtp=response.data
                    otpDialog()
                }
            }

        })
    }

    private fun userLoginWithPassword(){
        val call= AppClient.getClient().userLoginWithPassword(
            AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale,
            is_otp_login,
            is_mobile,
            "",
            strEmail,
            strPassword,
            ""
        )

        ClientResponse<BaseResponse<User>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<User>>{
            override fun onResponse(response: BaseResponse<User>?) {
                if (response!=null){
                    val user=response.data
                    AppPreferences.getInstance(currContext).saveUserDetails(user)
                    AppPreferences.getInstance(currContext).saveInPreference( Constants.KEY_TOKEN,user.access_token)
                    val data=user.data
                    HomeActivity.open(currActivity)
                    finish()
                }
            }

        })
    }

    private fun resendOtp(){
        val call=AppClient.getClient().resendOtp(AppSession.deviceId,
        AppSession.deviceType,
        AppSession.locale,
        loginWithOtp?.id!!,
        is_mobile,
        loginWithOtp?.token!!)

        ClientResponse<BaseResponse<LoginWithOtp>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<LoginWithOtp>>{
            override fun onResponse(response: BaseResponse<LoginWithOtp>?) {
                if (response!=null){
                    loginWithOtp=response.data
                }
            }

        })
    }
    private fun verifyOtp(otp:String){
        val call=AppClient.getClient().verifyOtp(
            AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale,
            loginWithOtp?.id!!,
            otp,
            loginWithOtp?.token!!,
            ""
        )

        ClientResponse<BaseResponse<User>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<User>>{
            override fun onResponse(response: BaseResponse<User>?) {
                if (response!=null){
                    val user=response.data
                    AppPreferences.getInstance(currContext).saveUserDetails(user)
                    AppPreferences.getInstance(currContext).saveInPreference( Constants.KEY_TOKEN,user.access_token)
                    val data=user.data
                    if (data.is_returner==0){
                        SelectGenderActivity.open(currActivity)
                    }else{
                        HomeActivity.open(currActivity)
                    }
                }
            }
        })
    }
    private fun countDownTimer(dialogView: View) {

        countDownTimer=object : CountDownTimer(1 * 60000, 1000) { // adjust the milli seconds here

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                dialogView.tvTimer.text = "" + String.format(
                    "%d : %d ",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            millisUntilFinished
                        )
                    )
                )
            }

            override fun onFinish() {
                dialogView.llResendOtp.visibility=View.VISIBLE
            }
        }.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer!=null)
            countDownTimer?.cancel()
        disposable.dispose()
    }

}
