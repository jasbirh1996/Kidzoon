/*
package com.htf.kidzoon.netUtils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import com.htf.htfnew.utils.AppPreferences
import com.htf.kidzoon.R
import com.htf.kidzoon.activities.LoginActivity
import com.htf.kidzoon.activities.SplashActivity
import com.htf.kidzoon.utils.AppSession
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.Constants
import com.htf.kidzoon.utils.Constants.KEY_FCM_TOKEN
import com.htf.kidzoon.utils.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object APIClient {

    var isRefreshingToken = false

    fun getClient(): APIInterface {
        val logging = HttpLoggingInterceptor()
        if (AppUtils.isAppDebug) {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder()
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("locale", AppSession.locale)

                val userToken =
                    AppPreferences.getInstance(MyApplication.getInstance()).getFromPreference(
                        Constants.KEY_TOKEN)
                if (userToken != null) {
                    if (userToken.length > 5) {
                        builder.header("Authorization", "Bearer $userToken")
                    }
                } else if (AppSession.userToken.length > 5) {
                    builder.header("Authorization", "Bearer " + AppSession.userToken)
                }

                val newRequest = builder.build()
                chain.proceed(newRequest)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(APIInterface::class.java)
    }

    fun getResponse(
        call: Call<String>, activity: Activity, fragment: Fragment?, showProgress: Boolean,
        retrofitResponse: RetrofitResponse
    ): Boolean {

        if (!AppUtils.isInternetOn(activity, fragment, 4993))
            return false

        if (isRefreshingToken) {
            Handler().postDelayed({
                getResponse(call, activity, fragment, showProgress, retrofitResponse)
            }, 5000)
            return true
        }

        var dialog: Dialog? = null
        if (showProgress) {
            dialog = AppUtils.showProgress(activity)
        }
        val finalDialog = dialog

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (showProgress && finalDialog != null)
                    AppUtils.hideProgress(finalDialog)

                if (response!!.isSuccessful) {
                    val serverResponse = response.body().toString()
                    retrofitResponse.onResponse(serverResponse)
                    Log.e("ServerResponse", "$serverResponse!")
                } else {
                    retrofitResponse.onResponse(null)
                    val code = response.code()
                    if (code == 401) {
                        AppPreferences.getInstance(activity).clearUserDetails()
                        AppPreferences.getInstance(activity).clearUserDetails()
                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        activity.startActivity(intent)
                    } else if (code == 400 || code == 403 || code == 422 || code == 500) {
                        try {
                            val error = response.errorBody()!!.string()
                            AppUtils.printLog("ResponseError", "$error!")
                            if (error.startsWith("<!DOCTYPE")) {
                                if (!activity.isFinishing)
                                    AppUtils.showToast(activity, activity.getString(R.string.server_error), true)
                            } else {
                                val jsonObject = JSONObject(error)
                                when {
                                    //  TOKEN IS EXPIRED NEED TO REFRESH TOKEN OF USER
                                    jsonObject.has("jwt_expired") && jsonObject.getInt("jwt_expired") == 1 -> {
                                        AppSession.userTokenIsValid = false
                                        refreshToken(call!!.clone(), activity, fragment, showProgress, retrofitResponse)
                                    }
                                    // TOKEN IS BLACKLISTED SO NEED TO SEND USER TO LOGIN SCREEN
                                    jsonObject.has("jwt_blacklisted") && jsonObject.getInt("jwt_blacklisted") == 1 -> {
                                        AppSession.userTokenIsValid = false
                                        AppPreferences.getInstance(activity).clearUserDetails()
                                        AppPreferences.getInstance(activity).clearPreference(KEY_FCM_TOKEN)
                                        val intent = Intent(activity, LoginActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        activity.startActivity(intent)

                                    }
                                    jsonObject.has("errors") -> {
                                        val errors = jsonObject.getJSONObject("errors")
                                        AppUtils.showToast(
                                            activity,
                                            errors.getJSONArray(errors.names().getString(0)).getString(0),
                                            true
                                        )
                                    }
                                    else -> AppUtils.showToast(
                                        activity,
                                        activity.getString(R.string.server_error),
                                        true
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            if (!activity.isFinishing)
                                AppUtils.showToast(activity, activity.getString(R.string.server_error), true)
                        }

                    } else {
                        if (!activity.isFinishing)
                            AppUtils.showToast(activity, activity.getString(R.string.server_error), true)
                    }

                }

            }


            override fun onFailure(call: Call<String>?, t: Throwable?) {
                call!!.cancel()
                if (showProgress && finalDialog != null)
                    AppUtils.hideProgress(finalDialog)
                retrofitResponse.onResponse(null)
                val message = t!!.message
                if (message != null && !message.isEmpty() && !message.equals("null", ignoreCase = true)) {
                    Log.e("RetrofitException", t.message + "")
                    if (!message.contains("not found: limit=1 content="))
                        AppUtils.showToast(activity, message + "", true)
                } else {
                    Log.e("RetrofitException", t.toString() + "")
                    if (!activity.isFinishing)
                        AppUtils.showToast(activity, activity.getString(R.string.server_error), true)
                }
                Log.e("RetrofitException", t.message)
                AppUtils.showToast(activity, t.message!!, true)
            }
        })
        return true

    }

    private fun refreshToken(
        call: Call<String>,
        activity: Activity,
        fragment: Fragment?,
        showProgress: Boolean,
        retrofitResponse: RetrofitResponse
    ) {
        val call1 = getClient().refreshToken(
        )
        getResponse(call1, activity, fragment, showProgress, object : RetrofitResponse {
            override fun onResponse(data: String?) {
                if (data != null) {
                    val jsonObject = JSONObject(data)
                    val jsonObject1 = jsonObject.optJSONObject(Constants.KEY_DATA)
                    val token = jsonObject1.optString(Constants.KEY_TOKEN)
                    val tokenExpireTime=jsonObject1.getInt("expires_in")
                    //AppSession.tokenExpireTime=tokenExpireTime
                    //AppPreferences.getInstance(activity).clareExpireTimeFromPreference(Constants.KEY_TOKEN_EXPIRE_TIME)
                    //AppPreferences.getInstance(activity).saveInPreference(Constants.KEY_TOKEN_EXPIRE_TIME, tokenExpireTime)
                    //val user = AppPreferences.getInstance(activity).getUserDetails()
                    //user!!.token = token
                    AppSession.userToken = token
                    AppPreferences.getInstance(activity).saveInPreference(Constants.KEY_TOKEN, token)
                   // AppPreferences.getInstance(activity).saveUserDetails(user)
                    getResponse(call, activity, fragment, showProgress, retrofitResponse)
                } else {
                   // AppPreferences.getInstance(activity).clearUserDetails()
                    AppPreferences.getInstance(activity).clearPreference(Constants.KEY_TOKEN)
                    val intent = Intent(activity, SplashActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)

                }

                isRefreshingToken = false
            }
        })
    }

}*/
