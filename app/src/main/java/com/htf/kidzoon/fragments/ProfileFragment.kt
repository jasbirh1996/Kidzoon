package com.htf.kidzoon.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.htf.kidzoon.R
import com.htf.kidzoon.activities.*
import kotlinx.android.synthetic.main.drawer_layout.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_profile, container, false)
        currActivity=activity!!
        selectedTab(1)
        setListener()
        //setToolbar()

        return rootView
    }

    private fun setToolbar() {

        /*appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = getString(R.string.my_profile)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = ""//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
*/

    }
    private fun setListener() {
        rootView.llTimeLine.setOnClickListener(this)
        rootView.llVideo.setOnClickListener(this)
        rootView.llPhotos.setOnClickListener(this)
       rootView.ivMenu.setOnClickListener(this)
      rootView.btnNotification.setOnClickListener(this)
        rootView.ivCamera.setOnClickListener(this)
    }

    private fun changeFragment(s:String,fragment: Fragment){
        val fragmentManager=childFragmentManager
        val ft=fragmentManager.beginTransaction()
        ft.replace(R.id.profile_container,fragment)
        ft.commit()
    }

    private fun selectedTab(selected: Int) {
        rootView.ivTimeLine.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.timeline_unselected))
        rootView.tvTimeLine.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        rootView.viewTimeline.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        rootView.ivVideo.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.video_gray))
        rootView.tvVideo.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        rootView.viewVideo.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        rootView.ivPhotos.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.photo_gray))
        rootView.tvPhotos.setTextColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        rootView.viewPhoto.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorDarkGrey))
        when(selected){
            1->{
                changeFragment("",TimelineFragment())
                rootView.ivTimeLine.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.timeline_selected))
                rootView.tvTimeLine.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
                rootView.viewTimeline.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorRed))
            }

            2->{
                changeFragment("",VideoFragment())
                rootView.ivVideo.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.video_selected))
                rootView.tvVideo.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
                rootView.viewVideo.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorRed))

            }

            3->{
                changeFragment("",PhotosFragment())
                rootView.ivPhotos.setImageDrawable(ContextCompat.getDrawable(currActivity,R.drawable.photo_selected))
                rootView.tvPhotos.setTextColor(ContextCompat.getColor(currActivity,R.color.colorRed))
                rootView.viewPhoto.setBackgroundColor(ContextCompat.getColor(currActivity,R.color.colorRed))

            }
        }


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.llTimeLine->{
                selectedTab(1)
            }
            R.id.llVideo->{
                selectedTab(2)
            }
            R.id.llPhotos->{
                selectedTab(3)
            }
            R.id.ivMenu->{
                openDrawer()
            }
            R.id.ivCamera->{
                val intent=Intent(activity,CameraActivity::class.java)
                startActivity(intent)
            }
            R.id.btnNotification->{
                NotificationActivity.open(currActivity)
            }
        }

    }

    private fun openDrawer() {
        val builder = AlertDialog.Builder(currActivity)
        val dialogView = currActivity.layoutInflater.inflate(R.layout.drawer_layout, null)
        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.show()
        builder.setView(dialogView)
        dialogView.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.llProfileInfo.setOnClickListener {
            ProfileInformationActivity.open(currActivity)
        }
        dialogView.llUserId.setOnClickListener {
            UserActivity.open(currActivity)
        }
        dialogView.llFriendRequest.setOnClickListener {
            FriendRequestActivity.open(currActivity)
        }
        dialogView.llFollowRequest.setOnClickListener {
            FollowRequestActivity.open(currActivity)
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
            LoginActivity.open(currActivity)
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

}
