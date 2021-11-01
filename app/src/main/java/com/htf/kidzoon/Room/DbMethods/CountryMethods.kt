package com.htf.kidzoon.Room.DbMethods

import android.annotation.SuppressLint
import android.util.Log
import com.htf.kidzoon.Room.AppDatabase
import com.htf.kidzoon.Room.Listeners.CountryListener
import com.htf.kidzoon.models.Country
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object CountryMethods {
    fun insertCountryList(db: AppDatabase, countryList:List<Country>){
        Observable.fromCallable {
            val countryDao=db.countryDao()
            with(countryDao){
                this.insert(countryList)
            }
        }.doOnNext{

        }.doOnComplete {
           //on complete listener
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getAllCountries(disposable:CompositeDisposable,db:AppDatabase, listener:CountryListener){
        disposable.add(
            db.countryDao().all().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ?.subscribe{t->
                    when {
                        t.isNotEmpty() -> {
                            listener.onGetAllCountries(t)
                        }
                        else -> {
                            listener.onGetAllCountries(null)
                        }
                    }
                } !!)
    }

    fun deleteCountry(db:AppDatabase){
        Completable.fromAction {
            db.countryDao().delete()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
            .subscribe()
    }



}