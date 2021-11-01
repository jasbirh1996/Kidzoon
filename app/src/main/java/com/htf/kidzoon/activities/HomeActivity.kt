package com.htf.kidzoon.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.siyamed.shapeimageview.RoundedImageView
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.Room.AppDatabase
import com.htf.kidzoon.Room.DbMethods.CountryMethods
import com.htf.kidzoon.fragments.ChatFragment
import com.htf.kidzoon.fragments.FeedsFragment
import com.htf.kidzoon.fragments.ProfileFragment
import com.htf.kidzoon.models.BaseResponse
import com.htf.kidzoon.models.Blank
import com.htf.kidzoon.models.Data
import com.htf.kidzoon.netUtils.APIResponse
import com.htf.kidzoon.netUtils.AppClient
import com.htf.kidzoon.netUtils.ClientResponse
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.Constants
import com.htf.kidzoon.utils.MyApplication
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_layout.view.*

class HomeActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    lateinit var disposable: CompositeDisposable
    lateinit var db:AppDatabase
    companion object{
        var homeActivity:Activity?=null
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeActivity=this
        db= AppDatabase.getAppDataBase(MyApplication.getAppContext())!!
        disposable= CompositeDisposable()
        setListener()
        selectTab(1)
    }

    private fun changeFragment(s:String,fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val ft=fragmentManager.beginTransaction()
        ft.replace(R.id.container,fragment)
        ft.commit()
    }

    private fun selectTab(selected: Int) {
        ivFeeds.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.feeds))
        tvFeeds.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDark))
        ivChat.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.chat))
        tvChat.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDark))
        ivProfile.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.profile_tab))
        tvProfile.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDark))
        when(selected){
            1->{
                changeFragment("",FeedsFragment())
                ivFeeds.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.feeds_selected))
                tvFeeds.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
            }

            2->{
                changeFragment("",ChatFragment())
                ivChat.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.chat_selected))
                tvChat.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
            }

            3->{
                changeFragment("",ProfileFragment())
                ivProfile.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.profile_selected))
                tvProfile.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
            }
        }

    }

    private fun setListener() {
        llFeeds.setOnClickListener(this)
        llChat.setOnClickListener(this)
        llProfile.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.llFeeds->{
                selectTab(1)
            }
            R.id.llChat->{
                selectTab(2)
            }
            R.id.llProfile->{
                selectTab(3)
            }
        }
    }

    fun openDrawer() {
        val user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        val data=user?.data!!
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.drawer_layout, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.show()

        dialogView.apply {
            tvName.text=data.name
            tvUserId.text=data.username
            tvLevel.text="${currActivity.getString(R.string.level)} ${data.level}"
            setUserImage(data,ivUserImage)
        }

        dialogView.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.llFollowRequest.setOnClickListener {
            FollowRequestActivity.open(currActivity)
        }
        dialogView.llUserId.setOnClickListener {
            UserActivity.open(currActivity)
        }
        dialogView.llFriendRequest.setOnClickListener {
            FriendRequestActivity.open(currActivity)
        }
        dialogView.llProfileInfo.setOnClickListener {
            ProfileInformationActivity.open(currActivity)
        }
        dialogView.llPrivacySetting.setOnClickListener {
            PrivacySettingActivity.open(currActivity)
        }
        dialogView.llChangePassword.setOnClickListener {
            ChangePasswordActivity.open(currActivity)
        }
        dialogView.llNotificationSetting.setOnClickListener {
            NotificationSettingActivity.open(currActivity)
        }
        dialogView.llLogOut.setOnClickListener {
            logout()
        }
        dialogView.llShareApp.setOnClickListener {
            shareIntent()
        }

        val window = dialog.window
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.FILL)
        window.setBackgroundDrawable(ContextCompat.getDrawable(currActivity, R.color.colorWhite))


    }

    private fun shareIntent() {
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            val sAux = "http://play.google.com/store/apps/details?id="+currActivity.packageName
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            startActivity(Intent.createChooser(i, getString(R.string.shareApp)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun ImageView.setImage(url:String,placeHolder:Int){
        Picasso.get().load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .placeholder(placeHolder)
            .into(this,object: Callback {
                override fun onSuccess() {
                    Picasso.get().load(url).placeholder(placeHolder).into(this@setImage)
                }
                override fun onError(e: java.lang.Exception?) {
                    Picasso.get().load(url).placeholder(placeHolder).into(this@setImage)

                }
            })
    }

    private fun setUserImage(data:Data,imageView: ImageView){
        if (data.image!=null){
            if (data.gender=="male"){
                imageView.setImage(Constants.USER_IMAGE_URL+data.image,R.drawable.male)
            }else{
                imageView.setImage(Constants.USER_IMAGE_URL+data.image,R.drawable.female)
            }
        }else{
            if (data.gender=="male"){
                imageView.setImageResource(R.drawable.male)
            }else{
                imageView.setImageResource(R.drawable.female)
            }
        }
    }

    private fun logout(){
        val call=AppClient.getClient().logout(AppSession.deviceId,
        AppSession.deviceType,
        AppSession.locale)

        ClientResponse<BaseResponse<Blank>>().getRxResponse(disposable,call,currActivity,null,true,object :APIResponse<BaseResponse<Blank>>{
            override fun onResponse(response: BaseResponse<Blank>?) {
                if (response!=null){
                    //CountryMethods.deleteCountry(db)
                    AppPreferences.getInstance(currActivity).clearUserDetails()
                    AppPreferences.getInstance(currActivity)
                        .clearPreference(Constants.KEY_TOKEN)
                    finish()
                    LoginActivity.open(currActivity)
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
