package com.htf.kidzoon.utils

import android.provider.Settings
object AppSession {

    var locale = "en"
    var userToken = ""
    var deviceId = Settings.Secure.getString(MyApplication.getAppContext().contentResolver,Settings.Secure.ANDROID_ID)
    var deviceType = "android"
    var currency = "SAR"
    var isLocaleEnglish = false
    var userTokenIsValid: Boolean = true
    var mySelectedTab: Int = 0
    var city: String=""
    var state: String=""
    var country: String=""
    var postalCode: String=""
    var orderID=""
    var tokenExpireTime:Int=0

    var isPendingRequestOpen=false


}