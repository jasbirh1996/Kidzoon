package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Blank
import com.htf.kidzoon.models.User
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.Constants
import com.htf.kidzoon.utils.MyApplication
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_privacy_setting.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class PrivacySettingActivity : LocalizeActivity(), View.OnClickListener {
    private var currActivity: Activity =this
    private var currContext: Context = MyApplication.getAppContext()
    private var user: User?=null
    lateinit var disposable: CompositeDisposable

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,PrivacySettingActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_setting)
        tvTitle.setText(R.string.privacy_settings)
        disposable= CompositeDisposable()
        user=AppPreferences.getInstance(currContext).getUserDetails()
        setPrivacySetting()
        setListener()

    }

    private fun setPrivacySetting() {
        val data=user?.data!!
        when(data.privacy){
            Constants.PRIVACY_PUBLIC->{
                select(2)
            }
            Constants.PRIVACY_FRIENDS->{
                select(1)
            }
            Constants.PRIVACY_ONLY_ME->{
                select(3)
            }
        }
    }

    private fun setListener() {
        llFriendsOnly.setOnClickListener(this)
        llPublicly.setOnClickListener(this)
        llOnlyMe.setOnClickListener(this)
        btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.llFriendsOnly->{
                select(1)
                changePrivacy(Constants.PRIVACY_FRIENDS)
                val data=user?.data
                data?.privacy=Constants.PRIVACY_FRIENDS
                user?.data=data!!
                AppPreferences.getInstance(currContext).saveUserDetails(user!!)
            }
            R.id.llPublicly->{
                select(2)
                changePrivacy(Constants.PRIVACY_PUBLIC)
                val data=user?.data
                data?.privacy=Constants.PRIVACY_PUBLIC
                user?.data=data!!
                AppPreferences.getInstance(currContext).saveUserDetails(user!!)
            }
            R.id.llOnlyMe->{
                select(3)
                changePrivacy(Constants.PRIVACY_ONLY_ME)
                val data=user?.data
                data?.privacy=Constants.PRIVACY_ONLY_ME
                user?.data=data!!
                AppPreferences.getInstance(currContext).saveUserDetails(user!!)
            }
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }

    private fun select(selected: Int) {
        ivFriendsOnly.setBackgroundResource(R.drawable.privacy_setting_untick_bg)
        ivPublicly.setBackgroundResource(R.drawable.privacy_setting_untick_bg)
        ivOnlyMe.setBackgroundResource(R.drawable.privacy_setting_untick_bg)
        when(selected){
            1->{
                ivFriendsOnly.setBackgroundResource(R.drawable.right_tik)
            }
            2->{
                ivPublicly.setBackgroundResource(R.drawable.right_tik)
            }
            3->{
                ivOnlyMe.setBackgroundResource(R.drawable.right_tik)
            }
        }
    }

    private fun changePrivacy(privacy:String){
        val call=AppClient.getClient().changePrivacy(AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale,
            privacy
        )

        ClientResponse<BaseResponse<Blank>>().getRxResponse(disposable,call,currActivity,null,false,object :APIResponse<BaseResponse<Blank>>{
            override fun onResponse(response: BaseResponse<Blank>?) {
                if (response!=null){

                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


}
