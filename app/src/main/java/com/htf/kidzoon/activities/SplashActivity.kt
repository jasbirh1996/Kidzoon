package com.htf.kidzoon.activities

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.utils.MyApplication

class SplashActivity : LocalizeActivity() {
    private var currActivity: Activity =this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val user=AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
            if (user!=null){
                val data=user.data
                if (data.is_returner==0){
                    if (data.gender!=null){
                        if (data.name!=null && data.dob!=null){
                            SetUpProfileActivity.open(currActivity)
                        }else{
                            WhatsYourNameActivity.open(currActivity)
                        }
                    }else{
                        SelectGenderActivity.open(currActivity)
                    }
                }else{
                    HomeActivity.open(currActivity)
                }
            }else{
                LoginActivity.open(currActivity)
            }
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
            finish()

        },3000)

    }
}
