package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.kidzoon.R
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Blank
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.MyApplication
import com.htf.kidzoon.utils.RegExp
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_password.etConfirmPassword
import kotlinx.android.synthetic.main.activity_set_up_profile.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class ChangePasswordActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var currContext:Context=MyApplication.getAppContext()
    private var strOldPassword=""
    private var strNewPassword=""
    private var strConfirmPassword=""
    lateinit var disposable: CompositeDisposable

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,ChangePasswordActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        tvTitle.text=getString(R.string.change_password)
        disposable= CompositeDisposable()
        setListener()
    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    private fun checkValidation():Boolean{
        var isValid=true
        strOldPassword=etOldPassword.text.toString().trim()
        strNewPassword=etNewPassword.text.toString().trim()
        strConfirmPassword=etConfirmPassword.text.toString().trim()
        when{
            RegExp.chkEmpty(strOldPassword) -> {
                isValid = false
                val message = getString(R.string.password_required)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }

            !RegExp.isValidPASSWORD(strOldPassword) -> {
                isValid = false
                val message = getString(R.string.password_invalid)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }

            RegExp.chkEmpty(strNewPassword) -> {
                isValid = false
                val message = getString(R.string.password_required)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }

            !RegExp.isValidPASSWORD(strNewPassword) -> {
                isValid = false
                val message = getString(R.string.password_invalid)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }
            RegExp.chkEmpty(strConfirmPassword) -> {
                isValid = false
                val message = getString(R.string.password_required)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }

            !RegExp.isValidPASSWORD(strConfirmPassword) -> {
                isValid = false
                val message = getString(R.string.password_invalid)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }
            (strConfirmPassword!=strNewPassword)->{
                isValid = false
                val message = getString(R.string.password_does_not_match)
                AppUtils.showSnackBar(currActivity, tvChangePasswordError, message)
            }

        }

        return isValid
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.btnSave->{
                if (checkValidation()){
                    changePassword()
                }
            }
        }

    }

    private fun changePassword(){
        val call=AppClient.getClient().changePassword(AppSession.deviceId,
        AppSession.deviceType,
        AppSession.locale,
        strNewPassword,
        strConfirmPassword)

        ClientResponse<BaseResponse<Blank>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<Blank>>{
            override fun onResponse(response: BaseResponse<Blank>?) {
                if (response!=null){
                    finish()
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
