package com.htf.kidzoon.netUtils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.util.Log
import androidx.fragment.app.Fragment
import com.htf.kidzoon.utils.AppUtils
import com.htf.kidzoon.utils.Constants.REPONSE_CODE_TOKEN_EXPIRED
import com.htf.kidzoon.utils.Constants.REPONSE_CODE_UNPROCESSABLE_ENTITY
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.*


class ClientResponse<T> {

    fun getResponse(call: Call<T>, activity: Activity, fragment: Fragment?, showProgress: Boolean,
                    retrofitResponse: APIResponse<T>
    ): Boolean{
        if (!AppUtils.isInternetOn(
                activity,
                fragment,
                4993
            )
        )
            return false

        /*if (APIClient.isRefreshingToken) {
            Handler().postDelayed({
                getResponse(call, activity, fragment, showProgress, retrofitResponse)
            }, 5000)
            return true
        }*/

        var dialog: Dialog? = null
        if (showProgress) {
            dialog = AppUtils.showProgress(activity)
        }
        val finalDialog = dialog

        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                dialog?.dismiss()
                Log.e("API_RESPONSE", t.message)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                finalDialog?.dismiss()
                if (response.isSuccessful){
                    retrofitResponse.onResponse(response = response.body())
                }else{
                    val error=response.errorBody()?.string()
                    Log.e("error",error!!)
                    val jsonObject=JSONObject(error)
                    jsonObject.has("error")

                }

            }

        })
        return true

    }


    fun getRxResponse(disposable: CompositeDisposable,call: Single<T>, activity: Activity, fragment: Fragment?, showProgress: Boolean,
                       retrofitResponse: APIResponse<T>):Boolean{

        if (!AppUtils.isInternetOn(
                activity,
                fragment,
                4993
            )
        )
            return false
        var dialog: Dialog? = null
        if (showProgress) {
            dialog = AppUtils.showProgress(activity)
        }
        val finalDialog = dialog

        disposable.add(
            call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dialog?.dismiss()
                    retrofitResponse.onResponse(response = it)
                },{
                    dialog?.dismiss()
                    if (it is HttpException) {
                        val body: ResponseBody = it.response().errorBody()!!
                        val error=body.string()
                        when(it.code()){
                            REPONSE_CODE_TOKEN_EXPIRED-> {
                                //User token has been expired
                            }
                            REPONSE_CODE_UNPROCESSABLE_ENTITY->{
                                retrofitResponse.onResponse(null)
                                val jsonObject = JSONObject(error)
                                if (jsonObject.has("errors")){
                                    val errors = jsonObject.getJSONObject("errors")
                                    AppUtils.showToast(
                                        activity,
                                        errors.getJSONArray(errors.names().getString(0)).getString(0),
                                        true
                                    )
                                }
                            }
                        }

                    }

                })
        )

        return true
    }


}