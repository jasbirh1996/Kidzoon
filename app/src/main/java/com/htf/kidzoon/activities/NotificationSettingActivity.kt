package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.NotificationSettingAdapter
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Blank
import com.htf.kidzoon.models.NotificationSetting
import com.htf.kidzoon.models.User
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.MyApplication
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_notification_setting.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class NotificationSettingActivity : LocalizeActivity(),View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private var currActivity: Activity =this
    private var arrNotificationSetting=ArrayList<NotificationSetting>()

    private var NOTIFY_ENABLE="Enabled"
    private var NOTIFY_DISABLE="Disabled"
    lateinit var disposable:CompositeDisposable
    private var user:User?=null

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,NotificationSettingActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)
        tvTitle.text=getString(R.string.notification_settings)
        tvDone.visibility=View.VISIBLE
        tvDone.text=getString(R.string.save)
        disposable= CompositeDisposable()
        user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        setListener()
        setNotifyData()
    }

    private fun setNotifyData() {
        val user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        val data=user?.data!!
        swPost.isChecked= data.post_notify==NOTIFY_ENABLE
        swLikes.isChecked= data.like_notify==NOTIFY_ENABLE
        swComments.isChecked= data.comment_notify==NOTIFY_ENABLE
        swFollowRequest.isChecked= data.follow_notify==NOTIFY_ENABLE
        swFriendsRequest.isChecked= data.friend_request_notify==NOTIFY_ENABLE
        swChat.isChecked= data.chat_notify==NOTIFY_ENABLE
        swShare.isChecked= data.share_notify==NOTIFY_ENABLE
    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        tvDone.setOnClickListener(this)
        swPost.setOnCheckedChangeListener(this)
        swLikes.setOnCheckedChangeListener(this)
        swComments.setOnCheckedChangeListener(this)
        swFollowRequest.setOnCheckedChangeListener(this)
        swFriendsRequest.setOnCheckedChangeListener(this)
        swChat.setOnCheckedChangeListener(this)
        swShare.setOnCheckedChangeListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.tvDone->{
                AppPreferences.getInstance(MyApplication.getAppContext()).saveUserDetails(user!!)
                changeNotificationSetting()
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        val data=user?.data
        when(buttonView?.id){
            R.id.swPost->{
                if (isChecked){
                    data?.post_notify=NOTIFY_ENABLE
                }else{
                    data?.post_notify=NOTIFY_DISABLE
                }
            }
            R.id.swLikes->{
                if (isChecked){
                    data?.like_notify=NOTIFY_ENABLE
                }else{
                    data?.like_notify=NOTIFY_DISABLE
                }
            }
            R.id.swComments->{
                if (isChecked){
                    data?.comment_notify=NOTIFY_ENABLE
                }else{
                    data?.comment_notify=NOTIFY_DISABLE
                }
            }
            R.id.swFollowRequest->{
                if (isChecked){
                    data?.follow_notify=NOTIFY_ENABLE
                }else{
                    data?.follow_notify=NOTIFY_DISABLE
                }
            }
            R.id.swFriendsRequest->{
                if (isChecked){
                    data?.friend_request_notify=NOTIFY_ENABLE
                }else{
                    data?.friend_request_notify=NOTIFY_DISABLE
                }
            }
            R.id.swChat->{
                if (isChecked){
                    data?.chat_notify=NOTIFY_ENABLE
                }else{
                    data?.chat_notify=NOTIFY_DISABLE
                }
            }
            R.id.swShare->{
                if (isChecked){
                    data?.share_notify=NOTIFY_ENABLE
                }else{
                    data?.share_notify=NOTIFY_DISABLE
                }
            }
        }
        user?.data=data!!
    }

    private fun changeNotificationSetting(){
        val user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        val data=user?.data
        val call=AppClient.getClient().changeNotificationSettings(
            AppSession.deviceId,
            AppSession.deviceType,
            AppSession.locale,
            data?.post_notify!!,
            data.like_notify,
            data.comment_notify,
            data.follow_notify,
            data.friend_request_notify,
            data.chat_notify,
            data.share_notify
        )

        ClientResponse<BaseResponse<Blank>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<Blank>>{
            override fun onResponse(response: BaseResponse<Blank>?) {
                if (response!=null){
                    Log.d("message",response.message)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
