package com.htf.kidzoon.Room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.htf.kidzoon.models.Country
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(countries: List<Country>)

    @Query("Select * from tbl_country")
    fun all(): Flowable<List<Country>>

    @Query("DELETE FROM tbl_country")
    fun delete()

}