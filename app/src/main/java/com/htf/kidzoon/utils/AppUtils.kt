package com.htf.kidzoon.utils


import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.htf.kidzoon.BuildConfig
import com.htf.kidzoon.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.toast_view.view.*
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object AppUtils {

    val isAppDebug: Boolean = BuildConfig.DEBUG

    val serverDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val serverTimeFormat = SimpleDateFormat("HH:mm:ss")
    val serverCardExpireFormat = SimpleDateFormat("yyyy-MM")
    val serverDefaultDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val serverDefaultDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val serverDefaultTimeFormat = SimpleDateFormat("HH:mm:ss")
    val displayDateTimeFormat = SimpleDateFormat("dd MMM, yyyy hh:mm a")
    val calendarDateFormat = SimpleDateFormat("EEE, dd MMM, yyyy")
    val calendarDateTimeFormat = SimpleDateFormat("EEE, dd MMM, yyyy 'at' hh:mm a")
    val calendarYearFormat = SimpleDateFormat("MMMM, yyyy")
    val dayFormat = SimpleDateFormat("EEEE")
    val targetDateFormat = SimpleDateFormat("dd MMM, yyyy")
    val targetTimeFormat = SimpleDateFormat("hh:mm a")
    val cardExpireFormat = SimpleDateFormat("MM/yy")

    val decimalFormat = DecimalFormat("#,##,##,###.##")

    private var dialog: Dialog? = null

    init {
        serverDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDateFormat.timeZone = TimeZone.getDefault()
        serverTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverCardExpireFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDefaultDateFormat.timeZone = TimeZone.getDefault()
        serverDefaultDateTimeFormat.timeZone = TimeZone.getDefault()
        serverDefaultTimeFormat.timeZone = TimeZone.getDefault()
        displayDateTimeFormat.timeZone = TimeZone.getDefault()
        calendarDateFormat.timeZone = TimeZone.getDefault()
        calendarDateTimeFormat.timeZone = TimeZone.getDefault()
        calendarYearFormat.timeZone = TimeZone.getDefault()
        dayFormat.timeZone = TimeZone.getDefault()
        targetDateFormat.timeZone = TimeZone.getDefault()
        targetTimeFormat.timeZone = TimeZone.getDefault()
        cardExpireFormat.timeZone = TimeZone.getDefault()
    }


    fun printLog(tag: String, message: String) {
        if (isAppDebug)
            Log.d(tag, message)
    }
    fun showProgress(activity: Activity): Dialog {
        val overlayDialog = Dialog(activity, android.R.style.Theme_Panel)
        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_progress, null)
        overlayDialog.setContentView(dialogView)
        overlayDialog.setCanceledOnTouchOutside(false)
        Glide.with(activity).asGif().load(R.drawable.loader).into(overlayDialog.ivLoader)
        if (!activity.isFinishing) {
            overlayDialog.show()
        }

        return overlayDialog
    }
    fun hideProgress(overlayDialog: Dialog) {
        if (overlayDialog.isShowing) {
            overlayDialog.dismiss()
        }
    }

    fun showSnackBar(activity: Activity, textView: TextView, message: String) {
        val snackbar = Snackbar.make(textView, message, 1000)
        val view = snackbar.view
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorTextRed))
        textView.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite))
        snackbar.show()
    }



    fun showToast(activity: Activity?, message: String, isError: Boolean) {
        if (activity != null) {
            val toast = Toast(activity)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER.toFloat()
            )

            val view= LayoutInflater.from(activity).inflate(R.layout.toast_view,null,false)

            view.tvToast.text = message
            if (isError)
                view.tvToast.background=ContextCompat.getDrawable(activity,R.drawable.bg_toast_error)
            else
                view.tvToast.background=ContextCompat.getDrawable(activity,R.drawable.bg_toast_success)

            toast.view = view
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }


    fun isInternetOn(activity: Activity, fragment: Fragment?, requestCode: Int): Boolean {

        var flag = false
        // get Connectivity Manager object to check connection
        val connec = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.CONNECTED ||
            connec.getNetworkInfo(1).state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(1).state == android.net.NetworkInfo.State.CONNECTED
        ) {

            flag = true

        } else if (connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(
                1
            ).state == android.net.NetworkInfo.State.DISCONNECTED
        ) {

            dialogInternet(activity, fragment, requestCode)
            flag = false
        }
        return flag
    }

    private fun dialogInternet(activity: Activity, fragment: Fragment?, requestCode: Int) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()

        val ad = AlertDialog.Builder(activity)
        ad.setTitle(activity.getString(R.string.noConnection))
        ad.setMessage(activity.getString(R.string.turnOnInternet))
        //        ad.setCancelable(false);
        ad.setNegativeButton(activity.getString(R.string.mobileData)) { dialog, which ->
            val i = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        ad.setPositiveButton(activity.getString(R.string.wifi)) { dialog, which ->
            val i = Intent(Settings.ACTION_WIFI_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        dialog = ad.show()
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.currentFocus != null && activity.currentFocus?.windowToken != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            } catch (ignored: NullPointerException) {
                printLog("null", "null")
            }
        }
    }

    fun loadImageCrop(url: String?, imageView: ImageView, placeHolder: Int, targetHeight: Int, targetWidth: Int) {
        //        System.out.println("IMAGE URL IS= " + url);
        if (url != null && !url.isEmpty()) {
           /* Picasso.get().load(IMAGE_URL + url).resize(targetWidth, targetHeight).centerCrop().placeholder(placeHolder)
                .into(imageView)*/
        } else {
            /*Picasso.get().load(placeHolder).resize(targetWidth, targetHeight).centerCrop().into(imageView)*/
        }
    }

    fun loadImageInside(url: String?, imageView: ImageView, placeHolder: Int, targetHeight: Int, targetWidth: Int) {
        //        System.out.println("IMAGE URL IS= " + url);
        if (url != null && !url.isEmpty() && !url.equals("null", ignoreCase = true)) {
           /* Picasso.get().load(IMAGE_URL + url).resize(targetWidth, targetHeight).centerInside()
                .placeholder(placeHolder).into(imageView)*/
        } else {
            /*Picasso.get().load(placeHolder).resize(targetWidth, targetHeight).centerInside().into(imageView)*/
        }
    }

    fun setText(textView: TextView, text: String?) {
        if (text != null && !text.equals("null", ignoreCase = true)) {
            textView.text = text
        } else {
            textView.text = "N/A"
        }
    }

    fun setText(textView: TextView, text: String?, defaultText: String) {
        if (text != null && !text.isEmpty() && !text.equals("null", ignoreCase = true)) {
            textView.text = text
        } else {
            textView.text = defaultText
        }
    }

    fun setText(textView: EditText, text: String?) {
        if (text != null && !text.equals("null", ignoreCase = true)) {
            textView.setText(text)
        } else {
            textView.setText("")
        }
    }

    fun setText(textView: EditText, text: String?, defaultText: String) {
        if (text != null && !text.equals("null", ignoreCase = true)) {
            textView.setText(text)
        } else {
            textView.setText(defaultText)
        }
    }

    fun setText(textView: EditText, text: Double?) {
        if (text != null) {
            textView.setText(text.toString())
        } else {
            textView.setText("")
        }
    }


    fun setTexts(textView: TextView, vararg texts: String) {
        var text = ""
        for (test in texts) {
            if (!test.equals("null", ignoreCase = true))
                text += test
            else
                text = ""
        }

        text = text.replace(", ,".toRegex(), ",")
        textView.text = Html.fromHtml(text.trim { it <= ' ' })
    }


    fun setTexts(textView: TextView, separator: String, vararg texts: String?) {
        var text = ""
        for (test in texts) {
            text += if (test != null && !test.equals("null", ignoreCase = true) && !test.isEmpty())
                test
            else
                ""
        }

        if (!separator.equals("", ignoreCase = true))
            text = text.replace((separator + separator).toRegex(), separator)
        textView.text = Html.fromHtml(text.trim { it <= ' ' })
    }

    fun convertToString(value: Any): Any {
        return if (value == 0 || value == 0.0 || value == 0f)
            ""
        else
            value
    }

    fun convertDateFormat(
        dateTimeString: String?,
        originalFormat: SimpleDateFormat,
        targetFormat: SimpleDateFormat
    ): String {
        if (dateTimeString == null || dateTimeString.equals("null", ignoreCase = true))
            return ""

        var targetDateString = dateTimeString
        try {
            val originalDate = originalFormat.parse(dateTimeString)
            val originalDateString = originalFormat.format(originalDate)
            val targetDate = originalFormat.parse(originalDateString)
            targetDateString = targetFormat.format(targetDate)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return targetDateString!!
        }
    }

    fun roundMathValueFromDouble(value: Double): Double {
        var result = 0.0
        result = Math.round(value * 100).toDouble() / 100
        return result
    }

    fun roundMathValueFromString(value: String): Double {
        var result = 0.0
        result = Math.round(value.toDouble() * 100).toDouble() / 100
        return result
    }


//    fun convertDateFormat(dateTimeString: String?, originalFormat: SimpleDateFormat, targetFormat: SimpleDateFormat, defaultValue: String): String {
//        if (dateTimeString == null || dateTimeString.equals("null", ignoreCase = true))
//            return defaultValue
//
//        var displayDateString: String = dateTimeString
//        originalFormat.timeZone = TimeZone.getTimeZone("UTC")
//        try {
//            val date = originalFormat.parse(dateTimeString)
//            originalFormat.timeZone = TimeZone.getDefault()
//            val dateWallet = originalFormat.format(date)
//            val date1 = originalFormat.parse(dateWallet)
//            displayDateString = targetFormat.format(date1)
//            originalFormat.timeZone = TimeZone.getDefault()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            return displayDateString
//        }
//    }

    fun generateHashKey(texts: ArrayList<String>): String {
        var hashKey = ""
        for (i in texts) {
            if (texts.indexOf(i) != 0) {
                if (i != null && !i.equals("null", ignoreCase = true) && !i.equals("")) {
                    if (!hashKey.equals(""))
                        hashKey += "|"
                    hashKey += i
                }
            } else {
                var value: String = i;
                if (value == null || value.equals("null", ignoreCase = true)) {
                    value = "";
                }
                hashKey += "|"
                hashKey += value
            }
        }
        hashKey = hashKey.substring(1)
        var md: MessageDigest? = null
        val sb = StringBuilder()
        var digest: ByteArray? = null
        val HEX_CHARS = "0123456789ABCDEF"
        try {
            md = MessageDigest.getInstance("SHA-512")
            digest = md!!.digest(hashKey.toByteArray())
            digest.forEach {
                val i = it.toInt()
                sb.append(HEX_CHARS[i shr 4 and 0x0f])
                sb.append(HEX_CHARS[i and 0x0f])
            }
//            println("SHA512 : " + sb.toString())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        val test: String = enCodeBase64(sb.toString().toUpperCase())
//        println("BASE 64 : $test")
        return test
    }

    fun enCodeBase64(text: String): String {
        // Sending side
        var data = ByteArray(0)
        try {
            data = text.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return Base64.encodeToString(data, Base64.DEFAULT).replace("\n", "").replace("\r", "")
    }

    fun printHashKey(context: Context) {
        // Add code to print out the key hash
        try {
            val info = context.packageManager.getPackageInfo(
                context.applicationInfo.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                printLog("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.DONUT) {
            val runningProcesses = am.runningAppProcesses
            try {
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }

        } else {
            try {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo?.packageName == context.packageName) {
                    isInBackground = false
                }
            } catch (e: Exception) {
            }

        }
        return isInBackground
    }


    fun convertDoubleToString(doubleValue:Double):String{

        return String.format("%.0f", doubleValue)

    }

    fun setPicassoImage(url: String?,view: ImageView,placeHolder: Int){

        Picasso.get().load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .placeholder(placeHolder)
            .into(view,object:Callback{
                override fun onSuccess() {
                    Picasso.get().load(url).placeholder(placeHolder).into(view)
                }
                override fun onError(e: java.lang.Exception?) {
                    Picasso.get().load(url).placeholder(placeHolder).into(view)

                }
            })
    }

}
