package com.htf.kidzoon.utils

import android.app.Application
import android.content.Context
import com.htf.kidzoon.Room.AppDatabase


class MyApplication : Application() {
    /*private var sAnalytics: GoogleAnalytics? = null
    private var sTracker: Tracker? = null*/

    override fun onCreate() {
        super.onCreate()
        instance = this
        setAppContext(this)
        AppDatabase.getAppDataBase(this)
    }


    companion object {
        private lateinit var instance: MyApplication
        private lateinit var mAppContext: Context

        fun getInstance(): MyApplication {
            return instance
        }

        fun getAppContext(): Context {
            return mAppContext
        }

        fun setAppContext(mAppContext: Context) {
            Companion.mAppContext = mAppContext
        }
    }
}