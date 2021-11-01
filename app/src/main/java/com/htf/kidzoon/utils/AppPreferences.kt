package com.htf.htfnew.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.htf.kidzoon.R
import com.htf.kidzoon.models.City
import com.htf.kidzoon.models.Filter
import com.htf.kidzoon.models.User
import com.htf.kidzoon.utils.Constants.KEY_CITY_DETAILS
import com.htf.kidzoon.utils.Constants.KEY_FILTER_DETAILS
import com.htf.kidzoon.utils.Constants.KEY_USER_JSON_DETAILS


class AppPreferences {

    companion object {

        private var mInstance: AppPreferences? = null
        private var mPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        fun getInstance(context: Context): AppPreferences {
            if (mInstance == null) {
                mInstance = AppPreferences()
            }
            if (mPreferences == null) {
                mPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                mEditor = mPreferences!!.edit()
            }
            return mInstance as AppPreferences
        }

    }

    fun clearPreference(key: String) {
        val fcmToken = getFromPreference(key)
        mEditor!!.clear()
        saveInPreference(key, fcmToken)
    }

    fun saveInPreference(key: String, value: String) {
        mEditor!!.putString(key, value)
        mEditor!!.commit()
    }


    fun getFromPreference(key: String): String {
        return mPreferences!!.getString(key, "")!!
    }

    fun getExpireTimeFromPreference(key: String): Int {
        return mPreferences!!.getInt(key, 0)
    }


    fun clareExpireTimeFromPreference(key: String){
        mEditor!!.putInt(key, 0)
        mEditor!!.commit()
    }


    fun clearFromPref(key: String) {
        mEditor!!.putString(key, "")
        mEditor!!.commit()
    }

    fun saveInPreference(key: String, value: Boolean) {
        mEditor!!.putBoolean(key, value)
        mEditor!!.commit()
    }

    fun saveInPreference(key: String, value: Int) {
        mEditor!!.putInt(key, value)
        mEditor!!.commit()
    }

    fun getBooleanFromPreference(key: String): Boolean {
        return mPreferences!!.getBoolean(key, false)
    }

    fun saveCityList(data: ArrayList<City>) {
        mEditor!!.putString("city", Gson().toJson(data))
        mEditor!!.commit()
    }


    fun getCityList(): ArrayList<City>? {
        val json = mPreferences!!.getString("city", "")
        var list: ArrayList<City>? = null
        if (!json.isNullOrEmpty()) {
            list =
                Gson().fromJson<ArrayList<City>>(json, object : TypeToken<ArrayList<City>>() {}.type)
        }
        return list
    }
    // -------------User PREFRENCES Details--------
    fun saveUserDetails(user: User) {
        mEditor!!.putString(KEY_USER_JSON_DETAILS, Gson().toJson(user))
        mEditor!!.commit()
    }
    fun getUserDetails(): User? {
        val userJson = mPreferences!!.getString(KEY_USER_JSON_DETAILS, "")
        var user: User? = null
        if (userJson != null && userJson != "") {
            user = Gson().fromJson(userJson, User::class.java)
        }
        return user
    }

    fun clearUserDetails() {
        mEditor!!.putString(KEY_USER_JSON_DETAILS, "")
        mEditor!!.commit()
    }


    fun isAutoLogin(): Boolean {
        val userJson = mPreferences!!.getString(KEY_USER_JSON_DETAILS, "")
        var user: User? = null
        if (userJson != null && userJson != "") {
            user = Gson().fromJson(userJson, User::class.java)
            return user != null
        } else {
            return false
        }
    }


    //-----------Save City----------------

    fun saveCity(city: City) {
        mEditor!!.putString(KEY_CITY_DETAILS, Gson().toJson(city))
        mEditor!!.commit()
    }

    fun getCity(): City? {
        val cityJson = mPreferences!!.getString(KEY_CITY_DETAILS, "")
        var city: City? = null
        if (cityJson != null && cityJson != "") {
            city = Gson().fromJson(cityJson, City::class.java)
        }
        return city
    }


    fun clearCity() {
        mEditor!!.putString(KEY_CITY_DETAILS, "")
        mEditor!!.commit()
    }

    fun saveFilter(filter: Filter){
        mEditor!!.putString(KEY_FILTER_DETAILS, Gson().toJson(filter))
        mEditor!!.commit()

    }

    fun getFilter(): Filter? {
        val filterJson = mPreferences!!.getString(KEY_FILTER_DETAILS, "")
        var filter: Filter? = null
        if (filterJson != null && filterJson != "") {
            filter = Gson().fromJson(filterJson, Filter::class.java)
        }
        return filter
    }

    fun clearFilter(){
        mEditor!!.putString(KEY_FILTER_DETAILS, "")
        mEditor!!.commit()
    }

    //Save Total Cart Count

  /*  fun saveTotalCount(key: String, total: Int) {
        mEditor!!.putInt(key, total)
        mEditor!!.commit()
    }

    fun getTotalCount(key: String): Int? {
        return mPreferences!!.getInt(key, 0)
    }

    //SAVE CMS DATA

    fun saveCMSDetails(key: String, cms: CMS) {
        mEditor!!.putString(key, Gson().toJson(cms))
        mEditor!!.commit()
    }

    fun getCMSDetails(key: String): CMS? {
        val cmsJSOn = mPreferences!!.getString(key, "")
        var cms: CMS? = null
        if (cmsJSOn != null && cmsJSOn != "") {
            cms = Gson().fromJson(cmsJSOn, CMS::class.java)
        }
        return cms
    }


    fun clearCMSDetails(key: String) {
        mEditor!!.putString(key, "")
        mEditor!!.commit()
    }

    fun saveBannerList(data: ArrayList<Banner>) {
        mEditor!!.putString("banner", Gson().toJson(data))
        mEditor!!.commit()
    }

    fun getBannerList(): ArrayList<Banner>? {
        val json = mPreferences!!.getString("banner", "")
        var list: ArrayList<Banner>? = null
        if (!json.isNullOrEmpty()) {
            list =
                Gson().fromJson<ArrayList<Banner>>(json, object : TypeToken<ArrayList<Banner>>() {}.type)
        }
        return list
    }

    fun clearBanner() {
        mEditor!!.putString("banner", "")
        mEditor!!.commit()
    }

    //Save Category List
    fun saveCategoryList(data: ArrayList<Category>) {
        mEditor!!.putString("category", Gson().toJson(data))
        mEditor!!.commit()
    }

    fun getCategoryList(): ArrayList<Category>? {
        val json = mPreferences!!.getString("category", "")
        var list: ArrayList<Category>? = null
        if (!json.isNullOrEmpty()) {
            list =
                Gson().fromJson<ArrayList<Category>>(json, object : TypeToken<ArrayList<Category>>() {}.type)
        }
        return list
    }

    fun clearCategory() {
        mEditor!!.putString("category", "")
        mEditor!!.commit()
    }


    fun saveCountryList(data: ArrayList<Country>) {
        mEditor!!.putString(KEY_COUNTRY_JSON, Gson().toJson(data))
        mEditor!!.commit()
    }

    fun getCountryList(): ArrayList<Country>? {
        val countryJson = mPreferences!!.getString(KEY_COUNTRY_JSON, "")
        var countryList: ArrayList<Country>? = null
        if (!countryJson.isNullOrEmpty()) {
            countryList =
                Gson().fromJson<ArrayList<Country>>(countryJson, object : TypeToken<ArrayList<Country>>() {}.type)
        }
        return countryList
    }

    fun clearCountryDetails(key: String) {
        mEditor!!.putString(key, "")
        mEditor!!.commit()
    }

    fun saveFirstTimeFlag(key:String,value:Boolean){
        mEditor!!.putBoolean(key,value)
        mEditor!!.commit()
    }

    fun clearFirstTimeFlag(key:String,value:Boolean){

        mEditor!!.putBoolean(key,value)
        mEditor!!.commit()
    }

    fun getFirstTimeFlag(key:String):Boolean{
        val flag = mPreferences!!.getBoolean(key,true)
        return flag
    }
*/
}